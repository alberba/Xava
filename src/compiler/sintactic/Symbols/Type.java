package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
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

    public void setConstante(boolean constante) {
        this.constante = constante;
    }

    public EnumType getStype() {
        return stype;
    }

    public void setStype(EnumType stype) {
        this.stype = stype;
    }

    public void generarIntermedio(Intermedio intermedio) {
    }
}
