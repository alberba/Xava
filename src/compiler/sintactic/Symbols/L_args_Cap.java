package compiler.sintactic.Symbols;

public class L_args_Cap {

    private SType stype;
    private String id;
    private L_args_Cap l_args_cap;

    public L_args_Cap(SType stype, String id, L_args_Cap l_args_cap) {
        this.stype = stype;
        this.id = id;
        this.l_args_cap = l_args_cap;
    }

    public L_args_Cap(SType stype, String id) {
        this.stype = stype;
        this.id = id;
    }
}
