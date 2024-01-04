package compiler.sintactic.Symbols;

public class D_asig extends SimboloBase {

    private Exp e;

    public D_asig(Exp e, int linea, int columna) {
        super(linea,columna);
        this.e = e;
    }

}
