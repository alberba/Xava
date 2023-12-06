package compiler.sintactic.Symbols;

public class Earit {
    private Value value;
    private Oparit oparit;
    private Earit earit;

    public Earit(Value value, Oparit oparit, Earit earit) {
        this.value = value;
        this.oparit = oparit;
        this.earit = earit;
    }

    public Earit(Value value) {
        this.value = value;
        this.oparit = null;
        this.earit = null;
    }
}
