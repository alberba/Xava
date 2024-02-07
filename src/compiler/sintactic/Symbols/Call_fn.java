package compiler.sintactic.Symbols;

import compiler.Intermedio.*;

import java.util.ArrayList;

public class Call_fn extends SimboloBase {
    private final String id;
    private final Args_Call args_call;

    public Call_fn(String id, Args_Call args_call, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.args_call = args_call;
    }

    public String getId() {
        return this.id;
    }

    /**
     * Generar Intermedio
     * @param intermedio Intermedio
     * @param devolver Si el callFn tiene que devolver un valor
     */
    public void generarIntermedio(Intermedio intermedio, boolean devolver) {
        Procedimiento proc = intermedio.getProcedimiento(id);
        ArrayList<Variable> parametros = new ArrayList<>();
        if (args_call != null) {
            // comprobamos si hay algún parametro a enviar a la función
            parametros = args_call.generarIntermedio(intermedio, parametros);
        }

        // Añadimos las instrucciones correpondientes para poder enviar los parametros a la función
        for (Variable parametro: parametros) {
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.PARAMETRO_SIMPLE, null, null, parametro.getId()));
        }

        // Si Call_fn llama a una función que tiene un return, se guarda el valor de retorno en una variable temporal
        if (devolver) {
            Variable temp = intermedio.añadirVariable(null, EnumType.ENTERO, null);
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.LLAMADA, temp.getId(), null, proc.getEtiqueta()));
        } else {
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.LLAMADA, null, null, proc.getEtiqueta()));
        }

    }
}
