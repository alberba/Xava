package compiler.sintactic.Symbols;

//import compiler.sintactic.AnSem;

public class Inst extends SimboloBase {
    private String type;
    private Exp exp;
    private Exp dimArray;
    private C_sents c_sents;
    private Decl decl_cap;
    private Decl decl;
    private String id;
    private Cont_cond contCond;
    //private AnSem anSem;

    // Constructor para ifs, fors y whiles
    public Inst(String type, Exp exp, C_sents c_sents, Decl decl_cap, Decl decl, Cont_cond contCond, int linea, int columna) {
        //anSem.esExpressionBooleana(exp);
        // añadir gestión error

        super(linea,columna);
        this.type = type;
        this.exp = exp;
        this.c_sents = c_sents;
        this.decl_cap = decl_cap;
        this.decl = decl;
        this.contCond = contCond;

    }

    public Inst(String type, String id, Exp dimArray, Exp exp, int linea, int columna) {
        //anSem.esExpressionBooleana(exp);
        // añadir gestión error

        super(linea,columna);
        this.type = type;
        this.dimArray = dimArray;
        this.id = id;
        this.exp = exp;
    }


}
