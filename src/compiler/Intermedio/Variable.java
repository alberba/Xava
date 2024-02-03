package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Variable {

    private String id;
    private EnumType tipo;
    private final ArrayList<Integer> longitud;

    public Variable(String id, EnumType tipo, ArrayList<Integer> longitud) {
        this.id = id;
        this.tipo = tipo;
        this.longitud = longitud;
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

}
