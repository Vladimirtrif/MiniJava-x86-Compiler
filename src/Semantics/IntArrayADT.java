public class IntArrayADT implements ADT {

	public final int length;

	public IntArrayADT(int length) {
		this.length = length;
	}

	@Override
	public boolean same(ADT other) {
		if (!(other instanceof IntArrayADT))
			return false;
		IntArrayADT otherArray = (IntArrayADT) other;
		return length == otherArray.length;
	}

	@Override
	public boolean assignable(ADT other) {
		return other instanceof IntArrayADT;
	}

	@Override
	public String toString() {
		return "IntArrayADT-(" + length + ")";
	}

}
