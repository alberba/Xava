package compiler.sintactic.Symbols;

public class Main_fn extends SimboloBase {

    private FSents fsents;

    public Main_fn(FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.fsents = fsents;
    }

}
