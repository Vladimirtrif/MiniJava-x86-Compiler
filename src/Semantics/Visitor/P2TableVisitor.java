package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

/**
 * 2nd and last pass of symbol table construction. Does six things:
 * (1) For every extending class, checks whether its superclass is defined in the global table.
 * (2) Constructs a method table for every method.
 * (3) Links every method table to its owner class.
 * (4) Converts the AST type of every parameter/variable into its ADT-equivalent.
 * (5) Links every parameter/variable to its owner method.
 * (6) Checks for duplicate method/parameter/variable declarations.
 * ! Semantic visitors are meant to be run in order.
 */
public class P2TableVisitor implements Visitor {
    private final GlobalADT global;
    private TableADT st;
    private boolean error;

    public P2TableVisitor(GlobalADT global) {
        this.global = global;
        st = global;
        error = false;
    }

    public TableADT getGlobalTable() {
        return st;
    }

    public boolean getError() {
        return error;
    }

    private void printError(String message) {
        error = true;
        System.out.println(message);
    }

    private void printErrorForUndefined(String name, int lineNumber) {
        printError(
            "UndefinedIdentifierException: class " + name
            + " at line " + lineNumber + " is not defined."
        );
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
        ClassADT c = global.get(name);
        MethodADT m = new MethodADT(MethodADT.MAIN_METHOD_NAME, VoidADT.VOID, c);
        error = error || c.put(name, m);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        st = global.get(n.i.s);
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
        ClassADT parent = global.getOrDeclare(parentName);
        if (parent == null) {
            printErrorForUndefined(parentName, n.line_number);
        }
        ClassADT c = global.get(n.i.s);
        c.parent = parent;
        st = c;         // Set scope to class
        // Class fields
        for (int i = 0; i < n.vl.size(); i++) {
			n.vl.get(i).accept(this);
		}
        // Class methods
        for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        st = st.prev;   // Set scope back to global
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
        t.prev = st;
        error = error || st.put(name, t);

        // Formal is (probably) a method parameter
        if (st instanceof MethodADT m) {
            m.addParamType(t);
        }
    }

    @Override
    public void visit(VarDecl n) {
        String name = n.i.s;
        ADT t = convertToADT(n.t);
        t.prev = st;
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
            ADT res = global.getOrDeclare(className);
            if (res == null) {
                printErrorForUndefined(className, it.line_number);
            }
            return global.get(className);
        }
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(IntArrayType n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(BooleanType n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(IntegerType n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(IdentifierType n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Block n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(If n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(While n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Print n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Assign n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(ArrayAssign n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(And n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(LessThan n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Plus n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Minus n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Times n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(ArrayLookup n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(ArrayLength n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Call n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(IntegerLiteral n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(True n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(False n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(IdentifierExp n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(This n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(NewArray n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(NewObject n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Not n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Identifier n) {
        throw new IllegalStateException("Unreachable code.");
    }

}
