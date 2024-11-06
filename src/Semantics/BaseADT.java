public class BaseADT implements ADT {

	public static final BaseADT INTEGER = new BaseADT("int");
	public static final BaseADT BOOLEAN = new BaseADT("boolean");

    private final String s;

	private BaseADT(String s) {
		this.s = s;
	}

	@Override
	public String toString() {
		return s;
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
