package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

import java.util.ArrayList;

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

    public void generarIntermedio(Intermedio intermedio, ArrayList<Variable> varLids) {
        e.generarIntermedio(intermedio);
        for (Variable varLid : varLids) {
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, intermedio.getUltimaVariable().getId(), null, varLid.getId()));
        }
    }
}
