package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Decls extends SimboloBase {

    private Decl decl;
    private Decls decls;

    public Decls(Decl decl, Decls decls, int linea, int columna) {
        super(linea,columna);
        this.decl = decl;
        this.decls = decls;
    }

    public void generarIntermedio(Intermedio intermedio) {
        decl.generarIntermedio(intermedio);
        if (decls != null) {
            decls.generarIntermedio(intermedio);
        }
    }

}
