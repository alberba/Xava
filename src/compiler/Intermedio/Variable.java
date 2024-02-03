package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Variable {

    private String id;
    private EnumType tipo;
    private final ArrayList<Integer> longitud;
    private final boolean esTemp;

    public Variable(String id, EnumType tipo, ArrayList<Integer> longitud, boolean esTemp) {
        this.id = id;
        this.tipo = tipo;
        this.longitud = longitud;
        this.esTemp = esTemp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EnumType getTipo() {
        return tipo;
    }

    public void setTipo(EnumType tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Integer> getLongitud() {
        return longitud;
    }

    public int getNumElementos() {
        int longitud = 1;
        if (this.longitud == null) {
            return 0;
        }
        for (Integer i : this.longitud) {
            longitud *= i;
        }

        return longitud;
    }

    public boolean isEsTemp() {
        return esTemp;
    }

}
