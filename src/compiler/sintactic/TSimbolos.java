package compiler.sintactic;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TSimbolos {
    private ArrayList<Symbol> td;
    private ArrayList<Integer> ta;
    private int nActual;
    private final int NOT_FOUND = -1;

    public TSimbolos() {
        this.td = new ArrayList<Symbol>();
        this.ta = new ArrayList<Integer>();
        // El nivel al inicio del programa será el ámbito global (0)
        this.nActual = 0;
        // Se inicializa el ámbito global con final -1 (sin contenido)
        ta.add(NOT_FOUND);
    }

    public void añadirAmbito() {
        ta.add(td.size() - 1);
        nActual++;
    }

    public boolean ponerSimbolo(Symbol symbol) {

        // Al tratarse de una función, comprobaremos si ha sido declarado previamente
        // Solo accederá en el ámbito global
        if (symbol.getTipoElemento() == TipoElemento.FUNCION){
            for (Symbol s : td) {
                if (s.equals(symbol)) {
                    return false;
                }
            }
        } else {

            // Se comprueba que el símbolo no esté ya en el ámbito actual
            for (int i = ta.get(nActual - 1) + 1; i <= ta.get(nActual); i++) {
                if (td.get(i).equals(symbol)) {
                    return false;
                }
            }

            // Se comprueba que el símbolo no esté en el ámbito global
            for (int i = 0; i <= ta.get(0); i++) {
                if (td.get(i).equals(symbol)) {
                    return false;
                }
            }
        }

        // Se añade el símbolo a la tabla de símbolos y se actualiza la tabla de ámbitos
        this.ta.set(nActual, ta.get(nActual) + 1);
        this.td.add(symbol);

        return true;
    }

    public Symbol busquedaSymbolTA(int ambito, String id) {
        // Primero se busca en el nivel actual y después se busca entre los globales,
        // siguiendo el principio de localidad
        int inicioNivel = 0;
        int finNivel = this.ta.get(0);
        Symbol simbolo = null;

        if (ambito != 0) { // Se mira si el nivel es el global
            // La posición inicio de un nivel es el siguiente a la posición de final del nivel anterior
            inicioNivel = this.ta.get(ambito - 1) + 1;
            finNivel = this.ta.get(ambito);
            // Se verifica que el nivel tenga símbolos
            if (inicioNivel == -1) {
                return null;
            }

            // Se recorre el nivel en búsqueda del símbolo
            for (int i = inicioNivel; i <= finNivel; i++) {
                simbolo = this.td.get(i);
                if (simbolo.getName().equals(id)) {
                    return simbolo;
                }
            }
            // Si no se encuentra
            finNivel = this.ta.get(0);
        }

        // El ámbito no tiene símbolos
        if (finNivel == NOT_FOUND) {
            return null;
        }

        // Recorrido del ámbito global
        for (int i = 0; i <= finNivel; i++) {
            simbolo = this.td.get(i);
            if (simbolo.getName().equals(id)) {
                return simbolo;
            }
        }
        // Si no se ha encontrado, se devuelve null
        return null;
    }

    // Busca el símbolo correspondiente al ID, primero a nivel local y si no se encuentra, se pasa a buscar a nivel
    // global
    public Symbol getSymbol(String id) {
        // Buscamos el símbolo en el nivel actual y lo almacenamos en una variable temporal
        Symbol simbolo = this.busquedaSymbolTA(this.nActual, id);

        // Comprobamos si hemos encontrado el símbolo
        if (simbolo != null) {
            // Si existe el símbolo se devuelve
            return simbolo;
        }

        // Si no está en el nivel actual, se busca a nivel global y se envía. En caso de no encontrarse a nivel global
        // tampoco, se enviaría un valor nulo.
        return this.busquedaSymbolTA(0, id);
    }

    public Symbol getFunction(String id) {

        // Iterar en todos los elementos del ambito global
        for (int i = 0; i < ta.get(0); i++) {
            // Obtenemos el elemento i de la td
            Symbol simbolo = td.get(i);
            // Comprobamos si es una función
            if (simbolo.getTipoElemento() == TipoElemento.FUNCION) {
                // Comprobamos si es la función correcta
                if(simbolo.getName().equals(id)){
                    // Devolvemos el símbolo correspondiente a la función
                    return simbolo;
                }
            }
        }
        // No se ha encontrado la función
        return null;
    }

    public ArrayList<Symbol> getParametros(String idFunc){

        int globales = ta.get(0);
        int i = 0;
        boolean encontrado = false;
        // Se recorren las declaraciones globales en busca de la función
        for (; i <= globales; i++) {

            // Si no es una función, se ignora
            if (td.get(i).getTipoElemento() != TipoElemento.FUNCION) {
                continue;
            }

            // Si no es la que se busca, se ignora también
            if (!td.get(i).getName().equals(idFunc)) {
                continue;
            }

            encontrado = true;
            break;
        }

        if (!encontrado) {
            return null;
        }

        // Los parámetros estarán situados antes de la función
        ArrayList<Symbol> simbolos = new ArrayList<>();
        i--;
        while (td.get(i).getTipoElemento() == TipoElemento.PARAMETRO) {
            simbolos.add(0, td.get(i));
            i--;
        }

        if (simbolos.isEmpty()) {
            return null;
        }

        return simbolos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tabla de símbolos:\n");
        sb.append("Tabla de ámbitos: ").append(this.ta.toString()).append("\n");
        sb.append("Tabla de símbolos: \n");
        for (int i = 0; i < this.td.size(); i++) {
            sb.append(i).append(": ").append(this.td.get(i).toString()).append("\n");
        }
        return sb.toString();
    }

}
