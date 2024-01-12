package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Exp extends SimboloBase {

    private Value value;
    private Op op;
    private Exp exp;

    public Exp(Value value, Op op, Exp exp, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = op;
        this.exp = exp;
    }

    public Exp(Value value, int linea, int columna) {
        super(linea, columna);
        this.value = value;
        this.op = null;
        this.exp = null;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public Op getOp() {
        return op;
    }

    public void setOp(Op op) {
        this.op = op;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public void generarIntermedio(Intermedio intermedio) {
        value.generarIntermedio(intermedio);
        if (exp != null) {
            exp.generarIntermedio(intermedio);
        }
    }
}
