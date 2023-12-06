package compiler.sintactic.Symbols;

public class L_args_p {

    private SType stype;
    private String id;
    private L_args_p l_args_p;

    public L_args_p(SType stype, String id, L_args_p l_args_p) {
        this.stype = stype;
        this.id = id;
        this.l_args_p = l_args_p;
    }

    public L_args_p(SType stype, String id) {
        this.stype = stype;
        this.id = id;
    }
}
