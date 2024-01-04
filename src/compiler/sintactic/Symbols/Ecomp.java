package compiler.sintactic.Symbols;

public class Ecomp extends SimboloBase {

    private Earit earit;
    private OpComp opcomp;
    private Ecomp ecomp;
    private String val_bol;

    public Ecomp(Earit earit, OpComp opcomp, Ecomp ecomp, int linea, int columna) {
        super(linea,columna);
        this.earit = earit;
        this.opcomp = opcomp;
        this.ecomp = ecomp;
        this.val_bol=null;
    }

    public Ecomp(Earit earit, int linea, int columna) {
        super(linea,columna);
        this.earit = earit;
        this.opcomp = null;
        this.ecomp = null;
        this.val_bol = null;
    }

    public Ecomp(String val_bol, int linea, int columna) {
        super(linea,columna);
        this.earit = null;
        this.opcomp = null;
        this.ecomp = null;
        this.val_bol = val_bol;
    }
}
