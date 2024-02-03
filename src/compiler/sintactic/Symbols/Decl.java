package compiler.sintactic.Symbols;

import compiler.Intermedio.Variable;
import compiler.Intermedio.Intermedio;

import java.util.ArrayList;

public class Decl extends SimboloBase {

    private final Type type;
    private Lid lid;

    private String id;
    private L_Dim l_dim;
    private D_asig d_asignacion;

    public Decl(Type type, Lid lid, D_asig d_asignacion, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.lid = lid;
        this.d_asignacion = d_asignacion;
    }

    public Decl(Type type, String id, L_Dim l_dim, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.id = id;
        this.l_dim = l_dim;
    }

    public void generarIntermedio(Intermedio intermedio) {

        if (lid != null) {
            ArrayList<Variable> varLids = new ArrayList<>();
            for (Lid lid = this.lid; lid != null; lid = lid.getLid()) {
                varLids.add(intermedio.añadirVariable(lid.getId(), type.getStype(), null));
            }
            if (d_asignacion != null) {
                d_asignacion.generarIntermedio(intermedio, varLids);
            }
        }

        if (id != null) {
            intermedio.añadirArray(id, l_dim);
        }

    }

    public Lid getLid() {
        return this.lid;
    }


}
