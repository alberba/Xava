package compiler.sintactic.Symbols;

public class Sent {

    private Decl decl;
    private Inst inst;

    public Sent(Decl decl) {
        this.decl = decl;
    }

    public Sent(Inst inst) {
        this.inst = inst;
    }

}
