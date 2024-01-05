package compiler.sintactic;

import compiler.sintactic.Symbols.*;
import compiler.Error;

public class AnSem {
    private final TSimbolos ts;

    public AnSem(TSimbolos ts) {
        this.ts = ts;
    }

    public boolean funcionDeclarada(Call_fn call_fn) {
        //*****  Original :
        //*****  if (ts.getFunction(call_fn.getId().getValue()) == null) {
        if (ts.getFunction(call_fn.getId()) == null) {
            // ERROR -> function not defined
            Error.añadirError(new Error("Función no definida", call_fn.getLinea(), Fase.SEMÁNTICO));
            return false;
        }
        return true;
    }


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
                    Error.añadirError(new Error("La variable no existe", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    tipo = s.getTipoReturn();
                }
                break;
            case "Call_fn":
                Symbol sCall = ts.getSymbol(exp.getValue().getValue());
                if (sCall == null) {
                    Error.añadirError(new Error("La función no existe", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
                } else {
                    tipo = sCall.getTipoReturn();
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
                Error.añadirError(new Error("Tipo de valor erróneo", exp.getLinea(), Fase.SEMÁNTICO));
                return null;
        }

        Op op = exp.getOp();
        if (op != null) {
            switch (tipo.name()) {
                case "ENTERO":
                    if (op.equals(Op.Y) || op.equals(Op.O)) {
                        Error.añadirError(new Error("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case "CARACTER":
                    if (!op.equals(Op.IGUAL) && !op.equals(Op.IGUALNT)) {
                        Error.añadirError(new Error("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                case "BOOLEANO":
                    if (!op.equals(Op.IGUAL) && !op.equals(Op.IGUALNT) && !op.equals(Op.Y) && !op.equals(Op.O)) {
                        Error.añadirError(new Error("Operación no válida para el tipo de dato", exp.getLinea(), Fase.SEMÁNTICO));
                        return null;
                    }
                    break;
                default:
                    Error.añadirError(new Error("Tipo de dato no válido", exp.getLinea(), Fase.SEMÁNTICO));
                    return null;
            }
            // El tipo de ambos operandos ha de ser el mismo
            if (tipo != gestExp(exp.getExp())) {
                Error.añadirError(new Error("Los tipos de los operandos no coinciden", exp.getLinea(), Fase.SEMÁNTICO));
                return null;
            }
            return tipo;


        } else {
            // No hay operador, por tanto es la producción Exp -> Value
            // No hace falta comprobar nada mas
            return tipo;
        }
    }

    public boolean gestAsig(EnumType eType, Exp exp) {
        EnumType eType2 = gestExp(exp);
        if (eType == eType2) {
            return true;
        } else {
            Error.añadirError(new Error("Se intentó asignar un valor de tipo " + eType2.name() + " a una variable de tipo " + eType.name(), exp.getLinea(), Fase.SEMÁNTICO));
            return false;
        }
    }

    public boolean esExpressionBooleana(Exp e) {
        StructureReturnType returnE = checkExpresion(e);

        if (returnE == null) {
            // el tipo de la expressión es null
            // HAY QUE AÑADIR EL ERROR
            return false;
        }
        if (returnE != StructureReturnType.BOOL) {
            // La expresión no es booleana

            // ERROR: AÑADIR ERROR
            return false;
        }
        return true;

    }
}
