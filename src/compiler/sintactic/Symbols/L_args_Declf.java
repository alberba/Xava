package compiler.sintactic.Symbols;

public class L_args_Declf {

    private SType stype;
    private String id;
    private L_args_Declf args;

    public L_args_Declf(SType stype, String id, L_args_Declf args) {
        this.stype = stype;
        this.id = id;
        this.args = args;
    }

    public L_args_Declf(SType stype, String id) {
        this.stype = stype;
        this.id = id;
    }
}
