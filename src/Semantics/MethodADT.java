package Semantics;

import java.util.*;

public class MethodADT extends SymbolTable implements ADT {

	public static final String MAIN_METHOD_NAME = "main";

	public final String name;
	public final List<ADT> paramTypes;
	public final ADT returnType;

	public MethodADT(String name, ADT returnType, ClassADT prev) {
		super(prev);
		this.name = name;
		paramTypes = new ArrayList<>();
		this.returnType = returnType;
	}

	public void addParamType(ADT t) {
		paramTypes.add(t);
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
