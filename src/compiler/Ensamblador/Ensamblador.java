package compiler.Ensamblador;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Procedimiento;
import compiler.Intermedio.Variable;
import compiler.sintactic.Symbol;
import compiler.sintactic.Symbols.EnumType;
import compiler.sintactic.TSimbolos;
import compiler.sintactic.TipoElemento;

import java.util.ArrayList;

public class Ensamblador {

    Intermedio intermedio;
    TSimbolos ts;
    private final ArrayList<String> codigo;
    private final ArrayList<String> instParams = new ArrayList<>();
    // Flags para saber si se necesitan las subrutinas de imprimir, leer entero, leer booleano y leer caracter
    boolean [] flagsSubrutinas = {false, false, false, false};

    public Ensamblador(Intermedio intermedio, TSimbolos ts) {

        this.ts = ts;
        this.intermedio = intermedio;
        codigo = new ArrayList<>();

    }

    public void generarEnsamblador() {

        codigo.add("\t\tORG\t$1000");
        declararVariables();

        codigo.add("");
        codigo.add("START:");
        for(Instruccion instruccion: intermedio.getCodigo()){
            instruccionAEnsamblador(instruccion);
        }
        codigo.add("\t\tSIMHALT");
        generarSubrutinas();
        codigo.add("\t\tEND\tSTART");

    }

    /**
     * Método encargado de declarar todas las variables en ensamblador
     */
    public void declararVariables() {
        codigo.add("*-----------------------------------------------------------");
        codigo.add("* VARIABLES Y CONSTANTES");
        codigo.add("*-----------------------------------------------------------");

        for (Variable var: intermedio.getTv()) {
            // Si es una variable temporal se declara al empezar
            String [] id = var.getId().split("\\$");
            if (id[0].startsWith("t") && esNum(id[1])) {
                switch (var.getTipo()) {
                    case ENTERO, CARACTER -> codigo.add(var.getId() + "\t\tDS.W\t1");
                    case BOOLEANO -> codigo.add(var.getId() + "\t\tDS.B\t1");
                }
            } else {
                Symbol symbol = ts.busquedaSymbolEnsamblador(id[0]);
                String tipo = "";
                switch (symbol.getTipoReturn()) {
                    case ENTERO, CARACTER -> tipo = "DS.W";
                    case BOOLEANO -> tipo = "DS.B";
                }
                if (symbol.getTipoElemento() == TipoElemento.ARRAY) { // Arrays
                    codigo.add(var.getId() + "\t\t" + tipo + "\t" + var.getNumElementos());
                } else { // Variables
                    codigo.add(var.getId() + "\t\t" + tipo + "\t1");
                }
            }
        }
        codigo.add("\t\t\tDS.W\t0");

    }

