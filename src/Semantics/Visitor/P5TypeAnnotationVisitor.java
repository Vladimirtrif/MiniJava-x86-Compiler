package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.BaseADT;
import Semantics.ClassADT;
import Semantics.GlobalADT;
import Semantics.TableADT;

import java.util.HashSet;
import java.util.Set;

public class P5TypeAnnotationVisitor implements Visitor {

    private final GlobalADT global;
    private TableADT st;
    private boolean error;

    public P5TypeAnnotationVisitor(GlobalADT global) {
        this.global = global;
        this.st = global;
        error = false;
    }

    public boolean getError() {
        return error;
    }

    private void printError(String message) {
        error = true;
        System.out.println(message);
    }

    @Override
    public void visit(Program n) {
        //visit main class
        n.m.accept(this);
        //visit all the classes
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        //set current symbol table/scope to current class
        st = global.get(n.i1.s);
        //annotate class
        n.type = st;
        // ! Ignores n.i2.s (String[] args) entirely
        //visit main method body
        n.s.accept(this);
        st = st.prev;
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
        //same code as regular class decl, will use backup if needed later
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
        st = (TableADT) st.get(n.i.s);
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
        throw new UnsupportedOperationException("Unreachable code.");
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
        //leave if node with undefined type
        //visit condition
        n.e.accept(this);
        //visit if body
        n.s1.accept(this);
        //visit else body
        n.s2.accept(this);
    }

    @Override
    public void visit(While n) {
        //annotate condition of while
        n.e.accept(this);
        //annotate body of while statement
        n.s.accept(this);
    }

    @Override
    public void visit(Print n) {
        //annotate expression inside of print statement
        n.e.accept(this);
    }

    @Override
    public void visit(Assign n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(ArrayAssign n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(And n) {
        n.type = BaseADT.BOOLEAN;
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(LessThan n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Plus n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Minus n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Times n) {
        n.type = BaseADT.INT;
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(ArrayLookup n) {
        //give it type int
        n.type = BaseADT.INT;
        //annotate left expression (should be array int)
        n.e1.accept(this);
        //annotate right expression (should be int)
        n.e2.accept(this);
    }

    @Override
    public void visit(ArrayLength n) {
        n.type = BaseADT.INT;
        n.e.accept(this);
    }

    @Override
    public void visit(Call n) {
        throw new UnsupportedOperationException("Unreachable code.");
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
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(This n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(NewArray n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(NewObject n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Not n) {
        n.type = BaseADT.BOOLEAN;
        n.e.accept(this);
    }

    @Override
    public void visit(Identifier n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

}
