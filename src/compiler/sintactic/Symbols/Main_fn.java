package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Main_fn extends SimboloBase {

    private FSents fsents;

    public Main_fn(FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.fsents = fsents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        if(fsents != null){
            fsents.generarIntermedio(intermedio);
        }
    }

}
