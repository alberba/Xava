package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.OperacionInst;

import java.util.ArrayList;

public class NodoExpDisponible {
    ArrayList <Expresion> G;
    ArrayList <Expresion> K;
    ArrayList <Expresion> In;
    ArrayList <Expresion> Out;

    public NodoExpDisponible() {
        this.G = new ArrayList<>();
        this.K = new ArrayList<>();
        this.In = new ArrayList<>();
        this.Out = new ArrayList<>();
    }

    public void AddG(Instruccion instruccion){
        Expresion expresion = new Expresion(instruccion);
        if(!G.contains(expresion)) {
            this.G.add(new Expresion(instruccion));
        }
    }
    public void QuitG(String id){
        boolean hayCambios = true;
        int i = 0;
        while(hayCambios){
            hayCambios = false;
            for(int j = i;j < G.size(); j++){
                Expresion expresion = G.get(j);
                if(expresion.getInstruccion().getOperador1().equals(id) || expresion.getInstruccion().getOperador2().equals(id)){
                    G.remove(expresion);
                    K.add(expresion);
                    hayCambios = true;
                    i = j;
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
        for(Expresion expresion : G){
            Instruccion instruccion1 = expresion.getInstruccion();
            if(!instruccion1.equals(instruccion)) {
                if (existeExp(instruccion, instruccion1)) {
                    return instruccion1;
                }
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
    public void InterseccionIn(ArrayList<Expresion> lista){
        this.In.retainAll(lista);
    }

    public ArrayList<Expresion> getIn() {
        return In;
    }

    public void setIn(ArrayList<Expresion> in) {
        In = in;
    }

    public ArrayList<Expresion> getOut() {
        return Out;
    }

    public void setOut(ArrayList<Expresion> out) {
        Out = out;
    }

    public ArrayList<Expresion> getG() {
        return G;
    }

    public void setG(ArrayList<Expresion> g) {
        G = g;
    }

    public ArrayList<Expresion> getK() {
        return K;
    }

    public void setK(ArrayList<Expresion> k) {
        K = k;
    }
}
