package compiler.Intermedio;

import compiler.sintactic.Symbols.*;
import compiler.sintactic.TSimbolos;

import java.util.ArrayList;
import java.util.Stack;

public class Intermedio {

    // Lista de instrucciones
    private ArrayList<Instruccion> codigo;

    private ArrayList<Variable> tv;

    private final ArrayList<Procedimiento> tp;
    private int nProdActual;
    private final Stack<String> pproc;

    private int counterTemps = 0;

    private boolean esParametro = false;

    private final TSimbolos ts;

    private int contadorEtiquetas = 0;

    public Intermedio(TSimbolos tsimbolos) {
        codigo = new ArrayList<>();
        tv = new ArrayList<>();
        tp = new ArrayList<>();
        pproc = new Stack<>();
        this.ts = tsimbolos;
    }

    public String nuevaEtiqueta(){
        contadorEtiquetas++;
        return "e" + contadorEtiquetas;
    }

    /**
     * Busca una variable en la tabla de variables y la devuelve. Si no existe, devuelve null. Utilizado por la clase Ensamblador
     * @param id Identificador de la variable (sintaxis: "id$idFuncion" o "id" si es global o temporal)
     * @return Variable
     */
    public Variable buscarVariable(String id) {
        for (Variable variable : tv) {
            if (variable.getId().equals(id)) {
                return variable;
            }
        }
        return null;
    }

