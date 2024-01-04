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
            Error.añadirError(new Error("Función no definida", linea, Fase.SEMÁNTICO));
            return false;
        }
        return true;
    }

    public boolean gestExp(Exp exp, Type tipo) {

        Type tipoExpr = exp.getTipusSubResultat();

        // No puede ser null
        if (tipoExpr == null) {


            return false;
            // Se verifica que sea del mismo tipo
        } else if (tipoExpr != tipo) {
            Error.añadirError(new Error("Se esperaba un " + tipo.toString() + " pero se ha encontrado un " + tipoExpr, exp.getLinea(), Fase.SEMÁNTICO));
            //Ho afeigm al parser per identificar quin tipus de senténcia es incorrecte
            return false;
        }
        // Llegados a este punto, el tipo de expresión es correcto
        if (exp.getEntrada() != null) { // Caso con solo entrada
            gestEntrada(exp.getEntrada());
        } else {
            gestEcomp(exp.getEcomp());
            if (exp.getOplog() != null && exp.getExp() != null) { // Caso con Oplog y Exp

                gestOplog(exp.getOplog());
                gestExp(exp.getExp(), tipo);
            } else if (!(exp.getOplog() == null && exp.getExp() == null)) { // Una XOR de Oplog y Exp
                Error.añadirError(new Error("Se tiene que poner un operador lógico junto a otra expresión", exp.getLinea(), Fase.SEMÁNTICO));
            }
        }
    }

    public boolean gestOplog(OpLog oplog) {
        
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
