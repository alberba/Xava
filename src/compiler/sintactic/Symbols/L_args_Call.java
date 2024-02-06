package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Variable;

import java.util.ArrayList;

public class L_args_Call extends SimboloBase {
    private final Exp exp;
    private L_args_Call l_args_call;

    public L_args_Call(Exp exp, L_args_Call l_args_call, int linea, int columna) {
        super(linea,columna);
        this.exp = exp;
        this.l_args_call = l_args_call;
    }

    public L_args_Call(Exp exp, int linea, int columna) {
        super(linea,columna);
        this.exp = exp;
    }

    /**
     * Función utilizada para calcular el número de parámetros pasados a una función
     * @return Número de parámetros obtenidos por el momento
     */
    public int getNumArgs() {
        if (l_args_call != null) {
            return l_args_call.getNumArgs() + 1;
        }
        return 1; // L_args_call siempre tendrá un value
    }

    public ArrayList<Variable> generarIntermedio(Intermedio intermedio, ArrayList<Variable> variables) {
        exp.generarIntermedio(intermedio);
        variables.add(intermedio.getUltimaVariable());
        if (l_args_call == null) {
            return variables;
        }
        for (L_args_Call aux = l_args_call; aux != null; aux = aux.getL_args_call()) {
            aux.getExp().generarIntermedio(intermedio);
            variables.add(intermedio.getUltimaVariable());
        }

        return variables;

    }

    public Exp getExp() {
        return exp;
    }

    public L_args_Call getL_args_call() {
        return l_args_call;
    }

    public void setL_args_call(L_args_Call l_args_call) {
        this.l_args_call = l_args_call;
    }
}
