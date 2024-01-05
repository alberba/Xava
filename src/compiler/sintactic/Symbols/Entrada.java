package compiler.sintactic.Symbols;

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
}
