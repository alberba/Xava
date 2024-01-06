package compiler.sintactic.Symbols;

public class Decls extends SimboloBase {

    private Decl decl;
    private Decls decls;

    public Decls(Decl decl, Decls decls, int linea, int columna) {
        super(linea,columna);
        this.decl = decl;
        this.decls = decls;
    }

}
