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

        String option = args[0];
        String fpath = args[1];

        boolean is_S = option.equals("-S");
        boolean is_P = option.equals("-P");
        boolean is_A = option.equals("-A");

        if (!isValidOption(is_S, is_P, is_A)) {
            printUsage();
            return;
        }

        Reader in = new BufferedReader(new FileReader(fpath));
        int exitCode = executeOption(is_S, is_P, is_A, in);
        System.exit(exitCode);
    }

    // Validates whether at least one option is selected
    private static boolean isValidOption(boolean is_S, boolean is_P, boolean is_A) {
        return is_S || is_P || is_A;
    }

    // Executes the selected option and returns the exit code
    private static int executeOption(boolean is_S, boolean is_P, boolean is_A, Reader in) {
        int exitCode = 0;
        try {
            if (is_S) {
                exitCode = runScanner(in);
            } else if (is_P) {
                exitCode = runParserPretty(in);
            } else if (is_A) {
                exitCode = runParserAbstract(in);
            }
        } catch (Exception e) {
            handleException(e);
            exitCode = 1;
        }
        return exitCode;
    }

    // Handles exceptions and prints error messages
    private static void handleException(Exception e) {
        System.err.println("Unexpected internal compiler error: " + e.toString());
        e.printStackTrace();
    }

    // Scanner functionality
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
    private static int runParser(Reader in, Visitor visitor) {
        ComplexSymbolFactory sf = new ComplexSymbolFactory();
        scanner s = new scanner(in, sf);
        parser p = new parser(s, sf);

        try {
            Symbol root = p.parse();
            @SuppressWarnings("unchecked")
            Program program = (Program) root.value;
            program.accept(visitor);
        } catch (Exception e) {
            System.err.println("Unexpected parser error: " + e.toString());
            e.printStackTrace();
            return 1;
        }

        return 0;
    }

    // Prints usage information for the command-line tool
    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("\tScanner: MiniJava.java -S <filename>");
        System.out.println("\tParser (Pretty-Print): MiniJava.java -P <filename>");
        System.out.println("\tParser (Abstract-Print): MiniJava.java -A <filename>");
    }
}
