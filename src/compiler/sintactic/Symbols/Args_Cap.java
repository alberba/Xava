package compiler.sintactic.Symbols;

public class Args_Cap extends SimboloBase {

    private L_args_Cap l_args_cap;

    public Args_Cap(L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.l_args_cap = l_args_cap;
    }
}
