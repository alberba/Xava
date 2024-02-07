package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Cap extends SimboloBase {

    private String id;
    private final Args_Cap args_cap;

    public Cap(String id, Args_Cap args_cap, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.args_cap = args_cap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Args_Cap getArgs_cap() {
        return args_cap;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if(args_cap != null){
            args_cap.generarIntermedio(intermedio);
        }
    }
}
