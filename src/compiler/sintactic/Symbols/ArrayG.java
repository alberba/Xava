package compiler.sintactic.Symbols;

public class ArrayG extends SimboloBase {

    private String id;
    private final L_array lArray;
    private final int dimension;

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

    public int getDimension() {
        return dimension;
    }

}
