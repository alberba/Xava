package compiler.sintactic.Symbols;

public class FuncionG extends SimboloBase {

    private Cap cap;
    private FSents fsents;

    public FuncionG(Cap cap, FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.cap = cap;
        this.fsents = fsents;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        cap.generacionTresDirecciones(tresDirecciones);
        //entrabloc
        /*
        GESTION DE LOS PARAMETROS
        mientras haya parametros{
            // Consulta en la tabla de simbolos el id
            arg = consulta(ts, idt)
            novaVariable = new Variable(idVariableExterior, idVariableexterno)
            ponerSimbolo(ts, novaVariable)

        */
        fsents.generacionTresDirecciones(tresDirecciones);
        //surtbloc
    }
}
