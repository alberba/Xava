package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Exp extends SimboloBase {

    private final Value value;
    private final Op op;
    private Exp exp1;
    private Exp exp2;
    private boolean esNot;

    public Exp(Value value, Op op, Exp exp2, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = op;
        this.exp2 = exp2;
    }

    public Exp(Exp exp1, Op op, Exp exp2, int linea, int columna) {
        super(linea, columna);
        this.value = null;
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    public Exp(Value value, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = null;
        this.exp1 = null;
    }

    public Exp(Exp exp1, int linea, int columna, boolean esNot) {
        super(linea, columna);
        this.value = null;
        this.op = null;
        this.exp1 = exp1;
        this.esNot = esNot;
    }

    /**
     * Método que crea las etiquetas e instrucciones necesarias para la ejecución
     */
    public void generarIntermedio(Intermedio intermedio) {
        ArrayList<Object> listaObjetos = new ArrayList<>();
        obtenerlistaObjetos(this, intermedio, listaObjetos);
        if (listaObjetos.size() > 1) {
            generarIntermedioArit(listaObjetos, intermedio);
            generarIntermedioLogic(listaObjetos, intermedio);
        }

    }

    /**
     * Método que obtiene una lista de objetos que contiene la operación general
     * @param exp Expresión a analizar
     * @param intermedio Objeto de la clase Intermedio para gestionar 3@ direcciones
     * @param listaObjetos Lista de objetos que contiene la operación general
     */
    public static void obtenerlistaObjetos(Exp exp, Intermedio intermedio, ArrayList<Object> listaObjetos) {
        if (exp.getOp() != null) {
            Value value = exp.getValue();
            if (value != null) {
                if (Objects.equals(value.getTipo(), "Exp")) {
                    value.getExp().generarIntermedio(intermedio);
                    listaObjetos.add(intermedio.getUltimaVariable());
                } else {
                    listaObjetos.add(value);
                }
            } else {
                obtenerlistaObjetos(exp.getExp1(), intermedio, listaObjetos);
            }
            listaObjetos.add(exp.getOp());
            obtenerlistaObjetos(exp.getExp2(), intermedio, listaObjetos);
        } else {
            Value value = exp.getValue();
            if (value != null) {
                // Se trata de una expresión simple, por lo que se debe generar el intermedio de la expresión
                value.generarIntermedio(intermedio);

                listaObjetos.add(intermedio.getUltimaVariable());
            } else {
                // Si no tiene value ni op, se trata de la negación de una expresión
                assert exp.getExp1() != null;
                if (exp.isEsNot()) {
                    exp.getExp1().generarIntermedio(intermedio);
                    String eTrue = intermedio.nuevaEtiqueta();
                    String eFin = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.NO, intermedio.getUltimaVariable().getId(), null, eTrue));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "0", null, intermedio.getUltimaVariable().getId()));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, eFin));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eTrue));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "-1", null, intermedio.getUltimaVariable().getId()));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eFin));
                    listaObjetos.add(intermedio.getUltimaVariable());
                } else {
                    obtenerlistaObjetos(exp.getExp1(), intermedio, listaObjetos);
                }
            }
        }

    }


    /**
     * Genera el intermedio de una operación aritmética
     * @param listaObjeto Lista de objetos que contiene la operación general
     * @param intermedio Objeto de la clase Intermedio para gestionar 3@ direcciones
     */
    private void generarIntermedioArit(ArrayList<Object> listaObjeto, Intermedio intermedio) {
        int indexMin;
        Variable[] variables;
        while (listaObjeto.size() > 1) {

            int indexMult = listaObjeto.indexOf(Op.MULT);
            int indexDiv = listaObjeto.indexOf(Op.DIV);
            int indexMod = listaObjeto.indexOf(Op.MOD);

            // Se mira que haya una multiplicación o una división
            if (indexMult != -1 || indexDiv != -1 || indexMod != -1) {

                //Se revisa cuál de las operaciones aparece antes (y si aparece), se recoge su índice
                indexMin = indexMult != -1 && indexDiv != -1 ? Math.min(indexMult, indexDiv) : Math.max(indexMult, indexDiv);

                indexMin = indexMod != -1 && indexMin != -1 ? Math.min(indexMin, indexMod) : Math.max(indexMin, indexMod);

                // Se obtiene el valor de la operación, sea un literal o una variable
                variables = obtenerVariablesOperacion(indexMin, listaObjeto, intermedio);

                // Creamos la variable destino de la operación
                Variable temp = intermedio.añadirVariable(null, EnumType.ENTERO, null);

                // Se añade la primera instrucción encontrada (mult vs. div)
                if (listaObjeto.get(indexMin) == Op.MULT) {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.MULTIPLICACION, variables[0].getId(), variables[1].getId(), temp.getId()));
                } else if (listaObjeto.get(indexMin) == Op.DIV) {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.DIVISION, variables[0].getId(), variables[1].getId(), temp.getId()));
                } else {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.MODULO, variables[0].getId(), variables[1].getId(), temp.getId()));
                }

            } else {

                int indexSum = listaObjeto.indexOf(Op.SUMA);
                int indexResta = listaObjeto.indexOf(Op.RESTA);

                //Se revisa cuál de las operaciones aparece antes (y si aparece), se recoge su índice
                indexMin = indexSum != -1 && indexResta != -1 ? Math.min(indexSum, indexResta) : Math.max(indexSum, indexResta);

                if (indexMin == -1) {
                    // Si no hay más operaciones, se sale del bucle
                    break;
                }
                // Se obtiene el valor de la operación, sea un literal o una variable
                variables = obtenerVariablesOperacion(indexMin, listaObjeto, intermedio);

                // Creamos la variable destino de la operación
                Variable temp = intermedio.añadirVariable(null, EnumType.ENTERO, null);

                // Se añade la primera instrucción encontrada (sum vs. rest)
                if (listaObjeto.get(indexMin) == Op.SUMA) {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SUMA, variables[0].getId(), variables[1].getId(), temp.getId()));
                } else {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.RESTA, variables[0].getId(), variables[1].getId(), temp.getId()));
                }

            }

            // Se sustituye la operación por la variable temporal
            listaObjeto.remove(indexMin + 1);
            listaObjeto.remove(indexMin);
            listaObjeto.remove(indexMin - 1);

            listaObjeto.add(indexMin - 1, intermedio.getUltimaVariable());
        }
    }

    /**
     * Método que genera el intermedio de una operación lógica: se crea una comparación entre los dos valores y se
     * asigna 0 o -1 según si el resultado es true o false
     * @param listaObjeto Lista de objetos que contiene la operación general
     * @param intermedio Objeto de la clase Intermedio para gestionar 3@ direcciones
     */
    private void generarIntermedioLogic(ArrayList<Object> listaObjeto, Intermedio intermedio) {
        Variable[] variables;
        OperacionInst op = null;
        int indexMin;
        ArrayList<Integer> indicesValidos;

        while (listaObjeto.size() > 1) {
            indicesValidos = new ArrayList<>();
            int indexIgual = listaObjeto.indexOf(Op.IGUAL);
            int indexIgualnt = listaObjeto.indexOf(Op.IGUALNT);
            int indexMAI = listaObjeto.indexOf(Op.MAI);
            int indexMEI = listaObjeto.indexOf(Op.MEI);
            int indexMAQ = listaObjeto.indexOf(Op.MAQ);
            int indexMEQ = listaObjeto.indexOf(Op.MEQ);
            if (indexIgual != -1) {
                indicesValidos.add(indexIgual);
            }
            if (indexIgualnt != -1) {
                indicesValidos.add(indexIgualnt);
            }
            if (indexMAI != -1) {
                indicesValidos.add(indexMAI);
            }
            if (indexMEI != -1) {
                indicesValidos.add(indexMEI);
            }
            if (indexMAQ != -1) {
                indicesValidos.add(indexMAQ);
            }
            if (indexMEQ != -1) {
                indicesValidos.add(indexMEQ);
            }

            if (!indicesValidos.isEmpty()) {
                indexMin = Collections.min(indicesValidos);
                variables = obtenerVariablesOperacion(indexMin, listaObjeto, intermedio);
                Variable temp = intermedio.añadirVariable(null, EnumType.BOOLEANO, null);
                // Comprobación de la operación
                switch ((Op) listaObjeto.get(1)) {
                    case IGUAL -> op = OperacionInst.IGUAL;
                    case IGUALNT -> op = OperacionInst.DIFERENTE;
                    case MAI -> op = OperacionInst.MAYOR_IGUAL;
                    case MEI -> op = OperacionInst.MENOR_IGUAL;
                    case MAQ -> op = OperacionInst.MAYOR;
                    case MEQ -> op = OperacionInst.MENOR;
                }
                generarInstruccionLogica(intermedio, variables, temp, op);
                listaObjeto.remove(indexMin + 1);
                listaObjeto.remove(indexMin);
                listaObjeto.remove(indexMin - 1);

                listaObjeto.add(indexMin - 1, intermedio.getUltimaVariable());
            } else {
                variables = obtenerVariablesOperacion(1, listaObjeto, intermedio);
                Variable temp = intermedio.añadirVariable(null, EnumType.BOOLEANO, null);
                switch ((Op) listaObjeto.get(1)) {
                    case Y -> op = OperacionInst.Y;
                    case O -> op = OperacionInst.O;
                }
                generarInstruccionLogica(intermedio, variables, temp, op);

                listaObjeto.remove(2);
                listaObjeto.remove(1);
                listaObjeto.remove(0);

                listaObjeto.add(0, intermedio.getUltimaVariable());
            }

        }
    }

    private void generarInstruccionLogica(Intermedio intermedio, Variable[] variables, Variable temp, OperacionInst op) {
        String etrue = intermedio.nuevaEtiqueta();
        intermedio.añadirInstruccion(new Instruccion(op, variables[0].getId(), variables[1].getId(), etrue));
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "0", null, temp.getId()));
        String efalse = intermedio.nuevaEtiqueta();
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, efalse)); // goto efalse
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, etrue)); // etrue: skip
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "-1", null, temp.getId())); // tn= -1
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, efalse)); // efalse: skip
    }

    /**
     * Se obtienen las dos variables de una operación x op y, siendo x e y las variables
     * @param index Índice del operador de la operación
     * @param listaObjeto Lista de objetos que contiene la operación general
     * @param intermedio Objeto de la clase Intermedio para gestionar 3@ direcci
     * @return Array de variables que contiene las variables de la operación
     */
    private Variable[] obtenerVariablesOperacion(int index, ArrayList<Object> listaObjeto, Intermedio intermedio) {
        Variable[] variables = new Variable[2];

        // Se obtiene la variable, ya sea a partir de un valor individual o de una expresión
        // Var 1
        if (listaObjeto.get(index - 1) instanceof Value aux) {
            // Transformamos el valor en una variable
            aux.generarIntermedio(intermedio);
            variables[0] = intermedio.getUltimaVariable();
        } else {
            variables[0] = (Variable) listaObjeto.get(index - 1);
        }
        // Var 2
        if (listaObjeto.get(index + 1) instanceof Value aux) {
            // Transformamos el valor en una variable
            aux.generarIntermedio(intermedio);
            variables[1] = intermedio.getUltimaVariable();
        } else {
            variables[1] = (Variable) listaObjeto.get(index + 1);
        }

        return variables.clone();
    }

    public Value getValue() {
        return value;
    }


    public Op getOp() {
        return op;
    }


    public Exp getExp1() {
        return exp1;
    }

    public Exp getExp2() {
        return exp2;
    }

    public boolean isEsNot() {
        return esNot;
    }
}
