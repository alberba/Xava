package compiler.sintactic;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class TSimbolos {
    private final ArrayList<ArrayList<Symbol>> tsimbolos;
    private int nActual;

    public TSimbolos() {
        this.tsimbolos = new ArrayList<>();
        // El nivel al inicio del programa será el ámbito global (0)
        this.nActual = 0;
        // Se inicializa el ámbito global con final -1 (sin contenido)
        this.tsimbolos.add(new ArrayList<>());
    }

    public void añadirAmbito() {
        nActual++;
        this.tsimbolos.add(new ArrayList<>());
    }

    public boolean ponerSymbol(Symbol symbol) {
        // Al tratarse de una función, comprobaremos si ha sido declarado previamente
        // Solo accederá en el ámbito global
        if (symbol.getTipoElemento() == TipoElemento.FUNCION) {
            for (Symbol s : tsimbolos.get(0)) {
                if (s.equals(symbol)) {
                    return false;
                }
            }
            // Se añade la función al ámbito global
            tsimbolos.get(0).add(symbol);
        } else {
            // Se comprueba que el símbolo no esté ya en el ámbito actual
            for (Symbol s : tsimbolos.get(nActual)) {
                if (s.equals(symbol)) {
                    return false;
                }
            }

            // Se comprueba que el símbolo no esté en el ámbito global
            for (Symbol s : tsimbolos.get(0)) {
                if (s.equals(symbol)) {
                    return false;
                }
            }
            this.tsimbolos.get(nActual).add(symbol);

        }


        return true;
    }


    // Busca el símbolo a partir de su id en el ámbito indicado
    public Symbol busquedaSymbolAmbito(int ambito, String id) {

        if (ambito != 0) { // Se mira si el nivel es el global
            // Se verifica que el nivel tenga símbolos
            if (this.tsimbolos.isEmpty()) {
                return null;
            }

            // Se recorre el nivel en búsqueda del símbolo
            for (Symbol simbolo : this.tsimbolos.get(ambito)) {
                if (simbolo.getName().equals(id)) {
                    return simbolo;
                }
            }
        }

        for (Symbol simbolo: this.tsimbolos.get(0)) {
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
        Symbol simbolo = this.busquedaSymbolAmbito(this.nActual, id);
        // Comprobamos si hemos encontrado el símbolo
        if (simbolo != null) {
            // Si existe el símbolo se devuelve
            return simbolo;
        }

        // Si no está en el nivel actual, se busca a nivel global y se envía. En caso de no encontrarse a nivel global
        // tampoco, se enviaría un valor nulo.
        return this.busquedaSymbolAmbito(0, id);
    }

    public Symbol getFuncion(String id) {

        // Iterar en todos los elementos del ambito global
        for (Symbol symbol : tsimbolos.get(0)) {
            // Comprobamos si es una función y si es la función que buscamos
            if (symbol.getTipoElemento() == TipoElemento.FUNCION && symbol.getName().equals(id)) {
                return symbol;
            }
        }

        // No se ha encontrado la función
        return null;
    }

    public ArrayList<Symbol> getParametros(String idFunc) {

        ArrayList<Symbol> globales = tsimbolos.get(0);
        int ambitoFuncion = getAmbitoFuncion(idFunc);

        // Si no se ha encontrado la función, se devuelve null
        if (ambitoFuncion < 0) {
            return null;
        }
        ArrayList<Symbol> funcion = tsimbolos.get(ambitoFuncion);
        ArrayList<Symbol> parametros = new ArrayList<>();
        // Los parámetros estarán situados al inicio del ámbito de la función
        for (int i = 0; i < funcion.size() && (funcion.get(i).getTipoElemento() == TipoElemento.PARAMETRO); i++) {

            parametros.add(globales.get(i));
        }

        return parametros.isEmpty() ? null : parametros;
    }

    public EnumType getTypeFuncionActual() {
        return tsimbolos.get(nActual).get(0).getTipoReturn();
    }

    /**
     * Devuelve el ámbito de la función indicada
     * @param idFunc id de la función
     * @return n del ambito de la función
     */
    private int getAmbitoFuncion(String idFunc) {
        if (Objects.equals(idFunc, "main")) {
            return tsimbolos.size() - 1;
        }
        // Se empieza desde 1 porque ya no puede ser el ámbito global
        int ambito = 1;
        // Se recorren las declaraciones globales en busca de la función
        for (int i = 0; i <= tsimbolos.get(0).size(); i++) {
            // Si no es la declaración de una función, se ignora
            if (tsimbolos.get(0).get(i).getTipoElemento() == TipoElemento.FUNCION) {
                // ES UNA FUNCIÓN
                // Si no es la que se busca, se ignora también
                if (!tsimbolos.get(0).get(i).getName().equals(idFunc)) {
                    // Se incrementa el ámbito por cada función recorrida hasta llegar a la que toca,
                    // ya que el ámbito de una función coincide con el orden en el que se declara
                    ambito++;
                } else {
                    // Se ha encontrado la función
                    return ambito;
                }
            }
        }
        return -1;
    }

    public void updatenActual(String idFuncion) {
        nActual = getAmbitoFuncion(idFuncion);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Tabla de símbolos:\n");
        for (int ambito = 0; ambito < tsimbolos.size(); ambito++) {
            sb.append("Ámbito ").append(ambito).append(":\n");
            for (int i = 0; i < tsimbolos.get(ambito).size(); i++) {
                sb.append(i).append(": ").append(tsimbolos.get(ambito).get(i).toString()).append("\n");
            }
        }
        return sb.toString();
    }

}
