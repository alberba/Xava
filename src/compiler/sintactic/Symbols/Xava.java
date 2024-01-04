package compiler.sintactic.Symbols;

public class Xava extends SimboloBase {

    private DeclGlob declGlob;
    private Main_fn main;
    private Funciones funciones;

    public Xava(DeclGlob declGlob, Main_fn main, Funciones funciones, int linea, int columna) {
        super(linea,columna);
        this.declGlob = declGlob;
        this.main = main;
        this.funciones = funciones;
    }

    public void generacionTresDirecciones(TresDirecciones tresDirecciones) {
        declGlob.generacionTresDirecciones(tresDirecciones);
        main.generacionTresDirecciones(tresDirecciones);
        funciones.generacionTresDirecciones(tresDirecciones);
    }



}
