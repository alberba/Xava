package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Main_fn extends SimboloBase {

    private Sents sents;

    public Main_fn(Sents sents, int linea, int columna) {
        super(linea,columna);
        this.sents = sents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        sents.generarIntermedio(intermedio);
    }

}
