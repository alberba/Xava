package compiler.sintactic;

import compiler.sintactic.Symbols.*;

public class AnSem {
    private final TSimbolos ts;

    public AnSem(TSimbolos ts) {
        this.ts = ts;
    }

    public boolean funcionDeclarada(Call_fn call_fn) {
        if (ts.getFunction(call_fn.getId().getValue()) == null) {
            // ERROR -> function not defined
            ErrorHandler.addError(ErrorCode.UNDECLARED_FUNCTION,
                    call_fn.getLine(),
                    call_fn.getColumn(),
                    Phase.SEMANTIC);
            return false;
        }
        return true;
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
