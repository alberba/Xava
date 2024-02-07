package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

public class Args_Cap extends SimboloBase {

    private final L_args_Cap l_args_cap;

    public Args_Cap(L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.l_args_cap = l_args_cap;
    }

    public L_args_Cap getL_args_cap() {
        return l_args_cap;
    }


    public void generarIntermedio(Intermedio intermedio) {
        if (l_args_cap != null) {
            intermedio.setEsParametro(true);
            l_args_cap.generarIntermedio(intermedio);
            intermedio.setEsParametro(false);
        }
    }
}
