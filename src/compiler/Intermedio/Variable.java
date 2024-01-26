package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;
import java.util.Objects;

public class Variable {

    private String id;
    private EnumType tipo;
    private boolean esTemp;
    private ArrayList<Variable> longitud;
    private String ambito;

    public Variable(String id, EnumType tipo, boolean esTemp, ArrayList<Variable> longitud, String ambito) {
        this.id = id;
        this.tipo = tipo;
        this.esTemp = esTemp;
        this.longitud = longitud;
        this.ambito = ambito;
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

    public boolean isEsTemp() {
        return esTemp;
    }

    public void setEsTemp(boolean esTemp) {
        this.esTemp = esTemp;
    }

    public ArrayList<Variable> getLongitud() {
        return longitud;
    }

    public void setLongitud(ArrayList<Variable> longitud) {
        this.longitud = longitud;
    }
}
