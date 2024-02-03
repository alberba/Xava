package compiler.sintactic.Symbols;

public class Type extends SimboloBase {

    private boolean constante;
    private EnumType stype;

    public Type(boolean constante, EnumType stype, int linea, int columna) {
        super(linea,columna);
        this.constante = constante;
        this.stype = stype;
    }

    public boolean getConstante() {
        return constante;
    }

    public EnumType getStype() {
        return stype;
    }

}
