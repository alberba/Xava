package compiler.sintactic.Symbols;

public class Value {
    private String num;
    String tipo;
    private Call_fn call_fn;
    private String id;
    private String car;


    public Value(String num) {
        this.tipo = "Numero";
        this.num = num;
    }
    public Value(Call_fn call_fn) {
        this.tipo = "Call_fn";
        this.call_fn = call_fn;
    }
    public Value(String id, String nulo) {
        this.tipo = "Id";
        this.id = id;
    }
    public Value(String car, String nulo1, String nulo2) {
        this.tipo = "Car";
        this.car = car;
    }
}