    /**
     * Función encargada de traducir las instrucciones intermedias a ensamblador
     * @param instruccion Instrucción a traducir
     */
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
            case Y:
                codigo.add("\tMOVE.B\t" + instruccion.getOperador2() + ", D0");
                codigo.add("\tAND.B\t" + instruccion.getOperador1() + ", D0");
                codigo.add("\tBNE\t" + instruccion.getDestino());
                break;
            case O:
                codigo.add("\tMOVE.B\t" + instruccion.getOperador2() + ", D0");
                codigo.add("\tOR.B\t" + instruccion.getOperador1() + ", D0");
                codigo.add("\tBNE\t" + instruccion.getDestino());
                break;
            case NO:
                codigo.add("\tNOT.B\t" + instruccion.getDestino());
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
                    codigo.add("\tMOVE.L\t(A7)+, D5");
                    codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", -(A7)");
                    codigo.add("\tMOVE.L\tD5, -(A7)");
                }
                codigo.add("\tRTS");
                break;
            case PARAMETRO_SIMPLE:
                instParams.add("\tMOVE.W\t" + instruccion.getDestino() + ", -(A7)");
                break;
            case IMPRIMIR:
                flagsSubrutinas[0] = true;
                ensambladorImprimir(instruccion);
                break;
            case ENTRADA_ENT:
                flagsSubrutinas[1] = true;
                codigo.add("\tJSR LEERENT\t");
                codigo.add("\tMOVE.W\tD1, " + instruccion.getDestino());
                break;
            case ENTRADA_BOOL:
                flagsSubrutinas[2] = true;
                codigo.add("\tJSR LEERBOOL\t");
                codigo.add("\tMOVE.B\tD1, " + instruccion.getDestino());
                break;
            case ENTRADA_CAR:
                flagsSubrutinas[3] = true;
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
        if (intermedio.buscarVariable(instruccion.getDestino()).getTipo() == EnumType.BOOLEANO) {
            codigo.add("\tMOVE.B\t" + var1 + ", " + instruccion.getDestino());
        } else {
            codigo.add("\tMOVE.W\t" + var1 + ", " + instruccion.getDestino());
        }
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
        if (!esMod) {
            if (optMultDiv(instruccion, op)) {
                return;
            }
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

    /**
     * Optimiza (si es posible) las instrucciones de multiplicación y división desplazando bits
     * @param instruccion Instrucción a optimizar
     * @param op Operación a realizar (MULS o DIVS)
     * @return Verdadero en caso de que se pueda optimizar, falso en caso contrario
     */
    private boolean optMultDiv(Instruccion instruccion, String op) {
        String var1 = convertirOperador(instruccion.getOperador1());
        String var2 = convertirOperador(instruccion.getOperador2());
        // Si var1 es 0 o var 2 es 0 y es una multiplicación (para evitar división entre 0), se optimiza haciendo un clear
        if (getValor(var1) == 0 || getValor(var2) == 0 && op.equals("MULS")) {
            codigo.add("\tCLR.W\t" + instruccion.getDestino());
            return true;
        }
        // Si var2 es múltiplo de 2
        if (esPotenciaDeDos(instruccion.getOperador2())) {
            // Se puede optimizar la multiplicación
            if (op.equals("MULS")) {
                codigo.add("\tMOVE.W\t" + var1 + ", D0");
                codigo.add("\tLSL\t#" + logBase2(getValor(var2)) + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
            } else { // Y la división
                codigo.add("\tMOVE.W\t" + var1 + ", D0");
                codigo.add("\tLSR\t#" + logBase2(getValor(var2)) + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
            }
            return true;
        } else if (esPotenciaDeDos(instruccion.getOperador1())) { // Si no lo es, pero var1 sí
            // Tan solo se puede optimizar la multiplicación
            if (op.equals("MULS")) {
                codigo.add("\tMOVE.W\t" + var2 + ", D0");
                codigo.add("\tLSL\t#" + logBase2(getValor(var1)) + ", D0");
                codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
                return true;
            } else { // No es el caso de la división (no tiene propiedad conmutativa)
                return false;
            }
        } else { // Sin múltiplo de 2, no hay nada que optimizar
            return false;
        }
    }

    private int logBase2(int num) {
        // Devuelve el número de 0s tras el último 1, equivale al log base 2
        // 8 = 1000 -> 3, 16 = 10000 -> 4, 32 = 100000 -> 5...
        return Integer.numberOfTrailingZeros(num);
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
        switch (op) {
            case "IGUAL":
                // Scc hace set si se cumple, clear si no, nos viene perfecto
                codigo.add("\tSEQ\tD0");   // Set if equal
                break;
            case "DIFERENTE":
                codigo.add("\tSNE\tD0");   // Set if NOT equal
                break;
            case "MENOR":
                codigo.add("\tSLT\tD0");   // Set if less
                break;
            case "MENOR_IGUAL":
                codigo.add("\tSLE\tD0");   // Set if less or equal
                break;
            case "MAYOR":
                codigo.add("\tSGT\tD0");   // Set if greater
                break;
            case "MAYOR_IGUAL":
                codigo.add("\tSGE\tD0");   // Set if greater or equal
                break;
        }
        codigo.add("\tCMP.B\t#-1, D0");
        codigo.add("\tBEQ\t" + instruccion.getDestino());
    }

    /**
     * Función encargada de traducir la instrucción de indexado a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorIndexado(Instruccion instruccion) {

        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tMOVE.W\t" + var2 + ", D0");
        codigo.add("\tLEA\t" + instruccion.getOperador1() + ", A0");
        // Se le suma
        codigo.add("\tMOVE.W\t(A0, D0.W), " + convertirOperador(instruccion.getDestino()));

    }

    /**
     * Función encargada de traducir la instrucción de asignación a array a ensamblador (Ej. a[3] = b)
     * Se asume que el operador1 es el valor a asignar y el operador2 es el índice
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorAsignado(Instruccion instruccion) {

        // Se obtiene el valor a asignar (No hace comprobar si es un literal o una variable, ya que se asume que es una variable)
        codigo.add("\tMOVE.W\t" + convertirOperador(instruccion.getOperador2()) + ", D0");
        codigo.add("\tLEA\t" + instruccion.getDestino() + ", A0");
        codigo.add("\tMOVE.W\t" + convertirOperador(instruccion.getOperador1()) + ", (A0, D0.W)");

    }

    /**
     * Función encargada de traducir el salto condicional a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorSCond(Instruccion instruccion) {

        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.B\t" + var1 + ", D0");
        codigo.add("\tCMP.B\t#0, D0");
        codigo.add("\tBEQ\t" + instruccion.getDestino());

    }

    /**
     * Función encargada de traducir la inicialización de un procedimiento a ensamblador
     *
     * @param instruccion Instrucción a traducir
     */
    private void ensambladorPmb(Instruccion instruccion) {
        Procedimiento proc = intermedio.getProcedimiento(instruccion.getDestino());
        // Se declaran las variables locales y los parámetros de la función
        ArrayList<Variable> parametros = proc.getParametros();

        codigo.add("\tMOVE.L\t(A7)+, D3"); // Guarda temporalmente la dirección de la llamada a la función
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tMOVE.W\t(A7)+, D4"); // Guarda temporalmente el valor de retorno
        }
        for (int i = parametros.size() - 1; i >= 0; i--) {
            codigo.add("\tMOVE.W\t(A7)+, " + parametros.get(i).getId()); // Se coge el valor de los parámetros
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
        Procedimiento procActual = intermedio.getProcedimiento(intermedio.getTp().get(intermedio.getnProdActual()).getId());
        // Se actualiza nProdActual
        String nombreFuncion = instruccion.getDestino();
        nombreFuncion = nombreFuncion.split("_")[1]; // Se obtiene el nombre a partir de la etiqueta ("e_nombre")
        intermedio.setNprodActual(nombreFuncion);
        Procedimiento proc = intermedio.getProcedimiento(nombreFuncion);

        // Se guardan en la pila las variables locales (en caso de recursividad, se podrían perder los valores de estas)
        for (Variable var: procActual.getDeclaraciones()) {
            if (var.getTipo() == EnumType.BOOLEANO) {
                codigo.add("\tMOVE.B\t" + var.getId() + ", -(A7)");
            } else {
                codigo.add("\tMOVE.W\t" + var.getId() + ", -(A7)");
            }
        }

        codigo.addAll(instParams);
        instParams.clear();

        // Si hay retorno, se reserva un hueco en la pila para él
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tSUBA.L\t#2, A7");
        }

        codigo.add("\tJSR\t" + instruccion.getDestino());

        // Recuperar los valores de retorno, si es que hay
        if (proc.getTipo() != EnumType.VACIO) {
            codigo.add("\tMOVE.W\t(A7)+, D6"); // Recuperar el valor de retorno
            if (proc.getTipo() == EnumType.BOOLEANO) {
                codigo.add("\tMOVE.B\tD6, " + instruccion.getOperador1());
            } else {
                codigo.add("\tMOVE.W\tD6, " + instruccion.getOperador1());
            }
        }
        codigo.add("\tADDA.L\t#2, A7"); // Limpiar la pila
        // Recuperar los valores de las variables locales
        for (int i = procActual.getDeclaraciones().size() - 1; i >= 0; i--) {
            if (procActual.getDeclaraciones().get(i).getTipo() == EnumType.BOOLEANO) {
                codigo.add("\tMOVE.B\t(A7)+, " + procActual.getDeclaraciones().get(i).getId());
            } else {
                codigo.add("\tMOVE.W\t(A7)+, " + procActual.getDeclaraciones().get(i).getId());
            }
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
        codigo.add("\tCLR.L\tD1");
        if (tipo == EnumType.BOOLEANO) {
            codigo.add("\tMOVE.B\t" + instruccion.getDestino() + ", D1");
        } else {
            codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", D1");
        }
        // Se llama a la subrutina correspondiente según el tipo de variable
        switch (tipo) {
            case BOOLEANO -> codigo.add("\tJSR IMPRIMIRBOOL");
            case ENTERO -> codigo.add("\tJSR IMPRIMIRENT");
            case CARACTER -> codigo.add("\tJSR IMPRIMIRCAR");
        }
    }

    /**
     * Función encargada de generar las subrutinas necesarias del ensamblador. Solo se generan las subrutinas que se
     * vayan a utilizar en el programa
     */
    private void generarSubrutinas() {
        codigo.add("*-----------------------------------------------------------");
        codigo.add("* SUBRUTINAS");
        codigo.add("*-----------------------------------------------------------");
        codigo.add("");
        if (flagsSubrutinas[0]) {
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
            codigo.add("\tCMP.B\t#0, D1"); // Mirar si es verdadero o falso
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
        }
        if (flagsSubrutinas[1]) {
            codigo.add("LEERENT:");
            codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
            codigo.add("\tCLR.L\tD0"); // Limpiar D0
            codigo.add("\tMOVE.W\t#4, D0"); // Indicar la tarea a realizar
            codigo.add("\tTRAP\t#15"); // Llama subrutina trap
            codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
            codigo.add("\tRTS");
            codigo.add("");
            codigo.add("*-----------------------------------------------------------");
        }
        if(flagsSubrutinas[3] || flagsSubrutinas[2]) { // leerBool necesita leerCar, si se activa leerBool, leerCar también
            codigo.add("LEERCAR:");
            codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
            codigo.add("\tCLR.L\tD0"); // Limpiar D0
            codigo.add("\tMOVE.W\t#5, D0"); // Indicar la tarea a realizar
            codigo.add("\tTRAP\t#15"); // Llama subrutina trap
            codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
            codigo.add("\tRTS");
            codigo.add("");
            codigo.add("*-----------------------------------------------------------");
        }
        if(flagsSubrutinas[2]) {
            codigo.add("LEERBOOL:");
            codigo.add("\tJSR\tLEERCAR");
            codigo.add("\tMOVE.W\tD0, -(A7)"); // Guardar el valor de D0
            codigo.add("\tLEA\t.AUX, A1");
            codigo.add("\tMOVE.W\t#13, D0");
            codigo.add("\tTRAP\t#15");
            codigo.add("\tMOVE.W\t(A7)+, D0"); // Recuperar el valor de D0
            codigo.add("\tCMP.B\t#'v', D1");
            codigo.add("\tBEQ\t.VERDADERO");
            codigo.add("\tCMP.B\t#'V', D1");
            codigo.add("\tBEQ\t.VERDADERO");
            codigo.add("\tCMP.B\t#'f', D1");
            codigo.add("\tBEQ\t.FALSO");
            codigo.add("\tCMP.B\t#'F', D1");
            codigo.add("\tBEQ\t.FALSO");
            codigo.add("\tBRA\tLEERBOOL"); // Si no se encuentra un carácter válido, se vuelve a pedir una entrada
            codigo.add(".VERDADERO\tMOVE.B\t#-1, D1");
            codigo.add("\tBRA\t.FINBOOL");
            codigo.add(".FALSO\tMOVE.B\t#0, D1");
            codigo.add(".FINBOOL");
            codigo.add("\tRTS");
            codigo.add(".AUX\tDC.B\t' ', 0");
            codigo.add("*-----------------------------------------------------------");
        }
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
    private boolean esPotenciaDeDos(String operador) {
        if (esNum(operador)) {
            int num = Integer.parseInt(operador);
            return (num & (num - 1)) == 0; // Si es potencia de 2, será n = 100... y n-1 = 011..., no comparten ningún bit
        }
        return false;
    }

    // Solo funciona con números
    private int getValor(String operador) {
        if (operador.contains("#") && esNum(operador.substring(1))) {
            operador = operador.replace("#", "0"); // No sé si funciona replace con "" pero con 0 va bien
            return Integer.parseInt(operador);
        } else {
            return -1;
        }

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
