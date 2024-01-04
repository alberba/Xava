package compiler.sintactic.Symbols;

public class Funciones extends SimboloBase {
    private FuncionG f;
    private Funciones sig;

    public Funciones(FuncionG f, Funciones sig, int linea, int columna) {
        super(linea,columna);
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
