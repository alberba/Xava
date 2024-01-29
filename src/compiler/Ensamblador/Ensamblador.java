package compiler.Ensamblador;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Procedimiento;
import compiler.Intermedio.Variable;
import compiler.sintactic.Symbol;
import compiler.sintactic.Symbols.EnumType;
import compiler.sintactic.TSimbolos;

import java.util.ArrayList;

public class Ensamblador {


    Intermedio intermedio;
    TSimbolos ts;
    private final ArrayList<String> codigo;

    public Ensamblador(Intermedio intermedio, TSimbolos ts) {

        this.ts = ts;
        this.intermedio = intermedio;
        codigo = new ArrayList<>();

    }

    public void generarEnsamblador() {

        codigo.add("\t\tORG\t$1000");
        codigo.add("START:");
        declararVariables();
        generarSubrutinas();
        for(Instruccion instruccion: intermedio.getCodigo()){
            instruccionAEnsamblador(instruccion);
        }
        codigo.add("\t\tSIMHALT");
        codigo.add("\t\tEND\tSTART");

    }

    public void declararVariables() {

        codigo.add("*-----------------------------------------------------------");
        codigo.add("* VARIABLES Y CONSTANTES");
        codigo.add("*-----------------------------------------------------------");

        for (Variable var: intermedio.getTv()) {
            Symbol symbol = ts.getSymbol(var.getId());
            if (symbol != null && symbol.isConstant()) { // Constantes
                codigo.add(var.getId() + "\t\tEQU\t" + var);
            } else { // Variables
                codigo.add(var.getId() + "\t\tDS.W\t1");
            }

        }

    }

