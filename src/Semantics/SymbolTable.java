package Semantics;

import java.util.*;

public class SymbolTable {

    public static final String TAB = "  ";
    public static final int GLOBAL_DEPTH = 0;
    public static final int CLASS_DEPTH = 1;
    public static final int METHOD_DEPTH = 2;

    private final HashMap<String, ADT> table;
    public final SymbolTable prev;
    public final int depth;

    public SymbolTable() {
        table = new HashMap<>();
        prev = null;
        depth = 0;
    }

    public SymbolTable(SymbolTable prev) {
        table = new HashMap<>();
        depth = prev.depth + 1;
        this.prev = prev;
    }

    public ADT get(String s) {
        return table.get(s);
    }

    public ADT deepget(String s) {
        for (SymbolTable st = this; st != null; st = st.prev) {
            ADT t = st.get(s);
            if (t != null) return t;
        }
        table.put(s, UndefinedADT.UNDEFINED);
        return UndefinedADT.UNDEFINED;
    }

    public boolean put(String s, ADT t) {
        boolean error = this.get(s) != null;
        if (error) {
            System.out.println(s + " is already declared.");
        }
        table.put(s, t);
        return error;
    }

    public String tableToString() {
        StringBuilder res = new StringBuilder();
        if (depth == GLOBAL_DEPTH) {
            res.append("Global:\n");
        }
        for (String s : table.keySet()) {
            ADT t = table.get(s);
            res.append(indent(depth + 1));
            res.append(s);
            res.append("->");
            res.append(t);
            res.append("\n");
            if (t instanceof SymbolTable st) {
                res.append(st.tableToString());
                res.append("\n");
            }
        }
        return res.toString();
    }

    private String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; i++)
			res += TAB;
		return res;
	}

}
