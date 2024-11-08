package Semantics;

import java.util.Set;

public class GlobalADT extends TableADT {

    public GlobalADT() {
        super();
    }

    public Set<String> classNames() {
        return this.keySet();
    }

    @Override
    public ClassADT get(String s) {
        return (ClassADT) super.get(s);
    }

    @Override
    public ClassADT getOrDeclare(String s) {
        return (ClassADT) super.getOrDeclare(s);
    }

    @Override
    public String toString() {
        return "<table 'GlobalADT'>";
    }

}
