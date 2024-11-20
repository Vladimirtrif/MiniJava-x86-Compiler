package Semantics;


public abstract class ADT {
    public static final String TAB = "  ";

    public ADT prev = null; // symbol table

    public boolean same(ADT o) { return o == this; }        // type info
    public boolean assignable(ADT o) { return o == this; }  // type info

    @Override
    public abstract String toString();          // type info
}
