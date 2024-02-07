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
    }

    public void Fase1(){
        tnd.ConstruirTND();
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
        //Quitamos E y S
        PND.remove(0);
        PND.remove(1);
        //Lista de analizados
        ArrayList<Bloque> analizados = new ArrayList<>();
        //Boolean que mira si ha habido cambios en la última ronda
        boolean hayCambios = true;
        while (!PND.isEmpty()) {
            if (analizados.size() == (bloques.size() - 2) && !hayCambios) {
                break;
            }
            Bloque b = PND.get(0);
            PND.remove(b);
            hayCambios = false;
            NodoExpDisponible nodo1 = lista.get(tnd.getPos(b));
            for(String nombre : tnd.getBloque(b.getId()).getPred()) {
                Bloque p = tnd.getBloque(nombre);
                NodoExpDisponible nodo2 = lista.get(tnd.getPos(p));
                int aux = nodo1.getIn().size();
                nodo1.InterseccionIn(nodo2.getOut());
                if (aux != nodo1.getIn().size()) {
                    //Ponemos cambios a true
                    hayCambios = true;
                }
            }
            ArrayList<Expresion> Itemp = new ArrayList<>(nodo1.getIn());
            Itemp.removeAll(nodo1.getK());
            ArrayList<Expresion> Gtemp = new ArrayList<>(nodo1.getG());
            Gtemp.addAll(Itemp);
            int aux = nodo1.getOut().size();
            nodo1.setOut(Gtemp);
            if (aux != nodo1.getOut().size()) {
                //Ponemos cambios a true
                hayCambios = true;
            }
            // s pertenece a TND(b).succ
            for (String nombre : tnd.getBloque(b.getId()).getSucc()) {
                Bloque bloque = tnd.getBloque(nombre);
                if (!PND.contains(bloque) && !bloque.getId().equals("S")) {
                    PND.add(tnd.getBloque(nombre));
                }
            }
        }
    }

    /**
     * Método para reemplezar las asignaciones de las que sabemos hay una expresión disponible
     * */
    public void UsoExpDisponibles(){
        // Obtenemos las instrucciones antes de empezar
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        // Bucle que recorre los bloques
        for(int i = 2; i < lista.size(); i++){
            // Solo nos interesan los bloque que no tengan G vacía
            if(!lista.get(i).getG().isEmpty()){
                // Obtenemos el objeto bloque
                Bloque bloque = tnd.getBloque("B"+(i-1));
                // Bucle para iterar en las instrucciones del bucle
                for(int j = bloque.getLineaI(); j < bloque.getLineaFi(); j++){
                    // Obtenemos la instruccion actual
                    Instruccion instruccion1 = instrucciones.get(j);
                    //Comprobamos si es alguna de las que pueden cambiarse
                    if(esAsig(instruccion1)) {
                        //Comprobamos si existe alguna variable con la misma expresión
                        Instruccion instruccion = lista.get(i).getExp(instruccion1);
                        if(instruccion != null){
                            //Si existe cambiamos la instrucción por una asignación a esa variable
                            instruccion1.setOperacion(OperacionInst.ASIG);
                            instruccion1.setOperando2(null);
                            instruccion1.setOperando1(instruccion.getDestino());
                        }
                    }
                }
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
