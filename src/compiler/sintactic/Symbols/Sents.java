package compiler.sintactic.Symbols;

public class Sents extends SimboloBase {
    private Sent sent;
    private Sents sig;

    public Sents(Sent sent, Sents sig, int linea, int columna) {
        super(linea,columna);
        this.sent = sent;
        this.sig = sig;
    }
}
