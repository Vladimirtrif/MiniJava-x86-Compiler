import java.util.*;
import AST.*;

public class ClassST {

    public final String id;
    HashMap<String, TypeADT> varDeclList;
    HashMap<String, MethodST> methodDeclList;
    GlobalST prev = Binding;

    public ClassST(MainClass c) {
        // TODO: Complete later
    }

    public ClassST(ClassDeclSimple c) {
        id = c.i;
    }

    public ClassST(ClassDeclExtends c) {

    }

    // public ClassST() {
    //     this(null);
    // }

    // public ClassST(String extendsName) {
    //     varDeclList = new HashMap();
    //     methodDeclList = new HashMap();
    //     this.extendsName = extendsName;
    // }
}

