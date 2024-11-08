package Semantics;

public class VoidADT extends ADT {

	public static final VoidADT VOID = new VoidADT();

	@Override
	public boolean same(ADT other) {
		return this == other;
	}

	@Override
	public boolean assignable(ADT other) {
		return this == other;
	}

	@Override
	public String toString() {
		return "void";
	}

}
