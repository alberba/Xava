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
        if (instruccion.getOperador1() == null && that.getOperador1() != null) return false;
        if (instruccion.getOperador2() == null && that.getOperador2() != null) return false;
        // Verificar si operador1 y operador2 son iguales
        if (instruccion.getOperador1() != null ? !instruccion.getOperador1().equals(that.getOperador1()) : false) return false;
        if (instruccion.getOperador2() != null ? !instruccion.getOperador2().equals(that.getOperador2()) : false) return false;
        // Continuar con las otras comparaciones
        return instruccion.getOperacion().equals(that.getOperacion());
    }


    @Override
    public int hashCode() {
        return Objects.hash(instruccion.getOperacion(), instruccion.getOperador1(), instruccion.getOperador2());
    }
}
