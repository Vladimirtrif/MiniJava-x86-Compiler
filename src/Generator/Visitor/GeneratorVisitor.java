package Generator.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

public class GeneratorVisitor implements Visitor {

    private final StringBuilder str;
    private final GlobalADT global;
    private TableADT st;
    private static final String TAB = "\t";

    // the number of bytes allocated on the stack
	private int stackBytes;

    public GeneratorVisitor(GlobalADT global) {
        str = new StringBuilder();
        this.global = global;
        st = global;
        stackBytes = 0;
    }

    @Override
    public String toString() {
        return str.toString();
    }

    private void println() {
        str.append("\n");
    }

    private void println(String s) {
        str.append(s);
        println();
    }

    private void gen(String s) {
        str.append(TAB);
        println(s);
    }

    private void gen(String inst, String i) {
        gen(inst + " " + i);
    }

    private void gen(String inst, String i, String j) {
        gen(inst + " " + i + "," + j);
    }

    private void gen(String inst, int c, String i) {
        gen(inst, "$" + c, i);
    }

    private void pushq(String r) {
        gen("pushq", r);
        stackBytes += 8;
    }

    private void popq(String r) {
        gen("popq", r);
        stackBytes -= 8;
    }

    private boolean isAligned() {
        return stackBytes % 16 == 0;
    }

    private void methodHeader() {
        pushq("%rbp");
        gen("movq", "%rsp", "%rbp");
    }

    private void methodFooter() {
        gen("movq", "%rbp", "%rsp");
		popq("%rbp");
		gen("ret");
    }

    @Override
    public void visit(Print n) {
        n.e.accept(this);

        pushq("%rdi");
        if (!isAligned()) pushq("%rdi");

        gen("movq", "%rax", "%rdi");
        gen("call", "put");

        if (isAligned()) popq("%rdi");
        popq("%rdi");
    }

    @Override
    public void visit(Program n) {
        gen(".text");
        gen(".globl asm_main");
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.get(i).accept(this);
		}
    }

    @Override
    public void visit(MainClass n) {
		println("asm_main:");

        methodHeader();
		n.s.accept(this);
		methodFooter();

        // vtable
		println();
		gen(".data");
		println(n.i1.s + "$$:" + TAB + ".quad 0");
		println();
    }

    @Override
    public void visit(ClassDeclSimple n) {
    }

    @Override
    public void visit(ClassDeclExtends n) {
    }

    @Override
    public void visit(VarDecl n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(MethodDecl n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Formal n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IntArrayType n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(BooleanType n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IntegerType n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IdentifierType n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Block n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(If n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(While n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Assign n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ArrayAssign n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(And n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(LessThan n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Plus n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Minus n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Times n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ArrayLookup n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(ArrayLength n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Call n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IntegerLiteral n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(True n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(False n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(IdentifierExp n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(This n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(NewArray n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(NewObject n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Not n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(Identifier n) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
