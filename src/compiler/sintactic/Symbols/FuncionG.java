package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Procedimiento;

public class FuncionG extends SimboloBase {

    private Cap cap;
    private FSents fsents;

    public FuncionG(Cap cap, FSents fsents, int linea, int columna) {
        super(linea,columna);
        this.cap = cap;
        this.fsents = fsents;
    }

    public void generarIntermedio(Intermedio intermedio) {
        Procedimiento proc = intermedio.getProcedimiento(cap.getId());
        intermedio.setNprodActual(cap.getId());
        intermedio.añadirInstruccion(new Instruccion(OperacionInst.INICIALIZACION, null, null, proc.getId()));
        intermedio.addPproc(proc.getId());
        cap.generarIntermedio(intermedio);
        fsents.generarIntermedio(intermedio);
        intermedio.subPproc();
    }

}
