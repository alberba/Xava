package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Sents extends SimboloBase {
    private Sent sent;
    private Sents sig;

    public Sents(Sent sent, Sents sig, int linea, int columna) {
        super(linea,columna);
        this.sent = sent;
        this.sig = sig;
    }

    public void generarIntermedio(Intermedio intermedio) {
        sent.generarIntermedio(intermedio);
        if (sig != null) {
            sig.generarIntermedio(intermedio);
        }

    }
}
