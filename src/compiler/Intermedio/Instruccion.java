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

}
