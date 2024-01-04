package compiler.sintactic.Symbols;

public class Sent extends SimboloBase {

    private Decl decl;
    private Inst inst;

    public Sent(Decl decl, int linea, int columna) {
        super(linea,columna);
        this.decl = decl;
    }

    public Sent(Inst inst, int linea, int columna) {
        super(linea,columna);
        this.inst = inst;
    }

}
