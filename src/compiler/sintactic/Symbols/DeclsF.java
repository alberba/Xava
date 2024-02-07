package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class DeclsF extends SimboloBase {

    private final DeclF declF;

    private final DeclsF declsF;

    public DeclsF(DeclF declF, DeclsF declsF, int linea, int columna){
        super(linea,columna);
        this.declF = declF;
        this.declsF = declsF;
    }

    public void generarIntermedio(Intermedio intermedio) {
        declF.generarIntermedio(intermedio);
        if(declsF !=null){
            declsF.generarIntermedio(intermedio);
        }
    }
}
