package Semantics;

import java.util.*;

public class MethodADT extends TableADT {

	public static final String MAIN_METHOD_NAME = "MAIN";

	public final String name;
	public final List<ADT> paramTypes;
	public final ADT returnType;

	public MethodADT(String name, ADT returnType, ClassADT prev) {
		super(prev);
		this.name = name;
		paramTypes = new ArrayList<>();
		this.returnType = returnType;
	}

	public ClassADT getClassADT() {
		return (ClassADT) this.prev;
	}

	public void addParamType(ADT t) {
		paramTypes.add(t);
	}

	@Override
	public boolean same(ADT o) {
		if (o instanceof UndefinedADT) return true;
		// Check MethodADT, paramTypes, and returnType
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
		String s = "";
		if (paramTypes.isEmpty()) {
			s += VoidADT.VOID;
		} else {
			s += "(" + paramTypes.get(0);
			for (int i = 1; i < paramTypes.size(); i++) {
				s += ", ";
				s += paramTypes.get(i);
			}
			s += ")";
		}
		s += " -> " + returnType;
		return s;
	}

	@Override
	public String tableToString() {
        String s = "method " + name + " : " + this.toString() + "\n";
        for (String varName : this.keySet()) {
            ADT t = this.get(varName);
            s += indent(depth + 1);
            s += t + " " + varName + "\n";
        }
        return s;
    }

	/**
	 * Extended features for offset calculation in Generator
	 */
	public Map<String, Integer> varToOffset = new HashMap<>();
	public int varToOffset(String s) { return varToOffset.get(s); }
}
