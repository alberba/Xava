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
}
