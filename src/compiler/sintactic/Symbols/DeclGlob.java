package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class DeclGlob extends SimboloBase {
    private DeclsF declsF;
    private Decls decls;

    public DeclGlob(DeclsF declsF, Decls decls, int linea, int columna) {
        super(linea,columna);
        this.declsF =declsF;
        this.decls = decls;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if (decls != null) {
            decls.generarIntermedio(intermedio);
        }

        if (declsF != null) {
            declsF.generarIntermedio(intermedio);
        }
    }

}
