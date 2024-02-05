package compiler.sintactic.Symbols;

//import compiler.sintactic.AnSem;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;
import compiler.Intermedio.Variable;

public class Inst extends SimboloBase {
    private final String type;
    private final Exp exp;
    private ArrayG arrayG;
    private C_sents c_sents;
    private Inst inst;
    private String id;
    private Cont_cond contCond;
    private Call_fn call_fn;
    //private AnSem anSem;

    /**
     * Constructor para ifs, fors y whiles
     */
    public Inst(String type, Exp exp, C_sents c_sents, Inst inst, Cont_cond contCond, Call_fn call_fn, int linea, int columna) {

        super(linea,columna);
        this.type = type;
        this.exp = exp;
        this.c_sents = c_sents;
        this.inst = inst;
        this.contCond = contCond;
        this.call_fn = call_fn;
    }

    /**
     * Constructor para asignación de variables y de posiciones de un array, además de para imprimir por pantalla.
     */
    public Inst(String type, String id, ArrayG arrayG, Exp exp, int linea, int columna) {

        super(linea,columna);
        this.type = type;
        this.arrayG = arrayG;
        this.id = id;
        this.exp = exp;
    }

    /**
     *  Método que se encarga de crear las etiquetas e instrucciones qué servirán para crear su versión en compilador
     * @param intermedio Objeto de la clase Intermedio que contiene las instrucciones y variables que se van a utilizar
     */
    public void generarIntermedio(Intermedio intermedio, String labelF, String labelI) {
        // Comprobar si tenemos una estructura if, for, while o do-while
        if (c_sents != null) {
            switch (type) {
                case "cond":
                    exp.generarIntermedio(intermedio);
                    String eFalse = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, eFalse));
                    if (contCond != null) { // Si hay un else
                        String labelFinal = intermedio.nuevaEtiqueta();
                        c_sents.generarIntermedio(intermedio, labelF, labelI);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFinal));
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eFalse));
                        contCond.generarIntermedio(intermedio, labelFinal, labelI, labelF);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal));
                    } else {
                        String eFinal = labelF != null ? labelF : eFalse;
                        c_sents.generarIntermedio(intermedio, eFinal, labelI);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eFalse));
                    }
                    break;
                case "mientras":
                    String labelInit = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit));
                    exp.generarIntermedio(intermedio);
                    String labelFinal = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal));
                    c_sents.generarIntermedio(intermedio, labelFinal, labelInit);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal));
                    break;
                case "para":
                    String labelInit_p = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit_p));
                    exp.generarIntermedio(intermedio);
                    String labelFinal_p = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal_p));
                    String labelInc = intermedio.nuevaEtiqueta();
                    c_sents.generarIntermedio(intermedio, labelFinal_p, labelInc);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInc));
                    inst.generarIntermedio(intermedio, null, null);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit_p));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal_p));
                    break;
                case "hacer_mientras":
                    String labelInit_hm = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit_hm));
                    String labelFinal_hm = intermedio.nuevaEtiqueta();
                    c_sents.generarIntermedio(intermedio, labelFinal_hm, labelInit_hm);
                    exp.generarIntermedio(intermedio);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal_hm));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit_hm));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal_hm));
                    break;
            }

        }
        // comprobamos si se trata de una asignación, impresión por pantalla o llamada a un subprograma
        switch (type) {
            case "asig":
                exp.generarIntermedio(intermedio);
                Variable varExp = intermedio.getUltimaVariable();
                Variable var = intermedio.añadirVariable(id, varExp.getTipo(), null);
                if (arrayG != null) {
                    intermedio.consultarArray(arrayG, false);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIGNADO, varExp.getId(), intermedio.getUltimaVariable().getId(), var.getId()));
                    break;
                } else {
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, varExp.getId(), null, var.getId()));
                }
                break;
            case "impr":
                exp.generarIntermedio(intermedio);
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.IMPRIMIR, null, null, intermedio.getUltimaVariable().getId()));
                break;
            case "call_fn":
                // Llamada a función que no devuelve nada
                call_fn.generarIntermedio(intermedio, false);
                break;
        }
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
