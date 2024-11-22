package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

/**
 * Note: For every expression that involves an operator,
 * if the type of the operator is well-defined (e.g., return type of PLUS is always an integer),
 * we take that type even if the operands are undefined
 */
public class P6TypeCheckingVisitor implements Visitor {

    private final GlobalADT global;
    private final int maxErrors;
    private ADT st;
    private final LinkedList<String> errors;
    public P6TypeCheckingVisitor(GlobalADT global) {
        this.global = global;
        this.st = global;
        this.errors = new LinkedList<>();
        this.maxErrors = 15;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void visit(Program n) {
        try {
            n.m.accept(this);
            for (int i = 0; i < n.cl.size(); i++) {
                n.cl.get(i).accept(this);
            }
        } catch (IllegalStateException e) {
            // do nothing, just used to jump out of recursion when we hit max allowable errors
        }
    }

    @Override
    public void visit(MainClass n) {
        // ! Ignores n.i2.s (String[] args) entirely
        n.type = n.i1.type = st = global.get(ADT.MAIN_NAME);
        st = ((ClassADT) st).getMethod(ADT.MAIN_NAME);
        n.s.accept(this);
        st = global;
    }

    @Override
    public void visit(ClassDeclSimple n) {  // class i, fields vl, methods ml
        n.i.type = n.type = global.get(n.i.s);
        st = n.i.type;

        // visit fields
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        // visit methods
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        st = st.prev;
    }

    @Override
    public void visit(ClassDeclExtends n) { // class i extends j, fields vl, methods ml
        n.i.type = n.type = global.get(n.i.s);
        n.j.type = global.get(n.j.s);
        st = n.i.type;

        // visit fields
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        // visit methods
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        st = st.prev;
    }

    @Override
    public void visit(VarDecl n) {
        n.t.accept(this);   // n.t.type != null
        n.i.type = n.type = n.t.type;
    }

    @Override
    public void visit(MethodDecl n) {
        if (!(st instanceof ClassADT c)) { throw new AssertionError(); }

        st = c.getMethod(n.i.s);
        n.type = n.i.type = st;

        // visit return type
        n.t.accept(this);

        // visit params
        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.get(i).accept(this);
        }

        // visit locals
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }

