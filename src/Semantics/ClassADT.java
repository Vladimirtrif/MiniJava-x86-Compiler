package Semantics;

import java.util.Set;

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
		return fields.keySet();
	}

	public Set<String> methodNames() {
		return methods.keySet();
	}

	public ADT getField(String s) {
		return fields.get(s);
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
		if (!hasNoCyclicExtends) {
			throw new IllegalStateException();
		}
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
		if (!hasNoCyclicExtends) {
			throw new IllegalStateException();
		}
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

	@Override
	public boolean put(String s, ADT t) {
		if (t instanceof MethodADT) {
			return methods.put(s, t);
		} else {
			return fields.put(s, t);
		}
	}

	@Override
	public boolean same(ADT o) {
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
	public String toString() {
        return "<class " + name + ">";
	}

}
