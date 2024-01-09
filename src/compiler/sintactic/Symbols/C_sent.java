package compiler.sintactic.Symbols;

public class C_sent extends SimboloBase{
    private String type;
    private RetProc retProc;

    public C_sent(String type, RetProc retProc, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.retProc = retProc;
    }

}
