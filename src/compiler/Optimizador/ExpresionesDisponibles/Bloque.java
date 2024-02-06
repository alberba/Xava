package compiler.Optimizador.ExpresionesDisponibles;

import java.util.ArrayList;
import java.util.Objects;

public class Bloque {
    private int lineaI, lineaFi = 0;
    private String id;
    private ArrayList<String> succ;
    private ArrayList<String> pred;
    public Bloque (int lineaI, int lineaFi, String id) {
        this.id = id;
        this.lineaFi = lineaFi;
        this.lineaI = lineaI;
        this.succ = new ArrayList<>();
        this.pred = new ArrayList<>();
    }
    public ArrayList<String> getSucc(){
        return this.succ;
    }
    public void AddSucc(String string){
        this.succ.add(string);
    }

    public ArrayList<String> getPred(){
        return this.pred;
    }

    public void AddPred(String string){
        this.pred.add(string);
    }
    public void setLineaFi(int i){
        this.lineaFi = i;
    }
    public int getLineaFi() {
        return lineaFi;
    }

    public String getId() {
        return id;
    }

    public int getLineaI() {
        return lineaI;
    }
    @Override
    public boolean equals(Object o) {
        Bloque bloque = (Bloque) o;
        return lineaI == bloque.lineaI &&
                lineaFi == bloque.lineaFi &&
                Objects.equals(id, bloque.id) &&
                Objects.equals(succ, bloque.succ) &&
                Objects.equals(pred, bloque.pred);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineaI, lineaFi, id, succ, pred);
    }
}
