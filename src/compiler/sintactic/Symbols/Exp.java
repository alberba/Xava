package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

import java.util.ArrayList;
import java.util.Objects;

public class Exp extends SimboloBase {

    private final Value value;
    private final Op op;
    private final Exp exp;

    public Exp(Value value, Op op, Exp exp, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = op;
        this.exp = exp;
    }

    public Exp(Value value, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = null;
        this.exp = null;
    }

    public Exp(Exp exp, int linea, int columna) {
        super(linea, columna);
        this.value = null;
        this.op = null;
        this.exp = exp;
    }

    public Value getValue() {
        return value;
    }


    public Op getOp() {
        return op;
    }


    public Exp getExp() {
        return exp;
    }


    /**
     * Método que crea las etiquetas e instrucciones necesarias para la ejecución
     */
    public void generarIntermedio(Intermedio intermedio) {
        if (op != null) {
            // Se trata de una operación, por lo que se debe generar el intermedio de la expresión
            ArrayList<Object> listaObjetos = new ArrayList<>();

            // Este bucle recorre todas las expresiones y las guarda en un arraylist. Esto nos servirá después para
            // hacer uso de las jerarquias de operaciones
            for(Exp exp = this; exp != null; exp = exp.getExp()) {
                if (Objects.equals(exp.getValue().getTipo(), "Exp")) {
                    // Se trata de una operación en paréntesis, el cual tiene prioridad 1 en la jerarquia de operaciones

                    // Se genera el intermedio de la expresión
                    exp.getValue().getExp().generarIntermedio(intermedio);
                    listaObjetos.add(intermedio.getUltimaVariable());

                } else {
                    listaObjetos.add(exp.getValue());
                }

                if(exp.getOp() == null) {
                    // Si encuentra un value, sale del ciclo, ya que no habrá más expresiones que analizar
                    break;
                }
                listaObjetos.add(exp.getOp());

            }
            // Se revisa el tipo de operación para generar el intermedio correspondiente
            switch ((Op) listaObjetos.get(1)) {
                case SUMA:
                case RESTA:
                case MULT:
                case DIV:
                case MOD:
                    generarIntermedioArit(listaObjetos, intermedio);
                    break;
                case IGUAL:
                case IGUALNT:
                case Y:
                case O:
                case MAI:
                case MEI:
                case MAQ:
                case MEQ:
                    generarIntermedioLogic(listaObjetos, intermedio);
                    break;
                default:
                    break;
            }
        } else {
            if (value != null) {
                // Se trata de una expresión simple, por lo que se debe generar el intermedio de la expresión
                value.generarIntermedio(intermedio);
            } else {
                // Si no tiene value ni op, se trata de la negación de una expresión
                assert exp != null;
                exp.generarIntermedio(intermedio);
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.NO, null, null, intermedio.getUltimaVariable().getId()));
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

        if (listaObjeto.size() > 1) {
            for (int i = 0; i < listaObjeto.size(); i = i + 2) {
                variables = obtenerVariablesOperacion(i + 1, listaObjeto, intermedio);
                Variable temp = intermedio.añadirVariable(null, EnumType.BOOLEANO, null);
                // Comprobación de la operación
                switch ((Op) listaObjeto.get(i + 1)) {
                    case IGUAL -> op = OperacionInst.IGUAL;
                    case IGUALNT -> op = OperacionInst.DIFERENTE;
                    case MAI -> op = OperacionInst.MAYOR_IGUAL;
                    case MEI -> op = OperacionInst.MENOR_IGUAL;
                    case MAQ -> op = OperacionInst.MAYOR;
                    case MEQ -> op = OperacionInst.MENOR;
                }
                // Se añade la instrucción
                generarInstruccionLogica(intermedio, variables, temp, op);
                // Se sustituye la operación por la variable temporal
                listaObjeto.remove(i + 2);
                listaObjeto.remove(i + 1);
                listaObjeto.remove(i);

                listaObjeto.add(i, intermedio.getUltimaVariable());
            }
        }

        while(listaObjeto.size() > 1) {
            // Aqui solo nos quedarían operaciones de tipo var &&/|| var
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

    /*private void generarIntermedioLogic(ArrayList<Object> listaObjeto, Intermedio intermedio) {
        Variable[] variables;
        OperacionInst op = null;

        while(listaObjeto.size() > 1){
            // Se obtienen las variables de la operación más hacia la izquierda
            variables = obtenerVariablesOperacion(1, listaObjeto, intermedio);

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

            // Se añade la instrucción
            String etrue = intermedio.nuevaEtiqueta();
            intermedio.añadirInstruccion(new Instruccion(op, variables[0].getId(), variables[1].getId(), etrue)); // if a op b goto etrue
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "0", null, temp.getId())); // tn = 0
            String efalse = intermedio.nuevaEtiqueta();
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, efalse)); // goto efalse
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, etrue)); // etrue: skip
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "-1", null, temp.getId())); // tn= -1
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, efalse)); // efalse: skip

            // Se sustituye la operación por la variable temporal
            listaObjeto.remove(2);
            listaObjeto.remove(1);
            listaObjeto.remove(0);

            listaObjeto.add(0, intermedio.getUltimaVariable());
        }
    }*/

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

}
