package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

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

    public void generarIntermedio(Intermedio intermedio) {
        if (declGlob != null) {
            declGlob.generarIntermedio(intermedio);
        }

        main.generarIntermedio(intermedio);

        if (funciones != null) {

            funciones.generarIntermedio(intermedio);
        }
    }

}
