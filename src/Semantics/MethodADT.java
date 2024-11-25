package Semantics;

import java.util.*;

public class MethodADT extends ADT {
	public final String name;
	public final int numParams;
	public final List<ADT> paramTypes;
	public final ADT returnType;
	private final SymbolTable table;

	public MethodADT(String name, int numParams, ADT returnType, ClassADT prev) {
		this.prev = prev;
		this.name = name;
		this.numParams = numParams;
		this.returnType = returnType;
		table = new SymbolTable();
		paramTypes = new ArrayList<>();
	}

	// SymbolTable
	public Set<String> varNames() { return table.keySet(); }
	public ADT get(String s) { return table.get(s); }
	public ADT getOrDeclare(String s) { return table.getOrDeclare(s); }
	public String put(String s, ADT t) {
		ADT res = table.put(s, t);
		if (table.size() <= numParams) {
			paramTypes.add(t);
		}
        return res == null
            ? null
            : "DuplicateNameError: Duplicate name '" + s 
			+ "' declared at method " + name
			+ " in class " + getClassADT().name + ".";
    }

	public ClassADT getClassADT() { return (ClassADT) this.prev; }

	@Override
	public boolean same(ADT o) {
		if (o instanceof UndefinedADT) return true;
		// Check MethodADT, paramTypes, and returnType
		if (!(o instanceof MethodADT oo))
			return false;
		if (paramTypes.size() != oo.paramTypes.size())
			return false;
		for (int i = 0; i < paramTypes.size(); i++) {
			if (!paramTypes.get(i).same(oo.paramTypes.get(i)))
				return false;
		}
		return returnType.same(oo.returnType);
	}

	@Override
	public boolean assignable(ADT o) { return same(o); }

	@Override
	public String toString() {
		String s = "";
		if (paramTypes.isEmpty()) {
			s += VoidADT.VOID;
		} else {
			s += "(" + paramTypes.get(0);
			for (int i = 1; i < paramTypes.size(); i++) {
				s += ", ";
				s += paramTypes.get(i);
			}
			s += ")";
		}
		s += " -> " + returnType;
		return s;
	}

	public String tableToString() {
        String s = "method " + name + " : " + this.toString() + "\n";
        for (String varName : varNames()) {
            ADT t = this.get(varName);
            s += SymbolTable.indent(3);
            s += t + " " + varName + "\n";
        }
        return s;
    }

	public ADT deepgetVar(String s) {
        ClassADT c = this.getClassADT();
        ADT t;  // n's type

        // 1. Check method scope
        t = this.get(s);	// either ADT, Undefined, or null
        if (t != null) { return t; }

        // 2. Check class scope
        t = c.deepgetField(s);	// either ADT, Undefined, or null
        if (t != null) { return t; }

		// 3. Not found
		return null;
	}

	public ADT deepgetVarOrDeclare(String s) {
		ADT t = this.deepgetVar(s);
		if (t == null) { 
			return this.getOrDeclare(s);	// null
		} else {
			return t;
		}
	}

	/**
	 * Extended features for offset calculation in Generator
	 */
	public Map<String, Integer> varToOffset = new HashMap<>();
	public int varToOffset(String s) { return varToOffset.get(s); }
}
