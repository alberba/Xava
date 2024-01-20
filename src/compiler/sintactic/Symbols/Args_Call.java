package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Variable;

import java.util.ArrayList;

public class Args_Call extends SimboloBase {

    private L_args_Call l_args_call;

    public Args_Call(L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.l_args_call = l_args_call;
    }

    public ArrayList<Variable> generarIntermedio(Intermedio intermedio, ArrayList<Variable> variables) {
        if(l_args_call != null){
            variables = l_args_call.generarIntermedio(intermedio, variables);
        }
        return variables;
    }
}
