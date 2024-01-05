package compiler.sintactic;

import compiler.sintactic.Symbols.EnumType;

import java.util.ArrayList;

public class Symbol {
    private final String nom;
    private final TipoElemento tipo;
    private final EnumType tipoReturn;
    private ArrayList<Symbol> content;
    private final boolean esConst;
    private final int dimension;
    private int line;

    public Symbol(String nom, TipoElemento tipo, EnumType tipoReturn,
                  ArrayList<Symbol> content, boolean esConst, int dimension, int line) {
        this.nom = nom;
        this.tipo = tipo;
        this.tipoReturn = tipoReturn;
        this.content = content;
        this.esConst = esConst;
        this.dimension = dimension;
        this.line = line;
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

    public ArrayList<Symbol> getContent() {
        return content;
    }

    public void setContent(ArrayList<Symbol> content) {
        this.content = content;
    }

    public boolean isConstant() {
        return esConst;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getName().equals(((Symbol) obj).getName());
    }

    @Override
    public String toString() {
        return "Symbol [nom=" + nom + ", tipo=" + tipo + ", tipoReturn="
                + tipoReturn + ", content=" + content + ", esConst=" + esConst
                + ", line=" + line + "]";
    }

}
