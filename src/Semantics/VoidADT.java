package Semantics;

public class VoidADT extends ADT {

	public static final VoidADT VOID = new VoidADT();

	@Override
	public boolean same(ADT o) {
		if (o instanceof UndefinedADT) return true;
		return this == o;
	}

	@Override
	public boolean assignable(ADT o) {
		return same(o);
	}

	@Override
	public String toString() {
		return "()";
	}

}
