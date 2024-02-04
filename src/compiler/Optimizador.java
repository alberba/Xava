package compiler;

import compiler.Intermedio.*;

import java.util.*;

public class Optimizador {


    private final Intermedio intermedio;
    private boolean hayCambios = true;

    public Optimizador(Intermedio intermedio) {
        this.intermedio = intermedio;
    }

    public Intermedio optimizarIntermedio() {
        while (hayCambios) {
            hayCambios = false;
            diferidasAndbranc();
            hayOperacionesConstantes();
            eliminarEtiquetasNoAccesibles();
            eliminarCodigoMuerto();
        }
        eliminarVariablesnoUsadas();
        return intermedio;

    }

    private void eliminarVariablesnoUsadas() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Variable> variables = intermedio.getTv();
        ArrayList<Variable> variablesAEliminar = new ArrayList<>();
        ArrayList<String> variablesAnalizadas = new ArrayList<>();

        for (Instruccion instruccion : instrucciones) {
            if (!variablesAnalizadas.contains(instruccion.getDestino())) {
                variablesAnalizadas.add(instruccion.getDestino());
            }
            if (!variablesAnalizadas.contains(instruccion.getOperador1())) {
                variablesAnalizadas.add(instruccion.getOperador1());
            }
            if (!variablesAnalizadas.contains(instruccion.getOperador2())) {
                variablesAnalizadas.add(instruccion.getOperador2());
            }
        }

        for (Variable variable : variables) {
            if (!variablesAnalizadas.contains(variable.getId())) {
                variablesAEliminar.add(variable);
            }
        }

