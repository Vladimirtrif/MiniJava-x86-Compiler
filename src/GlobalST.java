import AST.*;
import java.util.*;

public class GlobalST {
    public HashMap<String, ClassST> mapClassST = new HashMap<>();;
    private boolean error = false;

    public GlobalST(Program program) {
        this.mapClassST = new HashMap<>();
        if (program == null) {
            error = true;
            return;
        }
        for (int i = 0; i < program.cl.size(); i++) {
            ClassDecl c = program.cl.get(i);
            if (c instanceof ClassDeclSimple cc) {
                mapClassST.put(cc.i, new ClassST(cc, this));
            } else if (c instanceof ClassDeclExtends cc) {
                mapClassST.put(cc.i, new ClassST(cc, this));
            }
        }
    }

    public ClassST get(String id) {
        return mapClassST.get(id);
    }

    public boolean getError() {
        return error;
    }
}
