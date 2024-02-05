package compiler;

import compiler.Intermedio.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class Optimizador {


    private final Intermedio intermedio;
    private boolean hayCambios = true;

    public Optimizador(Intermedio intermedio) {
        this.intermedio = intermedio;
    }

    public Intermedio optimizarIntermedio() {
        while (hayCambios) {
            hayCambios = false;
            optimizar();
            eliminarCodigoMuerto();
        }
        eliminarVariablesnoUsadas();
        return intermedio;

    }

    /**
     * Método que se encarga de optimizar el código intermedio. Recorriendo todas las instrucciones, se buscarán las
     * asignaciones diferidas, los saltos sobre saltos, las etiquetas inaccesibles y las operaciones constantes para
     * optimizar el código.
     */
    private void optimizar() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Variable> variables = intermedio.getTv();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList<Variable> variablesAEliminar = new ArrayList<>();
        TreeMap<String, Boolean> labels = new TreeMap<>();
        ArrayList<Integer> indexEtiquetas = new ArrayList<>();
        HashSet<String> variablesAnalizadas = new HashSet<>();

        Instruccion instruccion;
        // Recorrerá todas las instrucciones
        boolean eliminado;
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);

            if (esAsignacionDiferida(instruccion, variablesAnalizadas)) {
                // En caso de ser una asignación diferida, se procesa
                eliminado = procesarAsignacionDiferida(i, instrucciones, variablesAnalizadas, instruccionesAEliminar, variablesAEliminar);
                // Si la instrucción se va a eliminar, no cabe seguir comprobando esta misma instrucción
                if (eliminado) {
                    continue;
                }

            } else if (esOperacionConstante(instruccion)) {
                // En caso de ser una operación constante, se procesa
                i = procesarOperacionConstante(instrucciones, i, instruccion);
            }

            // Juntamos la comprobación de las etiquetas inaccesibles y de los saltos sobre saltos
            switch (instruccion.getOperacion()) {
                case ETIQUETA:
                    // En caso de ser una etiqueta, se añade al árbol de etiquetas para comprobar si se le hace referencia
                    indexEtiquetas.add(i);
                    if (!labels.containsKey(instruccion.getDestino())) {
                        labels.put(instruccion.getDestino(), false);
                    }
                    break;
                case SALTO_COND:
                case MENOR:
                case MENOR_IGUAL:
                case IGUAL:
                case DIFERENTE:
                case MAYOR:
                case MAYOR_IGUAL:
                case NO:
                    procesarSaltoSobreSalto(instrucciones, i, instruccion);
                case LLAMADA:
                case SALTO_INCON:
                case Y:
                case O:
                    // Indicar que la etiqueta esta siendo referenciada
                    labels.put(instruccion.getDestino(), true);
                    break;
            }

        }
        // Eliminar etiquetas no accesibles
        procesarEtiquetasInaccesibles(labels, indexEtiquetas, instrucciones, instruccionesAEliminar);

        // Actualización del código intermedio y la tabla de variables si es necesario
        actualizarCodigoYTv(instrucciones, instruccionesAEliminar, variables, variablesAEliminar);

    }

    /**
     * Método que irá recorriendo cada instrucción del código intermedio en busca de código muerto,
     * es decir, instrucciones o procedimientos que no se ejecutan nunca. En caso de encontrar una, se eliminará.
     */
    private void eliminarCodigoMuerto() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Procedimiento> procedimientos = intermedio.getTp();
        ArrayList<Instruccion> instruccionesAEliminar = new ArrayList<>();
        ArrayList <Procedimiento> prodAEliminar = new ArrayList<>();

        Instruccion instruccion;
        // Recorrido de instrucciones
        for (int i = 0; i < instrucciones.size(); i++) {
            instruccion = instrucciones.get(i);
            switch (instruccion.getOperacion()) {
                case RETORNO:
                case SALTO_INCON:

                    // Si es un salto incondicional o un retorno, se eliminan las siguientes instrucciones hasta
                    // encontrar una etiqueta
                    for(int j = i + 1; j < instrucciones.size(); j++) {
                        Instruccion aux = instrucciones.get(j);
                        if (aux.getOperacion() == OperacionInst.ETIQUETA) {
                            // Comprueba si la etiqueta es la misma que el salto incondicional. En tal caso, no es necesario
                            // hacer uso del salto
                            if (aux.getDestino().equals(instruccion.getDestino())) {
                                instruccionesAEliminar.add(instruccion);
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
                    i = eliminarFuncionesNoLlamadas(instrucciones, i, instruccion, instruccionesAEliminar, prodAEliminar);
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

    private void eliminarVariablesnoUsadas() {
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        ArrayList<Variable> variables = intermedio.getTv();
        ArrayList<Variable> variablesAEliminar = new ArrayList<>();
        ArrayList<String> variablesAnalizadas = new ArrayList<>();
        // Recorrido de instrucciones para recoger las variables utilizadas
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
        // Recorrido de variables no presentes en ninguna instrucción (no utilizadas)
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

    /**
     * Procesa las asignaciones diferidas. Primero, se comprueba en las siguientes instrucciones si se realiza una operación con el destino
     * de la asignación diferida. Eso quiere decir que se puede sustituir la variable temporal por su asignación diferida. En caso de encontrar otra
     * asignación de la misma variable temporal, se deja de buscar y se añade la variable a las analizadas.
     * @param i Índice de la instrucción con asignación diferida
     * @param instrucciones Lista de instrucciones
     * @param variablesAnalizadas Conjunto de variables ya analizadas
     * @param instruccionesAEliminar Lista de instrucciones a eliminar
     * @param variablesAEliminar Lista de variables a eliminar
     * @return true si se ha eliminado la asignación diferida, false en caso contrario
     */
    private boolean procesarAsignacionDiferida(int i, ArrayList<Instruccion> instrucciones, HashSet<String> variablesAnalizadas, ArrayList<Instruccion> instruccionesAEliminar, ArrayList<Variable> variablesAEliminar) {
        boolean eliminado = false;
        boolean otraAsig = false;
        // Recorrido de instrucciones
        ArrayList <Instruccion> instruccionesAux = new ArrayList<>();
        Instruccion instruccion = instrucciones.get(i);
        for (int j = i + 1; j < instrucciones.size() && !otraAsig; j++) {
            Instruccion aux = instrucciones.get(j);
            // Si el operando 1 o 2 es igual al destino de la instrucción actual, se añade a la lista de instrucciones a analizar
            if (esOperandoIgual(aux.getOperador1(), instruccion.getDestino()) || esOperandoIgual(aux.getOperador2(), instruccion.getDestino())) {
                instruccionesAux.add(aux);
            } else if (esOperandoIgual(aux.getDestino(), instruccion.getDestino())) {
                // Si el destino coincide, se comprueba que no sea por un return
                if (aux.getOperacion() == OperacionInst.RETORNO) {
                    instruccionesAux.add(aux);
                } else {
                    // En caso contrario, no es diferida
                    otraAsig = true;
                }

            }
        }
        // Se comprueba que la asignación sea diferida
        if (!otraAsig) {
            // Actualización de las instrucciones
            for (Instruccion aux: instruccionesAux) {
                if (actualizarInstruccion(aux, instruccion)) {
                    hayCambios = true;
                    eliminado = true;
                    instruccionesAEliminar.add(instruccion);
                    variablesAnalizadas.add(instruccion.getDestino());
                    variablesAEliminar.add(intermedio.buscarVariable(instruccion.getDestino()));
                }
            }
        } else {
            variablesAnalizadas.add(instruccion.getDestino());
        }
        return eliminado;
    }

    /**
     * Método que se encarga de procesar las operaciones constantes. En caso de ser una operación constante,
     * se realizará la operación y se actualizará la instrucción con el resultado de la operación.
     * @param instrucciones Lista de instrucciones
     * @param i Índice de la instrucción a procesar
     * @param instruccion Instrucción a procesar
     * @return Índice de la instrucción a procesar actualizado
     */
    private int procesarOperacionConstante(ArrayList<Instruccion> instrucciones, int i, Instruccion instruccion) {
        String operacion;
        boolean condicionCumplida = false;
        // Variable para comprobar más tarde si se trata de una operación lógica
        boolean esLogica = false;
        hayCambios = true;
        // En caso de ser constante, se realizará la operación según el tipo de instrucción
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

        // En caso de ser una operación lógica, se cambiará la instrucción por un salto incondicional en caso
        // de que la condición se cumpla, o se eliminará la instrucción en caso contrario
        if (esLogica) {
            if (condicionCumplida) {
                instrucciones.set(i, new Instruccion(OperacionInst.SALTO_INCON, null, null, instruccion.getDestino()));
            } else {
                instrucciones.remove(instruccion);
                i--;
            }
        }
        return i;

    }

    private void procesarSaltoSobreSalto(ArrayList<Instruccion> instrucciones, int i, Instruccion instruccion) {
        // Comprobará si hay un salto sobre salto
        boolean encontrado = false;
        // Buscará la etiqueta a la que salta
        for (int j = i + 1; j + 1 < instrucciones.size() && !encontrado; j++) {
            if (instrucciones.get(j).getOperacion() == OperacionInst.ETIQUETA) {
                encontrado = true;
                // En caso de encontrarlo, comprobará que la siguiente instrucción sea un salto incondicional
                if (instrucciones.get(j).getDestino().equals(instruccion.getDestino())) {
                    if (instrucciones.get(j + 1).getOperacion() == OperacionInst.SALTO_INCON) {
                        hayCambios = true;
                        // Si se cumple, se cambiará la etiqueta del salto condicional al destino del salto incondicional
                        instruccion.setDestino(instrucciones.get(j + 1).getDestino());
                    }
                }
            }
        }
    }

    private int eliminarFuncionesNoLlamadas(ArrayList <Instruccion> instrucciones, int i, Instruccion instruccion, ArrayList<Instruccion> instruccionesAEliminar, ArrayList<Procedimiento> prodAEliminar) {
        // Comprobará si la función está siendo llamada. En caso de que no sea así, no es útil
        // traducir la función
        for (Instruccion aux : instrucciones) {
            if (aux.getOperacion() == OperacionInst.LLAMADA && aux.getDestino().equals("e_"
                    + instruccion.getDestino())) {
                return i;
            }
        }
        hayCambios = true;
        for (Procedimiento proc : intermedio.getTp()) {
            if (proc.getId().equals(instruccion.getDestino())) {
                prodAEliminar.add(proc);
                return i;
            }
        }

        // Elimina todas las instrucciones de la funcion
        for(int j = i - 1; j < instrucciones.size(); j++) {
            instruccion = instrucciones.get(j);
            if (instruccion.getOperacion() == OperacionInst.ETIQUETA &&
                    instruccion.getDestino().contains("e_")){
                i = j;
                return i;
            }
            instruccionesAEliminar.add(instruccion);
        }

        return i;

    }

    /**
     * Elimina las etiquetas a las que no se accede
     */
    private void procesarEtiquetasInaccesibles(TreeMap<String, Boolean> labels, ArrayList<Integer> indexEtiquetas, ArrayList<Instruccion> instrucciones, ArrayList<Instruccion> instruccionesAEliminar) {
        // Comprueba si existe una etiqueta a la que no se accede
        Instruccion instruccion;
        if (labels.containsValue(false)) {
            for (Integer indexEtiqueta : indexEtiquetas) {
                instruccion = instrucciones.get(indexEtiqueta);
                if (!labels.get(instruccion.getDestino())) {
                    hayCambios = true;
                    instruccionesAEliminar.add(instruccion);
                }
            }
        }
    }

    /**
     * Comprueba que los operadores de la instrucción sean literales ("a" o 4, por ejemplo)
     * @param instruccion Instrucción a comprobar
     * @return true si los operadores son literales, false en caso contrario
     */
    private boolean esOperacionConstante(Instruccion instruccion) {
        try {
            if (instruccion.getOperacion() == OperacionInst.ASIG || instruccion.getOperacion() == OperacionInst.INDEXADO
                || instruccion.getOperacion() == OperacionInst.ASIGNADO) {
                return false;
            }
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

    /**
     * Actualiza el código intermedio y la tabla de variables en caso de haber cambios
     * @param instrucciones Lista de instrucciones
     * @param instruccionesAEliminar Lista de instrucciones a eliminar
     * @param variables Lista de variables
     * @param variablesAEliminar Lista de variables a eliminar
     */
    private void actualizarCodigoYTv(ArrayList<Instruccion> instrucciones, ArrayList<Instruccion> instruccionesAEliminar, ArrayList<Variable> variables, ArrayList<Variable> variablesAEliminar) {
        // Si se han eliminado instrucciones, se actualiza el código intermedio
        if (!instruccionesAEliminar.isEmpty() || !variablesAEliminar.isEmpty()) {
            // Eliminación de las instrucciones y variables que ya no se usan
            instrucciones.removeAll(instruccionesAEliminar);
            variables.removeAll(variablesAEliminar);

            // Actualización de la lista de instrucciones y variables
            intermedio.setTv(variables);
            intermedio.setCodigo(instrucciones);
        }
    }

    // Método auxiliar para verificar si los operandos son iguales
    private boolean esOperandoIgual(String operando1, String operando2) {
        return operando1 != null && operando1.equals(operando2);
    }

    /**
     * Comprueba si se trata de una asignación diferida
     * @param instruccion Instrucción a comprobar
     * @param variablesAnalizadas Conjunto de variables ya analizadas
     * @return true si es una asignación diferida, false en caso contrario
     */
    private boolean esAsignacionDiferida(Instruccion instruccion, HashSet<String> variablesAnalizadas) {
        return !variablesAnalizadas.contains(instruccion.getDestino()) && instruccion.getOperacion() == OperacionInst.ASIG
                && intermedio.buscarVariable(instruccion.getDestino()).isEsTemp();
    }

}
