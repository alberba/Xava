package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Variable;

import java.util.ArrayList;

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
        value.generarIntermedio(intermedio);
        variables.add(intermedio.getUltimaVariable());
        if (l_args_call == null) {
            return variables;
        }
        for (L_args_Call aux = l_args_call; aux != null; aux = aux.getL_args_call()) {
            aux.getValue().generarIntermedio(intermedio);
            variables.add(intermedio.getUltimaVariable());
        }

        return variables;

    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public L_args_Call getL_args_call() {
        return l_args_call;
    }

    public void setL_args_call(L_args_Call l_args_call) {
        this.l_args_call = l_args_call;
    }
}
