package compiler.sintactic.Symbols;

public class L_args_Call {

    private SType stype;
    private String id;
    private L_args_Call l_args_call;

    public L_args_Call(String id, L_args_Call l_args_call) {
        this.id = id;
        this.l_args_call = l_args_call;
    }

    public L_args_Call(String id) {
        this.id = id;
    }
}
