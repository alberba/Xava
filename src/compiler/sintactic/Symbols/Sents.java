package compiler.sintactic.Symbols;

public class Sents {
    private Sent sent;
    private Sents sig;

    public Sents(Sent sent, Sents sig) {
        this.sent = sent;
        this.sig = sig;
    }
}