        if (!variablesAEliminar.isEmpty()) {
            // Eliminación de las variables que ya no se usan
            variables.removeAll(variablesAEliminar);

            // Actualización de la lista de variables
            intermedio.setTv(variables);
        }

    }

    private void diferidasAndbranc(){
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Variable> variables = intermedio.getTv();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList<Variable> variablesAEliminar = new ArrayList<>();
        ArrayList<String> variablesAnalizadas = new ArrayList<>();
        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            boolean eliminado = false;
            instruccion = instrucciones.get(i);

            if (!variablesAnalizadas.contains(instruccion.getDestino()) && instruccion.getOperacion() == OperacionInst.ASIG
                    && intermedio.buscarVariable(instruccion.getDestino()).isEsTemp()) {

                boolean otraAsig = false;
                ArrayList <Instruccion> instruccionesAux = new ArrayList<>();
                for (int j = i + 1; j < instrucciones.size() && !otraAsig; j++) {

                    Instruccion aux = instrucciones.get(j);
                    if (esOperandoIgual(aux.getOperador1(), instruccion.getDestino()) || esOperandoIgual(aux.getOperador2(), instruccion.getDestino())) {
                        instruccionesAux.add(aux);
                    } else {

                        if (esOperandoIgual(aux.getDestino(), instruccion.getDestino())) {
                            if (aux.getOperacion() == OperacionInst.RETORNO) {
                                instruccionesAux.add(aux);
                            } else {
                                otraAsig = true;
                            }
                        }
                    }
                }
                if (otraAsig) {
                    variablesAnalizadas.add(instruccion.getDestino());
                } else {
                    hayCambios = true;
                    for(Instruccion aux: instruccionesAux){
                        if (actualizarInstruccion(aux, instruccion)) {
                            eliminado = true;
                            instruccionesAEliminar.add(instruccion);
                            variablesAnalizadas.add(instruccion.getDestino());
                            variablesAEliminar.add(intermedio.buscarVariable(instruccion.getDestino()));
                        }
                    }
                }
            }
            if (eliminado) {
                continue;
            }
            switch (instruccion.getOperacion()) {
                case SALTO_COND:
                case MENOR:
                case MENOR_IGUAL:
                case IGUAL:
                case DIFERENTE:
                case MAYOR:
                case MAYOR_IGUAL:
                case NO:
                    boolean encontrado = false;
                    for (int j = i + 1; j + 1 < instrucciones.size() && !encontrado; j++) {
                        if (instrucciones.get(j).getOperacion() == OperacionInst.ETIQUETA) {
                            encontrado = true;
                            if(instrucciones.get(j).getDestino().equals(instruccion.getDestino())) {
                                if (instrucciones.get(j + 1).getOperacion() == OperacionInst.SALTO_INCON) {
                                    hayCambios = true;
                                    instruccion.setDestino(instrucciones.get(j + 1).getDestino());
                                }
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

    private void hayOperacionesConstantes() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();

        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            String operacion;
            // Si la operación no es constante, se salta a la siguiente
            if (!esOperacionConstante(instruccion)) {
                continue;
            }

            boolean condicionCumplida = false;
            boolean esLogica = false;
            hayCambios = true;
            switch (instruccion.getOperacion()) {
                case SUMA:
                    operacion = String.valueOf(Integer.parseInt(instruccion.getOperador1()) + Integer.parseInt(instruccion.getOperador2()));
                    instrucciones.set(i, new Instruccion(OperacionInst.ASIG, operacion, null, instruccion.getDestino()));
                    break;
                case RESTA:
                    operacion = String.valueOf(Integer.parseInt(instruccion.getOperador1()) - Integer.parseInt(instruccion.getOperador2()));
                    instrucciones.set(i, new Instruccion(OperacionInst.ASIG, operacion, null, instruccion.getDestino()));
                    break;
                case MULTIPLICACION:
                    operacion = String.valueOf(Integer.parseInt(instruccion.getOperador1()) * Integer.parseInt(instruccion.getOperador2()));
                    instrucciones.set(i, new Instruccion(OperacionInst.ASIG, operacion, null, instruccion.getDestino()));
                    break;
                case DIVISION:
                    operacion = String.valueOf(Integer.parseInt(instruccion.getOperador1()) / Integer.parseInt(instruccion.getOperador2()));
                    instrucciones.set(i, new Instruccion(OperacionInst.ASIG, operacion, null, instruccion.getDestino()));
                    break;
                case MODULO:
                    operacion = String.valueOf(Integer.parseInt(instruccion.getOperador1()) % Integer.parseInt(instruccion.getOperador2()));
                    instrucciones.set(i, new Instruccion(OperacionInst.ASIG, operacion, null, instruccion.getDestino()));
                    break;
                case IGUAL:
                    esLogica = true;
                    // Sirve tanto para ints como para chars
                    condicionCumplida = instruccion.getOperador1().equals(instruccion.getOperador2());
                    break;
                case DIFERENTE:
                    esLogica = true;
                    // Sirve tanto para ints como para chars
                    condicionCumplida = !instruccion.getOperador1().equals(instruccion.getOperador2());
                    break;
                case MENOR:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) < Integer.parseInt(instruccion.getOperador2());
                    break;
                case MENOR_IGUAL:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) <= Integer.parseInt(instruccion.getOperador2());
                    break;
                case MAYOR:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) > Integer.parseInt(instruccion.getOperador2());
                    break;
                case MAYOR_IGUAL:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) >= Integer.parseInt(instruccion.getOperador2());
                    break;
                case SALTO_COND:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) == 0;
                    break;
            }

            if (esLogica) {
                if (condicionCumplida) {
                    instrucciones.set(i, new Instruccion(OperacionInst.SALTO_INCON, null, null, instruccion.getDestino()));
                } else {
                    instrucciones.remove(instruccion);
                    i--;
                }
            }

        }

    }

    private void eliminarEtiquetasNoAccesibles() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        TreeMap<String, Integer> labels = new TreeMap<>();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList<Integer> indexEtiquetas = new ArrayList<>();

        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            switch (instruccion.getOperacion()) {
                case ETIQUETA:
                    indexEtiquetas.add(i);
                    if (!labels.containsKey(instruccion.getDestino())) {
                        labels.put(instruccion.getDestino(), 0);
                    }
                    break;
                case NO:
                case Y:
                case O:
                case IGUAL:
                case DIFERENTE:
                case MENOR:
                case MENOR_IGUAL:
                case MAYOR:
                case MAYOR_IGUAL:
                case SALTO_INCON:
                case SALTO_COND:
                case LLAMADA:
                    labels.put(instruccion.getDestino(), 1);
                    break;
            }
        }
        if (labels.containsValue(0)) {
            for (Integer indexEtiqueta : indexEtiquetas) {
                instruccion = instrucciones.get(indexEtiqueta);
                if (labels.get(instruccion.getDestino()) == 0) {
                    hayCambios = true;
                    instruccionesAEliminar.add(instruccion);
                }
            }
        }

        if (!instruccionesAEliminar.isEmpty()) {
            // Eliminación de las instrucciones que ya no se usan
            instrucciones.removeAll(instruccionesAEliminar);

            // Actualización de la lista de instrucciones
            intermedio.setCodigo(instrucciones);
        }
    }

    private void eliminarCodigoMuerto() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Procedimiento> procedimientos = intermedio.getTp();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList <Procedimiento> prodAEliminar = new ArrayList<>();

        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            switch (instruccion.getOperacion()) {
                case RETORNO:
                case SALTO_INCON:
                    for(int j = i + 1; j < instrucciones.size(); j++) {
                        Instruccion aux = instrucciones.get(j);
                        if (aux.getOperacion() == OperacionInst.ETIQUETA) {
                            if (aux.getDestino().equals(instruccion.getDestino())) {
                                instruccionesAEliminar.add(instruccion);
                                instruccionesAEliminar.add(aux);
                                i = j + 1;
                            } else {
                                i = j;
                            }
                            break;
                        }
                        instruccionesAEliminar.add(aux);
                    }
                    break;
                case INICIALIZACION:
                    boolean encontrado = false;
                    for (Instruccion aux : instrucciones) {
                        if (aux.getOperacion() == OperacionInst.LLAMADA && aux.getDestino().equals("e_"
                                + instruccion.getDestino())) {
                            encontrado = true;
                            break;
                        }
                    }
                    if (encontrado) {
                        break;
                    }
                    for (Procedimiento proc : intermedio.getTp()) {
                        if (proc.getId().equals(instruccion.getDestino())) {
                            prodAEliminar.add(proc);
                            break;
                        }
                    }

                    for(int j = i - 1; j < instrucciones.size(); j++) {
                        instruccion = instrucciones.get(j);
                        if (instruccion.getOperacion() == OperacionInst.ETIQUETA &&
                                instruccion.getDestino().contains("e_")){
                            i = j;
                            break;
                        }
                        instruccionesAEliminar.add(instruccion);
                    }
                    break;
                default:
                    break;
            }
        }

        if (!instruccionesAEliminar.isEmpty()) {
            // Eliminación de las instrucciones que ya no se usan
            instrucciones.removeAll(instruccionesAEliminar);
            if (prodAEliminar.isEmpty()) {
                procedimientos.removeAll(prodAEliminar);
                intermedio.setTp(procedimientos);
            }

            // Actualización de la lista de instrucciones
            intermedio.setCodigo(instrucciones);
        }
    }

    private boolean esOperacionConstante(Instruccion instruccion) {
        try {
            // Si los operadores son int, es una operación constante
            Integer.parseInt(instruccion.getOperador1());
            if (instruccion.getOperacion() != OperacionInst.SALTO_COND && instruccion.getOperacion() != OperacionInst.NO) {
                Integer.parseInt(instruccion.getOperador2());
            }
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            // Si los operadores son char, es una operación constante
            return instruccion.getOperador1() != null && instruccion.getOperador1().length() == 1 && // length == 1 implica char constante
                    ((instruccion.getOperacion() == OperacionInst.IGUAL || instruccion.getOperacion() == OperacionInst.DIFERENTE) &&
                            (instruccion.getOperador2() != null && instruccion.getOperador2().length() == 1)); // o el segundo operador es char constante también
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
        if (esOperandoIgual(aux.getDestino(), asigDiferida.getDestino())) {
            aux.setDestino(asigDiferida.getOperador1());
            return true;
        }

        return false;
    }

    // Método auxiliar para verificar si los operandos son iguales
    private boolean esOperandoIgual(String operando1, String operando2) {
        return operando1 != null && operando1.equals(operando2);
    }

}
