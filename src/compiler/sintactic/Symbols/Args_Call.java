package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Args_Call extends SimboloBase {

    private L_args_Call l_args_call;

    public Args_Call(L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.l_args_call = l_args_call;
    }

    @Override
    public void generarIntermedio(Intermedio intermedio) {
        if(l_args_call != null){
            l_args_call.generarIntermedio(intermedio);
        }
    }
}
