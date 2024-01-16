package compiler.sintactic.Symbols;

import compiler.Intermedio.Variable;
import compiler.sintactic.Symbols.D_asig;
import compiler.sintactic.Symbols.Lid;
import compiler.sintactic.Symbols.Type;
import compiler.Intermedio.Intermedio;

import java.util.ArrayList;

public class Decl extends SimboloBase {

    private Type type;
    private Lid lid;
    private ArrayG arrayG;
    private D_asig d_asignacion;

    public Decl(Type type, Lid lid, D_asig d_asignacion, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.lid = lid;
        this.d_asignacion = d_asignacion;
    }

    public Decl(Type type, ArrayG arrayG, int linea, int columna) {
        super(linea,columna);
        this.type = type;
        this.arrayG = arrayG;
    }

    public void generarIntermedio(Intermedio intermedio) {
        type.generarIntermedio(intermedio);

        if(lid != null){
            ArrayList<Variable> varLids = new ArrayList<>();
            for (Lid lid = this.lid; lid != null; lid = lid.getLid()) {
                varLids.add(intermedio.añadirVariable(lid.getId(), type.getStype(), null));
            }
            if(d_asignacion != null){
                d_asignacion.generarIntermedio(intermedio, varLids);
            }
        }

        if(arrayG != null){
            arrayG.generarIntermedio(intermedio);
        }

    }

    public Lid getLid(){
        return this.lid;
    }


}
