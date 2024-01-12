package compiler.sintactic.Symbols;

public class L_array {

    private Exp exp;
    private L_array lArray;

    public L_array(Exp exp, L_array lArray) {
        this.exp = exp;
        this.lArray = lArray;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public L_array getlArray() {
        return lArray;
    }

    public void setlArray(L_array lArray) {
        this.lArray = lArray;
    }
}
