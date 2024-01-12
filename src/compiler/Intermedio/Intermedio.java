package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Intermedio {

    private ArrayList<Variable> tv;

    private int counterTemps = 0;

    private int counterGlobales = 0;

    private boolean esParametro = false;

    private ArrayList<Procedimiento> tp;

    public Variable añadirVariable(String id, EnumType tipo) {
        Variable v = null;
        // Se mira si es una variable temporal
        if (id == null) {
            counterTemps++;
            // El nombre de las variables temporales será tn, siendo n el número de variable volátil
            v = new Variable("t" + counterTemps, tipo, true); // esTemp a true
        } else {
            // Si no lo es, primero se observa si se trata de la declaración de una variable global
            if (tp.isEmpty()) {
                v = new Variable(id + "_" + tp.size(), tipo, false);
                counterGlobales++;
            } else { // En caso contrario, se busca en el procedimiento actual
                // Se obtiene el último procedimiento de la tabla (el actual)
                Procedimiento proc = tp.get(tp.size() - 1);
                // Se guardan las cantidades para recorrer los arrays posteriormente
                int nProcs = proc.getNumParametros();
                int nVars = nProcs + proc.getNumDeclaraciones();

                // Se recorren las variables del proceso en busca de coincidencias con el identificador
                for (int i = 0; i < nVars; i++) {
                    if (i < nProcs) {
                        // Coincidencia en los parámetros
                        if (id.equals(proc.getParametros().get(i).getId())) {
                            return proc.getParametros().get(i);
                        }
                    } else {
                        // Coincidencia en las declaraciones
                        // (a "i" se le resta el "n" de parámetros para recorrer las declaraciones desde 0)
                        if (id.equals(proc.getDeclaraciones().get(i - nProcs).getId())) {
                            return proc.getDeclaraciones().get(i - nProcs);
                        }
                    }
                }

                // Llegados a este punto, se puede asumir que la variable no existe, entonces será creada
                v = new Variable(id + "_" + tp.size(), tipo, false);
                // Se añade la variable a la lista correspondiente
                if (esParametro) {
                    proc.addParametro(v);
                } else {
                    proc.addDeclaracion(v);
                }
            }
        }
        // Se añade a la tabla de variables
        tv.add(v);
        return v;
    }

    public Procedimiento añadirProcedimiento(String id, EnumType tipo) {
        Procedimiento proc = new Procedimiento(id, tipo);
        tp.add(proc);
        return proc;
    }

    public void añadirInstruccion(Instruccion inst) {

    }
}
