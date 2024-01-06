package compiler.sintactic.Symbols;

public class Cap extends SimboloBase {

    private EnumType eType;
    private String id;
    private Args_Cap args_cap;

    public Cap(EnumType eType, String id, Args_Cap args_cap, int linea, int columna) {
        super(linea,columna);
        this.eType = eType;
        this.id = id;
        this.args_cap = args_cap;
    }

    // Constructor para cuando es una función void
    public Cap(String id, Args_Cap args_cap, int linea, int columna) {
        super(linea,columna);
        this.eType = null;
        this.id = id;
        this.args_cap = args_cap;
    }

    public EnumType geteType() {
        return eType;
    }

    public void seteType(EnumType eType) {
        this.eType = eType;
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

    public void setArgs_cap(Args_Cap args_cap) {
        this.args_cap = args_cap;
    }
}
