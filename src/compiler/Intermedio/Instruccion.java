package compiler.Intermedio;

public class Instruccion {

    private OperacionInst operacion;
    private String operador1;
    private String operador2;
    private String destino;

    public Instruccion(OperacionInst operacion, String operador1, String operador2, String destino) {
        this.operacion = operacion;
        this.operador1 = operador1;
        this.operador2 = operador2;
        this.destino = destino;
    }

    @Override
    public String toString() {
        switch (operacion) {
            case SUMA:
                return destino + " = " + operador1 + " + " + operador2;
            case RESTA:
                return destino + " = " + operador1 + " - " + operador2;
            case MULTIPLICACION:
                return destino + " = " + operador1 + " * " + operador2;
            case DIVISION:
                return destino + " = " + operador1 + " / " + operador2;
            case MODULO:
                return destino + " = " + operador1 + " % " + operador2;
            case IGUAL:
                return destino + " = " + operador1 + " == " + operador2;
            case DIFERENTE:
                return destino + " = " + operador1 + " != " + operador2;
            case MENOR:
                return destino + " = " + operador1 + " < " + operador2;
            case MENOR_IGUAL:
                return destino + " = " + operador1 + " <= " + operador2;
            case MAYOR:
                return destino + " = " + operador1 + " > " + operador2;
            case MAYOR_IGUAL:
                return destino + " = " + operador1 + " >= " + operador2;
            case Y:
                return destino + " = " + operador1 + " && " + operador2;
            case O:
                return destino + " = " + operador1 + " || " + operador2;
            case NO:
                return destino + " != " + operador1;
            case ASIG:
                return destino + " = " + operador1;
            case INDEXADO:
                return destino + " = " + operador1 + "[" + operador2 + "]";
            case ASIGNADO:
                return destino + "[" + operador2 + "] = " + operador1;
            case ETIQUETA:
                return destino + ": skip";
            case SALTO_INCON:
                return "goto " + destino;
            case SALTO_COND:
                return "if " + operador1 + " goto " + destino;
            case INICIALIZACION:
                return "pmb " + destino;
            case LLAMADA:
                return "call " + destino;
            case RETORNO:
                return "retorno";
            case PARAMETRO_SIMPLE:
                return "param_s " + destino;
            case PARAMETRO_R:
                return "param_r " + destino;
            case PARAMETRO_ARRAY:
                return "param " + destino + "[" + operador1 + "]";
            case IMPRIMIR:
                return "imprimir " + destino;
            case ENTRADA_ENT:
                return destino + " = leerEnt()";
            case ENTRADA_BOOL:
                return destino + " = leerBool()";
            case ENTRADA_CAR:
                return destino + " = leerCar()";
            default:
                return "";

        }
    }

}
