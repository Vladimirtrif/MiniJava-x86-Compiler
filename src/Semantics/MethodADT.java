import java.util.*;


public class MethodADT extends SymbolTable implements ADT {

	public String name;
	public ADT returnType;
	public List<ADT> paramTypes;

	public MethodADT(String name, ADT returnType, List<ADT> paramTypes) {
        super(SymbolTable.METHOD_DEPTH);
		this.name = name;
		this.returnType = returnType;
		this.paramTypes = paramTypes;
	}

	@Override
	public boolean same(ADT o) {
		if (!(o instanceof MethodADT oo))
			return false;
		if (paramTypes.size() != oo.paramTypes.size())
			return false;
		for (int i = 0; i < paramTypes.size(); i++) {
			if (!paramTypes.get(i).same(oo.paramTypes.get(i)))
				return false;
		}
		return returnType.same(oo.returnType);
	}

	@Override
	public boolean assignable(ADT o) {
		return same(o);
	}

	@Override
	public String toString() {
        // TODO
        throw new UnsupportedOperationException();
	}

}
