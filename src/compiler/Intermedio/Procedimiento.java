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
    private String etiqueta;

    public Procedimiento(String id, EnumType tipo, ArrayList<Variable> parametros, ArrayList<Variable> declaraciones, String etiqueta) {
        this.id = id;
        this.tipo = tipo;
        this.parametros = parametros;
        this.declaraciones = declaraciones;
        this.etiqueta = etiqueta;
    }

    public Procedimiento(String id, EnumType tipo, String etiqueta) {
        this.id = id;
        this.tipo = tipo;
        this.parametros = new ArrayList<>();
        this.declaraciones = new ArrayList<>();
        this.etiqueta = etiqueta;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(EnumType tipo) {
        this.tipo = tipo;
    }

    public void setParametros(ArrayList<Variable> parametros) {
        this.parametros = parametros;
    }

    public void setDeclaraciones(ArrayList<Variable> declaraciones) {
        this.declaraciones = declaraciones;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
