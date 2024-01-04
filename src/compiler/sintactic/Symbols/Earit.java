package compiler.sintactic.Symbols;

public class Earit extends SimboloBase {
    private Value value;
    private OpArit oparit;
    private Earit earit;
    private Exp exp;

    public Earit(Value value, OpArit oparit, Earit earit, int linea, int columna) {
        super(linea,columna);
        this.value = value;
        this.oparit = oparit;
        this.earit = earit;
    }

    public Earit(Value value, int linea, int columna) {
        super(linea,columna);
        this.value = value;
    }

    public Earit(Exp exp, int linea, int columna) {
        super(linea,columna);
        this.exp = exp;
    }
}
