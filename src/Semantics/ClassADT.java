package Semantics;

import java.util.*;

public class ClassADT extends TableADT {

	public String name;
    public String parentName;
    public ClassADT parent = null;

	private TableADT fields;
	private TableADT methods;

	public boolean hasNoCyclicExtends;

    public ClassADT(String name, GlobalADT prev) {
        super(prev);
        this.name = name;
		hasNoCyclicExtends = true;
		fields = this;
		methods = new TableADT(prev);
    }

	public ClassADT(String name, String parentName, GlobalADT prev) {
        this(name, prev);
        this.parentName = parentName;
	}

	public Set<String> fieldNames() {
		return super.keySet();
	}

	public Set<String> methodNames() {
		return methods.keySet();
	}

	public ADT getField(String s) {
		return super.get(s);
	}

	public MethodADT getMethod(String s) {
		return (MethodADT) methods.get(s);
	}

	public ADT getFieldOrDeclare(String s) {
		return fields.getOrDeclare(s);
	}

	public MethodADT getMethodOrDeclare(String s) {
		return (MethodADT) methods.getOrDeclare(s);
	}

	public ADT deepgetField(String s) {
		if (!hasNoCyclicExtends) { throw new IllegalStateException(); }
		ClassADT c = this;
		while (c != null) {
			ADT res = c.getField(name);
			if (res != null) {
				return res;
			}
			c = c.parent;
		}
		return null;
	}

	public MethodADT deepgetMethod(String s) {
		if (!hasNoCyclicExtends) { throw new IllegalStateException(); }
		ClassADT c = this;
		while (c != null) {
			MethodADT res = c.getMethod(name);
			if (res != null) {
				return res;
			}
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

	public MethodADT deepgetMethodOrDeclare(String s) {
		MethodADT res = deepgetMethod(s);
		if (res == null) {
			return getMethodOrDeclare(s);
		} else {
			return res;
		}
	}

	public String putField(String s, ADT t) {
		return super.put(s, t);
	}

	public String putMethod(String s, MethodADT t) {
		return methods.put(s, t);
	}

	@Override
	public String put(String s, ADT t) {
		throw new UnsupportedOperationException("Usage: putField, putMethod");
	}

	@Override
	public ADT get(String s) {
		throw new UnsupportedOperationException("Usage: getField, getMethod");
	}

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

	@Override
	public String tableToString() {
		String s = "class " + name + "\n";
		for (String fieldName : fieldNames()) {
            ADT t = getField(fieldName);
            s += TableADT.indent(depth + 1);
            s += t + " " + fieldName + "\n";
        }
        for (String methodName : methodNames()) {
            MethodADT m = getMethod(methodName);
            s += TableADT.indent(depth + 1);
            s += m.tableToString();
        }
        return s;
	}

	/**
	 * Extended features for offset calculation in Generator
	 */
	public List<MethodADT> allMethods = new ArrayList<>();
	public List<String> allFields = new ArrayList<>();
	public Map<String, Integer> methodToOffset = new HashMap<>();
	public Map<String, Integer> fieldToOffset = new HashMap<>();
	public int methodToOffset(String s) { return methodToOffset.get(s); }
	public int methodToOffset(MethodADT m) { return methodToOffset(m.name); }
	public int fieldToOffset(String s) { return fieldToOffset.get(s); }
}
