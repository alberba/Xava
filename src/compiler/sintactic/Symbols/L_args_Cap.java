package compiler.sintactic.Symbols;

import compiler.Intermedio.Intermedio;
public class L_args_Cap extends SimboloBase {

    private final EnumType enumType;
    private String id;
    private L_args_Cap l_args_cap;

    public L_args_Cap(EnumType enumType, String id, L_args_Cap l_args_cap, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
        this.l_args_cap = l_args_cap;
    }

    public L_args_Cap(EnumType enumType, String id, int linea, int columna) {
        super(linea,columna);
        this.enumType = enumType;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public L_args_Cap getL_args_cap() {
        return l_args_cap;
    }


    public void generarIntermedio(Intermedio intermedio) {
        // Se añade los parametros en la tabla de variables con (id + "_" + idFuncion) para que no se repitan
        intermedio.añadirVariable(id, enumType, null);
        if(l_args_cap != null){
            l_args_cap.generarIntermedio(intermedio);
        }
    }
}
