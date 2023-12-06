package compiler.sintactic.Symbols;

public class Cap {

    private SType stype;
    private String id;
    private Args_p args_p;

    public Cap(SType stype, String id, Args_p args_p) {
        this.stype = stype;
        this.id = id;
        this.args_p = args_p;
    }

    // Constructor para cuando es una función void
    public Cap(String id, Args_p args_p) {
        this.stype = null;
        this.id = id;
        this.args_p = args_p;
    }
}
