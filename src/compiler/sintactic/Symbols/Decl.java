package compiler.sintactic.Symbols;

import compiler.sintactic.Symbols.D_asig;
import compiler.sintactic.Symbols.Lid;
import compiler.sintactic.Symbols.Type;

public class Decl extends SimboloBase {

    private Type type;
    private Lid lid;
    private String id;
    private String num;
    private D_asig d_asignacion;

    public Decl(Type type, Lid lid, D_asig d_asignacion, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.lid = lid;
        this.d_asignacion = d_asignacion;
    }

    public Decl(Type type, String id, String num, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.id = id;
        this.num = num;
    }


}
