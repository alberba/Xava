package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

public class C_sent extends SimboloBase{
    private String type;
    private RetProc retProc;

    public C_sent(String type, RetProc retProc, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.retProc = retProc;
    }

    public void generarIntermedio(Intermedio intermedio, String labelFinal, String labelInit, String retorno) {
        switch(type){
            case "Romper":
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFinal));
                break;
            case "Continuar":
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit));
                break;
            case"ret_proc":
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.RETORNO, null, null, null));
                retProc.generarIntermedio(intermedio);
                break;
        }
    }

}
