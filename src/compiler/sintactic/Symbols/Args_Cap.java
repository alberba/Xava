package compiler.sintactic.Symbols;

import java.util.ArrayList;

import compiler.sintactic.Symbol;
import compiler.sintactic.TipoElemento;

public class Args_Cap extends SimboloBase {

    private L_args_Cap l_args_cap;

    public Args_Cap(L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.l_args_cap = l_args_cap;
    }

    public L_args_Cap getL_args_cap() {
        return l_args_cap;
    }

    public void setL_args_cap(L_args_Cap l_args_cap) {
        this.l_args_cap = l_args_cap;
    }

    
}
