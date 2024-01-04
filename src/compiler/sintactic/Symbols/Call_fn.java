package compiler.sintactic.Symbols;

public class Call_fn extends SimboloBase {
    private String id;
    private Args_Call args_call;

    public Call_fn(String id, Args_Call args_call, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.args_call = args_call;
    }

    public String getId(){
        return this.id;
    }

}
