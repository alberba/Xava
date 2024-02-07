package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

public class Xava extends SimboloBase {

    private final DeclGlob declGlob;
    private final Main_fn main;
    private final Funciones funciones;

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

        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, "e_fin"));
    }

}
