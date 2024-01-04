package compiler.sintactic.Symbols;

public class FSents extends SimboloBase {

    private Sents sents;
    private RetProc retProc;

    public FSents(Sents sents, RetProc retProc, int linea, int columna) {
        super(linea,columna);
        this.sents = sents;
        this.retProc = retProc;
    }

    public FSents(Sents sents, int linea, int columna) {
        super(linea,columna);
        this.sents = sents;
    }

}
