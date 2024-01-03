package compiler.sintactic.Symbols;

public class DeclGlob {
    private DeclsF declsF;
    private Decls decls;

    public DeclGlob(DeclsF declsF, Decls decls) {
        this.declsF =declsF;
        this.decls = decls;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        decls.generacionTresDirecciones(tresDirecciones);
    }
}
