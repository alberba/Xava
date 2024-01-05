package compiler.sintactic.Symbols;

public class L_args_Cap extends SimboloBase {

    private EnumType enumType;
    private String id;
    private L_args_Cap l_args_cap;

    public L_args_Cap(EnumType enumType, String id, L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
        this.l_args_cap = l_args_cap;
    }

    public L_args_Cap(EnumType enumType, String id, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
    }
}
