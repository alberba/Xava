package compiler.sintactic.Symbols;

public class DeclsF extends SimboloBase {
    private DeclF declF;
    private DeclsF declsF;
    public DeclsF(DeclF declF, DeclsF declsF, int linea, int columna){
        super(linea,columna);
        this.declF = declF;
        this.declsF = declsF;
    }
}
