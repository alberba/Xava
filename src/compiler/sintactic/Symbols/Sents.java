package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Sents extends SimboloBase {
    private final Sent sent;
    private final Sents sig;

    public Sents(Sent sent, Sents sig, int linea, int columna) {
        super(linea,columna);
        this.sent = sent;
        this.sig = sig;
    }

    public void generarIntermedio(Intermedio intermedio) {
        sent.generarIntermedio(intermedio, null, null);
        if (sig != null) {
            sig.generarIntermedio(intermedio);
        }

    }
}
