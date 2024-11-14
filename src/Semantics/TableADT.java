package Semantics;

import java.util.*;

public class TableADT extends ADT {

    public static final String TAB = "  ";
    public static final int GLOBAL_DEPTH = 0;
    public static final int CLASS_DEPTH = 1;
    public static final int METHOD_DEPTH = 2;

    private final HashMap<String, ADT> table;
    public final int depth;

    public TableADT() {
        table = new HashMap<>();
        depth = 0;
    }

    public TableADT(TableADT prev) {
        table = new HashMap<>();
        depth = prev.depth + 1;
        this.prev = prev;
    }

    public ADT get(String s) {
        return table.get(s);
    }

    public ADT getOrDeclare(String s) {
        ADT t = this.get(s);
        if (t == null) {
            table.put(s, UndefinedADT.UNDEFINED);
        }
        return t;
    }

    public String put(String s, ADT t) {
        return table.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate name '" + s + "' declared at depth " + depth + ".";
    }

    public Set<String> keySet() {
        return table.keySet();
    }

    public String tableToString() { return null; }

    public static String indent(int depth) {
		String res = "";
		for (int i = 0; i < depth; i++)
			res += TAB;
		return res;
	}

    @Override
    public boolean same(ADT o) {
        return o == this;
    }

    @Override
    public boolean assignable(ADT o) {
        return o == this;
    }

    @Override
    public String toString() { return null; }

}
