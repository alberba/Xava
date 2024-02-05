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
                return "if " + operador1 + " = " + operador2 + " goto " + destino;
            case DIFERENTE:
                return "if " + operador1 + " != " + operador2 + " goto " + destino;
            case MENOR:
                return "if " + operador1 + " < " + operador2 + " goto " + destino;
            case MENOR_IGUAL:
                return "if " + operador1 + " <= " + operador2 + " goto " + destino;
            case MAYOR:
                return "if " + operador1 + " > " + operador2 + " goto " + destino;
            case MAYOR_IGUAL:
                return "if " + operador1 + " >= " + operador2 + " goto " + destino;
            case Y:
                return "if " + operador1 + " and " + operador2 + " goto " + destino;
            case O:
                return "if " + operador1 + " or " + operador2 + " goto " + destino;
            case NO:
                return "if not " + operador1 + " goto " + destino;
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
                return "if " + operador1 + " = 0 goto " + destino;
            case INICIALIZACION:
                return "pmb " + destino;
            case LLAMADA:
                if (operador1 != null) {
                    return "call " + destino + ", " + operador1;
                } else {
                    return "call " + destino;
                }
            case RETORNO:
                if (destino != null) {
                    return "retorno " + destino;
                } else {
                    return "retorno";
                }
            case PARAMETRO_SIMPLE:
                return "param_s " + destino;
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

    public OperacionInst getOperacion() {
        return operacion;
    }

    public String getOperador1() {
        return operador1;
    }

    public String getOperador2() {
        return operador2;
    }

    public String getDestino() {
        return destino;
    }

    public void setOperador1(String operador1) {
        this.operador1 = operador1;
    }

    public void setOperador2(String operador2) {
        this.operador2 = operador2;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setOperacion(OperacionInst op){this.operacion = op;}
}
