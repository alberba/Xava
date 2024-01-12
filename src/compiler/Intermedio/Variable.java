package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

public class Variable {

    private String id;
    private EnumType tipo;
    private boolean esTemp;

    public Variable(String id, EnumType tipo, boolean esTemp) {
        this.id = id;
        this.tipo = tipo;
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

    public boolean isEsTemp() {
        return esTemp;
    }

    public void setEsTemp(boolean esTemp) {
        this.esTemp = esTemp;
    }
}
