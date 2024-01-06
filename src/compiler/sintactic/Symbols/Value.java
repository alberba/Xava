package compiler.sintactic.Symbols;

public class Value extends SimboloBase {
    private String value;
    private String tipo;
    private Exp dimension;
    private Call_fn call_fn;
    private Entrada entrada;
    private Exp exp;


    public Value(String value, String tipo, int linea, int columna) {
        super(linea,columna);
        this.tipo = tipo;
        this.value = value;
    }
    public Value(String value, String tipo, Exp dimension, int linea, int columna) {
        super(linea,columna);
        this.tipo = tipo;
        this.dimension = dimension;
        this.value = value;
    }
    public Value(Call_fn call_fn, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Call_fn";
        this.call_fn = call_fn;
    }

    public Value(Entrada entrada, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Entrada";
        this.entrada = entrada;
    }

    public Value(Exp exp, int linea, int columna) {
        super(linea,columna);
        this.tipo= "Exp";
        this.exp = exp;
    }

    public Exp getDimension() {
        return dimension;
    }

    public void setDimension(Exp dimension) {
        this.dimension = dimension;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Call_fn getCall_fn() {
        return call_fn;
    }

    public void setCall_fn(Call_fn call_fn) {
        this.call_fn = call_fn;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
}
