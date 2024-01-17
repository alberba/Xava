package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Procedimiento;
import compiler.Intermedio.Instruccion;
import compiler.Intermedio.OperacionInst;

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

    public void generarIntermedio(Intermedio intermedio) {
        Procedimiento proc = intermedio.getProcedimiento(id);
        if (args_call != null) {
            intermedio.setEsParametro(true);
            args_call.generarIntermedio(intermedio);
            intermedio.setEsParametro(false);
        }

        intermedio.añadirInstruccion(new Instruccion(OperacionInst.LLAMADA, null, null, proc.getEtiqueta()));
    }
}
