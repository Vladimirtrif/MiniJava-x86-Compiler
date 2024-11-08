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

    public boolean put(String s, ADT t) {
        boolean error = this.get(s) != null;
        if (error) {
            System.out.println("DuplicateNameError: Duplicate name '" + s + "' declared at depth " + depth + ".");
        }
        table.put(s, t);
        return error;
    }

    public Set<String> keySet() {
        return table.keySet();
    }

    public String tableToString() {
        // TODO: Change if ugly
        StringBuilder res = new StringBuilder();
        if (depth == GLOBAL_DEPTH) {
            res.append("Global:\n");
        }
        for (String s : table.keySet()) {
            ADT t = table.get(s);
            res.append(indent(depth + 1));
            res.append(s);
            res.append("::=");
            res.append(t);
            res.append("\n");
            if (t instanceof TableADT st) {
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

    @Override
    public boolean same(ADT o) {
        return o == this;
    }

    @Override
    public boolean assignable(ADT o) {
        return o == this;
    }

    @Override
    public String toString() {
        return "<table 'TableADT'>";
    }

}
