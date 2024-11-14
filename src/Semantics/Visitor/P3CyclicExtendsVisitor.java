package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

/**
 * Checks for cyclic extends call.
 * Immediately halts if the first error is produced.
 * ! Semantic visitors are meant to be run in order.
 */
public class P3CyclicExtendsVisitor implements Visitor {

    private final GlobalADT global;
    private final List<String> errors;

    // set of non-simple classes we know are free from cyclic extends
    private final Set<ClassADT> okay;

    public P3CyclicExtendsVisitor(GlobalADT global) {
        okay = new HashSet<>();
        this.global = global;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void visit(Program n) {
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
            if (!errors.isEmpty()) return;
        }
    }

    @Override
    public void visit(MainClass n) {
        ClassADT c = global.get(MethodADT.MAIN_METHOD_NAME);
        c.hasNoCyclicExtends = true;
    }

    @Override
    public void visit(ClassDeclSimple n) {
        ClassADT c = global.get(n.i.s);
        c.hasNoCyclicExtends = true;
    }

    @Override
    public void visit(ClassDeclExtends n) {
        ClassADT c0 = global.get(n.i.s);
        if (!okay.contains(c0)) {
            Set<ClassADT> visited = new HashSet<>();
            for (ClassADT c = c0; c.parent != null; c = c.parent) {
                if (visited.contains(c)) {
                    errors.add("CyclicExtendsError: Cyclic extends detected at " + c + ".");
                    return;
                }
                visited.add(c);
            }
            for (ClassADT c = (ClassADT) c0; c.parent != null; c = (ClassADT) c.parent) {
                okay.add(c);
            }
        }
        c0.hasNoCyclicExtends = true;
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
