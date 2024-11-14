package Semantics;

public class UndefinedADT extends ADT {

	public static final UndefinedADT UNDEFINED = new UndefinedADT();

	@Override
	public boolean same(ADT o) {
		return true;
	}

	@Override
	public boolean assignable(ADT o) {
		return true;
	}

	@Override
	public String toString() {
		return "any";
	}

}
