package Semantics;


public abstract class ADT {
    public static final String TAB = "  ";
    public static final String MAIN_NAME = "MAIN";

    public ADT prev = null; // symbol table

    public boolean same(ADT o) { return o == this; }        // type info
    public boolean assignable(ADT o) {  // type info
        // this is assignable to o
        return o == this;
    }

    @Override
    public abstract String toString();          // type info
}
