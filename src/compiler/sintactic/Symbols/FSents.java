package compiler.sintactic.Symbols;

public class FSents {

    private Sents sents;
    private RetProc retProc;

    public FSents(Sents sents, RetProc retProc) {
        this.sents = sents;
        this.retProc = retProc;
    }

    public FSents(Sents sents) {
        this.sents = sents;
    }

}
