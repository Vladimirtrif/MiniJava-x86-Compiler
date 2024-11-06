package Semantics;

public class UndefinedADT implements ADT {

	public static final UndefinedADT UNDEFINED = new UndefinedADT();

	@Override
	public String toString() {
		return "undefined";
	}

	@Override
	public boolean same(ADT other) {
		return this == other;
	}

	@Override
	public boolean assignable(ADT other) {
		return this == other;
	}

}
