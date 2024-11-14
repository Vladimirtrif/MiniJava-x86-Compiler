package Semantics;


public class BaseADT extends ADT {

	public static final BaseADT INT = new BaseADT("int");
	public static final BaseADT BOOLEAN = new BaseADT("boolean");
	public static final BaseADT INT_ARRAY = new BaseADT("int[]");

    private final String s;

	private BaseADT(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
	}

	@Override
	public boolean same(ADT o) {
		if (o instanceof UndefinedADT) return true;
		return this == o;
	}

	@Override
	public boolean assignable(ADT o) {
		return this == o;
	}

}
