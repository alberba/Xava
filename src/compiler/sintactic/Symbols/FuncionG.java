package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class FuncionG extends SimboloBase {

    private Cap cap;
    private FSents fsents;

    public FuncionG(Cap cap, FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.cap = cap;
        this.fsents = fsents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        cap.generarIntermedio(intermedio);
        fsents.generarIntermedio(intermedio);
    }

}
