package AST.Visitor;

import AST.*;

public class AbstractPrintVisitor implements Visitor {
    
    private int indentation;
    private static final String TAB = "  ";

    public AbstractPrintVisitor() {
        super();
        indentation = 0;
    }

    private void print(String message) {
        System.out.print(message);
    }

    private void println() {
        print("\n");
    }

    private void printsp() {
        print(" ");
    }

    private void printIndent() {
        for (int i = 0; i < indentation; ++i) {
            print(TAB);
        }
    }

    private void printIndent(String message) {
        printIndent();
        print(message);
    }

    private void printlnIndent(String message) {
        printIndent(message);
        println();
    }

    private void printLineNumber(ASTNode n) {
        print(" (line " + n.line_number + ")");
    }

    // MainClass m;
    // ClassDeclList cl;
    @Override
    public void visit(Program n) {
        printlnIndent("Program");
        indentation++;
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.get(i).accept(this);
        }
        indentation--;
    }

    // Identifier i1,i2;
    // Statement s;
    @Override
    public void visit(MainClass n) {
        printIndent("MainClass ");
        n.i1.accept(this);
        printLineNumber(n);
        println();
        indentation++;
        n.s.accept(this);
        indentation--;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    @Override
    public void visit(ClassDeclSimple n) {
        printIndent("Class ");
        n.i.accept(this);
        printLineNumber(n);
        println();

        indentation++;
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        indentation--;
    }

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    @Override
    public void visit(ClassDeclExtends n) {
        printIndent("Class ");
        n.i.accept(this);
        printIndent(" extends ");
        n.j.accept(this);
        printLineNumber(n);
        println();

        indentation++;
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.get(i).accept(this);
        }
        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.get(i).accept(this);
        }
        indentation--;
    }

    // Type t;
    // Identifier i;
    @Override
    public void visit(VarDecl n) {
        printIndent("VarDecl ");
        n.i.accept(this);
        printLineNumber(n);
        println();
        indentation++;
        printIndent("declType: ");
        n.t.accept(this);
        indentation--;
        println();
    }

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    @Override
    public void visit(MethodDecl n) {
        printIndent("MethodDecl ");
        n.i.accept(this);   // e.g., bar
        printLineNumber(n);
        println();
        indentation++;
        printIndent("returnType: ");
        n.t.accept(this);   // e.g., int
        println();
        printIndent("parameters:\n");
        indentation++;
        for (int i = 0; i < n.fl.size(); i++) {
            printIndent();
            n.fl.get(i).accept(this);
            println();
        }
        indentation--;
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }
        if (n.e != null) {
            printIndent("Return");
            printLineNumber(n.e);
            indentation++;
            println();
            printIndent();
            n.e.accept(this);
            indentation--;
            println();
        }
        indentation--;
    }

    // Type t;
    // Identifier i;
    @Override
    public void visit(Formal n) {
        n.t.accept(this);
        System.out.print(" ");
        n.i.accept(this);
    }

    @Override
    public void visit(IntArrayType n) {
        print("int []");
    }

    @Override
    public void visit(BooleanType n) {
        print("boolean");
    }

    @Override
    public void visit(IntegerType n) {
        print("int");
    }

    // String s;
    @Override
    public void visit(IdentifierType n) {
        print(n.s);
    }

    // StatementList sl;
    @Override
    public void visit(Block n) {
        printIndent("Block");
        printLineNumber(n);
        println();
        indentation++;
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.get(i).accept(this);
        }
        indentation--;
    }

    // Exp e;
    // Statement s1,s2;
    @Override
    public void visit(If n) {
        printIndent("If");
        printLineNumber(n);
        println();
        indentation++;
        printIndent("condition: ");
        n.e.accept(this);
        println();
        n.s1.accept(this);
        indentation--;
        if (n.s2 != null) {
            printlnIndent("Else");
            indentation++;
            n.s2.accept(this);
            indentation--;
        }
    }

    // Exp e;
    // Statement s;
    @Override
    public void visit(While n) {
        printIndent("While");
        printLineNumber(n);
        println();
        indentation++;
        printIndent("condition: ");
        n.e.accept(this);
        println();
        n.s.accept(this);
        indentation--;
    }

    // Exp e;
    @Override
    public void visit(Print n) {
        printIndent("Print");
        printLineNumber(n);
        println();
        indentation++;
        printIndent();
        n.e.accept(this);
        println();
        indentation--;
    }

    // Identifier i;
    // Exp e;
    @Override
    public void visit(Assign n) {
        printIndent("Assign");
        printLineNumber(n);
        indentation++;
        println();
        printIndent();
        n.i.accept(this);
        print(" := ");
        n.e.accept(this);
        indentation--;
        println();
    }

    // Identifier i;
    // Exp e1,e2;
    @Override
    public void visit(ArrayAssign n) {
        printIndent("ArrayAssign");
        printLineNumber(n);
        indentation++;
        println();
        printIndent();
        n.i.accept(this);
        print("[");
        n.e1.accept(this);
        print("] := ");
        n.e2.accept(this);
        indentation--;
        println();
    }

    // Exp e1,e2;
    @Override
    public void visit(And n) {
        print("(");
        n.e1.accept(this);
        print(" && ");
        n.e2.accept(this);
        print(")");
    }

    // Exp e1,e2;
    @Override
    public void visit(LessThan n) {
        print("(");
        n.e1.accept(this);
        print(" < ");
        n.e2.accept(this);
        print(")");
    }

    // Exp e1,e2;
    @Override
    public void visit(Plus n) {
        print("(");
        n.e1.accept(this);
        print(" + ");
        n.e2.accept(this);
        print(")");
    }

    // Exp e1,e2;
    @Override
    public void visit(Minus n) {
        print("(");
        n.e1.accept(this);
        print(" - ");
        n.e2.accept(this);
        print(")");
    }

    // Exp e1,e2;
    @Override
    public void visit(Times n) {
        print("(");
        n.e1.accept(this);
        print(" * ");
        n.e2.accept(this);
        print(")");
    }

    // Exp e1,e2;
    @Override
    public void visit(ArrayLookup n) {
        print("(");
        n.e1.accept(this);
        print("[");
        n.e2.accept(this);
        print("]");
        print(")");
    }

    // Exp e;
    @Override
    public void visit(ArrayLength n) {
        print("(");
        n.e.accept(this);
        print(".length");
        print(")");
    }

    // Exp e;
    // Identifier i;
    // ExpList el;
    @Override
    public void visit(Call n) {
        print("(");
        n.e.accept(this);
        print(".");
        n.i.accept(this);
        print("(");
        for (int i = 0; i < n.el.size(); i++) {
            n.el.get(i).accept(this);
            if (i + 1 < n.el.size()) {
                print(", ");
            }
        }
        print("))");
    }

    // int i;
    @Override
    public void visit(IntegerLiteral n) {
        print(String.valueOf(n.i));
    }

    @Override
    public void visit(True n) {
        print("true");
    }

    @Override
    public void visit(False n) {
        print("false");
    }

    // String s;
    @Override
    public void visit(IdentifierExp n) {
        print(n.s);
    }

    @Override
    public void visit(This n) {
        print("this");
    }

    // Exp e;
    @Override
    public void visit(NewArray n) {
        print("new int[");
        n.e.accept(this);
        print("]");
    }

    // Identifier i;
    @Override
    public void visit(NewObject n) {
        print("new ");
        n.i.accept(this);
        print("()");
    }

    // Exp e;
    @Override
    public void visit(Not n) {
        print("(!");
        n.e.accept(this);
        print(")");
    }

    // String s;
    @Override
    public void visit(Identifier n) {
        print(n.s);
    }

}
