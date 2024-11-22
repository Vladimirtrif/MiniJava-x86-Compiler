import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import Semantics.*;
import Semantics.Visitor.*;
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

        String option = args[0];
        String fpath = args[1];

        boolean is_S = option.equals("-S");
        boolean is_P = option.equals("-P");
        boolean is_A = option.equals("-A");
        boolean is_T = option.equals("-T");
        if (!isValidOption(is_S, is_P, is_A, is_T)) {
            printUsage();
            return;
        }
        Reader in = new BufferedReader(new FileReader(fpath));
        int exitCode = executeOption(is_S, is_P, is_A, is_T, in);
        System.exit(exitCode);
    }

    // Validates whether at least one option is selected
    private static boolean isValidOption(boolean is_S, boolean is_P, boolean is_A, boolean is_T) {
        return is_S || is_P || is_A || is_T;
    }

    // Executes the selected option and returns the exit code
    @SuppressWarnings("CallToPrintStackTrace")
    private static int executeOption(boolean is_S, boolean is_P, boolean is_A, boolean is_T, Reader in) {
        int exitCode = 0;
        try {
            if (is_S) {
                exitCode = runScanner(in);
            } else if (is_P) {
                exitCode = runParserPretty(in);
            } else if (is_A) {
                exitCode = runParserAbstract(in);
            } else if (is_T) {
                exitCode = runTypeChecker(in);
            }
        } catch (IOException e) {
            System.err.println("Unexpected internal compiler error: " + e);
            e.printStackTrace();
            exitCode = 1;
        }
        return exitCode;
    }

    // Scanner
    private static int runScanner(Reader in) throws IOException {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        int exitCode = 0;
        Symbol t = s.next_token();
        while (t.sym != sym.EOF) {
            if (t.sym == sym.error) exitCode = 1;
            // Print each scanned token
            System.out.print(s.symbolToString(t) + " ");
            t = s.next_token();
        }
        return exitCode;
    }

    // Parser (Pretty-Print) functionality
    private static int runParserPretty(Reader in) {
        return runParser(in, new PrettyPrintVisitor());
    }

    // Parser (Abstract-Print) functionality
    private static int runParserAbstract(Reader in) {
        return runParser(in, new AbstractPrintVisitor());
    }

    // Common parsing logic for both pretty and abstract printing
    @SuppressWarnings("CallToPrintStackTrace")
    private static int runParser(Reader in, Visitor visitor) {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        parser p = new parser(s, sf);
        try {
            Symbol root = p.parse();
            Program program = (Program) root.value;
            program.accept(visitor);
        } catch (Exception e) {
            System.err.println("Unexpected parser error: " + e.toString());
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    // Type-Check functionality
    @SuppressWarnings("CallToPrintStackTrace")
    private static int runTypeChecker(Reader in) {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        parser p = new parser(s, sf);
        String indent = "  ";

        // Run parser
        Symbol root;
        try {
            root = p.parse();
        } catch (Exception e) {
            System.err.println("Unexpected parser error: " + e.toString());
            e.printStackTrace();
            return 1;
        }

        // Run type-checking visitors
        Program program = (Program) root.value;

        // P1
        System.out.print("[1/6] Running P1TableVisitor...");
        P1TableVisitor visitorP1 = new P1TableVisitor();
        program.accept(visitorP1);
        GlobalADT global = visitorP1.getGlobalADT();
        List<String> errorsP1 = visitorP1.getErrors();
        if (errorsP1.isEmpty()) {
            System.out.println("\tpassed");
        } else {
            System.out.println("\tfailed");
            System.err.println("\nError log:");
            for (String error : errorsP1) { System.err.println(indent + error); }
            System.err.println("\nLast symbol table:\n");
            System.err.println(global.tableToString());
            return 1;
        }

        // P2
        System.out.print("[2/6] Running P2TableVisitor...");
        P2TableVisitor visitorP2 = new P2TableVisitor(global);
        program.accept(visitorP2);
        List<String> errorsP2 = visitorP2.getErrors();
        if (errorsP2.isEmpty()) {
            System.out.println("\tpassed");
        } else {
            System.out.println("\tfailed");
            System.err.println("\nError log:");
            for (String error : errorsP2) { System.err.println(indent + error); }
            System.err.println("\nLast symbol table:\n");
            System.err.println(global.tableToString());
            return 1;
        }

        // P3
        System.out.print("[3/6] Running P3CyclicExtendsVisitor...");
        P3CyclicExtendsVisitor visitorP3 = new P3CyclicExtendsVisitor(global);
        program.accept(visitorP3);
        List<String> errorsP3 = visitorP3.getErrors();
        if (errorsP3.isEmpty()) {
            System.out.println("\tpassed");
        } else {
            System.out.println("\tfailed");
            System.err.println("\nError log:");
            for (String error : errorsP3) { System.out.println(indent + error); }
            System.err.println("\nLast symbol table:\n");
            System.err.println(global.tableToString());
            return 1;
        }

        // P4
        System.out.print("[4/6] Running P4OverloadVisitor...");
        P4OverloadVisitor visitorP4 = new P4OverloadVisitor(global);
        program.accept(visitorP4);
        List<String> errorsP4 = visitorP4.getErrors();
        if (errorsP4.isEmpty()) {
            System.out.println("\tpassed");
        } else {
            System.out.println("\tfailed");
            System.err.println("\nError log:");
            for (String error : errorsP4) { System.err.println(indent + error); }
            System.err.println("\nLast symbol table:\n");
            System.err.println(global.tableToString());
            return 1;
        }

        // P5
        System.out.print("[5/6] Running P5OffsetVisitor...");
        P5OffsetVisitor visitorP5 = new P5OffsetVisitor(global);
        program.accept(visitorP5);  // impossible to get an error
        System.out.println("\tpassed");

        // P6
        System.out.print("[6/6] Running P6TypeCheckingVisitor...");
        P6TypeCheckingVisitor visitorP6 = new P6TypeCheckingVisitor(global);
        program.accept(visitorP5);
        List<String> errorsP6 = visitorP6.getErrors();
        if (errorsP6.isEmpty()) {
            System.out.println("\tpassed");
        } else {
            System.out.println("\tfailed");
            System.err.println("\nError log:");
            for (String error : errorsP6) { System.err.println(indent + error); }
            System.out.println("\nLast symbol table:\n");
            System.out.println(global.tableToString());
            return 1;
        }

        System.out.println("\nLast symbol table:\n");
        System.out.println(global.tableToString());
        return 0;
    }

    // Prints usage information for the command-line tool
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("\tScanner: MiniJava.java -S <filename>");
        System.out.println("\tParser (Pretty-Print): MiniJava.java -P <filename>");
        System.out.println("\tParser (Abstract-Print): MiniJava.java -A <filename>");
        System.out.println("\tTypeChecker: MiniJava.java -T <filename>");
    }
}
