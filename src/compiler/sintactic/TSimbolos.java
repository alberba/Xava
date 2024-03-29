package compiler.sintactic;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;
import java.util.Objects;

public class TSimbolos {
    private final ArrayList<ArrayList<Symbol>> tsimbolos;
    private int nActual;
    private int indiceDeclFunciones;

    public TSimbolos() {
        this.tsimbolos = new ArrayList<>();
        // El nivel al inicio del programa será el ámbito global (0)
        this.nActual = 0;
        // Se inicializa el ámbito global con final -1 (sin contenido)
        this.tsimbolos.add(new ArrayList<>());
        //
        this.indiceDeclFunciones = -1;
    }

    public void añadirAmbito() {
        nActual++;
        this.tsimbolos.add(new ArrayList<>());
    }

    /**
     * Método encargado de meter un símbolo en la tabla de símbolos
     * @param symbol Símbolo a meter
     * @return true si se ha podido meter, false si ya hay un símbolo con el mismo nombre
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean ponerSymbol(Symbol symbol) {
        // Al tratarse de una función, comprobaremos si ha sido declarado previamente
        // Solo accederá en el ámbito global
        if (symbol.getTipoElemento() == TipoElemento.FUNCION) {

            if (tsimbolos.get(0).isEmpty()) {
                indiceDeclFunciones = 0;
            } else {
                for (Symbol s : tsimbolos.get(0)) {
                    if (s.equals(symbol)) {
                        return false;
                    }
                }
                // Al encontrar la primera función, se marca que hay funciones y se guarda el índice
                if (indiceDeclFunciones == -1) {
                    indiceDeclFunciones = tsimbolos.get(0).size();
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

    /**
     * Busca el símbolo a partir de su id en el ámbito indicado
     * @param id id del símbolo
     * @return Símbolo si se encuentra, null si no
     */
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

    /**
     * Utilizado en ensamblador, busca el símbolo en toda la ts
     * @param id id del símbolo
     * @return Símbolo si se encuentra, null si no
     */
    public Symbol busquedaSymbolEnsamblador(String id) {
        // Se verifica que haya símbolos
        if (this.tsimbolos.isEmpty()) {
            return null;
        }

        // Se recorren los ámbitos
        for (ArrayList<Symbol> ambito : this.tsimbolos) {
            // Se recorre el ámbito en busca del símbolo
            for (Symbol simbolo : ambito) {
                if (simbolo.getName().equals(id)) {
                    return simbolo;
                }
            }
        }

        // Si no se ha encontrado, se devuelve null
        return null;
    }


    /**
     * Busca el símbolo correspondiente al ID, primero a nivel local y si no se encuentra,
     * se pasa a buscar a nivel global
     * @param id ID del símbolo
     * @return Símbolo si se encuentra, null si no
     */
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

    /**
     * Método utilizado para buscar el simbolo de una función a partir de su id
     * @param id id de la función
     * @return Símbolo de la función si se encuentra, null si no
     */
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

    /**
     * Método utilizado para obtener los parámetros de una función
     * @param idFunc id de la función
     * @return Lista de parámetros de la función
     */
    public ArrayList<Symbol> getParametros(String idFunc) {
        int ambitoFuncion = getAmbitoFuncion(idFunc);

        // Si no se ha encontrado la función, se devuelve null
        if (ambitoFuncion < 0) {
            return null;
        }
        ArrayList<Symbol> funcion = tsimbolos.get(ambitoFuncion);
        ArrayList<Symbol> parametros = new ArrayList<>();
        // Los parámetros estarán situados al inicio del ámbito de la función
        for (int i = 0; i < funcion.size() && (funcion.get(i).getTipoElemento() == TipoElemento.PARAMETRO); i++) {
            parametros.add(funcion.get(i));
        }

        return parametros.isEmpty() ? null : parametros;
    }

    /**
     * Método utilizado para obtener el número de parámetros de una función
     * @param idFunc id de la función
     * @return Número de parámetros de la función
     */
    public int getNumParametros(String idFunc) {
        ArrayList<Symbol> parametros = getParametros(idFunc);
        return parametros == null ? 0 : parametros.size();
    }

    /**
     * Método utilizado para obtener el tipo de retorno de la función actual
     * @return Tipo de retorno de la función
     */
    public EnumType getTypeFuncionActual() {
        // Se utiliza el ámbito - 1 como offset para obtener la declaración de la función
        return tsimbolos.get(0).get(indiceDeclFunciones + (nActual - 1)).getTipoReturn();
    }

    /**
     * Devuelve el ámbito de la función indicada
     * @param idFunc id de la función
     * @return n del ambito de la función
     */
    private int getAmbitoFuncion(String idFunc) {

        // Si es la función principal, se devuelve el último ámbito
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
