package compiler.sintactic.Symbols;

import compiler.Intermedio.*;
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
        Variable var = intermedio.añadirVariable(null, enumType, null);
        switch (enumType) {
            case CARACTER -> intermedio.añadirInstruccion(new Instruccion(OperacionInst.ENTRADA_CAR, null, null, var.getId()));
            case ENTERO -> intermedio.añadirInstruccion(new Instruccion(OperacionInst.ENTRADA_ENT, null, null, var.getId()));
            case BOOLEANO -> intermedio.añadirInstruccion(new Instruccion(OperacionInst.ENTRADA_BOOL, null, null, var.getId()));
        }
    }
}
