package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class C_sents extends SimboloBase {

        private Sent sent;
        private C_sent c_sent;
        private C_sents c_sents;

        public C_sents(C_sent c_sent, C_sents c_sents, int linea, int columna) {
            super(linea,columna);
            this.c_sent = c_sent;
            this.c_sents = c_sents;
        }

        public C_sents(Sent sent, C_sents c_sents, int linea, int columna) {
            super(linea,columna);
            this.sent = sent;
            this.c_sents = c_sents;
        }

    public void generarIntermedio(Intermedio intermedio) {
        if (c_sent != null) {
            c_sent.generarIntermedio(intermedio);
        }
        if (sent != null) {
            sent.generarIntermedio(intermedio);
        }
        c_sents.generarIntermedio(intermedio);
    }
}
