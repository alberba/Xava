package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Procedimiento;

public class Main_fn extends SimboloBase {

    private final Sents sents;

    public Main_fn(Sents sents, int linea, int columna) {
        super(linea,columna);
        this.sents = sents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        Procedimiento proc = intermedio.añadirProcedimiento("main", EnumType.VACIO);
        intermedio.setNprodActual("main");
        intermedio.actualizarAmbito("main");
        intermedio.addPproc("main");
        sents.generarIntermedio(intermedio);
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, "e_fin"));
    }

}
