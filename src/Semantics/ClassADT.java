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
	public ADT getMethod(String s) { return methods.get(s); }
	public ADT getFieldOrDeclare(String s) { return fields.getOrDeclare(s); }
	public ADT getMethodOrDeclare(String s) { return methods.getOrDeclare(s); }

	public String putField(String s, ADT t) {
        return fields.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate field name '" + s 
			+ "' declared at class " + name + ".";
    }

	public String putMethod(String s, ADT t) {
        return methods.put(s, t) == null
            ? null
            : "DuplicateNameError: Duplicate method name '" + s 
			+ "' declared at class " + name + ".";
    }

	public String tableToString() {
		String s = "class " + name;
		if (parent != null) {
			s += " extends " + parent;
		}
		s += "\n";
		for (String fieldName : fieldNames()) {
            ADT t = this.getField(fieldName);
            s += SymbolTable.indent(2);
            s += t + " " + fieldName + "\n";
        }
        for (String methodName : methodNames()) {
			ADT t = this.getMethod(methodName);
			if (t instanceof MethodADT m) {
				s += SymbolTable.indent(2);
				s += m.tableToString();
			}
        }
        return s;
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
		if (o instanceof UndefinedADT) return true;
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
	public LinkedHashMap<String, MethodADT> deepMethods = new LinkedHashMap<>();
	public LinkedHashMap<String, ADT> deepFields = new LinkedHashMap<>();
	
	// get & getOrDeclare by searching through all superclasses
	public ADT deepgetField(String s) {
		assert(hasOffset);
		return deepFields.get(s);
	}
	public MethodADT deepgetMethod(String s) {
		assert(hasOffset);
		return deepMethods.get(s);
	}
	public ADT deepgetMethodOrDeclare(String s) {
		assert(hasOffset);
		MethodADT res = deepgetMethod(s);
		if (res == null) {
			return getMethodOrDeclare(s);
		} else {
			return res;
		}
	}

	public Map<String, Integer> methodToOffset = new HashMap<>();
	public Map<String, Integer> fieldToOffset = new HashMap<>();

	public int methodToOffset(String s) {
		assert(hasOffset);
		return methodToOffset.get(s);
	}
	public int methodToOffset(MethodADT m) {
		assert(hasOffset);
		return methodToOffset(m.name);
	}
	public int fieldToOffset(String s) {
		assert(hasOffset);
		return fieldToOffset.get(s);
	}
}
