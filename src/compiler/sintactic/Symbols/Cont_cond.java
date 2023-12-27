package compiler.sintactic.Symbols;

public class Cont_cond {

        private Exp exp;
        private Cont_cond cont_cond;
        private Sents sents;

        public Cont_cond(Exp exp, Sents sents, Cont_cond cont_cond) {
            this.exp = exp;
            this.sents = sents;
            this.cont_cond = cont_cond;
        }

        public Cont_cond() {
        }
}
