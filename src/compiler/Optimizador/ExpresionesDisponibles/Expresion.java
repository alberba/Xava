package compiler.Optimizador.ExpresionesDisponibles;

import compiler.Intermedio.Instruccion;

import java.util.Objects;

public class Expresion {
    Instruccion instruccion;
    public Expresion(Instruccion instruccion){
        this.instruccion = instruccion;
    }

    public Instruccion getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(Instruccion instruccion) {
        this.instruccion = instruccion;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expresion)) return false;
        Instruccion that = ((Expresion) o).getInstruccion();
        // Verificar si operador1 y operador2 son null
        if (instruccion.getOperando1() == null && that.getOperando1() != null) return false;
        if (instruccion.getOperando2() == null && that.getOperando2() != null) return false;
        // Verificar si operador1 y operador2 son iguales
        if (instruccion.getOperando1() != null ? !instruccion.getOperando1().equals(that.getOperando1()) : false) return false;
        if (instruccion.getOperando2() != null ? !instruccion.getOperando2().equals(that.getOperando2()) : false) return false;
        // Continuar con las otras comparaciones
        return instruccion.getOperacion().equals(that.getOperacion());
    }


    @Override
    public int hashCode() {
        return Objects.hash(instruccion.getOperacion(), instruccion.getOperando1(), instruccion.getOperando2());
    }
}
