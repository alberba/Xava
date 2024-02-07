package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.OperacionInst;

import java.util.ArrayList;
import java.util.Objects;

public class TND {
    private final ArrayList <Bloque> TND;
    private final ArrayList <String> TE;
    private final Intermedio intermedio;
    public TND(Intermedio intermedio){
        TND = new ArrayList<>();
        TE = new ArrayList<>();
        this.intermedio = intermedio;
    }

    //Algoritmo que construye la TND
    public void ConstruirTND() {
        // Inicializamos con bloque entrada y salida
        TND.add(new Bloque(-1,-1, "E"));
        TE.add("");
        TND.add(new Bloque(-1,-1,"S"));
        TE.add("");

        // Obtenemos las instrucciones a analizar
        ArrayList<Instruccion> instrucciones = intermedio.getCodigo();
        // Contador de bloques
        int contador = 2;
        //Lista para revisar los saltos de los bloques
        ArrayList <Integer> Saltos = new ArrayList<>();

        // Inicializamos el primer bloque
        TND.add(new Bloque(0,-1,"B1"));
        // Comprobamos si tiene etiqueta o no
        if (instrucciones.get(0).getOperacion().equals(OperacionInst.ETIQUETA)) {
            // Cualquier bloque sin etiqueta se guardará como una etiqueta vacía ya que esto facilitará el acceso a la TE
            TE.add(instrucciones.get(0).getDestino());
        } else { TE.add(""); }

        // Bucle que recorre instruccion a instruccion
        for (int i = 1; i < instrucciones.size(); i++) {
            // Comprobamos si debería acabar el bloque
            if (StopLider(instrucciones.get(i))) {
                // Si el final es una etiqueta
                if (instrucciones.get(i).getOperacion().equals(OperacionInst.ETIQUETA)) {
                    //Guardamos como que la línea anterior fue el final
                    Bloque bloque = TND.get(contador);
                    bloque.setLineaFi(i - 1);
                    //Añadimos su sucesor
                    bloque.AddSucc("B" + contador);
                    //Creamos su sucesor
                    TND.add(new Bloque(i,0,"B"+contador));
                    TND.get(TND.size() - 1).AddPred("B" + (contador - 1));
                    //Guardamos su etiqueta en la TE
                    TE.add(instrucciones.get(i).getDestino());
                    contador++;
                } else {
                    //Si no tiene en su final una etiqueta
                    Bloque bloque = TND.get(contador);
                    //Significa que acabó en esta linea
                    bloque.setLineaFi(i);
                    //Creamos el nuevo bloque, con su inicio siendo la siguiente linea
                    TND.add(new Bloque(i + 1,0,"B"+contador));
                    //Comoprobamos si es un salto incondicional
                    if (!instrucciones.get(i).getOperacion().equals(OperacionInst.SALTO_INCON)) {
                        //Si no lo es, el siguiente bloque es su sucesor
                        bloque.AddSucc("B" + contador);
                        TND.get( TND.size() - 1).AddPred("B" + (contador - 1));
                    }
                    // Comprobamos si el siguiente bloque tiene etiqueta o no
                    if (instrucciones.get(i + 1).getOperacion().equals(OperacionInst.ETIQUETA)) {
                        TE.add(instrucciones.get(i + 1).getDestino());
                    }else {TE.add("");}
                    //Guardamos que el bloque acababa en salto
                    Saltos.add(contador);
                    i++;
                    contador++;
                }
            }
        }
        //últimos detalles necesarios tras el bucle
        TND.get(TND.size() - 1).setLineaFi(instrucciones.size() - 1);
        TND.get(0).AddSucc("B1");
        TND.get(1).AddPred("B"+(contador - 1));

        //Bucle para agregar los predecesores y sucesores de los saltos
        for(Integer i : Saltos){
            Bloque salto = TND.get(i);
            Bloque bloque = getBloqueEtiqueta(instrucciones.get(salto.getLineaFi()).getDestino());
            bloque.AddPred(salto.getId());
            salto.AddSucc(bloque.getId());
        }
    }
    public boolean StopLider(Instruccion instruccion){
        return switch(instruccion.getOperacion()){
            case IGUAL, DIFERENTE, MENOR, MENOR_IGUAL, MAYOR, MAYOR_IGUAL, Y, O, NO, ETIQUETA-> true;
            case null, default -> false;
        };
    }

    public Bloque getBloqueEtiqueta(String etiqueta) {
        for (int i = 0; i < TE.size(); i++) {
            if (etiqueta.equals(TE.get(i))) {
                return TND.get(i);
            }
        }
        return null;
    }

    // Obtener un bloque concreto
    public Bloque getBloque(String id) {
        if (Objects.equals(id, "E")) {
            return TND.get(0);
        } else if (Objects.equals(id, "S")) {
            return TND.get(1);
        } else {
            String numeroStr = id.substring(1); // Extraer todos los caracteres después del primer carácter
            return TND.get(Integer.parseInt(numeroStr) + 1);
        }
    }

    public int getPos(Bloque bloque) {
        if (bloque.getId().equals("E")) {
            return 0;
        } else if (bloque.getId().equals("S")) {
            return 1;
        }
        String numeroStr = bloque.getId().substring(1);
        return Integer.parseInt(numeroStr) + 1;
    }

    public ArrayList<Bloque> getTND() {
        return TND;
    }
}
