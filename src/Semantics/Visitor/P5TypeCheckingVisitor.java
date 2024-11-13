package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

public class P5TypeCheckingVisitor implements Visitor {

    private final GlobalADT global;
    private TableADT st;
    public P5TypeCheckingVisitor(GlobalADT global) {
        this.global = global;
        this.st = global;
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
        //visit left side of assign
       n.i.accept(this);
       //visit right side
        n.e.accept(this);
        //leave assign type as undefined
    }

    @Override
    public void visit(ArrayAssign n) {
        //visit array id (should be int)
        n.i.accept(this);
        //visit index expression (again should be int)
        n.e1.accept(this);
        //visit right side of equals exp (again should be int)
        n.e2.accept(this);
        //leave assign type as undefined
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
        //visit e in e.i(el)
        n.e.accept(this);
        if(n.e.type instanceof ClassADT) {
            //set scope for identifier visit
            TableADT tmp = st;
            st = global.get(((ClassADT)n.e.type).name);
            //annotate i in e.i(el) (without using visitor pattern)
            n.i.type = searchForMethod(n.i.s);
            //fix scope
            st = tmp;
        }

        //visit all parameters
        for(int i = 0; i < n.el.size(); i++) {
            n.el.get(i).accept(this);
        }
        //give the call the return type of the method called if possible, otherwise set to undefined
        try {
            n.type = ((MethodADT)n.i.type).returnType;
        } catch (ClassCastException e) {
            n.type = UndefinedADT.UNDEFINED;
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
    public void visit(IdentifierExp n) {
        n.type = searchForVar(n.s);
    }

    @Override
    public void visit(This n) {
        //set type of 'this' to the class it is used in or if it's outside of scope somehow to undefined
        if(st instanceof ClassADT) {
            n.type = st;
        } else {
            n.type = UndefinedADT.UNDEFINED;
        }
    }

    @Override
    public void visit(NewArray n) {
        //visit expression inside brackets (should be int)
        n.e.accept(this);
        //mark node as int array type
        n.type = BaseADT.INT_ARRAY;
    }

    @Override
    public void visit(NewObject n) {
        //visit identifier
        n.i.accept(this);
        //set type to the type of id if class, otherwise set to undefined
        try {
            n.type = (ClassADT)n.i.type;
        } catch (ClassCastException e) {
            n.type = UndefinedADT.UNDEFINED;
        }
    }

    @Override
    public void visit(Not n) {
        n.type = BaseADT.BOOLEAN;
        n.e.accept(this);

    }

    @Override
    public void visit(Identifier n) {
        n.type = searchForVar(n.s);
    }

    private ADT searchForMethod(String s) {
        ClassADT tmp = (ClassADT) st;
        while(tmp != null) {
            ADT result = tmp.get(s);
            if(result != null) {
                return result;
            }
            tmp = tmp.parent;
        }
        return UndefinedADT.UNDEFINED;
    }

    private ADT searchForVar(String s) {
        //search method scope for var decl
        if(((MethodADT) st).get(s) != null) {
            return ((MethodADT) st).get(s);
        }
        //search class and parent classes for fields
        ClassADT tmp = (ClassADT) st.prev;
        while(tmp != null) {
            ADT result = tmp.getField(s);
            if(result != null) {
                return result;
            }
            tmp = tmp.parent;
        }
        return UndefinedADT.UNDEFINED;
    }

}
