package compiler.sintactic.Symbols;

public class Xava {

    private DeclGlob declGlob;
    private Main_fn main;
    private Funciones funciones;

    public Xava(DeclGlob declGlob, Main_fn main, Funciones funciones) {
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
