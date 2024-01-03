package compiler.sintactic.Symbols;

public class Main_fn {

    private FSents fsents;

    public Main_fn(FSents fsents) {
        this.fsents = fsents;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        fsents.generacionTresDirecciones(tresDirecciones);
    }
}
