package compiler.sintactic.Symbols;

//import compiler.sintactic.AnSem;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

public class Inst extends SimboloBase {
    private String type;
    private Exp exp;
    private Exp dimArray;
    private C_sents c_sents;
    private Decl decl_cap;
    private Inst inst;
    private String id;
    private Cont_cond contCond;
    private Call_fn call_fn;
    //private AnSem anSem;

    /**
     * Constructor para ifs, fors y whiles
     */
    public Inst(String type, Exp exp, C_sents c_sents, Decl decl_cap, Inst inst, Cont_cond contCond, Call_fn call_fn, int linea, int columna) {

        super(linea,columna);
        this.type = type;
        this.exp = exp;
        this.c_sents = c_sents;
        this.decl_cap = decl_cap;
        this.inst = inst;
        this.contCond = contCond;
        this.call_fn = call_fn;
    }

    /**
     * Constructor para asignación de variables y de posiciones de un array, además de para imprimir por pantalla.
     */
    public Inst(String type, String id, Exp dimArray, Exp exp, int linea, int columna) {

        super(linea,columna);
        this.type = type;
        this.dimArray = dimArray;
        this.id = id;
        this.exp = exp;
    }

    /**
     *  Método que se encarga de crear las etiquetas e instrucciones qué servirán para crear su versión en compilador
     * @param intermedio objeto intermedio que se usará para recorrer todas las instrucciones luego.
     */
    public void generarIntermedio(Intermedio intermedio) {
        //Comprobar si se trata de un array
        if (dimArray != null) {
            dimArray.generarIntermedio(intermedio);
        }
        //Comprobar si tenemos una estructura if, for, while o do-while
        if (c_sents != null) {
            switch (type) {
                case "cond":
                    exp.generarIntermedio(intermedio);
                    String eFalse = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, eFalse));
                    if (contCond != null) {
                        String labelFinal = intermedio.nuevaEtiqueta();
                        c_sents.generarIntermedio(intermedio, labelFinal, null, null);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFinal));
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eFalse));
                        contCond.generarIntermedio(intermedio, labelFinal);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal));
                    } else {
                        c_sents.generarIntermedio(intermedio, eFalse, null, null);
                        intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, eFalse));
                    }

                    break;
                case "mientras":
                    String labelInit = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit));
                    exp.generarIntermedio(intermedio);
                    String labelFinal = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal));
                    c_sents.generarIntermedio(intermedio, labelFinal, labelInit, null);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal));
                    break;
                case "para":
                    decl_cap.generarIntermedio(intermedio);
                    String labelInit_p = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit_p));
                    exp.generarIntermedio(intermedio);
                    String labelFinal_p = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal_p));
                    c_sents.generarIntermedio(intermedio, labelFinal_p, labelInit_p, null);
                    inst.generarIntermedio(intermedio);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit_p));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal_p));
                    break;
                case "hacer_mientras":
                    String labelInit_hm = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit_hm));
                    String labelFinal_hm = intermedio.nuevaEtiqueta();
                    c_sents.generarIntermedio(intermedio, labelFinal_hm, labelInit_hm, null);
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
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.ASIG, intermedio.getUltimaVariable().getId(), null, id));
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

    public void setType(String type) {
        this.type = type;
    }

    public Exp getExp() {
        return exp;
    }

    public void setExp(Exp exp) {
        this.exp = exp;
    }

    public Exp getDimArray() {
        return dimArray;
    }

    public void setDimArray(Exp dimArray) {
        this.dimArray = dimArray;
    }

    public C_sents getC_sents() {
        return c_sents;
    }

    public void setC_sents(C_sents c_sents) {
        this.c_sents = c_sents;
    }

    public Decl getDecl_cap() {
        return decl_cap;
    }

    public void setDecl_cap(Decl decl_cap) {
        this.decl_cap = decl_cap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Cont_cond getContCond() {
        return contCond;
    }

    public void setContCond(Cont_cond contCond) {
        this.contCond = contCond;
    }

    public Call_fn getCall_fn() {
        return call_fn;
    }

    public void setCall_fn(Call_fn call_fn) {
        this.call_fn = call_fn;
    }
}
