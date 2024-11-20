package Generator.Visitor;

import AST.*;
import AST.Visitor.Visitor;
import Semantics.*;

public class GeneratorVisitor implements Visitor {

    private final StringBuilder str;
    private final GlobalADT global;
    private ADT st;
    private static final String TAB = "\t";
    private int stackBytes;     // number of bytes currently allocated on stack

    // Constructor
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

    private void push(String r) {
        gen("pushq", r);
        stackBytes += 8;
    }

    private void pop(String r) {
        gen("popq", r);
        stackBytes -= 8;
    }

    private void prologue() {
        push("%rbp");
        gen("movq", "%rsp", "%rbp");
    }

    private void epilogue() {
        gen("movq", "%rbp", "%rsp");
		pop("%rbp");
		gen("ret");
    }

    private boolean pushalign(String r) {
        if (stackBytes % 16 != 0) {
            push(r);
            return true;
        }
        return false;
    }

    private void call(String f) {
        boolean align = pushalign("%rax");
        gen("call", f);
        if (align) pop("%rdx");
    }

    private void call(String c, String m) {
		call(c + "$" + m);
	}

    @Override
    public void visit(Print n) {
        n.e.accept(this);
        push("%rdi");
        gen("movq", "%rax", "%rdi");
        call("put");
        pop("%rdi");
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

        prologue();
		n.s.accept(this);
        epilogue();

        // vtable
		println();
		gen(".data");
		println(n.i1.s + "$$: " + ".quad 0");
		println();
    }

    private void vtable() {
        ClassADT c = (ClassADT) st;
        String cid = c.name;
        int mem = 8 + 8 * c.allFields.size();

		// 1. Generate constructor
		gen(cid + "$" + cid + ":");
		prologue();
		push("%rdi");
		gen("movq", mem, "%rdi");
		call("mjmalloc");    // %rax points to obj
		pop("%rdi");

		// set pointer to base class dispatch table. 
		gen("leaq", cid + "$$", "%rdx");
		gen("movq", "%rdx", "(%rax)");

		// zero-initialize
		for(int i = 8; i < mem; i += 8) { 
			gen("movq", 0, i + "(%rax)");
		}
		epilogue(); 

		println();

        // 2. Generate vtable
		gen(".data");
        String parent = c.parent == null ? "0" : c.parent.name + "$$";
        println(c.name + "$$: .quad " + parent);
		gen(".quad " + c.name + "$" + c.name);
		for(MethodADT m : c.allMethods.values()) {
			gen(".quad " +  m.getClassADT().name + "$" + m.name);
		}
		println();
    }

    @Override
    public void visit(ClassDeclSimple n) {
        st = global.get(n.i.s);
        for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
        vtable();
        st = st.prev;
    }

    @Override
    public void visit(ClassDeclExtends n) {
        st = global.get(n.i.s);
		for (int i = 0; i < n.ml.size(); i++) {
			n.ml.get(i).accept(this);
		}
		vtable();
		st = st.prev;
    }

    @Override
    public void visit(VarDecl n) {}

    @Override
    public void visit(MethodDecl n) {
        ClassADT c = (ClassADT) st;
	    MethodADT m = c.getMethod(n.i.s);
        st = m;

		// generate method label
		gen(c.name + "$" + m.name + ":");   // "class$method:"

		prologue();
		if (n.vl.size() > 0) {
            gen("subq", 8 * n.vl.size(), "%rsp");
        }
		for (int i = 0; i < n.sl.size(); i++) {
			n.sl.get(i).accept(this);
		}
		n.e.accept(this); 
		epilogue();

		st = st.prev;
    }

    @Override
    public void visit(Formal n) {}

    @Override
    public void visit(Block n) {
        for (int i = 0; i < n.sl.size(); i++) {
			n.sl.get(i).accept(this);
		}
    }

    @Override
    public void visit(If n) {
        // TODO
    }

    @Override
    public void visit(While n) {
        // TODO
    }

    @Override
    public void visit(Assign n) {   // i = e;
        n.e.accept(this);   // stored at (%rax)
        ADT owner = n.i.type.prev;
        switch (owner) {
            case MethodADT m -> {
                int offset = m.varToOffset(n.i.s);
                gen("movq", "%rax", offset + "(%rbp)");
            }
            case ClassADT c -> {
                int offset = c.fieldToOffset(n.i.s);
                gen("movq", "%rax", offset + "(%rdi)");
            }
            default -> throw new IllegalStateException("Unreachable code.");
        }
    }

    @Override
    public void visit(ArrayAssign n) {  // i[e1] = e2
        // eval e1 and e2
        n.e1.accept(this);
		push("%rax");
		n.e2.accept(this); 
		pop("%rdx");    // e1 in %rdx, e2 in %rax

        // fetch i at %rcx
        ADT owner = n.i.type.prev;
        switch (owner) {
            case MethodADT m -> {
                int offset = m.varToOffset(n.i.s);
                gen("movq", offset + "(%rbp)", "%rcx");
            }
            case ClassADT c -> {
                int offset = c.fieldToOffset(n.i.s);
                gen("movq", offset + "(%rdi)", "%rcx");
            }
            default -> throw new IllegalStateException("Unreachable code.");
        }

        // assign
        gen("movq", "%rax", "8(%rcx,%rdx,8)");
    }

