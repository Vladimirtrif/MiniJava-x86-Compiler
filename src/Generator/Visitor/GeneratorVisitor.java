package Generator.Visitor

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;
import java.util.*;

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

    private void run(String s) {
        str.append(TAB);
        println(s);
    }

    private void run(String inst, String i) {
        run(inst + " " + i);
    }

    private void run(String inst, String i, String j) {
        run(inst + " " + i + "," + j);
    }

    private void run(String inst, int c, String i) {
        run(inst, "$" + c, i);
    }

    private void pushq(String r) {
        run("pushq", r);
        stackBytes += 8;
    }

    private void popq(String r) {
        run("popq", r);
        stackBytes -= 8;
    }

    private boolean isAligned() {
        return stackBytes % 16 == 0;
    }

    private void align(String r) {
		if (!isAligned()) {
			pushq(r);
			stackBytes += 8;
		}
	}

    private void methodHeader() {
        pushq("%rbp");
        run("movq", "%rsp", "%rbp");
    }

    private void methodFooter() {
        run("movq", "%rbp", "%rsp");
		popq("%rbp");
		run("ret");
    }

    @Override
    public void visit(Print n) {
        n.e.accept(this);
		pushq("%rdi");
		run("movq", "%rax","%rdi");
		align("%rax");
	    run("call", "put");
		align("%rdx");
		popq("%rdi");
    }

    @Override
    public void visit(Program n) {
        run(".text");
        run(".globl asm_main");
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
		
		// dispatch table for the main class
		println();
		run(".data");
		println(n.i1.s + "$$: .quad 0");
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
