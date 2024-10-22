import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java.util.*;
import java.io.*;


class MiniJava {

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length != 2) {
            printUsage();
            return;
        }

        boolean is_S = args[0].equals("-S");
        boolean is_P = args[0].equals("-P");
        boolean is_A = args[0].equals("-A");

        if (!(is_S && is_P && is_P)) {
            printUsage();
            return;
        }

        String fpath = args[1];
        Reader in = new BufferedReader(new FileReader(fpath));

        int exitCode = 0;

        try {
            if (is_S) exitCode = runScanner(in);
            else if (is_P) exitCode = runParserPretty(in);
            else if (is_A) exitCode = runParserAbstract(in);
        } catch (Exception e) {
            // encountered a bug!
            System.err.println("Unexpected internal compiler error: " + 
                        e.toString());
            // print out a stack dump
            e.printStackTrace();
            // exit with code 1
            exitCode = 1;
        }
        // System.out.println();
        // System.out.println("exitCode: " + exitCode);
        System.exit(exitCode);
    }


    private static int runScanner(Reader in) throws IOException {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        int exitCode = 0;

        Symbol t = s.next_token();
        while (t.sym != sym.EOF) {
            if (t.sym == sym.error)
                exitCode = 1;
            // print each token that we scan
            System.out.print(s.symbolToString(t) + " ");
            t = s.next_token();
        }

        return exitCode;
    }


    private static int runParserPretty(Reader in) {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        parser p = new parser(s, sf);
        Symbol root;
        // replace p.parse() with p.debug_parse() in the next line to see
        // a trace of parser shift/reduce actions during parsing
        root = p.parse();
        // We know the following unchecked cast is safe because of the
        // declarations in the CUP input file giving the type of the
        // root node, so we suppress warnings for the next assignment.
        @SuppressWarnings("unchecked")
        List<Statement> program = (List<Statement>)root.value;
        for (Statement statement: program) {
            statement.accept(new PrettyPrintVisitor());
            System.out.print("\n");
        }
    }


    private static int runParserAbstract(Reader in) {
        return 2;
    }


    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("\tScanner: MiniJava.java -S <filename>");
        System.out.println("\tParser (Pretty-Print): MiniJava.java -P <filename>");
        System.out.println("\tParser (Abstract-Print): MiniJava.java -A <filename>");
    }
}