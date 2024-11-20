package Semantics;

import java.util.Set;

public class GlobalADT extends ADT {
    private final SymbolTable table;

    public GlobalADT() {
        table = new SymbolTable();
    }

	public Set<String> classNames() { return table.keySet(); }
	public ClassADT get(String s) { return (ClassADT) table.get(s); }
	public ADT getOrDeclare(String s) { return table.getOrDeclare(s); }
	public String put(String s, ADT t) {
        return table.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate class name '" + s + "' declared.";
    }

    @Override
    public String toString() {
        return "global";
    }

    public String tableToString() {
        String s = "global\n";
        for (String className : classNames()) {
            ClassADT c = (ClassADT) this.get(className);
            s += SymbolTable.indent(1);
            s += c.tableToString();
        }
        return s;
    }
}
