package compiler.sintactic.Symbols;

public class RetProc extends SimboloBase {

    private Exp e;

    public RetProc(Exp e, int linea, int columna) {
        super(linea,columna);
        this.e = e;
    }
}
