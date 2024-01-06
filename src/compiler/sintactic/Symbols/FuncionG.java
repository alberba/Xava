package compiler.sintactic.Symbols;

public class FuncionG extends SimboloBase {

    private Cap cap;
    private FSents fsents;

    public FuncionG(Cap cap, FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.cap = cap;
        this.fsents = fsents;
    }

}
