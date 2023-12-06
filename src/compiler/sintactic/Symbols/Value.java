package compiler.sintactic.Symbols;

public class Value {
    private String num;
    private Call_fn call_fn;
    private String id;
    private String car;

    public Value(String num) {
        this.num = num;
        this.call_fn = null;
        this.id = null;
        this.car = null;
    }
    public Value(Call_fn call_fn) {
        this.num = null;
        this.call_fn = call_fn;
        this.id = null;
        this.car = null;
    }
    public Value(String id, String nulo) {
        this.num = null;
        this.call_fn = null;
        this.id = id;
        this.car = null;
    }
    public Value(String car, String nulo1, String nulo2) {
        this.num = null;
        this.call_fn = null;
        this.id = null;
        this.car = car;
    }
}
