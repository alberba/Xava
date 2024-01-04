package compiler.sintactic.Symbols;

public class Exp extends SimboloBase {

    private Ecomp ecomp;
    private OpLog oplog;
    private Exp exp;
    private Entrada entrada;

    public Exp(Ecomp ecomp, OpLog oplog, Exp exp, int linea, int columna) {
        super(linea,columna);
        this.ecomp = ecomp;
        this.oplog = oplog;
        this.exp = exp;
    }

    public Exp(Ecomp ecomp, int linea, int columna) {
        super(linea,columna);
        this.ecomp = ecomp;
    }

    public Exp(Entrada entrada, int linea, int columna) {
        super(linea,columna);
        this.entrada = entrada;
    }

    public Ecomp getEcomp() {
        return ecomp;
    }

    public void setEcomp(Ecomp ecomp) {
        this.ecomp = ecomp;
    }

    public OpLog getOplog() {
        return oplog;
    }

    public void setOplog(OpLog oplog) {
        this.oplog = oplog;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
}
