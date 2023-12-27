package compiler.sintactic.Symbols;

public class Exp {

    private Ecomp ecomp;
    private Oplog oplog;
    private Exp exp;
    private Entrada entrada;

    public Exp(Ecomp ecomp, Oplog oplog, Exp exp) {
        this.ecomp = ecomp;
        this.oplog = oplog;
        this.exp = exp;
    }

    public Exp(Ecomp ecomp) {
        this.ecomp = ecomp;
    }

    public Exp(Entrada entrada) {
        this.entrada = entrada;
    }
}
