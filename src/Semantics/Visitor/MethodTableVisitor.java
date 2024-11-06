package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

public class MethodTableVisitor implements Visitor {
    private final SymbolTable global;
    private SymbolTable st;
    private boolean error;

    public MethodTableVisitor(GlobalTable st) {
        global = st;
        this.st = st;
        error = false;
    }

    public SymbolTable getGlobalTable() {
        return st;
    }

    public boolean getError() {
        return error;
    }

    private void printErrorForUndefined(String name, int lineNumber) {
        System.out.println("Error at line " + lineNumber + ": " + name + " is not defined.");
    }

    @Override
    public void visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        // ! Ignores n.i2.s (String[] args) entirely
        String name = n.i1.s;
        ClassADT c = (ClassADT) (st.get(name));
        MethodADT m = new MethodADT(MethodADT.MAIN_METHOD_NAME, VoidADT.VOID, c);
        c.put(name, m);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        st = (ClassADT) st.get(n.i.s);
		for (int i = 0; i < n.vl.size(); i++) {
			n.vl.get(i).accept(this);
		}
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        st = st.prev;
    }

    @Override
    public void visit(ClassDeclExtends n) {
        // Check if parent class is defined
        String parentName = n.j.s;
        ADT parent = st.get(parentName);
        if (parent.same(UndefinedADT.UNDEFINED)) {
            printErrorForUndefined(parentName, n.line_number);
            error = true;
        }

        st = (ClassADT) st.get(n.i.s);  // Set scope to class
        // Class fields
        for (int i = 0; i < n.vl.size(); i++) {
			n.vl.get(i).accept(this);
		}
        // Class methods
        for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        st = st.prev;                   // Set scope back to global
    }

    @Override
    public void visit(MethodDecl n) {
        String name = n.i.s;
        ADT returnType = convertToADT(n.t);
        MethodADT m = new MethodADT(name, returnType, (ClassADT) st);

        st = m;         // Set scope to method
        // Method parameters
        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.get(i).accept(this);
        }
        // Method local variables
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        st = st.prev;   // Set scope back to class

        error = error || st.put(name, m);
    }

    @Override
    public void visit(Formal n) {
        String name = n.i.s;
        ADT t = convertToADT(n.t);
        error = error || st.put(name, t);

        // Formal is probably a method parameter
        if (st instanceof MethodADT m) {
            m.paramTypes.add(t);
        }
    }

    @Override
    public void visit(VarDecl n) {
        String name = n.i.s;
        ADT t = convertToADT(n.t);
        error = error || st.put(name, t);
    }

    public ADT convertToADT(AST.Type t) {
        if (t instanceof AST.IntegerType) {
            return BaseADT.INT;
        } else if  (t instanceof AST.BooleanType) {
            return BaseADT.BOOLEAN;
        } else if (t instanceof AST.IntArrayType) {
            return BaseADT.INT_ARRAY;
        } else if (t instanceof AST.IdentifierType it) {
            String className = it.s;
            ADT res = global.deepget(className);
            if (res.same(UndefinedADT.UNDEFINED)) {
                printErrorForUndefined(className, it.line_number);
                error = true;
            }
        }
        System.out.println("Unreachable code in convertToADT.");
        error = true;
        return null;
    }

    @Override
    public void visit(IntArrayType n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(BooleanType n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(IntegerType n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(IdentifierType n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Block n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(If n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(While n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Print n) {
        throw new UnsupportedOperationException("Unreachable code.");
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
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(LessThan n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Plus n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Minus n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Times n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(ArrayLookup n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(ArrayLength n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Call n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(IntegerLiteral n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(True n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(False n) {
        throw new UnsupportedOperationException("Unreachable code.");
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
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Identifier n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

}
