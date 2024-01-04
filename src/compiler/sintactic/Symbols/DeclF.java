package compiler.sintactic.Symbols;

public class DeclF extends SimboloBase {
    private TypeF type;
    private String id;
    private Args_Declf args_declf;
    public DeclF (TypeF type, String id, Args_Declf args_declf, int linea, int columna){
        super(linea,columna);
        this.type = type;
        this.id = id;
        this.args_declf = args_declf;
    }
}
