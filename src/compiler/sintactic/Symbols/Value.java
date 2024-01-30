package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

public class Value extends SimboloBase {
    private String value;
    private String tipo;
    private Call_fn call_fn;
    private Entrada entrada;
    private Exp exp;
    private ArrayG arrayG;


    public Value(String value, String tipo, int linea, int columna) {
        super(linea,columna);
        this.tipo = tipo;
        // Los caracteres se guardan entre comillas, por lo que hay que ignorarlas al guardar el valor
        if (tipo.equals("Car")) {
            this.value = value.substring(1, value.length() - 1);
        } else {
            this.value = value;
        }
    }

    public Value(Call_fn call_fn, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Call_fn";
        this.call_fn = call_fn;
    }

    public Value(Entrada entrada, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Entrada";
        this.entrada = entrada;
    }

    public Value(Exp exp, int linea, int columna) {
        super(linea,columna);
        this.tipo= "Exp";
        this.exp = exp;
    }

    public Value(ArrayG arrayG, int linea, int columna) {
        super(linea,columna);
        this.tipo = "Arr";
        this.arrayG = arrayG;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Call_fn getCall_fn() {
        return call_fn;
    }

    public void setCall_fn(Call_fn call_fn) {
        this.call_fn = call_fn;
    }

    public Entrada getEntrada() {
        return entrada;
    }

    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }

    public ArrayG getArrayG() {
        return arrayG;
    }

    public void setArrayG(ArrayG arrayG) {
        this.arrayG = arrayG;
    }

    /**
     * Método que crea las etiquetas e instrucciones necesarias para la ejecución
     */
    public void generarIntermedio(Intermedio intermedio) {
        // Comprobamos si es un simple Valor
        if (value != null) {
            Variable v;
            switch (tipo) {
                case "Id":
                    // Guardamos el valor de la variable en una variable temporal
                    EnumType tipo = intermedio.getTs().getSymbol(value).getTipoReturn();
                    v = intermedio.añadirVariable(value, tipo, null);
                    String temp = intermedio.añadirVariable(null, tipo, null).getId();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, v.getId(), null, temp));
                    break;
                case "Ent":
                    // Guardamos el valor entero en una variable temporal
                    v = intermedio.añadirVariable(null, EnumType.ENTERO, null);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, value, null, v.getId()));
                    break;
                case "Car":
                    // Guardamos el valor carácter en una variable temporal
                    v = intermedio.añadirVariable(null, EnumType.CARACTER, null);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, value, null, v.getId()));
                case "Bol":
                    // Guardamos el valor booleano en una variable temporal
                    v = intermedio.añadirVariable(null, EnumType.BOOLEANO, null);
                    if (value.equals("verdadero")) {
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "-1", null, v.getId()));
                    } else {
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, "0", null, v.getId()));
                    }

                    break;
            }
        }
        if(arrayG != null) {
            // Consultar el valor de array, b = a[3][2]
            // 1. Obtener la dirección de a[3][2]
            intermedio.consultarArray(arrayG, true);
        }
        if(call_fn != null) {
            // Llamada a un subprograma
            Variable temp = intermedio.añadirVariable(null, EnumType.ENTERO, null);
            call_fn.generarIntermedio(intermedio, true);
        }
        if(entrada != null) {
            // Llamada a entrada por teclado
            entrada.generarIntermedio(intermedio);
        }
        if(exp != null) {
            exp.generarIntermedio(intermedio);
        }
    }
}
