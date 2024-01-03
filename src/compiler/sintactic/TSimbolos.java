package compiler.sintactic;

import java.util.ArrayList;

public class TSimbolos {
    private ArrayList<Symbol> te;
    private ArrayList<Symbol> td;

    private ArrayList<Integer[]> ta;
    private int nActual;

    public TSimbolos() {
        this.te = new ArrayList<Symbol>();
        this.td = new ArrayList<Symbol>();
        this.ta = new ArrayList<>();
        this.nActual = 0;
    }

    public boolean ponerSimbolo(Symbol symbol) {
        // Si el símbolo pertenece a una función, hay que revisar que esta no exista ya en la ts
        if (esFuncion(symbol)) {
            for (Symbol sym : td) {
                if (sym.equals(symbol)) {
                    return false;
                }
            }
        }
        return true;


    }

    private boolean esFuncion(Symbol sym) {

        return true;
    }

    public void entrarBloque() {
        nActual++;
        ta[nActual] = ta[nActual - 1]

    }

    public void salirBloque() {
        if (nActual == 0) {
            // DEVOLVER ERROR
        }

        int lini = ta[nActual];
        nActual--;
        int lfin = ta[nActual];
        for (int i = lini; i >= lfin; i--) {
            int id = te.get(i).getId();
            /*
            NO ESTA ADAPTADO AL TRABAJO

            Symbol symbol = te.get(i).getSymbol();
            symbol.np = td.get(id).getNp();
            symbol.d = td.get(id).getD();
            td.set(id, symbol);
            */
        }

    }

    public Symbol getFunction(String id) {

        // Mirar si hay funciones
        if (ta.isEmpty()) {
            return null;
        }

        // Iterar en todas las funciones
        for (Integer[] access : ta) {

            // Obtenemos el symbolo Type=FUNCTION
            if (td.get(access[1]).getName().equals(id)) {
                return td.get(access[1]);
            }
        }

        return null;
    }
}
