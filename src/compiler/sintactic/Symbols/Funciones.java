package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Funciones extends SimboloBase {
    private FuncionG f;
    private Funciones sig;

    public Funciones(FuncionG f, Funciones sig, int linea, int columna) {
        super(linea,columna);
        this.f = f;
        this.sig = sig;
    }

    public void generarIntermedio(Intermedio intermedio) {
            f.generarIntermedio(intermedio);
            sig.generarIntermedio(intermedio);
    }

}