        // visit stmts
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }

        // visit return exp
        n.e.accept(this);

        st = st.prev;
    }

    @Override
    public void visit(Formal n) {
        n.t.accept(this);   // n.t.type != null
        n.type = n.i.type = n.t.type;
    }

    @Override
    public void visit(IntArrayType n) {
        n.type = BaseADT.INT_ARRAY;
    }

    @Override
    public void visit(BooleanType n) {
        n.type = BaseADT.BOOLEAN;
    }

    @Override
    public void visit(IntegerType n) {
        n.type = BaseADT.INT;
    }

    @Override
    public void visit(IdentifierType n) {
        n.type = global.getOrDeclare(n.s);  // ClassADT, Undefined, or null
        if (n.type == null) {
            n.type = UndefinedADT.UNDEFINED;
            addError("Class " + n.s + " is not defined. Error on line " + n.line_number);
        }
    }

    @Override
    public void visit(Block n) {    // {sl[0]; sl[1]; ...}
        n.type = null;
        for(int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }
    }

    @Override
    public void visit(If n) {   // if (e) s1; else s2;
        n.type = null;
        n.e.accept(this);
        if (!BaseADT.BOOLEAN.same(n.e.type)) {
            addError("TypeError: condition of if, has type " + n.e.type + " but must be boolean. In line " + n.e.line_number);
        }
        n.s1.accept(this);
        n.s2.accept(this);
    }

    @Override
    public void visit(While n) {    // while (e) s;
        n.type = null;
        n.e.accept(this);
        if(!BaseADT.BOOLEAN.same(n.e.type)) {
            addError("TypeError: condition of while, has type " + n.e.type.toString() + " but must be boolean. In line " + n.e.line_number);
        }
        n.s.accept(this);
    }

    @Override
    public void visit(Print n) {    // System.out.println(e);
        n.type = null;
        n.e.accept(this);
        if(!(BaseADT.INT.same(n.e.type) || BaseADT.BOOLEAN.same(n.e.type))) {
            addError("Expression inside print statement has type " + n.e.type.toString() + " but must be int or boolean. On line " + n.line_number);
        }
    }

    @Override
    public void visit(Assign n) {   // i = e;
        n.type = null;
        n.i.accept(this);
        n.e.accept(this);
        if (!n.e.type.assignable(n.i.type)) {
            addError("Expression of type " + n.e.type.toString() + " is not assignable to variable " + n.i.toString() + " which has type " + n.i.type.toString() + ". In line " + n.line_number);
        }
    }

    @Override
    public void visit(ArrayAssign n) {  // i[e1] = e2;
        n.type = null;

        // visit i (int array)
        n.i.accept(this);
        if (!BaseADT.INT_ARRAY.same(n.i.type)) {
            addError("TypeError: is " + n.i.type.toString() + " but must be an int array. In line " + n.i.line_number);
            // note: we don't fix i's type here (why?)
        }

        // visit e1 (int)
        n.e1.accept(this);
        if (!BaseADT.INT.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }

        // visit e2 (int)
        n.e2.accept(this);
        if (!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(And n) {  // e1 && e2
        n.type = BaseADT.BOOLEAN;
        n.e1.accept(this);
        if (!BaseADT.BOOLEAN.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " but must be a boolean. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if (!BaseADT.BOOLEAN.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " but must be a boolean. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(LessThan n) { // e1 < e2
        n.type = BaseADT.BOOLEAN;
        n.e1.accept(this);
        if (!BaseADT.INT.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if (!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Plus n) { // e1 + e2
        n.type = BaseADT.INT;
        n.e1.accept(this);
        if(!BaseADT.INT.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if(!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Minus n) {    // e1 - e2
        n.type = BaseADT.INT;
        n.e1.accept(this);
        if (!BaseADT.INT.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if (!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Times n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        if (!BaseADT.INT.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if (!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(ArrayLookup n) {  // e1[e2]
        n.type = BaseADT.INT;
        n.e1.accept(this);
        if (!BaseADT.INT_ARRAY.same(n.e1.type)) {
            addError("TypeError: is " + n.e1.type.toString() + " but must be an array. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        if (!BaseADT.INT.same(n.e2.type)) {
            addError("TypeError: is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(ArrayLength n) {
        n.type = BaseADT.INT;
        n.e.accept(this);
        if(!BaseADT.INT_ARRAY.same(n.e.type)) {
            addError("TypeError: is " + n.e.type.toString() + " but must be an array. In line " + n.line_number);
        }
    }

    @Override
    public void visit(Call n) { // e.i(el)
        n.e.accept(this);  // note: n.e.type != null

        // if e is not ClassADT
        if (!(n.e.type instanceof ClassADT)) {
            // throw error (unless e is undefined, whose error should've been handled in "n.e.accept(this)")
            if (!(n.e.type instanceof UndefinedADT)) {
                addError("TypeError: is" + n.e.toString() + " but must be an instance of a class. In line " + n.line_number);
            }
            n.type = UndefinedADT.UNDEFINED;    // set n to undefined
            return;
        }
        ClassADT e = (ClassADT) n.e.type;   // now we know e is ClassADT

        n.i.type = e.deepgetMethodOrDeclare(n.i.s); // MethodADT, Undefined, or null
        // if i is not MethodADT
        if (!(n.i.type instanceof MethodADT)) {
            // throw error (unless i is undefined, whose error should've been handled during previous call to e.i)
            if (n.i.type == null) {
                addError("No method " + n.i.s + " for " + ((ClassADT) n.e.type).name + ". Line " + n.line_number);
            }
            n.type = UndefinedADT.UNDEFINED;    // set n to undefined
            return;
        }
        MethodADT i = (MethodADT) n.i.type; // now we know i is MethodADT

        n.type = i.returnType;  // important!!

        // does num params match num args?
        if (n.el.size() !=  i.numParams) {
            addError("Incorrect number of arguments on method call line " + n.line_number);
            return;
        }

        // visit all args
        for (int k = 0; k < i.numParams; k++) {
            Exp arg = n.el.get(k);
            arg.accept(this);
            ADT paramType = i.paramTypes.get(k);    // correct type according to signature
            if(!arg.type.assignable(paramType)) {
                addError("TypeError: Incorrect argument type in method call. Expected type " + paramType + " but was " + arg.type + " In line number " + n.line_number);
            }
        }

    }

    @Override
    public void visit(IntegerLiteral n) {
        n.type = BaseADT.INT;
    }

    @Override
    public void visit(True n) {
        n.type = BaseADT.BOOLEAN;
    }

    @Override
    public void visit(False n) {
        n.type = BaseADT.BOOLEAN;
    }

    @Override
    public void visit(This n) {
        // set type of 'this' to the class it is used in or if it's outside of scope somehow to undefined
        if (st instanceof MethodADT m) {
            n.type = m.getClassADT();
        } else {
            n.type = UndefinedADT.UNDEFINED;
            addError("'This' is undefined in line " + n.line_number);
        }
    }

    @Override
    public void visit(NewArray n) { // new int[e]
        n.e.accept(this);
        if(!BaseADT.INT.same(n.e.type)) {
            addError("TypeError: is  " + n.e.type.toString() + " but must have type int. In line " + n.e.line_number);
        }
        n.type = BaseADT.INT_ARRAY;
    }

    @Override
    public void visit(NewObject n) {    // new i()
        ADT i = global.getOrDeclare(n.i.s); // either ClassADT, Undefined, or null
        if (i != null) {
            n.type = n.i.type = i;
        } else {
            addError(n.i.toString() + " has type " + n.i.type.toString() + " but must be a class. In line " + n.i.line_number);
            n.type = n.i.type = UndefinedADT.UNDEFINED;
        }
    }

    @Override
    public void visit(Not n) {  // (!e)
        n.e.accept(this);
        if(!BaseADT.BOOLEAN.same(n.e.type)) {
            addError("TypeError: is " + n.e.type + " but must have type Boolean. In line " + n.e.line_number);
        }
        n.type = BaseADT.BOOLEAN;
    }

    @Override
    public void visit(IdentifierExp n) {
        if (!(st instanceof MethodADT m)) { throw new AssertionError(); }
        ADT t = m.deepgetVarOrDeclare(n.s);
        if (t == null) {
            addError(n.s + " isn't declared. In line " + n.line_number);
            n.type = UndefinedADT.UNDEFINED;
        }
    }

    @Override
    public void visit(Identifier n) {
        if (!(st instanceof MethodADT m)) { throw new AssertionError(); }
        ADT t = m.deepgetVarOrDeclare(n.s);
        if (t == null) {
            addError(n.s + " isn't declared. In line " + n.line_number);
            n.type = UndefinedADT.UNDEFINED;
        }
    }

    private void addError(String s) {
        this.errors.add(s);
        if (this.errors.size() > maxErrors - 1) {
            throw new IllegalStateException();
        }
    }

}
