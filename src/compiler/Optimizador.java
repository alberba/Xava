package compiler;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Optimizador {


    private final Intermedio intermedio;
    private boolean hayCambios = true;

    public Optimizador(Intermedio intermedio) {
        this.intermedio = intermedio;
    }

    public Intermedio optimizarIntermedio() {
        while (hayCambios) {
            hayCambios = false;
            asignDiferidas();
        }
        return intermedio;
    }

    private void asignDiferidas() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Variable> variables = intermedio.getTv();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList<Variable> variablesAEliminar = new ArrayList<>();
        ArrayList<String> variablesAnalizadas = new ArrayList<>();


        Instruccion asigDiferida;
        for (int i = 0; i < instrucciones.size(); i++) {
            asigDiferida = instrucciones.get(i);

            System.out.println(asigDiferida);
            if (!variablesAnalizadas.contains(asigDiferida.getDestino()) && asigDiferida.getOperacion() == OperacionInst.ASIG &&
                    intermedio.buscarVariable(asigDiferida.getDestino()).isEsTemp()) {

                boolean otraAsig = false;
                ArrayList <Instruccion> instruccionesAux = new ArrayList<>();
                for (int j = i + 1; j < instrucciones.size() && !otraAsig; j++) {

                    Instruccion aux = instrucciones.get(j);
                    if (esOperandoIgual(aux.getOperador1(), asigDiferida.getDestino()) || esOperandoIgual(aux.getOperador2(), asigDiferida.getDestino())) {
                        instruccionesAux.add(aux);
                    } else {

                        if (esOperandoIgual(aux.getDestino(), asigDiferida.getDestino())) {
                            otraAsig = true;
                        }
                    }
                }
                if (otraAsig) {
                    variablesAnalizadas.add(asigDiferida.getDestino());
                } else {
                    hayCambios = true;
                    for(Instruccion aux: instruccionesAux){
                        if (actualizarInstruccion(aux, asigDiferida)) {
                            instruccionesAEliminar.add(asigDiferida);
                            variablesAnalizadas.add(asigDiferida.getDestino());
                            variablesAEliminar.add(intermedio.buscarVariable(asigDiferida.getDestino()));
                        }
                    }
                }
            }
        }

        if (!instruccionesAEliminar.isEmpty()) {
            // Eliminación de las instrucciones y variables que ya no se usan
            instrucciones.removeAll(instruccionesAEliminar);
            variables.removeAll(variablesAEliminar);

            // Actualización de la lista de instrucciones y variables
            intermedio.setTv(variables);
            intermedio.setCodigo(instrucciones);
        }
    }

    // Método refactorizado para actualizar la instrucción
    private boolean actualizarInstruccion(Instruccion aux, Instruccion asigDiferida) {

        if (esOperandoIgual(aux.getOperador1(), asigDiferida.getDestino())) {
            aux.setOperador1(asigDiferida.getOperador1());
            return true;
        }
        if (esOperandoIgual(aux.getOperador2(), asigDiferida.getDestino())) {
            aux.setOperador2(asigDiferida.getOperador1());
            return true;
        }

        return false;
    }

    // Método auxiliar para verificar si los operandos son iguales
    private boolean esOperandoIgual(String operando1, String operando2) {
        return operando1 != null && operando1.equals(operando2);
    }

}
