package compiler.sintactic.Symbols;

public class Cap {

    private SType stype;
    private String id;
    private Args_Cap args_cap;

    public Cap(SType stype, String id, Args_Cap args_cap) {
        this.stype = stype;
        this.id = id;
        this.args_cap = args_cap;
    }

    // Constructor para cuando es una función void
    public Cap(String id, Args_Cap args_cap) {
        this.stype = null;
        this.id = id;
        this.args_cap = args_cap;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {

        if (stype != null) {
            tresDirecciones.add(id + " = " + "new " + stype.getTipo() + "()");
        }
        id.generacionTresDirecciones(tresDirecciones);
        args_cap.generacionTresDirecciones(tresDirecciones);
    }

    public SType getStype() {
        return stype;
    }

    public void setStype(SType stype) {
        this.stype = stype;
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
