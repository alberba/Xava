package compiler.sintactic.Symbols;

public class Funciones {
    private FuncionG f;
    private Funciones sig;

    public Funciones(FuncionG f, Funciones sig) {
        this.f = f;
        this.sig = sig;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        f.generacionTresDirecciones(tresDirecciones);
        if (sig != null) {
            sig.generacionTresDirecciones(tresDirecciones);
        }
    }
}
