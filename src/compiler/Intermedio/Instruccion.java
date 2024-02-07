package compiler.Intermedio;

import java.util.Objects;

public class Instruccion {

    private OperacionInst operacion;
    private String operando1;
    private String operando2;
    private String destino;

    public Instruccion(OperacionInst operacion, String op1, String op2, String destino) {
        this.operacion = operacion;
        this.operando1 = op1;
        this.operando2 = op2;
        this.destino = destino;
    }

    @Override
    public String toString() {
        switch (operacion) {
            case SUMA:
                return destino + " = " + operando1 + " + " + operando2;
            case RESTA:
                return destino + " = " + operando1 + " - " + operando2;
            case MULTIPLICACION:
                return destino + " = " + operando1 + " * " + operando2;
            case DIVISION:
                return destino + " = " + operando1 + " / " + operando2;
            case MODULO:
                return destino + " = " + operando1 + " % " + operando2;
            case IGUAL:
                return "if " + operando1 + " = " + operando2 + " goto " + destino;
            case DIFERENTE:
                return "if " + operando1 + " != " + operando2 + " goto " + destino;
            case MENOR:
                return "if " + operando1 + " < " + operando2 + " goto " + destino;
            case MENOR_IGUAL:
                return "if " + operando1 + " <= " + operando2 + " goto " + destino;
            case MAYOR:
                return "if " + operando1 + " > " + operando2 + " goto " + destino;
            case MAYOR_IGUAL:
                return "if " + operando1 + " >= " + operando2 + " goto " + destino;
            case Y:
                return "if " + operando1 + " and " + operando2 + " goto " + destino;
            case O:
                return "if " + operando1 + " or " + operando2 + " goto " + destino;
            case NO:
                return "if not " + operando1 + " goto " + destino;
            case ASIG:
                return destino + " = " + operando1;
            case INDEXADO:
                return destino + " = " + operando1 + "[" + operando2 + "]";
            case ASIGNADO:
                return destino + "[" + operando2 + "] = " + operando1;
            case ETIQUETA:
                return destino + ": skip";
            case SALTO_INCON:
                return "goto " + destino;
            case SALTO_COND:
                return "if " + operando1 + " = 0 goto " + destino;
            case INICIALIZACION:
                return "pmb " + destino;
            case LLAMADA:
                if (operando1 != null) {
                    return "call " + destino + ", " + operando1;
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

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    public String getDestino() {
        return destino;
    }

    public void setOperando1(String operador1) {
        this.operando1 = operador1;
    }

    public void setOperando2(String operador2) {
        this.operando2 = operador2;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setOperacion(OperacionInst op){this.operacion = op;}

    @Override
    public boolean equals(Object o) {
        Instruccion that = (Instruccion) o;
        // Verificar si operador1 y operador2 son null
        if (operando1 == null && that.operando1 != null) return false;
        if (operando2 == null && that.operando2 != null) return false;
        // Verificar si operador1 y operador2 son iguales
        if (operando1 != null && !operando1.equals(that.operando1)) return false;
        if (operando2 != null && !operando2.equals(that.operando2)) return false;
        // Continuar con las otras comparaciones
        return operacion.equals(that.operacion) &&
                destino.equals(that.destino);
    }


    @Override
    public int hashCode() {
        return Objects.hash(operacion, operando1, operando2, destino);
    }
}
