package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

public class P5TypeCheckingVisitor implements Visitor {

    private final GlobalADT global;
    private final int maxErrors;
    private ADT st;
    private final LinkedList<String> errors;
    public P5TypeCheckingVisitor(GlobalADT global) {
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
            //visit main class
            n.m.accept(this);
            //visit all the classes
            for (int i = 0; i < n.cl.size(); i++) {
                n.cl.get(i).accept(this);
            }
        }
        catch (IllegalStateException e) {
            //do nothing, just used to jump out of recursion when we hit max allowable errors
        }
        // for(String s : this.errors) {
        //     System.err.println(s);
        // }
    }

    @Override
    public void visit(MainClass n) {
        //set current symbol table/scope to current class
        st = global.get(MethodADT.MAIN_METHOD_NAME);
        //annotate class
        n.type = st;
        // ! Ignores n.i2.s (String[] args) entirely
        //set scope to main method
        st = ((ClassADT)st).getMethod(MethodADT.MAIN_METHOD_NAME);
        //visit main method body
        n.s.accept(this);
        //fix scope
        st = global;
    }

    @Override
    public void visit(ClassDeclSimple n) {
        //set current symbol table/scope to current class
        st = global.get(n.i.s);
        //annotate class
        n.type = st;
        // visit instance vars
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        //visit methods
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        st = st.prev;
    }

    @Override
    public void visit(ClassDeclExtends n) {
        //same code as regular class decl
        //set current symbol table/scope to current class
        st = global.get(n.i.s);
        //annotate class
        n.type = st;
        // visit instance vars
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        //visit methods
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        st = st.prev;
    }

    @Override
    public void visit(VarDecl n) {
        //visit type
        n.t.accept(this);
        //annotate identifier with the type it's declared to be
        //Note: Doesn't visit identifier AST Node (since it should be leaf/end of statement in var decl)
        n.i.type = n.type;
        //leave var decl node with undefined type
    }

    @Override
    public void visit(MethodDecl n) {
        //set scope to method table
        st = ((ClassADT)st).getMethod(n.i.s);
        //annotate method and methodID with signature
        n.type = st;
        n.i.type = st;
        //visit return type
        n.t.accept(this);
        //visit parameters/formals
        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.get(i).accept(this);
        }
        //visit local var decls
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        //visit statements
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }
        //visit return expression
        n.e.accept(this);

        //set table back
        st = st.prev;

    }

    @Override
    public void visit(Formal n) {
        //visit type
        n.t.accept(this);
        //set formal type to same type
        n.type = n.t.type;
        //set id to same type
        n.i.type = n.t.type;
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
        //set to correct class type
        n.type = global.get(n.s);
        //check if null (ie class doesn't exist), and set to undefined if class doesn't exist
        if(n.type == null) {
            n.type = UndefinedADT.UNDEFINED;
            addError("Class " + n.s + " is not defined. Error on line " + n.line_number);
        }
    }

    @Override
    public void visit(Block n) {
        //leave block with undefined type
        //visit each statement inside statement list
        for(int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }
    }

    @Override
    public void visit(If n) {
        //leave "if" node with undefined type
        //visit condition
        n.e.accept(this);
        //typecheck condition
        if(!BaseADT.BOOLEAN.equals(n.e.type)) {
            addError("Invalid type, condition of if, has type " + n.e.type.toString() + " but must be boolean. In line " + n.e.line_number);
        }
        //visit if body
        n.s1.accept(this);
        //visit else body
        n.s2.accept(this);
    }

    @Override
    public void visit(While n) {
        //annotate condition of while
        n.e.accept(this);
        //typecheck condition
        if(!BaseADT.BOOLEAN.equals(n.e.type)) {
            addError("Invalid type, condition of while, has type " + n.e.type.toString() + " but must be boolean. In line " + n.e.line_number);
        }
        //annotate body of while statement
        n.s.accept(this);
    }

    @Override
    public void visit(Print n) {
        //annotate expression inside of print statement
        n.e.accept(this);
        if(!(BaseADT.INT.same(n.e.type) || BaseADT.BOOLEAN.same(n.e.type))) {
            addError("Expression inside print statement has type " + n.e.type.toString() + " but must be int or boolean. On line " + n.line_number);
        }
    }

    @Override
    public void visit(Assign n) {
        //visit left side of assign
       n.i.accept(this);
       //visit right side
        n.e.accept(this);
        //typecheck
        if(!n.i.type.assignable(n.e.type)) {
            addError("Expression of type " + n.e.type.toString() + " is not assignable to variable " + n.i.toString() + " which has type " + n.i.type.toString() + ". In line " + n.line_number);

        }
        //leave assign type as undefined
    }

    @Override
    public void visit(ArrayAssign n) {
        //visit array id (should be array)
        n.i.accept(this);
        if(!BaseADT.INT_ARRAY.equals(n.i.type)) {
            addError("Invalid type, is " + n.i.type.toString() + " but must be an int array. In line " + n.i.line_number);
        }
        //visit index expression (should be int)
        n.e1.accept(this);
        if(!BaseADT.INT.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }
        //visit right side of equals exp (again should be int)
        n.e2.accept(this);
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
        //leave assign type as undefined
    }

    @Override
    public void visit(And n) {
        n.type = BaseADT.BOOLEAN;
        n.e1.accept(this);
        //typecheck
        if(!BaseADT.BOOLEAN.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " but must be a boolean. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        //typecheck
        if(!BaseADT.BOOLEAN.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " but must be a boolean. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(LessThan n) {
        n.type = BaseADT.BOOLEAN;
        n.e1.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Plus n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Minus n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(Times n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " but must be an int. In line " + n.e1.line_number);
        }
        n.e2.accept(this);
        //typecheck
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(ArrayLookup n) {
        //give it type int
        n.type = BaseADT.INT;
        //annotate left expression
        n.e1.accept(this);
        //typecheck left expression
        if(!BaseADT.INT_ARRAY.equals(n.e1.type)) {
            addError("Invalid type, is " + n.e1.type.toString() + " but must be an array. In line " + n.e1.line_number);
        }
        //annotate right expression (should be int)
        n.e2.accept(this);
        //typecheck right expression
        if(!BaseADT.INT.equals(n.e2.type)) {
            addError("Invalid type, is " + n.e2.type.toString() + " but must be an int. In line " + n.e2.line_number);
        }
    }

    @Override
    public void visit(ArrayLength n) {
        n.type = BaseADT.INT;
        n.e.accept(this);
        if(!BaseADT.INT_ARRAY.equals(n.e.type)) {
            addError("Invalid type, is " + n.e.type.toString() + " but must be an array. In line " + n.line_number);
        }
    }

    @Override
    public void visit(Call n) {
        //visit e in e.i(el)
        n.e.accept(this);
        //check that e evaluates to a class ADT
        if(!(n.e.type instanceof ClassADT)) {
            //if not throw error
            addError("Invalid type, is" + n.e.toString() + " but must be an instance of a class. In line " + n.line_number);
            //set type to undefined since we don't know what the method returns
            n.type = UndefinedADT.UNDEFINED;
            //return since we can't really typecheck method if we don't know which class it is for
            return;
        }
        //set scope for identifier visit
        ADT tmp = st;
        st = global.get(((ClassADT)n.e.type).name);
        //annotate i in e.i(el) (without using visitor pattern)
        n.i.type = searchForMethod(n.i.s);
        //fix scope
        st = tmp;
        //check that we found a method with that name (ie it isn't undefined)
        if(!(n.i.type instanceof MethodADT)) {
            //if not throw error
            addError("No method " + n.i.s + " for " + ((ClassADT) n.e.type).name + ". Line " + n.line_number);
            //set type to undefined since we don't know what the method returns
            n.type = UndefinedADT.UNDEFINED;
            //return since we can't checl params if it isn't methodADT
            return;
        }
        //check number of parameters is correct
        if(n.el.size() != ((MethodADT) n.i.type).paramTypes.size()) {
            addError("Incorrect number of parameters on method call line " + n.line_number);
        }
        //visit all parameters
        for(int i = 0; i < Math.min(n.el.size(), ((MethodADT) n.i.type).paramTypes.size()) ; i++) {
            //passed in parameter exp node from exp list
            Exp tmpParm = n.el.get(i);
            tmpParm.accept(this);
            //correct type of param according to method signature
            ADT parmCorrectType = ((MethodADT)n.i.type).paramTypes.get(i);
            if(!parmCorrectType.assignable(tmpParm.type)) {
                addError("Incorrect parameter type in method call. Expected type " + parmCorrectType.toString() + " but was " + tmpParm.type.toString() + " In line number " + n.line_number);
            }
        }
        //give the call the return type of the method, cast works since we checked above that n.i.type is MethodADT
        n.type = ((MethodADT)n.i.type).returnType;
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
    public void visit(IdentifierExp n) {
        n.type = searchForVar(n.s, n.s + " isn't declared. In line " + n.line_number);
    }

    @Override
    public void visit(This n) {
        //set type of 'this' to the class it is used in or if it's outside of scope somehow to undefined
        if (st instanceof MethodADT m) {
            n.type = m.getClassADT();
        }
         else {
            n.type = UndefinedADT.UNDEFINED;
            addError("'This' is undefined in line " + n.line_number);
        }
    }

    @Override
    public void visit(NewArray n) {
        //visit expression inside brackets (should be int)
        n.e.accept(this);
        //type check expression inside of brackets(should be int)
        if(!BaseADT.INT.equals(n.e.type)) {
            addError("Invalid type, is  " + n.e.type.toString() + " but must have type int. In line " + n.e.line_number);
        }
        //mark node as int array type
        n.type = BaseADT.INT_ARRAY;
    }

    @Override
    public void visit(NewObject n) {
        //annotate identifier with type of the class
        n.i.type = global.get(n.i.s);
        //check that classADT returned by global sm isn't null (ie the class exists)
        if(n.i.type != null) {
            n.type = n.i.type;
        } else {
            n.type = UndefinedADT.UNDEFINED;
            n.i.type = UndefinedADT.UNDEFINED;
            addError(n.i.toString() + " has type " + n.i.type.toString() + " but must be a class. In line " + n.i.line_number);
        }
    }

    @Override
    public void visit(Not n) {
        n.type = BaseADT.BOOLEAN;
        n.e.accept(this);
        if(!BaseADT.BOOLEAN.equals(n.e.type)) {
            addError("Invalid type, is " + n.e.type.toString() + " but must have type Boolean. In line " + n.e.line_number);
        }
    }

    @Override
    public void visit(Identifier n) {
        n.type = searchForVar(n.s, n.s + " isn't declared. In line " + n.line_number);
    }

    private ADT searchForMethod(String s) {
        ClassADT tmp = (ClassADT) st;
        while(tmp != null) {
            MethodADT result = tmp.getMethod(s);
            if(result != null) {
                return result;
            }
            tmp = tmp.parent;
        }
        return UndefinedADT.UNDEFINED;
    }

    private ADT searchForVar(String s, String errorMessage) {
        ADT methodScopeResult = ((MethodADT) st).get(s);
        //search method scope for var decl
        if(methodScopeResult != null) {
            return methodScopeResult;
        }
        //search class and parent classes for fields
        ClassADT tmp = ((MethodADT) st).getClassADT();
        while(tmp != null) {
            ADT result = tmp.getField(s);
            if(result != null) {
                return result;
            }
            tmp = tmp.parent;
        }
        //if not found, add to method sm as local (so we don't continue getting errors for this identifier in method scope)
        ((MethodADT) st).put(s, UndefinedADT.UNDEFINED);
        //add error to errors
        addError(errorMessage);
        return UndefinedADT.UNDEFINED;
    }

    private void addError(String s) {
        this.errors.add(s);
        if(this.errors.size() > maxErrors - 1) {
            throw new IllegalStateException();
        }
    }

}
