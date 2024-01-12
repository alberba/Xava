package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class D_asig extends SimboloBase {

    private Exp e;

    public D_asig(Exp e, int linea, int columna) {
        super(linea,columna);
        this.e = e;
    }

    public Exp getE() {
        return e;
    }

    public void setE(Exp e) {
        this.e = e;
    }

    public void generarIntermedio(Intermedio intermedio) {
        e.generarIntermedio(intermedio);
    }
}
