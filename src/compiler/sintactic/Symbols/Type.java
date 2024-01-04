package compiler.sintactic.Symbols;

public class Type extends SimboloBase {

    private Boolean constante;
    private SType stype;

    public Type(Boolean constante, SType stype, int linea, int columna) {
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

    public SType getStype() {
        return stype;
    }

    public void setStype(SType stype) {
        this.stype = stype;
    }
}
