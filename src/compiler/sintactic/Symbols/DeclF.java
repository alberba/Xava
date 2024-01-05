package compiler.sintactic.Symbols;

public class DeclF extends SimboloBase {
    private EnumType eType;
    private String id;
    private Args_Declf args_declf;
    public DeclF (EnumType eType, String id, Args_Declf args_declf, int linea, int columna){
        super(linea,columna);
        this.eType = eType;
        this.id = id;
        this.args_declf = args_declf;
    }
}
