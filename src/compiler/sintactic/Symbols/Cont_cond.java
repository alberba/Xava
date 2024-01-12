package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Cont_cond extends SimboloBase {

        private Exp exp;
        private Cont_cond cont_cond;
        private Sents sents;

        public Cont_cond(Exp exp, Sents sents, Cont_cond cont_cond, int linea, int columna) {
            super(linea,columna);
            this.exp = exp;
            this.sents = sents;
            this.cont_cond = cont_cond;
        }

        public Cont_cond(int linea, int columna) {
            super(linea,columna);
        }

    public void generarIntermedio(Intermedio intermedio) {
        if (exp != null) {
            exp.generarIntermedio(intermedio);
        }
        sents.generarIntermedio(intermedio);
        if (cont_cond != null) {
            cont_cond.generarIntermedio(intermedio);
        }
    }
}
