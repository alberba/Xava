package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.OperacionInst;

import java.util.ArrayList;

public class AnExpDisponible {
    ArrayList<Instruccion> expDisponibles ;
    public AnExpDisponible(){
        expDisponibles = new ArrayList<>();
    }
    //Busca si se ha actualizado alguna variable guardada, si es asi, esa variable se elimina
    public void removeExpDisponible(String id){
        for(Instruccion instruccion : expDisponibles){
            if((instruccion.getOperador1() != null && instruccion.getOperador1().equals(id)) || (instruccion.getOperador2() != null && instruccion.getOperador2().equals(id))){
                expDisponibles.remove(instruccion);
                break;
            }
        }
    }
    //Devuelve el id de una variable si contiene la expresión deseada
    public String getExpDisponible(Instruccion instruccion){
        for(Instruccion instruccion2 : expDisponibles){
            if(existeExp(instruccion,instruccion2)){
                return instruccion2.getDestino();
            }
        }
        expDisponibles.add(instruccion);
        return "$";
    }
    //Obtiene si la expresion de una es equivalente a la expresion de la otra
    public boolean existeExp(Instruccion instruccion1, Instruccion instruccion2){
        if(instruccion1.getOperacion() == instruccion2.getOperacion()) {
            String idI1O1 = instruccion1.getOperador1() != null ? instruccion1.getOperador1() : "";
            String idI2O1 = instruccion2.getOperador1() != null ? instruccion2.getOperador1() : "";
            String idI1O2 = instruccion1.getOperador2() != null ? instruccion1.getOperador2() : "";
            String idI2O2 = instruccion2.getOperador2() != null ? instruccion2.getOperador2() : "";
            boolean Convertible = instruccion1.getOperacion() == OperacionInst.SUMA || instruccion1.getOperacion() == OperacionInst.MULTIPLICACION;
            if(idI1O2.equals(idI2O2) || idI1O1.equals(idI2O1) || idI1O1.equals(idI2O2) || idI1O2.equals(idI2O1)){
                if(Convertible){
                    return true;
                } else if (idI1O2.equals(idI2O2) && idI1O1.equals(idI2O1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void Clear(){
        this.expDisponibles = new ArrayList<>();
    }
}
