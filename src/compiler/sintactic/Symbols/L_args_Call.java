package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class L_args_Call extends SimboloBase {
    private Value value;
    private L_args_Call l_args_call;

    public L_args_Call(Value value, L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.value = value;
        this.l_args_call = l_args_call;
    }

    public L_args_Call(Value value, int linea, int columna) {
        super(linea,columna);
        this.value = value;
    }

    public void generarIntermedio(Intermedio intermedio) {
        value.generarIntermedio(intermedio);
        if (l_args_call != null) {
            l_args_call.generarIntermedio(intermedio);
        }
    }
}
