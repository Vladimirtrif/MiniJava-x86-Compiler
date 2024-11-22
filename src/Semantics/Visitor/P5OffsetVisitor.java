package Semantics.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

public class P5OffsetVisitor implements Visitor {

    private final GlobalADT global;
    private ADT st;

    public P5OffsetVisitor(GlobalADT global) {
        this.global = global;
        st = global;
    }

    @Override
    public void visit(Program n) {
        Set<ClassADT> leafs = new LinkedHashSet<>();

        // Add all potential leaf classes
        for (int i = 0; i < n.cl.size(); i++) {
            switch (n.cl.get(i)) {
                case ClassDeclSimple c -> leafs.add((ClassADT) global.get(c.i.s));
                case ClassDeclExtends c -> leafs.add((ClassADT) global.get(c.i.s));
                default -> throw new IllegalStateException();
            }
        }

        // Remove all non-leaf classes
        for (int i = 0; i < n.cl.size(); i++) {
            if (n.cl.get(i) instanceof ClassDeclExtends c) {
                ClassADT nonLeaf = (ClassADT) global.get(c.j.s);
                if (leafs.contains(nonLeaf)) {
                    leafs.remove(nonLeaf);
                }
            }
        }

        // Start assigning offsets from each leaf class
        for (ClassADT leaf : leafs) {
            assignOffset(leaf);
        }

        // Visit every class
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
    }

    private void assignOffset(ClassADT c) {
        if (c.hasOffset) { return; }

        int i;
        
        // Copy parent's fields and methods
        if (c.parent != null) {
            assignOffset(c.parent);
            c.deepFields.putAll(c.parent.deepFields);
            c.deepMethods.putAll(c.parent.deepMethods);
        }

        // Insert all fields & methods
        for (String name : c.fieldNames()) {
            c.deepFields.put(name, c.getField(name));
        }
        for (String name : c.methodNames()) {
            c.deepMethods.put(name, (MethodADT) c.getMethod(name));
        }

        // Invert the maps for O(1) offset access
        i = 8;
        for (String name : c.deepFields.keySet()) {
            c.fieldToOffset.put(name, i);
            i += 8;
        }
        i = 8;
        for (String name : c.deepMethods.keySet()) {
            c.methodToOffset.put(name, i);
            i += 8;
        }

        c.hasOffset = true;
    }

    @Override
    public void visit(ClassDeclSimple n) {
        st = global.get(n.i.s);
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        st = st.prev;
    }

    @Override
    public void visit(ClassDeclExtends n) {
        st = global.get(n.i.s);
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        st = st.prev;
    }

    @Override
    public void visit(MethodDecl n) {
        ClassADT c = (ClassADT) st;
        MethodADT m = (MethodADT) c.getMethod(n.i.s);
        int i = 0;
        for (String name : m.varNames()) {
            if (i < m.numParams) {
                // name is param (located before %rbp)
                m.varToOffset.put(name, 8 + 8 * i);
            } else {
                // name is local (located after %rbp)
                int j = i - m.numParams;
                m.varToOffset.put(name, -8 - 8*j);
            }
            i++;
        }
    }

    @Override
    public void visit(MainClass n) {
        throw new IllegalStateException("Unreachable code.");
    }

    @Override
    public void visit(VarDecl n) {
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
