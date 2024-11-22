package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

/**
 * Invalidates method overloads but not overrides.
 * ! Semantic visitors are meant to be run in order.
 */
public class P4OverloadVisitor implements Visitor {

    private final GlobalADT global;
    private final List<String> errors;

    public P4OverloadVisitor(GlobalADT global) {
        this.global = global;
        errors = new ArrayList<>();
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public void visit(Program n) {
        Set<ClassADT> leafs = new HashSet<>();

        // Add all leaf classes with parents
        for (int i = 0; i < n.cl.size(); i++) {
            if (n.cl.get(i) instanceof ClassDeclExtends c) {
                leafs.add((ClassADT) global.get(c.i.s));
            }
        }

        // Remove all non-leaf classes
        for (int i = 0; i < n.cl.size(); i++) {
            if (n.cl.get(i) instanceof ClassDeclExtends c) {
                ClassADT nonLeaf = (ClassADT) global.get(c.j.s);
                if (leafs.contains(nonLeaf))
                    leafs.remove(nonLeaf);
            }
        }

        // Visit every leaf class
        for (ClassADT leaf : leafs) {
            checkOverload(leaf);
        }
    }

    private void checkOverload(ClassADT cl) {
        Map<String, MethodADT> visited = new HashMap<>();
        for (ClassADT c = cl; c != null; c = c.parent) {
            for (String name : c.methodNames()) {
                MethodADT method = (MethodADT) c.getMethod(name);
                if (visited.containsKey(name)) {
                    MethodADT oldMethod = visited.get(name);
                    // Two methods with the same name and different types
                    if (!method.same(oldMethod)) {
                        errors.add(
                            "OverloadError: " + method + " at " + c.name
                            + " illegally overloads " + oldMethod + " at " + oldMethod.getClassADT().name + "."
                        );
                    }
                }
                visited.put(name, method);
            }
        }
    }

    @Override
    public void visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException("Unreachable code.");
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
    public void visit(MethodDecl n) {
        throw new UnsupportedOperationException("Unreachable code.");
    }

    @Override
    public void visit(VarDecl n) {
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
