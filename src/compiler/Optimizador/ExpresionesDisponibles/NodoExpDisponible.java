package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;

import java.util.ArrayList;

public class NodoExpDisponible {
    ArrayList <Instruccion> G;
    ArrayList <Instruccion> K;
    ArrayList <Instruccion> In;
    ArrayList <Instruccion> Out;

    public NodoExpDisponible() {
        this.G = new ArrayList<>();
        this.K = new ArrayList<>();
        this.In = new ArrayList<>();
        this.Out = new ArrayList<>();
    }

    public void AddG(Instruccion instruccion){
        this.G.add(instruccion);
    }
    public void QuitG(String id){
        boolean hayCambios = true;
        int i = 0;
        while(hayCambios){
            hayCambios = false;
            for(int j = i;j < G.size(); j++){
                Instruccion instruccion = G.get(j);
                if(instruccion.getOperador1().equals(id) || instruccion.getOperador2().equals(id)){
                    G.remove(instruccion);
                    K.add(instruccion);
                    hayCambios = true;
                    i = j - 1;
                    break;
                }
            }
        }
    }

    public void InterseccionIn(ArrayList<Instruccion> lista){
        this.In.retainAll(lista);
    }

    public ArrayList<Instruccion> getIn() {
        return In;
    }

    public void setIn(ArrayList<Instruccion> in) {
        In = in;
    }

    public ArrayList<Instruccion> getOut() {
        return Out;
    }

    public void setOut(ArrayList<Instruccion> out) {
        Out = out;
    }

    public ArrayList<Instruccion> getG() {
        return G;
    }

    public void setG(ArrayList<Instruccion> g) {
        G = g;
    }

    public ArrayList<Instruccion> getK() {
        return K;
    }

    public void setK(ArrayList<Instruccion> k) {
        K = k;
    }
}
