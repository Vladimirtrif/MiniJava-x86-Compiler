package Semantics;

import java.util.*;

public class SymbolTable {

    public static final String TAB = "  ";

    private final LinkedHashMap<String, ADT> table;

    public SymbolTable() {
        table = new LinkedHashMap<>();
    }

    public ADT get(String s) { return table.get(s); }

    public ADT getOrDeclare(String s) {
        ADT t = this.get(s);
        if (t == null) {
            table.put(s, UndefinedADT.UNDEFINED);
        }
        return t;
    }

    public ADT put(String s, ADT t) { return table.put(s, t); }

    public Set<String> keySet() { return table.keySet(); }

    public int size() { return table.size(); }

    public static String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; i++)
			res += TAB;
		return res;
	}
}
