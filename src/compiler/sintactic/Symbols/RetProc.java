package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

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
        e.generarIntermedio(intermedio);
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.RETORNO, null, null, null));
    }
}
