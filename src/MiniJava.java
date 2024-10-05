import Scanner.*;
import Parser.*;
import AST.*;
import AST.Visitor.*;
import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java.util.*;
import java.io.*;


class MiniJava {
    public static void main(String[] args) {
        if (args.length != 2 || !args[0].equals("-S")) {
            System.out.println("Usage: java MiniJava -S filename.java");
            return;
        }
        String fpath = args[1];
        int exitCode = 1;
        try {
            ComplexSymbolFactory sf = new ComplexSymbolFactory();
            Reader in = new BufferedReader(new FileReader(fpath));
            scanner s = new scanner(in, sf);
            Symbol t = s.next_token();
            while (t.sym != sym.EOF) { 
                // print each token that we scan
                System.out.print(s.symbolToString(t) + " ");
                t = s.next_token();
            }
            exitCode = 0;
        } catch (Exception e) {
            // yuck: some kind of error in the compiler implementation
            // that we're not expecting (a bug!)
            System.err.println("Unexpected internal compiler error: " + 
                        e.toString());
            // print out a stack dump
            e.printStackTrace();
        }
        System.exit(exitCode);
   }
}