package Semantics;


public abstract class ADT {

    public TableADT prev = null;

    public abstract boolean same(ADT o);
    
    public abstract boolean assignable(ADT o);

    @Override
    public abstract String toString();

}
