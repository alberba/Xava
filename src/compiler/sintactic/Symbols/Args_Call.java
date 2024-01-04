package compiler.sintactic.Symbols;

public class Args_Call extends SimboloBase {

    private L_args_Call l_args_call;

    public Args_Call(L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.l_args_call = l_args_call;
    }
}
