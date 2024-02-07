package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;
public class Entrada extends SimboloBase {

    private final EnumType enumType;

    public Entrada(EnumType enumType, int line, int column) {
        super(line, column);
        this.enumType = enumType;
    }

    public EnumType getEnumType() {
        return enumType;
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
