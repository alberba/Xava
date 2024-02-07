package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.OperacionInst;

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

    /**
     * Método que devuelve una expresión en caso de que esté almacenada en G
     * @return Instruccion o null
     * */
    public Instruccion getExp(Instruccion instruccion){
        for(Instruccion instruccion1 : G){
            if(existeExp(instruccion, instruccion1)){
                return instruccion;
            }
        }
        return null;
    }

    /**
     * Obtiene si la expresion de una es equivalente a la expresion de la otra
     * @param instruccion1 instruccion a comparar
     * @param instruccion2 instruccion a comparar
     * @return boolean
     */
    public boolean existeExp(Instruccion instruccion1, Instruccion instruccion2){
        if(instruccion1.getOperacion() == instruccion2.getOperacion()) {
            String idI1O1 = instruccion1.getOperador1();
            String idI2O1 = instruccion2.getOperador1();
            String idI1O2 = instruccion1.getOperador2();
            String idI2O2 = instruccion2.getOperador2();
            boolean Convertible = instruccion1.getOperacion() == OperacionInst.SUMA || instruccion1.getOperacion() == OperacionInst.MULTIPLICACION;
            boolean simetricos = idI1O2.equals(idI2O2) && idI1O1.equals(idI2O1);
            if(simetricos || idI1O1.equals(idI2O2) && idI1O2.equals(idI2O1)){
                if(Convertible){
                    return true;
                } else //noinspection RedundantIfStatement
                    if (simetricos) {
                        return true;
                    }
            }
        }
        return false;
    }


    /**
     * Hace que se guarde la instersección del conjunto In con el conjunto pasado por parametro
     * */
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
