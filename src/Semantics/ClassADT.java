package Semantics;

public class ClassADT extends SymbolTable implements ADT {

	public String name;
    public String parentName;
    public ClassADT parent = null;

    public ClassADT(String name, GlobalTable prev) {
        super(prev);
        this.name = name;
    }

	public ClassADT(String name, String parentName, GlobalTable prev) {
        this(name, prev);
        this.parentName = parentName;
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
        // TODO
        throw new UnsupportedOperationException();
	}

}
