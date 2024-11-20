package Semantics;

import java.util.*;

public class ClassADT extends ADT {

	public String name;
    public String parentName;
    public ClassADT parent = null;

	private SymbolTable fields;
	private SymbolTable methods;

	public boolean hasNoCyclicExtends;

    public ClassADT(String name, GlobalADT prev) {
        this.prev = prev;
        this.name = name;
		hasNoCyclicExtends = true;
		fields = new SymbolTable();
		methods = new SymbolTable();
    }

	public ClassADT(String name, String parentName, GlobalADT prev) {
        this(name, prev);
		hasNoCyclicExtends = false;
        this.parentName = parentName;
	}

	// SymbolTable operations

	public Set<String> fieldNames() { return fields.keySet(); }
	public Set<String> methodNames() { return methods.keySet(); }
	public ADT getField(String s) { return fields.get(s); }
	public MethodADT getMethod(String s) { return (MethodADT) methods.get(s); }
	public ADT getFieldOrDeclare(String s) { return fields.getOrDeclare(s); }
	public ADT getMethodOrDeclare(String s) { return methods.getOrDeclare(s); }

	public String putField(String s, ADT t) {
        return fields.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate field name '" + s 
			+ "' declared at class " + name + ".";
    }

	public String putMethod(String s, ADT t) {
        return fields.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate method name '" + s 
			+ "' declared at class " + name + ".";
    }

	public String tableToString() {
		String s = "class " + name + "\n";
		for (String fieldName : fieldNames()) {
            ADT t = getField(fieldName);
            s += SymbolTable.indent(2);
            s += t + " " + fieldName + "\n";
        }
        for (String methodName : methodNames()) {
            MethodADT m = getMethod(methodName);
            s += SymbolTable.indent(2);
            s += m.tableToString();
        }
        return s;
	}

	// get & getOrDeclare by searching through all superclasses

	public ADT deepgetField(String s) {
		if (!hasNoCyclicExtends) { throw new IllegalStateException(); }
		ClassADT c = this;
		while (c != null) {
			ADT res = c.getField(name);
			if (res != null) { return res; }
			c = c.parent;
		}
		return null;
	}

	public MethodADT deepgetMethod(String s) {
		if (!hasNoCyclicExtends) { throw new IllegalStateException(); }
		ClassADT c = this;
		while (c != null) {
			MethodADT res = c.getMethod(name);
			if (res != null) { return res; }
			c = c.parent;
		}
		return null;
	}

	public ADT deepgetFieldOrDeclare(String s) {
		ADT res = deepgetField(s);
		if (res == null) {
			return getFieldOrDeclare(s);
		} else {
			return res;
		}
	}

	public ADT deepgetMethodOrDeclare(String s) {
		MethodADT res = deepgetMethod(s);
		if (res == null) {
			return getMethodOrDeclare(s);
		} else {
			return res;
		}
	}

	// ADT operations

	@Override
	public boolean same(ADT o) {
		if (o instanceof UndefinedADT) return true;
		if (!(o instanceof ClassADT oo))
			return false;
		return name.equals(oo.name);
	}

	@Override
	public boolean assignable(ADT o) {
		if (!(o instanceof ClassADT oo))
			return false;
		if (name.equals(oo.name))
			return true;
		while (oo.parent != null) {
			oo = oo.parent;
            if (name.equals(oo.name))
                return true;
		}
		return false;
	}

	@Override
	public String toString() { return name; }

	/**
	 * Extended features for offset calculation in GeneratorVisitor
	 */
	public boolean hasOffset = false;
	public LinkedHashMap<String, MethodADT> allMethods = new LinkedHashMap<>();
	public LinkedHashMap<String, ADT> allFields = new LinkedHashMap<>();
	public Map<String, Integer> methodToOffset = new HashMap<>();
	public Map<String, Integer> fieldToOffset = new HashMap<>();
	public int methodToOffset(String s) {
		if (!hasNoCyclicExtends || !hasOffset) {
			throw new IllegalStateException(); 
		}
		return methodToOffset.get(s);
	}
	public int methodToOffset(MethodADT m) { return methodToOffset(m.name); }
	public int fieldToOffset(String s) {
		if (!hasNoCyclicExtends || !hasOffset) {
			throw new IllegalStateException();
		}
		return fieldToOffset.get(s);
	}
}
