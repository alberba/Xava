package compiler.sintactic;

import compiler.ErrorC;
import compiler.sintactic.Symbols.*;

import java.util.ArrayList;

public class AnSem {
    private final TSimbolos ts;

    public AnSem(TSimbolos ts) {
        this.ts = ts;
    }

    /**
     * Comprueba que la Expresión sea correcta y devuelve el tipo de la expresión
     * @param exp Expresión a comprobar
     * @return Tipo de la expresión resultante
     */
    public EnumType gestExp(Exp exp) {
        EnumType tipo;
        // Caso de negación: E -> NO E
        if (exp.isEsNot()) {
            return gestExp(exp.getExp1());
        }
        if (exp.getValue() != null) {
            if (exp.getOp() == null) {
                return gestValue(exp.getValue());
            } else {
                // Check del value
                tipo = gestValue(exp.getValue());
            }
        } else {
            // Check de la expresión
            tipo = gestExp(exp.getExp1());
        }
        if (tipo == null) {
            return null;
        }
        // Check de la operación
        Op op = exp.getOp();
        if (op != null) {
            // Comprobación de operadores según el tipo de dato
            switch (tipo) {
                case ENTERO:
                    if (op.equals(Op.Y) || op.equals(Op.O)) {
                        ErrorC.añadirError(new ErrorC("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case CARACTER:
                    if (!op.equals(Op.IGUAL) && !op.equals(Op.IGUALNT)) {
                        ErrorC.añadirError(new ErrorC("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case BOOLEANO:
                    if (!op.equals(Op.IGUAL) && !op.equals(Op.IGUALNT) && !op.equals(Op.Y) && !op.equals(Op.O)) {
                        ErrorC.añadirError(new ErrorC("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                default:
                    ErrorC.añadirError(new ErrorC("Tipo de dato no válido", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
            }
            // El tipo de ambos operandos ha de ser el mismo
            if (tipo != gestExp(exp.getExp2())) {
                ErrorC.añadirError(new ErrorC("Los tipos de los operandos no coinciden", exp.getLinea(), Fase.SEMÁNTICO));
                return null;
            } else {
                // El tipo de la expresión será el tipo de los operandos
                return switch (op) {
                    case IGUAL, IGUALNT, Y, O, MAI, MEI, MAQ, MEQ, NO -> EnumType.BOOLEANO;
                    case SUMA, RESTA, MULT, DIV, MOD -> EnumType.ENTERO;
                };
            }
        } else {
            // No hay operador, por tanto es la producción Exp -> Value
            // No hace falta comprobar nada mas
            return tipo;
        }
    }


    /**
     * Comprueba que el Value sea correcto y devuelve el tipo de la expresión
     * @param value Value a comprobar
     * @return Tipo subyacente básico del value
     */
    private EnumType gestValue(Value value) {
        switch (value.getTipo()) {
            case "Ent":
                return EnumType.ENTERO;
            case "Car":
                return EnumType.CARACTER;
            case "Bol":
                return EnumType.BOOLEANO;
            case "Id":
                Symbol s = ts.getSymbol(value.getValue());
                if (s == null) {
                    ErrorC.añadirError(new ErrorC("La variable no existe", value.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    return s.getTipoReturn();
                }
            case "Arr":
                Symbol sArr = ts.getSymbol(value.getArrayG().getId());
                if (sArr == null) {
                    ErrorC.añadirError(new ErrorC("El array no existe", value.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    return sArr.getTipoReturn();
                }
            case "Call_fn":
                Symbol sCall = ts.getSymbol(value.getCall_fn().getId());
                if (sCall == null) {
                    ErrorC.añadirError(new ErrorC("La función no existe", value.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    EnumType tipo = sCall.getTipoReturn();
                    // Comprobación de retorno de función
                    if (tipo == EnumType.VACIO) {
                        ErrorC.añadirError(new ErrorC("La función no devuelve ningún valor", value.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    return tipo;
                }
            case "Entrada":
                return value.getEntrada().getEnumType();
            case "Exp":
                return gestExp(value.getExp());
            default:
                ErrorC.añadirError(new ErrorC("Tipo de valor erróneo", value.getLinea(), Fase.SEMÁNTICO));
                return null;
        }
    }

    /**
     * Comprueba si la asignación se realiza correctamente. Se encargará de comprobar que el tipo de la variable
     * coincide con el tipo de la expresión de la derecha de la asignación
     *
     * @param eType Tipo de la variable
     * @param exp Expresión de la derecha de la asignación
     */
    public void gestAsig(EnumType eType, Exp exp) {
        EnumType eType2 = gestExp(exp);
        if (eType2 == null) {
            return;
        }
        if (eType != eType2) {
            if (exp.getValue().getCall_fn() != null) {
                ErrorC.añadirError(new ErrorC("Se intentó asignar un valor de tipo " + eType2.name() + " a una variable de tipo " + eType.name(), exp.getValue().getCall_fn().getLinea(), Fase.SEMÁNTICO));
            } else {
                ErrorC.añadirError(new ErrorC("Se intentó asignar un valor de tipo " + eType2.name() + " a una variable de tipo " + eType.name(), exp.getLinea(), Fase.SEMÁNTICO));
            }
        }
    }

    /**
     * Función que comprueba que si existe el simbolo correspondiente al array
     * y si los valores de indexación presentan coherencia con la declaración
     * (que se encuentre dentro del rango permitido).
     * @param linea Linea del código donde se encuentra el array
     */
    public void gestArray(ArrayG array, int linea) {
        Symbol symbol = ts.getSymbol(array.getId());

        if (symbol == null) {
            ErrorC.añadirError(new ErrorC("El array no existe", linea, Fase.SEMÁNTICO));
        } else {
            if (symbol.getNDimensiones() != array.getDimension()) {
                ErrorC.añadirError(new ErrorC("La variable no tiene esas dimensiones", linea, Fase.SEMÁNTICO));
            } else {
                int i = 0;
                for (L_array l_arr = array.getlArray(); l_arr != null; l_arr = l_arr.getlArray()) {
                    // Si una de las indexaciones se realiza con un literal
                    if (l_arr.getExp().getValue().getTipo().equals("Ent")) {
                        // Podemos comprobar en tiempo de compilación si es correcto
                        if (Integer.parseInt(l_arr.getExp().getValue().getValue()) >= symbol.getDimensiones().get(i)) {
                            ErrorC.añadirError(new ErrorC("El índice del array se encuentra fuera del rango de elementos", l_arr.getExp().getLinea(), Fase.SEMÁNTICO));
                        }
                        if (Integer.parseInt(l_arr.getExp().getValue().getValue()) < 0) {
                            ErrorC.añadirError(new ErrorC("La indexación presenta un valor negativo", l_arr.getExp().getLinea(), Fase.SEMÁNTICO));
                        }
                    }
                    i++;
                }
            }
        }
    }

    /**
     * Función que comprueba si la expresión del return de la función concide con el tipo de la función
     * @param retProc Objeto de la clase RetProc
     */
    public void gestReturnFunc(RetProc retProc) {
        EnumType typeFunc = ts.getTypeFuncionActual();
        if (typeFunc == EnumType.VACIO) {
            ErrorC.añadirError(new ErrorC("No se puede poner un DEVOLVER en una función de tipo vacío", retProc.getLinea(), Fase.SEMÁNTICO));
        } else {
            EnumType typeReturn = gestExp(retProc.getE());
            if (typeReturn != typeFunc) {
                ErrorC.añadirError(new ErrorC("El tipo de devolución no coincide con el tipo de la función", retProc.getLinea(), Fase.SEMÁNTICO));
            }
        }
    }

    /**
     * Comprueba si en el caso de que la Función sea una función no vacía, que haya un return al final de la función
     *
     * @param fsents conjunto de sentencias de la función
     */
    public void isReturn(FSents fsents) {
        if (fsents.getRetProc() == null) { // Caso sin devuelve
             if (ts.getTypeFuncionActual() != EnumType.VACIO) { // Tiene que ser vacío
                 ErrorC.añadirError(new ErrorC("Se debe poner un DEVOLVER al final de la función", fsents.getLinea(), Fase.SEMÁNTICO));
             }
        } else { // Caso con devuelve
            // Comprobará que el return esté bien hecho
            gestReturnFunc(fsents.getRetProc());
        }

    }

    /**
     * Comprueba a partir del encabezado de una función si esta existe y si los parámetros son correctos,
     * devuelve true si es el caso, false en caso contrario.
     *
     * @param cap Cap de la función para obtener el ID de la función y de los parámetros
     * @return true si la función existe y los parámetros son correctos, false en caso contrario
     */
    public boolean existeFuncion(Cap cap) {
        Symbol symbol = ts.getFuncion(cap.getId());
        // Se verifica la existencia de la función
        if (symbol == null) {
            ErrorC.añadirError(new ErrorC("La función no existe", cap.getLinea(), Fase.SEMÁNTICO));
            return false;
        } else {
            // Se verifica coincidencia también en cada uno de los parámetros
            ArrayList<Symbol> parametros = ts.getParametros(cap.getId());
            for (L_args_Cap l_args_cap = cap.getArgs_cap().getL_args_cap(); l_args_cap != null; l_args_cap = l_args_cap.getL_args_cap()) {
                if (!ts.getSymbol(l_args_cap.getId()).equals(parametros.get(parametros.size() - 1))) {
                    ErrorC.añadirError(new ErrorC("El parámetro " + l_args_cap.getId() + " no existe", l_args_cap.getLinea(), Fase.SEMÁNTICO));
                    return false;
                }
                parametros.remove(parametros.size() - 1);
            }
            return true;
        }
    }

    /**
     * Función que verifica si los argumentos que se le pasan son correctos.
     * Se habrá verificado de antemano que el número de parametros coincida.
     * @param args_call Argumentos de la llamada a la función
     * @param id ID de la función
     */
    public void gestArgsCall(String id, Args_Call args_call) {
        // Se comprueba que el tipo de los parámetros sea el correcto para cada uno de los argumentos
        L_args_Call aux = args_call.getL_args_call();
        ArrayList<Symbol> parametros = ts.getParametros(id);
        boolean error = false;

        for (int i = ts.getNumParametros(id) - 1; i >= 0; i--) { // Por algún motivo parece que se guardan al revés los parámetros
            if (gestValue(aux.getValue()) != parametros.get(i).getTipoReturn()) {
                error = true;
            }
            // Siguiente argumento
            aux = aux.getL_args_call();
        }

        if (error) { // Se hace de esta forma para no poner un error por cada parámetro incorrecto
            ErrorC.añadirError(new ErrorC("El tipo de alguno de los parámetros no coincide con el tipo de la función", args_call.getLinea(), Fase.SEMÁNTICO));
        }

    }

}
