package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

/**
 * 1st pass of symbol table construction. Does three things:
 * (1) Constructs one global table.
 * (2) Constructs a class table for every defined class.
 * (3) Links every class table to the global table.
 * (4) Checks for duplicate class declarations.
 * ! Semantic visitors are meant to be run in order.
 */
public class P1TableVisitor implements Visitor {

    private final GlobalADT global;
    private final List<String> errors;

    public P1TableVisitor() {
        global = new GlobalADT();
        errors = new ArrayList<>();
    }

    public GlobalADT getGlobalADT() {
        return global;
    }

    public List<String> getErrors() {
        return errors;
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
        makeClass(ADT.MAIN_NAME, null, n.line_number);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        makeClass(n.i.s, null, n.line_number);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        makeClass(n.i.s, n.j.s, n.line_number);
    }

    private void makeClass(String name, String parentName, int lineNumber) {
        ADT t = new ClassADT(name, parentName, global);
        String error = global.put(name, t, lineNumber);
        if (error != null) {
            errors.add(error);
        }
    }

    @Override
    public void visit(VarDecl n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(MethodDecl n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(Formal n) {
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
