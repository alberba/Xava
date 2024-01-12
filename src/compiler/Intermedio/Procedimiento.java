package compiler.Intermedio;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Procedimiento {
    // Nombre del procedimiento
    private String id;
    // Tipo que devuelve
    private EnumType tipo;
    // Parametros de la funcion
    private ArrayList<Variable> parametros;
    // Declaraciones que tiene dentro
    private ArrayList<Variable> declaraciones;

    public Procedimiento(String id, EnumType tipo, ArrayList<Variable> parametros, ArrayList<Variable> declaraciones) {
        this.id = id;
        this.tipo = tipo;
        this.parametros = parametros;
        this.declaraciones = declaraciones;
    }

    public Procedimiento(String id, EnumType tipo) {
        this.id = id;
        this.tipo = tipo;
        this.parametros = new ArrayList<>();
        this.declaraciones = new ArrayList<>();
    }

    // Añade un parametro
    public void addParametro(Variable parameter) {
        this.parametros.add(parameter);
    }

    // Añade una declaracion
    public void addDeclaracion(Variable declaracion) {
        this.declaraciones.add(declaracion);
    }

    // Obtiene el nombre de la función
    public String getId() {
        return id;
    }

    // Obtiene el tipo de la función
    public EnumType getTipo() {
        return tipo;
    }

    // Obtiene los parametros de la función
    public ArrayList<Variable> getParametros() {
        return parametros;
    }

    // Getter de la cantidad de parámetros en el arraylist
    public int getNumParametros() {
        return parametros.size();
    }

    // Obtiene las declaraciones de la función
    public ArrayList<Variable> getDeclaraciones() {
        return declaraciones;
    }

    // Getter de la cantidad de declaraciones en el arraylist
    public int getNumDeclaraciones() {
        return declaraciones.size();
    }



}
