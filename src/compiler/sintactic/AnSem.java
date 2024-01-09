package compiler.sintactic;

import compiler.sintactic.Symbols.*;

import java.util.ArrayList;

import compiler.ErrorC;

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

        switch (exp.getValue().getTipo()) {
            case "Ent":
                tipo = EnumType.ENTERO;
                break;
            case "Car":
                tipo = EnumType.CARACTER;
                break;
            case "Bol":
                tipo = EnumType.BOOLEANO;
                break;
            case "Id":
                Symbol s = ts.getSymbol(exp.getValue().getValue());
                if (s == null) {
                    ErrorC.añadirError(new ErrorC("La variable no existe", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    tipo = s.getTipoReturn();
                }
                break;
            case "Arr":
                Symbol sArr = ts.getSymbol(exp.getValue().getValue());
                if (sArr == null) {
                    ErrorC.añadirError(new ErrorC("El array no existe", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    tipo = sArr.getTipoReturn();
                }
                break;
            case "Call_fn":
                Symbol sCall = ts.getSymbol(exp.getValue().getValue());
                if (sCall == null) {
                    ErrorC.añadirError(new ErrorC("La función no existe", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    tipo = sCall.getTipoReturn();
                    if (sCall.getTipoReturn() != EnumType.VACIO) {
                        ErrorC.añadirError(new ErrorC("La función no devuelve ningún valor", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                }
                break;
            case "Entrada":
                tipo = exp.getValue().getEntrada().getEnumType();
                break;
            case "Exp":
                tipo = gestExp(exp.getExp());
                if (tipo == null) {
                    return null;
                }
                break;
            default:
                ErrorC.añadirError(new ErrorC("Tipo de valor erróneo", exp.getLinea(), Fase.SEMÁNTICO));
                return null;
        }

        Op op = exp.getOp();
        if (op != null) {
            switch (tipo.name()) {
                case "ENTERO":
                    if (op.equals(Op.Y) || op.equals(Op.O)) {
                        ErrorC.añadirError(new ErrorC("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case "CARACTER":
                    if (!op.equals(Op.IGUAL) && !op.equals(Op.IGUALNT)) {
                        ErrorC.añadirError(new ErrorC("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case "BOOLEANO":
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
            if (tipo != gestExp(exp.getExp())) {
                ErrorC.añadirError(new ErrorC("Los tipos de los operandos no coinciden", exp.getLinea(), Fase.SEMÁNTICO));
                return null;
            } else {
                switch (op) {
                    case IGUAL:
                    case IGUALNT:
                    case Y:
                    case O:
                    case MAI:
                    case MEI:
                    case MAQ:
                    case MEQ:
                        return EnumType.BOOLEANO;
                    case SUMA:
                    case RESTA:
                    case MULT:
                    case DIV:
                    case MOD:
                        return EnumType.ENTERO;
                    default:
                        return null;
                }
            }


        } else {
            // No hay operador, por tanto es la producción Exp -> Value
            // No hace falta comprobar nada mas
            return tipo;
        }
    }

    /**
     * Comprueba si la asignación se realiza correctamente. Se encargará de comprobar que el tipo de la variable
     * coincide con el tipo de la expresión de la derecha de la asignación
     *
     * @param eType Tipo de la variable
     * @param exp Expresión de la derecha de la asignación
     * @return true si la asignación se realiza correctamente, false en caso contrario
     */
    public boolean gestAsig(EnumType eType, Exp exp) {
        EnumType eType2 = gestExp(exp);
        if (eType == eType2) {
            return true;
        } else {
            ErrorC.añadirError(new ErrorC("Se intentó asignar un valor de tipo " + eType2.name() + " a una variable de tipo " + eType.name(), exp.getLinea(), Fase.SEMÁNTICO));
            return false;
        }
    }

    /**
     * Función que comprueba, en caso de que sea un array, si existe el simbolo y si es un array
     *
     * @param id ID del array
     * @param dimension Dimension del array
     * @param linea Linea del código donde se encuentra el array
     */
    public void gestArray(String id, Exp dimension, int linea) {
        Symbol symbol = ts.getSymbol(id);

        if (symbol == null) {
            ErrorC.añadirError(new ErrorC("El array no existe", linea, Fase.SEMÁNTICO));
        } else {
            if (symbol.getDimension() == 0) {
                ErrorC.añadirError(new ErrorC("La variable no es un array", linea, Fase.SEMÁNTICO));
            }
        }
    }

    /**
     * Función que comprueba si la expresión del return de la función concide con el tipo de la función
     * @param typeCapFunc Tipo de la función escrita en el cap
     * @param fSents Sentencias de la función
     */
    public void gestReturnFunc(EnumType typeCapFunc, FSents fSents) {
        if (fSents.getRetProc()!=null){
            EnumType typeReturn = gestExp(fSents.getRetProc().getE());
            if (typeReturn != typeCapFunc) {
                ErrorC.añadirError(new ErrorC("El tipo de retorno no coincide con el tipo de la función", fSents.getRetProc().getLinea(), Fase.SEMÁNTICO));
            }
        } else {
            if (typeCapFunc != EnumType.VACIO) {
                ErrorC.añadirError(new ErrorC("La función no devuelve ningún valor", fSents.getLinea(), Fase.SEMÁNTICO));
            }
        }
    }

    public boolean existeFuncion(Cap cap) {
        ArrayList <Symbol> simbolos = ts.getParametros(cap.getId());
        if (simbolos == null) {
            return false;
        } else {
            Arra
            for (Symbol s : simbolos) {
                if (s.getTipoElemento() == TipoElemento.PARAMETRO && ca) {
                    return true;
                }
            }
            return true;
        }
    }

    /**
     * Función que comprueba si la expresión es del tipo Logica o no
     *
     * @param exp Expresión a comprobar
     */
    public void gestExpLogica(Exp exp) {
        if (gestExp(exp) != EnumType.BOOLEANO) {
            ErrorC.añadirError(new ErrorC("La expresión no es booleana", exp.getLinea(), Fase.SEMÁNTICO));
        }
    }

}
