package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class RetProc extends SimboloBase {

    private Exp e;

    public RetProc(Exp e, int linea, int columna) {
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
        if(e != null){
            e.generarIntermedio(intermedio);
        }
    }
}
