package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class Entrada extends SimboloBase {

    private EnumType enumType;

    public Entrada(EnumType enumType, int line, int column) {
        super(line, column);
        this.enumType = enumType;
    }

    public EnumType getEnumType() {
        return enumType;
    }

    public void setEnumType(EnumType enumType) {
        this.enumType = enumType;
    }

    public void generarIntermedio(Intermedio intermedio) {

    }
}
