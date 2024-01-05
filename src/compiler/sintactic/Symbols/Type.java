package compiler.sintactic.Symbols;

public class Type extends SimboloBase {

    private Boolean constante;
    private EnumType stype;

    public Type(Boolean constante, EnumType stype, int linea, int columna) {
        super(linea,columna);
        this.constante = constante;
        this.stype = stype;
    }

    public Boolean getConstante() {
        return constante;
    }

    public void setConstante(Boolean constante) {
        this.constante = constante;
    }

    public EnumType getStype() {
        return stype;
    }

    public void setStype(EnumType stype) {
        this.stype = stype;
    }
}
