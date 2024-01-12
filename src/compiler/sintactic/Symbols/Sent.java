package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Sent extends SimboloBase {

    private Decl decl;
    private Inst inst;

    public Sent(Decl decl, int linea, int columna) {
        super(linea,columna);
        this.decl = decl;
    }

    public Sent(Inst inst, int linea, int columna) {
        super(linea,columna);
        this.inst = inst;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if(decl != null){
            decl.generarIntermedio(intermedio);
        }
        if(inst != null){
            inst.generarIntermedio(intermedio);
        }
    }

}
