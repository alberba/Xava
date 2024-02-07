package compiler.sintactic;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Symbol {
    private final String nom;
    private final TipoElemento tipo;
    private final EnumType tipoReturn;
    private final ArrayList<Integer> dimensiones;
    private final boolean esConst;

    public Symbol(String nom, TipoElemento tipo, EnumType tipoReturn,
                  ArrayList<Integer> dimensiones, boolean esConst) {
        this.nom = nom;
        this.tipo = tipo;
        this.tipoReturn = tipoReturn;
        this.dimensiones = dimensiones;
        this.esConst = esConst;
    }


    public boolean isEsConst() {
        return esConst;
    }

    public ArrayList<Integer> getDimensiones() {
        return dimensiones;
    }

    public int getNDimensiones() {
        return dimensiones.size();
    }

    public String getName() {
        return nom;
    }

    public TipoElemento getTipoElemento() {
        return tipo;
    }

    public EnumType getTipoReturn() {
        return tipoReturn;
    }


    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((Symbol) obj).getName());
    }

    @Override
    public String toString() {
        return "Symbol [nom=" + nom + ", tipo=" + tipo + ", tipoReturn="
                + tipoReturn + ", dimensiones=" + dimensiones + ", esConst=" + esConst
                + "]";
    }

}
