package compiler.sintactic.Symbols;

public class Cap extends SimboloBase {

    private TypeF typeF;
    private String id;
    private Args_Cap args_cap;

    public Cap(TypeF typeF, String id, Args_Cap args_cap, int linea, int columna) {
        super(linea,columna);
        this.typeF = typeF;
        this.id = id;
        this.args_cap = args_cap;
    }

    // Constructor para cuando es una función void
    public Cap(String id, Args_Cap args_cap, int linea, int columna) {
        super(linea,columna);
        this.typeF = null;
        this.id = id;
        this.args_cap = args_cap;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {

        if (typeF != null) {
            tresDirecciones.add(id + " = " + "new " + typeF.toString() + "()");
        }
        id.generacionTresDirecciones(tresDirecciones);
        args_cap.generacionTresDirecciones(tresDirecciones);
    }

    public TypeF getType() {
        return typeF;
    }

    public void setType(TypeF type) {
        this.typeF = type;
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
