package compiler.sintactic.Symbols;

public class L_array extends SimboloBase {

    private Exp exp;
    private final L_array lArray;

    public L_array(Exp exp, L_array lArray, int linea, int columna) {
        super(linea, columna);
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


}
