package Semantics;

public class UndefinedADT extends ADT {

	public static final UndefinedADT UNDEFINED = new UndefinedADT();

	@Override
	public boolean same(ADT other) {
		return true;
	}

	@Override
	public boolean assignable(ADT other) {
		return true;
	}

	@Override
	public String toString() {
		return "undefined";
	}

}
