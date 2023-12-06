package compiler.sintactic.Symbols;

public class Decls {

    private Decl decl;
    private Decls decls;

    public Decls(Decl decl, Decls decls) {
        this.decl = decl;
        this.decls = decls;
    }

    public Decls(Decl decl) {
        this.decl = decl;
    }
}
