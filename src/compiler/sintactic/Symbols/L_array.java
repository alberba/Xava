package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

public class L_array extends SimboloBase {

    private Exp exp;
    private L_array lArray;

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

    public void setlArray(L_array lArray) {
        this.lArray = lArray;
    }

}
