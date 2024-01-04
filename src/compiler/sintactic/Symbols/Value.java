package compiler.sintactic.Symbols;

public class Value extends SimboloBase {
    private String num;
    String tipo;
    private Call_fn call_fn;
    private String id;
    private String car;


    public Value(String num, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Numero";
        this.num = num;
    }
    public Value(Call_fn call_fn, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Call_fn";
        this.call_fn = call_fn;
    }
    public Value(String id, String nulo, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Id";
        this.id = id;
    }
    public Value(String car, String nulo1, String nulo2, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Car";
        this.car = car;
    }
}
