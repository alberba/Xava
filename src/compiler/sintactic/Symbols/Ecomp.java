package compiler.sintactic.Symbols;

public class Ecomp {

    private Earit earit;
    private OpComp opcomp;
    private Ecomp ecomp;
    private String val_bol;

    public Ecomp(Earit earit, OpComp opcomp, Ecomp ecomp) {
        this.earit = earit;
        this.opcomp = opcomp;
        this.ecomp = ecomp;
        this.val_bol=null;
    }

    public Ecomp(Earit earit) {
        this.earit = earit;
        this.opcomp = null;
        this.ecomp = null;
        this.val_bol = null;
    }

    public Ecomp(String val_bol) {
        this.earit = null;
        this.opcomp = null;
        this.ecomp = null;
        this.val_bol = val_bol;
    }
}
