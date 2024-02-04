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
            asignDiferidas();
            brancSobreBranc();
            hayOperacionesConstantes();
            eliminarEtiquetasNoAccesibles();
            eliminarCodigoMuerto();
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

    private void brancSobreBranc() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();

        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            switch (instruccion.getOperacion()) {
                case SALTO_COND:
                case MENOR:
                case MENOR_IGUAL:
                case IGUAL:
                case DIFERENTE:
                case MAYOR:
                case MAYOR_IGUAL:
                case NO:
                    break;
                default:
                    continue;
            }
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
        // Actualización de la lista de instrucciones
        intermedio.setCodigo(instrucciones);
    }

    private void hayOperacionesConstantes() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();

        Instruccion instruccion;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            String operacion;
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
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) == Integer.parseInt(instruccion.getOperador2());
                    break;
                case DIFERENTE:
                    esLogica = true;
                    condicionCumplida = Integer.parseInt(instruccion.getOperador1()) != Integer.parseInt(instruccion.getOperador2());
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
            Integer.parseInt(instruccion.getOperador1());
            if (instruccion.getOperacion() != OperacionInst.SALTO_COND && instruccion.getOperacion() != OperacionInst.NO) {
                Integer.parseInt(instruccion.getOperador2());
            }
            return true;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
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
