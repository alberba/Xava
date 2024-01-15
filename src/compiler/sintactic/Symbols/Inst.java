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
    private Decl decl;
    private String id;
    private Cont_cond contCond;
    //private AnSem anSem;

    // Constructor para ifs, fors y whiles
    public Inst(String type, Exp exp, C_sents c_sents, Decl decl_cap, Decl decl, Cont_cond contCond, int linea, int columna) {
        //anSem.esExpressionBooleana(exp);
        // añadir gestión error

        super(linea,columna);
        this.type = type;
        this.exp = exp;
        this.c_sents = c_sents;
        this.decl_cap = decl_cap;
        this.decl = decl;
        this.contCond = contCond;

    }

    public Inst(String type, String id, Exp dimArray, Exp exp, int linea, int columna) {
        //anSem.esExpressionBooleana(exp);
        // añadir gestión error

        super(linea,columna);
        this.type = type;
        this.dimArray = dimArray;
        this.id = id;
        this.exp = exp;
    }

    public void generarIntermedio(Intermedio intermedio) {

        if (dimArray != null) {
            dimArray.generarIntermedio(intermedio);
        }
        if (c_sents != null) {
            switch (type) {
                case "cond":
                    exp.generarIntermedio(intermedio);
                    String labelTrue = intermedio.nuevaEtiqueta();
                    String labelFalse = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelTrue));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFalse));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelTrue));
                    c_sents.generarIntermedio(intermedio);
                    break;
                case "mientras":
                    String labelInit = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelInit));
                    exp.generarIntermedio(intermedio);
                    String labelFinal = intermedio.nuevaEtiqueta();
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFinal));
                    c_sents.generarIntermedio(intermedio);
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelInit));
                    intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFinal));
                    break;
                case "para":
                    break;
            }

        }
        if (decl_cap != null) {
            decl_cap.generarIntermedio(intermedio);
        }
        if (decl != null) {
            decl.generarIntermedio(intermedio);
        }
        if (contCond != null) {
            contCond.generarIntermedio(intermedio);
        }
    }


}
