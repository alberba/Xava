package compiler.Intermedio;

import compiler.sintactic.Symbols.*;
import compiler.sintactic.TSimbolos;

import java.util.ArrayList;

public class Intermedio {

    // Lista de instrucciones
    private ArrayList<Instruccion> codigo;

    private ArrayList<Variable> tv;

    private ArrayList<Procedimiento> tp;

    private int counterTemps = 0;

    private int counterGlobales = 0;

    private boolean esParametro = false;

    private final TSimbolos ts;

    private int contadorEtiquetas = 0;

    public Intermedio(TSimbolos tsimbolos) {
        codigo = new ArrayList<>();
        tv = new ArrayList<>();
        tp = new ArrayList<>();
        this.ts = tsimbolos;
    }

    public String nuevaEtiqueta(){
        contadorEtiquetas++;
        return "e" + contadorEtiquetas;
    }

    public Variable añadirVariable(String id, EnumType tipo, ArrayList<Variable> longitud) {
        Variable v = null;
        // Se mira si es una variable temporal
        if (id == null) {
            counterTemps++;
            // El nombre de las variables temporales será tn, siendo n el número de variable volátil
            v = new Variable("t" + counterTemps, tipo, true, longitud); // esTemp a true
        } else {
            // Si no lo es, primero se observa si se trata de la declaración de una variable global
            if (tp.isEmpty()) {
                v = new Variable(id + "_" + tp.size(), tipo, false, longitud);
                counterGlobales++;
            } else { // En caso contrario, se busca en el procedimiento actual
                // Se obtiene el último procedimiento de la tabla (el actual)
                Procedimiento proc = tp.get(tp.size() - 1);
                // Se guardan las cantidades para recorrer los arrays posteriormente
                int nProcs = proc.getNumParametros();
                int nVars = nProcs + proc.getNumDeclaraciones();

                // Se recorren las variables del proceso en busca de coincidencias con el identificador
                for (int i = 0; i < nVars; i++) {
                    if (i < nProcs) {
                        // Coincidencia en los parámetros
                        if (id.equals(proc.getParametros().get(i).getId())) {
                            return proc.getParametros().get(i);
                        }
                    } else {
                        // Coincidencia en las declaraciones
                        // (a "i" se le resta el "n" de parámetros para recorrer las declaraciones desde 0)
                        if (id.equals(proc.getDeclaraciones().get(i - nProcs).getId())) {
                            return proc.getDeclaraciones().get(i - nProcs);
                        }
                    }
                }

                // Llegados a este punto, se puede asumir que la variable no existe, entonces será creada
                v = new Variable(id + "_" + tp.size(), tipo, false, longitud);
                // Se añade la variable a la lista correspondiente
                if (esParametro) {
                    proc.addParametro(v);
                } else {
                    proc.addDeclaracion(v);
                }
            }
        }
        // Se añade a la tabla de variables
        tv.add(v);
        return v;
    }

    public Procedimiento añadirProcedimiento(String id, EnumType tipo) {
        Procedimiento proc = new Procedimiento(id, tipo);
        tp.add(proc);
        return proc;
    }

    public void añadirInstruccion(Instruccion inst) {
        codigo.add(inst);
    }

    public void añadirArray(ArrayG arrayG) {
        EnumType typeArr = ts.getSymbol(arrayG.getId()).getTipoReturn();

        ArrayList<Variable> variables = new ArrayList<>();
        for (L_array lArray = arrayG.getlArray(); lArray != null; lArray = lArray.getlArray()) {
            lArray.getExp().generarIntermedio(this);
            variables.add(this.getUltimaVariable());
        }
        this.añadirVariable(arrayG.getId(), typeArr, variables);
    }

    public void consultarArray(ArrayG arrayG){
        // Obtenemos el tipo del array
        EnumType typeArr = ts.getSymbol(arrayG.getId()).getTipoReturn();
        int nbytes = 0;
        switch (typeArr) {
            case CARACTER -> nbytes = 2;
            case ENTERO -> nbytes = 4;
            case BOOLEANO -> nbytes = 1;
            default -> {
            }
        }
        // Obtener las posiciones a las que se quiere acceder
        ArrayList<Variable> varArray = new ArrayList<>();
        for (L_array lArray = arrayG.getlArray(); lArray != null; lArray = lArray.getlArray()) {
            lArray.getExp().generarIntermedio(this);
            varArray.add(this.getUltimaVariable());
        }

        // Obtener la posición del array
        boolean encontrado = false;
        int i = 0;
        for(; i < tv.size() && !encontrado; i++){
            if(tv.get(i).getId().equals(arrayG.getId())){
                encontrado = true;
            }
        }

        // Obtener el array
        Variable array = tv.get(i);
        // Obtener las dimensiones del array
        ArrayList<Variable> varArrayDecl = array.getLongitud();

        // Bucle que añade todas las instrucciones que calculan la dirección de memoria a la que se quiere acceder
        Variable tempAnt = varArray.get(0);
        for (int j = 0; j < varArray.size() - 1; j++) {
            Variable temp = this.añadirVariable(null, EnumType.ENTERO, null);
            // En el ejemplo a [2] [1], se obtiene el valor de a [2] y se multiplica por 3, ya que la declaración de "a" es a[3] [3]
            this.añadirInstruccion(new Instruccion(OperacionInst.MULTIPLICACION, tempAnt.getId(), varArrayDecl.get(j+1).getId(), temp.getId()));
            if (j != 0) {
                this.añadirInstruccion(new Instruccion(OperacionInst.SUMA, temp.getId(), varArray.get(j+1).getId(), temp.getId()));
            }

            tempAnt = temp;
        }

        Variable temp = null;
        // tempAnt sera null si el array es de una dimensión
        if (tempAnt != null) {
            // Se obtiene el índice
            temp = this.añadirVariable(null, EnumType.ENTERO, null);
            this.añadirInstruccion(new Instruccion(OperacionInst.SUMA, tempAnt.getId(), varArray.get(varArray.size() - 1).getId(), temp.getId()));
        } else {
            temp = varArray.get(0);
        }


        // Se multiplica el índice por nbytes
        Variable temp2 = this.añadirVariable(null, EnumType.ENTERO, null);
        this.añadirInstruccion(new Instruccion(OperacionInst.MULTIPLICACION, temp.getId(), String.valueOf(nbytes), temp2.getId()));

        Variable tempFinal = this.añadirVariable(null, EnumType.ENTERO, null);
        this.añadirInstruccion(new Instruccion(OperacionInst.INDEXADO, array.getId(), temp2.getId(), tempFinal.getId()));
    }

    public Variable getUltimaVariable() {
        return tv.get(tv.size() - 1);
    }

    public Procedimiento getProcedimiento(String id) {
        for (Procedimiento proc : tp) {
            if (proc.getId().equals(id)) {
                return proc;
            }
        }
        return null;
    }

    public void setEsParametro(boolean esParametro) {
        this.esParametro = esParametro;
    }

    public TSimbolos getTs() {
        return ts;
    }
}
