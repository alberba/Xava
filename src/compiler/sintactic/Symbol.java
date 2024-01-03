package compiler.sintactic;

import java.util.ArrayList;

public class Symbol {
    private final String nom;
    private final StructureType tipo;
    private final StructureReturnType tipoReturn;
    private ArrayList<Symbol> content;
    private final boolean esGlobal;
    private final boolean esConst;
    private int line;

    public Symbol(String nom, StructureType tipo, StructureReturnType tipoReturn,
                  ArrayList<Symbol> content, boolean esGlobal, boolean esConst, int line) {
        this.nom = nom;
        this.tipo = tipo;
        this.tipoReturn = tipoReturn;
        this.content = content;
        this.esGlobal = esGlobal;
        this.esConst = esConst;
        this.line = line;
    }

    public String getName() {
        return nom;
    }

    public StructureType getStructureType() {
        return tipo;
    }

    public StructureReturnType getStructureReturnType() {
        return tipoReturn;
    }

    public ArrayList<Symbol> getContent() {
        return content;
    }

    public void setContent(ArrayList<Symbol> content) {
        this.content = content;
    }

    public boolean isGlobal() {
        return esGlobal;
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
                + tipoReturn + ", content=" + content + ", esGlobal=" + esGlobal + ", esConst=" + esConst
                + ", line=" + line + "]";
    }

}
