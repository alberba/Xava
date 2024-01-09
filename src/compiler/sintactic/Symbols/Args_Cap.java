package compiler.sintactic.Symbols;

import java.util.ArrayList;

import compiler.sintactic.Symbol;

public class Args_Cap extends SimboloBase {

    private L_args_Cap l_args_cap;

    public Args_Cap(L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.l_args_cap = l_args_cap;
    }

    public ArrayList<Symbol> getArgs() {
        ArrayList<Symbol> args = new ArrayList<Symbol>();
        for (L_args_Cap l_args_cap = this.l_args_cap; l_args_cap != null; l_args_cap = l_args_cap.getL_args_cap()) {
            Symbol s = new Symbol(l_args_cap.getId(), l_args_cap.getEnumType(), TipoElemento.VARIABLE);
            args.add(s);
        }
    }

    public L_args_Cap getL_args_cap() {
        return l_args_cap;
    }

    public void setL_args_cap(L_args_Cap l_args_cap) {
        this.l_args_cap = l_args_cap;
    }

    
}
