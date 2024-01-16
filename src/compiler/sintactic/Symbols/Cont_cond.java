package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

public class Cont_cond extends SimboloBase {

        private Exp exp;
        private Cont_cond cont_cond;
        private C_sents c_sents;

        public Cont_cond(Exp exp, C_sents c_sents, Cont_cond cont_cond, int linea, int columna) {
            super(linea,columna);
            this.exp = exp;
            this.c_sents = c_sents;
            this.cont_cond = cont_cond;
        }

        public Cont_cond(int linea, int columna) {
            super(linea,columna);
        }

    public void generarIntermedio(Intermedio intermedio, String labelFinal) {
        if (exp != null) {
            exp.generarIntermedio(intermedio);
            // Se trata de un else if
            String labelTrue = intermedio.nuevaEtiqueta();
            String labelFalse = intermedio.nuevaEtiqueta();
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelTrue));
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFalse));
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelTrue));
            c_sents.generarIntermedio(intermedio, labelFinal, labelFinal, null);
            if (cont_cond != null) {
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFinal));
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFalse));
                cont_cond.generarIntermedio(intermedio, labelFinal);
            }
        } else {
            c_sents.generarIntermedio(intermedio, labelFinal, labelFinal, null);
        }
    }
}
