package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.HashSet;

public class P3CyclicExtendsVisitor implements Visitor {
    private final HashSet<ClassADT> okay;
    private final GlobalTable global;
    private boolean error;

    public P3CyclicExtendsVisitor(GlobalTable global) {
        okay = new HashSet<>();
        this.global = global;
        error = true;
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
        for (int i = 0; i < n.cl.size(); i++) {
            if (n.cl.get(i) instanceof ClassDeclExtends c) {
                c.accept(this);
            }
        }
    }

    @Override
    public void visit(ClassDeclExtends n) {
        ClassADT c0 = (ClassADT) global.get(n.i.s);
        if (okay.contains(c0)) return;
        HashSet<ClassADT> visited = new HashSet<>();
        for (ClassADT c = (ClassADT) c0.prev; c != null; c = (ClassADT) c.prev) {
            // Note: c is non-null by P2
            if (visited.contains(c)) {
                printError("Cyclic extends detected at class " + c.name + ".");
                return;
            }
            visited.add(c);
        }
        for (ClassADT c = (ClassADT) c0.prev; c != null; c = (ClassADT) c.prev) {
            okay.add(c);
        }
    }

    @Override
    public void visit(MainClass n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(ClassDeclSimple n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(VarDecl n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(MethodDecl n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(Formal n) {
        throw new UnsupportedOperationException("Unreachable code.");
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