    public void instruccionAEnsamblador(Instruccion instruccion) {
        switch(instruccion.getOperacion()) {
            case ASIG:
                ensambladorAsig(instruccion);
                break;
            case SUMA:
                ensambladorSumaResta(instruccion, "ADD");
                break;
            case RESTA:
                ensambladorSumaResta(instruccion, "SUB");
                break;
            case MULTIPLICACION:
                ensambladorMultDivMod(instruccion, "MULS", false);
                break;
            case DIVISION:
                ensambladorMultDivMod(instruccion, "DIVS", false);
                break;
            case MODULO:
                ensambladorMultDivMod(instruccion, "DIVS", true);
                break;
            case IGUAL:
            case DIFERENTE:
            case MENOR:
            case MENOR_IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
                ensambladorComp(instruccion, instruccion.getOperacion().toString());
                break;
            case NO:
                ensambladorNo(instruccion);
                break;
            case INDEXADO:
                ensambladorIndexado(instruccion);
                break;
            case ASIGNADO:
                ensambladorAsignado(instruccion);
                break;
            case ETIQUETA:
                codigo.add(instruccion.getDestino() + ":");
                break;
            case SALTO_INCON:
                codigo.add("\tJMP\t" + instruccion.getDestino());
                break;
            case SALTO_COND:
                ensambladorSCond(instruccion);
                break;
            case INICIALIZACION:
                ensambladorPmb(instruccion);
                break;
            case LLAMADA:
                ensambladorLlamada(instruccion);
                break;
            case RETORNO:
                if (instruccion.getDestino() != null) {
                    codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", -(A7)");
                }
                codigo.add("\tRTS");
                break;
            case PARAMETRO_SIMPLE:
                codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", -(A7)");
                break;
            case IMPRIMIR:
                ensambladorImprimir(instruccion);
                break;
            case ENTRADA_ENT:
                codigo.add("\tJSR LEERENT\t");
                codigo.add("\tMOVE.W\tD1, " + instruccion.getDestino());
                break;
            case ENTRADA_BOOL:
                codigo.add("\tJSR LEERBOOL\t");
                codigo.add("\tMOVE.W\tD1, " + instruccion.getDestino());
                break;
            case ENTRADA_CAR:
                codigo.add("\tJSR LEERCAR\t");
                codigo.add("\tMOVE.W\tD1, " + instruccion.getDestino());
                break;
            default:
                break;
        }
    }

    private boolean esNum(String operador) {
        try {
            Integer.parseInt(operador);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Función encargada de traducir la instrucción de asignación a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorAsig(Instruccion instruccion) {
        String var1 = convertirOperador(instruccion.getOperador1());

        codigo.add("\tMOVE.W\t" + var1 + ", " + instruccion.getDestino());
    }

    /**
     * Función encargada de traducir la instrucción de suma y resta a ensamblador
     *
     * @param instruccion Instrucción a traducir
     * @param Op Operación a realizar (ADD o SUB)
     */
    private void ensambladorSumaResta(Instruccion instruccion, String Op) {

        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\t" + Op + ".W\t" + var2 + ", D0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());

    }

    /**
     * Función encargada de traducir la instrucción de multiplicación, división y módulo a ensamblador
     *
     * @param instruccion Instrucción a traducir
     * @param op Operación a realizar (MULS o DIVS). En caso de ser módulo, se usa DIVS
     * @param esMod Booleano que indica si es una operación de módulo
     */
    private void ensambladorMultDivMod(Instruccion instruccion, String op, boolean esMod) {
        // Se mira si se puede optimizar la operación
        if (optMultDiv(instruccion, op)) {
            return;
        }
        // En caso contrario, se opera normalmente
        String var1 = convertirOperador(instruccion.getOperador1());
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        codigo.add("\tMOVE.W\t" + var2 + ", D1");
        codigo.add("\tEXT.L\tD1");
        codigo.add("\t" + op + ".W\tD1, D0");
        if (esMod) {
            codigo.add("\tSWAP\tD0");
        }
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());

    }

    private boolean optMultDiv(Instruccion instruccion, String op) {
        String var1 = convertirOperador(instruccion.getOperador1());
        String var2 = convertirOperador(instruccion.getOperador2());
        // Si var2 es múltiplo de 2
        if (esMultiploDeDos(instruccion.getOperador2())) {
            // Se puede optimizar la multiplicación
            if (op.equals("MULS")) {
                codigo.add("\tMOVE.W\t" + var1 + ", D0");
                codigo.add("\tLSL\t" + getValor(var2) / 2 + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
            } else { // Y la división
                codigo.add("\tMOVE.W\t" + var1 + ", D0");
                codigo.add("\tLSR\t" + getValor(var2) / 2 + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
            }
            return true;
        } else if (esMultiploDeDos(instruccion.getOperador1())) { // Si no lo es, pero var1 sí
            // Tan solo se puede optimizar la multiplicación
            if (op.equals("MULS")) {
                codigo.add("\tMOVE.W\t" + var2 + ", D0");
                codigo.add("\tLSL\t" + getValor(var1) / 2 + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
                return true;
            } else { // No es el caso de la división (no tiene propiedad conmutativa)
                return false;
            }
        } else { // Sin múltiplo de 2, no hay nada que optimizar
            return false;
        }
    }


    /**
     * Función encargada de traducir las intstrucciones de operaciones lógicas a ensamblador
     *
     * @param instruccion Instrucción a traducir
     * @param op Operación a realizar (IGUAL, DIFERENTE, MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL)
     */
    private void ensambladorComp(Instruccion instruccion, String op) {
        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tCMP.W\t" + var2 + ", D0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
        switch (op) {
            case "IGUAL":
                // Scc hace set si se cumple, clear si no, nos viene perfecto
                codigo.add("\tSEQ\tD0");   // Set if equal
                codigo.add("\tEXT.W\tD0"); // No se puede extender de byte a long directamente, se extiende de byte a word
                codigo.add("\tEXT.L\tD0"); // Y después de word a long
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "DIFERENTE":
                codigo.add("\tSNE\tD0");   // Set if NOT equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MENOR":
                codigo.add("\tSLT\tD0");   // Set if less
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MENOR_IGUAL":
                codigo.add("\tSLE\tD0");   // Set if less or equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MAYOR":
                codigo.add("\tSGT\tD0");   // Set if greater
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MAYOR_IGUAL":
                codigo.add("\tSGE\tD0");   // Set if greater or equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
        }
    }

    /**
     * Función encargada de traduci la instrucción del NOT a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorNo(Instruccion instruccion) {
        codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", D0");
        codigo.add("\tNOT.W\tD0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
    }

    /**
     * Función encargada de traducir la instrucción de indexado a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorIndexado(Instruccion instruccion) {

        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tMOVE.W\t" + var2 + ", D0");
        codigo.add("\tLEA\t" + instruccion.getOperador1() + "A0");
        // Se le suma
        codigo.add("\tMOVE.W\t(A0, D0.W), " + instruccion.getDestino());

    }

    /**
     * Función encargada de traducir la instrucción de asignación a array a ensamblador (Ej. a[3] = b)
     * Se asume que el operador1 es el valor a asignar y el operador2 es el índice
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorAsignado(Instruccion instruccion) {

        // Se obtiene el valor a asignar (No hace comprobar si es un literal o una variable, ya que se asume que es una variable)
        codigo.add("\tMOVE.W\t" + instruccion.getOperador2() + ", D0");
        codigo.add("\tLEA\t" + instruccion.getDestino() + ", A0");
        codigo.add("\tMOVE.W\t" + instruccion.getOperador1() + ", (A0, D0.W)");

    }

    /**
     * Función encargada de traducir el salto condicional a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorSCond(Instruccion instruccion) {

        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        codigo.add("\tCMP.W\t#0, D0");
        codigo.add("\tBEQ\t" + instruccion.getDestino());

    }

    /**
     * Función encargada de traducir la inicialización de un procedimiento a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorPmb(Instruccion instruccion) {

        Procedimiento proc = intermedio.getProcedimiento(instruccion.getDestino());

        ArrayList<Variable> parametros = proc.getParametros();
        codigo.add("\tMOVE.L\t(A7)+, D3"); // Guarda temporalmente la dirección de la llamada a la función
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tMOVE.W\t(A7)+, D4"); // Guarda temporalmente el valor de retorno
        }
        for (int i = parametros.size() - 1; i >= 0; i--) {
            codigo.add("\tMOVE.W\t(A7)+, " + parametros.get(i).getId()); // Reserva espacio para los parámetros
        }

        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tMOVE.W\tD4, -(A7)"); // Vuelve a poner el valor de retorno en la pila
        }

        codigo.add("\tMOVE.L\tD3, -(A7)"); // Vuelve a poner la dirección de la llamada a la función en la pila

    }

    /**
     * Función encargada de traducir la llamada a un procedimiento a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorLlamada(Instruccion instruccion) {
        String nombreFuncion = instruccion.getDestino();
        nombreFuncion = nombreFuncion.split("_")[1]; // Se obtiene el nombre a partir de la etiqueta ("e_nombre")
        Procedimiento proc = intermedio.getProcedimiento(nombreFuncion);

        // Si hay retorno, se reserva un hueco en la pila para él
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tSUBA.L\t#2, A7");
        }
        codigo.add("\tJSR\t" + instruccion.getDestino());

        // Recuperar los valores de retorno, si es que hay
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tMOVE.W\t(A7)+, " + instruccion.getOperador1());
        }

        // Recuperamos espacio de la pila descartando los valores ocupados por los parámetros
        for(int i = 0; i < proc.getNumParametros(); i++) {
            codigo.add("\tADDA.L\t#2, A7");
        }
    }

    /**
     * Función encargada de generar el código que llama a la subrutina de imprimir, solo se imprimen variables,
     * no se pueden imprimir literales
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorImprimir(Instruccion instruccion) {
        EnumType tipo = intermedio.buscarVariable(instruccion.getDestino()).getTipo();
        codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", D1");
        // Se llama a la subrutina correspondiente según el tipo de variable
        switch (tipo) {
            case BOOLEANO -> codigo.add("\tJSR IMPRIMIRBOOL");
            case ENTERO -> codigo.add("\tJSR IMPRIMIRENT");
            case CARACTER -> codigo.add("\tJSR IMPRIMIRCAR");
        }
    }

    /**
     * Función encargada de generar las subrutinas necesarias del ensamblador
     */
    private void generarSubrutinas() {
        codigo.add("*-----------------------------------------------------------");
        codigo.add("* SUBRUTINAS");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("");
        codigo.add("IMPRIMIRENT:");
        codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
        codigo.add("\tCLR.L\tD0"); // Limpiar D0
        codigo.add("\tEXT.L\tD1"); // Limpiar D1
        codigo.add("\tMOVE.W\t#3, D0"); // Indicar la tarea a realizar
        codigo.add("\tTRAP\t#15"); // Llama subrutina trap
        codigo.add("\tLEA\t.AUX, A1");
        codigo.add("\tMOVE.W\t#13, D0");
        codigo.add("\tTRAP\t#15");
        codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
        codigo.add("\tRTS");
        codigo.add(".AUX\tDC.B\t' ', 0");
        codigo.add("");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("IMPRIMIRBOOL:");
        codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
        codigo.add("\tCLR.L\tD0"); // Limpiar D0
        codigo.add("\tMOVE.W\tD0, A1");
        codigo.add("\tCMP\t#0, D1"); // Mirar si es verdadero o falso
        codigo.add("\tBEQ\t.ESFALSO");
        codigo.add("\tLEA\t.VERDADERO, A1"); // Guardar contenido a imprimir
        codigo.add("\tBRA\t.IMPRIMIR");
        codigo.add(".ESFALSO\tLEA\t.FALSO, A1");
        codigo.add(".IMPRIMIR");
        codigo.add("\tMOVE.W\t#13, D0");
        codigo.add("\tTRAP\t#15");
        codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
        codigo.add("\tRTS");
        codigo.add(".FALSO\tDC.B\t'falso', 0");
        codigo.add(".VERDADERO\tDC.B\t'verdadero', 0");
        codigo.add("");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("IMPRIMIRCAR:");
        codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
        codigo.add("\tCLR.L\tD0"); // Limpiar D0
        codigo.add("\tMOVE.W\t#6, D0"); // Indicar la tarea a realizar
        codigo.add("\tTRAP\t#15"); // Llama subrutina trap
        codigo.add("\tLEA\t.AUX, A1"); // Recuperar el valor de D0
        codigo.add("\tMOVE.W\t#13, D0");
        codigo.add("\tTRAP\t#15");
        codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
        codigo.add("\tRTS");
        codigo.add(".AUX\tDC.B\t' ', 0");
        codigo.add("");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("LEERENT:");
        codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
        codigo.add("\tCLR.L\tD0"); // Limpiar D0
        codigo.add("\tMOVE.W\t#4, D0"); // Indicar la tarea a realizar
        codigo.add("\tTRAP\t#15"); // Llama subrutina trap
        codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
        codigo.add("\tRTS");
        codigo.add("");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("LEERCAR:");
        codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
        codigo.add("\tCLR.L\tD0"); // Limpiar D0
        codigo.add("\tMOVE.W\t#5, D0"); // Indicar la tarea a realizar
        codigo.add("\tTRAP\t#15"); // Llama subrutina trap
        codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
        codigo.add("\tRTS");
        codigo.add("");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("LEERBOOL:");
        codigo.add("\tJSR\tLEERCAR");
        codigo.add("\tCMP.B\t#'v', D1");
        codigo.add("\tBEQ\t.VERDADERO");
        codigo.add("\tCMP.B\t#'V', D1");
        codigo.add("\tBEQ\t.VERDADERO");
        codigo.add("\tCMP.B\t#'f', D1");
        codigo.add("\tBEQ\t.FALSO");
        codigo.add("\tCMP.B\t#'F', D1");
        codigo.add("\tBEQ\t.FALSO");
        codigo.add("\tBRA\tLEERBOOL"); // Si no se encuentra un carácter válido, se vuelve a pedir una entrada
        codigo.add(".VERDADERO\tMOVE.W\t#-1, D1");
        codigo.add("\tBRA\t.FINBOOL");
        codigo.add(".FALSO\tMOVE.W\t#0, D1");
        codigo.add(".FINBOOl");
        codigo.add("\tRTS");
    }

    /**
     * Función encargada de buscar si el operador es un literal o una variable y de adecuar la sintaxis a ensamblador
     *
     * @param valor Id del valor o valor del literal
     * @return String con el valor convertido a ensamblador
     */
    private String convertirOperador(String valor) {

        if (intermedio.buscarVariable(valor) == null) {
            if(esNum(valor)) {
                valor = "#" + valor;
            } else {
                valor = "#'" + valor + "'";
            }
        }

        return valor;
    }

    // Solo funciona para literales
    private boolean esMultiploDeDos(String operador) {
        if (esNum(operador)) {
            int num = Integer.parseInt(operador);
            return num % 2 == 0;
        }
        return false;
    }

    // Solo funciona con números
    private int getValor(String operador) {
        operador = operador.replace("#", "0"); // No sé si funciona replace con "" pero con 0 va bien
        return Integer.parseInt(operador);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String linea : codigo) {
            sb.append(linea).append("\n");
        }
        return sb.toString();
    }

}
