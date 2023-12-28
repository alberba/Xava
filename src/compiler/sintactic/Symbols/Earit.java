package compiler.sintactic.Symbols;

public class Earit {
    private Value value;
    private OpArit oparit;
    private Earit earit;
    private Exp exp;

    public Earit(Value value, OpArit oparit, Earit earit) {
        this.value = value;
        this.oparit = oparit;
        this.earit = earit;
    }

    public Earit(Value value) {
        this.value = value;
    }

    public Earit(Exp exp) {
        this.exp = exp;
    }
}
