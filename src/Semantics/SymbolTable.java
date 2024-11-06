import java.util.*;


public class SymbolTable {

    public static final String TAB = "  ";
    public static final int GLOBAL_DEPTH = 0;
    public static final int CLASS_DEPTH = 1;
    public static final int METHOD_DEPTH = 2;

    private final HashMap<String, ADT> table;
    private final int depth;

    public SymbolTable(int depth) {
        table = new HashMap<>();
        this.depth = depth;
    }

    public ADT get(String s) {
        return table.get(s);
    }

    public void put(String s, ADT t) {
        table.put(s, t);
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
