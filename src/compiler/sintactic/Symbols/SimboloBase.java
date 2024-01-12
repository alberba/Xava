package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

public abstract class SimboloBase {
    private int linea;
    private int columna;
    public SimboloBase(int linea, int columna){
        this.linea = linea;
        this.columna = columna;
    }
    public int getLinea(){
        return this.linea;
    }

    public int getColumna(){
        return this.linea;
    }

    public abstract void generarIntermedio(Intermedio intermedio);
}
