package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Main_fn extends SimboloBase {

    private final Sents sents;

    public Main_fn(Sents sents, int linea, int columna) {
        super(linea,columna);
        this.sents = sents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        intermedio.actualizarAmbito("main");
        intermedio.addPproc("main");
        sents.generarIntermedio(intermedio);
    }

}
