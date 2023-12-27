package compiler.sintactic.Symbols;

public class C_sents {

        private Sent sent;
        private C_sent c_sent;
        private C_sents c_sents;

        public C_sents(C_sent c_sent, C_sents c_sents) {
            this.c_sent = c_sent;
            this.c_sents = c_sents;
        }

        public C_sents(Sent sent, C_sents c_sents) {
            this.sent = sent;
            this.c_sents = c_sents;
        }
}
