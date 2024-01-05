package compiler.sintactic.Symbols;

public class L_args_Declf extends SimboloBase {

    private EnumType enumType;
    private String id;
    private L_args_Declf args;

    public L_args_Declf(EnumType enumType, String id, L_args_Declf args, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
        this.args = args;
    }

    public L_args_Declf(EnumType stype, String id, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
    }
}
