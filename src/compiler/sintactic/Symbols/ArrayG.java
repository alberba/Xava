package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;

public class ArrayG extends SimboloBase {

    private String id;
    private L_array lArray;
    private int dimension;

    public ArrayG(String id, L_array lArray, int dimension, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.lArray = lArray;
        this.dimension = dimension;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public L_array getlArray() {
        return lArray;
    }

    public void setlArray(L_array lArray) {
        this.lArray = lArray;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    @Override
    public void generarIntermedio(Intermedio intermedio) {

    }
}
