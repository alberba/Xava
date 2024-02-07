package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

public class DeclF extends SimboloBase {
    private final EnumType eType;
    private final String id;
    public DeclF (EnumType eType, String id, int linea, int columna){
        super(linea,columna);
        this.eType = eType;
        this.id = id;
    }

    public void generarIntermedio(Intermedio intermedio) {
        intermedio.añadirProcedimiento(id, eType);
    }
}
