package compiler.sintactic.Symbols;

public class Decl {

    private Type type;
    private Lid lid;
    private D_asig d_asignacion;

    public Decl(Type type, Lid lid, D_asig d_asignacion) {
        this.type = type;
        this.lid = lid;
        this.d_asignacion = d_asignacion;
    }
}
