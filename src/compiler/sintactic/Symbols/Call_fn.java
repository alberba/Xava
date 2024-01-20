package compiler.sintactic.Symbols;

import compiler.Intermedio.*;

import java.util.ArrayList;

public class Call_fn extends SimboloBase {
    private String id;
    private Args_Call args_call;

    public Call_fn(String id, Args_Call args_call, int linea, int columna) {
        super(linea,columna);
        this.id = id;
        this.args_call = args_call;
    }

    public String getId() {
        return this.id;
    }

    public void generarIntermedio(Intermedio intermedio, Variable varReturn) {
        Procedimiento proc = intermedio.getProcedimiento(id);
        ArrayList<Variable> parametros = new ArrayList<>();
        if (args_call != null) {
            intermedio.setEsParametro(true);
            parametros = args_call.generarIntermedio(intermedio, parametros);
            intermedio.setEsParametro(false);
        }
        if (varReturn != null) {
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.PARAMETRO_R, null, null, varReturn.getId()));
        }
        for (Variable parametro: parametros) {
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.PARAMETRO_SIMPLE, null, null, parametro.getId()));
        }
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.LLAMADA, null, null, proc.getEtiqueta()));
    }
}
