package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

import java.util.ArrayList;

public class AnExpDisponible {
    TND tnd;
    ArrayList<NodoExpDisponible> lista;
    Intermedio intermedio;
    public AnExpDisponible(Intermedio intermedio){
        this.intermedio = intermedio;
        this.lista = new ArrayList<>();
        tnd = new TND(intermedio);
        tnd.ConstruirTND();
    }

    public void Fase1(){
        ArrayList<Bloque> bloques = tnd.getTND();
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        //Añado dos para E y S, aunque no se usen para evitar errores futuros
        lista.add(new NodoExpDisponible());
        lista.add(new NodoExpDisponible());
        for(int i = 2; i < bloques.size(); i++) {
            NodoExpDisponible nodo = new NodoExpDisponible();
            lista.add(nodo);
            // i pertenece a TND(b).ii TND(b).if
            for(int j = bloques.get(i).getLineaI(); j <= bloques.get(i).getLineaFi(); j++){
                Instruccion s = instrucciones.get(j);
                if (esAsig(s)) {
                    nodo.AddG(instrucciones.get(j));
                    nodo.QuitG(instrucciones.get(j).getDestino());
                }
            }
        }
    }

    public void Fase2(){
        ArrayList<Bloque> bloques = tnd.getTND();
        ArrayList<Bloque> PND = (ArrayList<Bloque>) bloques.clone();
        while (!PND.isEmpty()) {
            Bloque b = PND.get(0);
            PND.remove(b);
            NodoExpDisponible nodo1 = lista.get(tnd.getPos(b));
            for(String nombre : tnd.getBloque(b.getId()).getPred()) {
                Bloque p = tnd.getBloque(nombre);
                NodoExpDisponible nodo2 = lista.get(tnd.getPos(p));
                nodo1.InterseccionIn(nodo2.getOut());
            }
            ArrayList<Instruccion> Itemp = new ArrayList<>(nodo1.getIn());
            Itemp.removeAll(nodo1.getK());
            ArrayList<Instruccion> Gtemp = new ArrayList<>(nodo1.getG());
            Gtemp.addAll(Itemp);
            nodo1.setOut(Gtemp);
            // s pertenece a TND(b).succ
            for (String nombre : tnd.getBloque(b.getId()).getSucc()) {
                PND.add(tnd.getBloque(nombre));
            }
        }
    }

    /**
     * Método que se encarga de comprobar si una instrucción es una asignación para expresiones disponibles
     * @param instruccion Instrucción a comprobar
     * @return true si es una asignación, false en caso contrario
     */
    private boolean esAsig(Instruccion instruccion) {
        return switch (instruccion.getOperacion()) {
            case SUMA, RESTA, MODULO, MULTIPLICACION, DIVISION -> true;
            default -> false;
        };
    }

}
