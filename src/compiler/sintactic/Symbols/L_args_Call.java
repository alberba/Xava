package compiler.sintactic.Symbols;

public class L_args_Call extends SimboloBase {
    private String id;
    private L_args_Call l_args_call;

    public L_args_Call(String id, L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.l_args_call = l_args_call;
    }

    public L_args_Call(String id, int linea, int columna) {
        super(linea,columna);
        this.id = id;
    }
}