    @Override
    public void visit(And n) {
        // TODO
    }

    @Override
    public void visit(LessThan n) {
        // TODO
    }

    @Override
    public void visit(Plus n) {
       n.e2.accept(this);
       gen("movq", "%rax", "%rdx");
       n.e1.accept(this);
       gen("addq", "%rdx", "%rax");
    }

    @Override
    public void visit(Minus n) {
        n.e2.accept(this);
        gen("movq", "%rax", "%rdx");
        n.e1.accept(this);
        gen("subq", "%rdx", "%rax");
    }

    @Override
    public void visit(Times n) {
        n.e2.accept(this);
        gen("movq", "%rax", "%rdx");
        n.e1.accept(this);
        gen("imulq", "%rdx", "%rax");
    }

    @Override
    public void visit(ArrayLookup n) {
		n.e1.accept(this);
		push("%rax");
		n.e2.accept(this);
		pop("%rdx");
		gen("movq", "8(%rdx,%rax,8)", "%rax");
    }

    @Override
    public void visit(ArrayLength n) {
        n.e.accept(this);
        gen("movq", "(%rax)", "%rax");
    }

    @Override
    public void visit(Call n) { // e.i(el);
        ClassADT c = (ClassADT) n.e.type;
		MethodADT m = c.getMethod(n.i.s);

        push("%rdi");   // push "this"
	
        // align before pushing args
        // why before? only then we know the args immediately
        //     precede callee's stack frame
        boolean align = (stackBytes + 8*n.el.size()) % 16 != 0;
		if (align) push("%rax"); 

        // push arguments
		for (int i = 0; i < n.el.size(); i++) {
			n.el.get(i).accept(this);
			push("%rax");
		}

        // place e at %rdi after pushing args
        // why after? otherwise, below can modify %rdi such that
        //     an arg like "this" wouldn't have the same meaning
        n.e.accept(this);
        gen("movq", "%rax", "%rdi");

        // actually call
		gen("lea", c.methodToOffset(m) + "(%rdi)", "%rax");
		gen("call", "*(%rax)");  

        // pop arguments
		for (int i = 0; i < n.el.size(); i++) {
            pop("%rdx");
        }

		if (align) pop("%rdx");
		pop("%rdi");    // pop "this"
    }

    @Override
    public void visit(IntegerLiteral n) {
        gen("movq", n.i, "%rax");
    }

    @Override
    public void visit(True n) {
        gen("movq", 1, "%rax");
    }

    @Override
    public void visit(False n) {
        gen("movq", 0, "%rax");
    }

    @Override
    public void visit(IdentifierExp n) {
        ADT owner = n.type.prev;
        switch (owner) {
            case MethodADT m -> {
                // if a method owns n, then n is either param or local.
                // if param, then n's offset is positive (located above rbp)
                // if local, then n's offset is negative (located below rbp)
                int offset = m.varToOffset(n.s);
                gen("movq", offset + "(%rbp)", "%rax");
            }
            case ClassADT c -> {
                // if a class owns n, then n is a field.
                int offset = c.fieldToOffset(n.s);
                gen("movq", offset + "(%rdi)", "%rax");
            }
            default -> throw new IllegalStateException("Unreachable code.");
        }
    }

    @Override
    public void visit(This n) {
        gen("movq", "%rdi", "%rax");
    }

    @Override
    public void visit(NewArray n) {
        // 1. Evaluate array length
        n.e.accept(this);           // %rax = len
        push("%rax");               // push len
		gen("incq", "%rax");        // %rax = len + 1 (so that arr[0] = len) 
		gen("shlq", 3, "%rax");     // %rax = 8 * (len + 1)

        // 1. Allocate array of 8 * (e + 1) bytes
		push("%rdi");
        gen("movq", "%rax", "%rdi");    // %rdi = 8 * (len + 1)
		call("mjmalloc");               // %rax = arr_ptr
		pop("%rdi");
    
        // 2. Ready for for-loop
		pop("%rdx");                    // pop len
		gen("movq", "%rdx", "(%rax)");  // (%rax) = len
		gen("movq", 8, "%rcx");         // index = 1
		push("%rax");                   // push array ptr to stack

        String len = "%rdx";
        String index = "%rcx";
        String arr = "%rax";
		
        // 3. Execute for-loop
		String done = "done";
		String test = "test";
		gen(test + ":");

		// test
		gen("testq", 0, len);
		gen("je", done);
        // end test

		// body
		gen("addq", index, arr);            // arr[index]
		gen("movq", 0, "(" + arr + ")");    // arr[index] = 0
		gen("shlq", 2, index);              // index++
		gen("decq", len);	                // len--
		// end body

		gen("jmp", test);
		gen(done + ":");

		pop("%rax");    // return arr ptr
    }

    @Override
    public void visit(NewObject n) {
		call(n.i.s, n.i.s); // constructor call
    }

    @Override
    public void visit(Not n) {
        // TODO
    }

    @Override
    public void visit(Identifier n) {
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

}
