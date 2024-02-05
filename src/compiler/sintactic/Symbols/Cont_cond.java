package compiler.sintactic.Symbols;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

public class Cont_cond extends SimboloBase {

    private Exp exp;
    private Cont_cond cont_cond;
    private C_sents c_sents;

    public Cont_cond(Exp exp, C_sents c_sents, Cont_cond cont_cond, int linea, int columna) {
        super(linea,columna);
        this.exp = exp;
        this.c_sents = c_sents;
        this.cont_cond = cont_cond;
    }

    public Cont_cond(int linea, int columna) {
        super(linea,columna);
    }

    /**
     * Genera la contracondición del if (else if y else), muy similar a la generación de código del if
     * @param intermedio Objeto de la clase Intermedio que contiene las instrucciones y variables que se van a utilizar
     * @param labelFinalIf Etiqueta que indica el final del if
     */
    public void generarIntermedio(Intermedio intermedio, String labelFinalIf, String labelFinalExt, String labelIniExt) {
        if (exp != null) {
            exp.generarIntermedio(intermedio);
            // Se trata de un else if
            String labelFalse = intermedio.nuevaEtiqueta();
            intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_COND, intermedio.getUltimaVariable().getId(), null, labelFalse));
            String efinal = labelFinalExt != null ? labelFinalExt : labelFinalIf;
            c_sents.generarIntermedio(intermedio, efinal, labelIniExt);
            if (cont_cond != null) { // Se mira si hay más else ifs
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.SALTO_INCON, null, null, labelFinalIf));
                intermedio.añadirInstruccion(new Instruccion(OperacionInst.ETIQUETA, null, null, labelFalse));
                cont_cond.generarIntermedio(intermedio, labelFinalIf, labelFinalExt, labelIniExt);
            }
        } else { // Se trata de un else
            String efinal = labelFinalExt != null ? labelFinalExt : labelFinalIf;
            c_sents.generarIntermedio(intermedio, efinal, labelIniExt);
        }
    }
    }
