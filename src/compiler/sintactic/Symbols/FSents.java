package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
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

    public Sents getSents() {
        return sents;
    }

    public void setSents(Sents sents) {
        this.sents = sents;
    }

    public RetProc getRetProc() {
        return retProc;
    }

    public void setRetProc(RetProc retProc) {
        this.retProc = retProc;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if (sents != null) {
            sents.generarIntermedio(intermedio);
        }
        if (retProc != null) {
            retProc.generarIntermedio(intermedio);
        }
    }
}
