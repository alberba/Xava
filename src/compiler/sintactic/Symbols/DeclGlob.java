package compiler.sintactic.Symbols;

public class DeclGlob {
    private Decls decls;

    public DeclGlob(Decls decls) {
        this.decls = decls;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        decls.generacionTresDirecciones(tresDirecciones);
    }
}
