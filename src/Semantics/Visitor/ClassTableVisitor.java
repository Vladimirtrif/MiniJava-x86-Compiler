package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

public class ClassTableVisitor implements Visitor {
    private final GlobalTable global;
    private boolean error;

    public ClassTableVisitor() {
        global = new GlobalTable();
        error = false;
    }

    public SymbolTable getGlobalTable() {
        return global;
    }

    public boolean getError() {
        return error;
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
        makeClass(n.i1.s, null);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        makeClass(n.i.s, null);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        makeClass(n.i.s, n.j.s);
    }

    private void makeClass(String name, String parentName) {
        ADT t = new ClassADT(name, parentName, global);
        error = error || global.put(name, t);
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
