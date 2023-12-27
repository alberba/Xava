package compiler.sintactic.Symbols;

import compiler.sintactic.AnSem;

public class Inst {
    private String type;
    private Exp exp;
    private Sents sents;
    private Decl decl_cap;
    private Decl decl;
    private String id;
    private AnSem anSem;

    // Constructor para ifs, fors y whiles
    public Inst(String type, Exp exp, Sents sents, Decl decl_cap, Decl decl) {
        anSem.esExpressionBooleana(exp);
        // añadir gestión error

        this.type = type;
        this.exp = exp;
        this.sents = sents;
        this.decl_cap = decl_cap;
        this.decl = decl;

    }

    public Inst(String type, String id, Exp exp) {
        anSem.esExpressionBooleana(exp);
        // añadir gestión error

        this.type = type;
        this.id = id;
        this.exp = exp;
    }


}
