package compiler.sintactic.Symbols;

public class L_args_Declf extends SimboloBase {

    private SType stype;
    private String id;
    private L_args_Declf args;

    public L_args_Declf(SType stype, String id, L_args_Declf args, int linea, int columna) {
        super(linea,columna);
        this.stype = stype;
        this.id = id;
        this.args = args;
    }

    public L_args_Declf(SType stype, String id, int linea, int columna) {
        super(linea,columna);
        this.stype = stype;
        this.id = id;
    }
}