    /**
     * Añade una variable a la tabla de variables y la devuelve. Antes de añadir la variable, es necesario comprobar
     * que no exista ya otra variable con el mismo nombre en el mismo ámbito o en el ámbito global.
     * @param id Identificador de la variable
     * @param tipo Tipo de la variable
     * @param longitud Longitud de la variable (null si no es un array)
     * @return La variable añadida, o encontrada
     */
    public Variable añadirVariable(String id, EnumType tipo, ArrayList<Integer> longitud) {
        Variable v;
        // Se mira si es una variable temporal
        if (id == null) {
            counterTemps++;
            // El nombre de las variables temporales será tn, siendo n el número de variable volátil
            if (tp.isEmpty()) {
                v = new Variable("t$" + counterTemps, tipo, longitud, true);
            } else {
                v = new Variable("t$" + counterTemps, tipo, longitud, true);
            }
        } else {
            // Si no lo es, primero se observa si se trata de la declaración de una variable global
            if (tp.isEmpty()) { // Si tp está empty, se está declarando en el ámbito 0, por lo que es global
                v = new Variable(id, tipo, longitud, false);
            } else { // En caso contrario, se busca en el procedimiento actual
                // Se obtiene el procedimiento actual de la tabla
                Procedimiento proc = tp.get(nProdActual);
                // Se guardan las cantidades para recorrer los arrays posteriormente
                int nParams = proc.getNumParametros();
                int nVars = nParams + proc.getNumDeclaraciones();

                // Se recorren las variables del proceso en busca de coincidencias con el identificador
                for (int i = 0; i < nVars; i++) {
                    if (i < nParams) {
                        // Coincidencia en los parámetros
                        if ((id + "$" + proc.getId()).equals(proc.getParametros().get(i).getId())) {
                            return proc.getParametros().get(i);
                        }
                    } else {
                        // Coincidencia en las declaraciones
                        // (a "i" se le resta el "n" de parámetros para recorrer las declaraciones desde 0)
                        if ((id + "$" + pproc.peek()).equals(proc.getDeclaraciones().get(i - nParams).getId())) {
                            return proc.getDeclaraciones().get(i - nParams);
                        }
                    }
                }

                // Llegados a este punto, se puede asumir que la variable no existe, entonces será creada
                String idFuncion = pproc.peek();
                v = new Variable(id + "$" + idFuncion, tipo, longitud, false);
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
        String etiquetaFuncion = "e_" + id;
        Procedimiento proc = new Procedimiento(id, tipo, etiquetaFuncion);
        tp.add(proc);
        return proc;
    }

    public void añadirInstruccion(Instruccion inst) {
        codigo.add(inst);
    }

    public void añadirArray(String id, L_Dim l_dim) {
        EnumType typeArr = ts.getSymbol(id).getTipoReturn();

        ArrayList<Integer> dimensiones = new ArrayList<>();
        for (L_Dim lDim = l_dim; lDim != null; lDim = lDim.getL_dim()) {
            dimensiones.add(lDim.getNum());
        }

        this.añadirVariable(id, typeArr, dimensiones);
    }

    public void consultarArray(ArrayG arrayG, boolean esIndexado) {
        // Obtenemos el tipo del array
        EnumType typeArr = ts.getSymbol(arrayG.getId()).getTipoReturn();
        int nbytes = 0;
        switch (typeArr) {
            case CARACTER, ENTERO -> nbytes = 2;
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
        Variable array = añadirVariable(arrayG.getId(), typeArr, null);

        // Obtener las dimensiones del array
        assert array != null;
        ArrayList<Integer> varArrayDecl = array.getLongitud();

        // Bucle que añade todas las instrucciones que calculan la dirección de memoria a la que se quiere acceder
        Variable tempAnt = varArray.get(0);
        Variable temp = null;
        for (int j = 0; j < varArray.size() - 1; j++) {
            temp = this.añadirVariable(null, EnumType.ENTERO, null);
            // En el ejemplo a [2] [1], se obtiene el valor de a [2] y se multiplica por 3, ya que la declaración de "a" es a[3] [3]
            this.añadirInstruccion(new Instruccion(OperacionInst.MULTIPLICACION, tempAnt.getId(), varArrayDecl.get(j+1).toString(), temp.getId()));
            if (j != 0) {
                this.añadirInstruccion(new Instruccion(OperacionInst.SUMA, temp.getId(), varArray.get(j+1).toString(), temp.getId()));
            }

            tempAnt = temp;
        }

        // temp sera null si el array es de una dimensión
        if (temp != null) {
            // Se obtiene el índice
            temp = this.añadirVariable(null, EnumType.ENTERO, null);
            this.añadirInstruccion(new Instruccion(OperacionInst.SUMA, tempAnt.getId(), varArray.get(varArray.size() - 1).getId(), temp.getId()));
        } else {
            temp = varArray.get(0);
        }


        // Se multiplica el índice por nbytes
        Variable temp2 = this.añadirVariable(null, EnumType.ENTERO, null);
        this.añadirInstruccion(new Instruccion(OperacionInst.MULTIPLICACION, temp.getId(), String.valueOf(nbytes), temp2.getId()));

        if (esIndexado) {
            Variable tempFinal = this.añadirVariable(null, EnumType.ENTERO, null);
            this.añadirInstruccion(new Instruccion(OperacionInst.INDEXADO, array.getId(), temp2.getId(), tempFinal.getId()));
        }
    }

    public Variable getUltimaVariable() {
        return tv.get(tv.size() - 1);
    }

    /**
     * Dado un identificador, devuelve el procedimiento correspondiente
     * @param id Identificador del procedimiento
     * @return Objeto Procedimiento
     */
    public Procedimiento getProcedimiento(String id) {
        for (Procedimiento proc : tp) {
            if (proc.getId().equals(id)) {
                return proc;
            }
        }
        return null;
    }

    /**
     * Actualiza el apuntador al procedimiento actual
     * @param id Identificador del procedimiento
     */
    public void setNprodActual(String id) {
        for (Procedimiento p: tp) {
            if(p.getId().equals(id)){
                nProdActual = tp.indexOf(p);
            }
        }
    }

    public void setEsParametro(boolean esParametro) {
        this.esParametro = esParametro;
    }

    public void addPproc(String idproc) {
        pproc.push(idproc);
    }

    public TSimbolos getTs() {
        return ts;
    }

    public void subPproc() {
        pproc.pop();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Instruccion instruccion : codigo) {
            sb.append(instruccion.toString()).append("\n");
        }
        return sb.toString();
    }

    public void actualizarAmbito(String idFuncion) {
        ts.updatenActual(idFuncion);
    }

    public ArrayList<Variable> getTv() {
        return tv;
    }

    public ArrayList<Instruccion> getCodigo() {
        return codigo;
    }

    public void setCodigo(ArrayList<Instruccion> codigo) {
        this.codigo = codigo;
    }

    public void setTv(ArrayList<Variable> tv) {
        this.tv = tv;
    }
}
