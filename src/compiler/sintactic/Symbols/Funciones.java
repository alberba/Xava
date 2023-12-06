package compiler.sintactic.Symbols;

public class Funciones {
    private Funcion f;
    private Funciones sig;

    public Funciones(Funcion f, Funciones sig) {
        this.f = f;
        this.sig = sig;
    }
}
