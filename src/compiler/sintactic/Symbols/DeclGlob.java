package compiler.sintactic.Symbols;

public class DeclGlob extends SimboloBase {
    private DeclsF declsF;
    private Decls decls;

    public DeclGlob(DeclsF declsF, Decls decls, int linea, int columna) {
        super(linea,columna);
        this.declsF =declsF;
        this.decls = decls;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        decls.generacionTresDirecciones(tresDirecciones);
    }
}
