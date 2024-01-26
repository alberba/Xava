package compiler.Ensamblador;

import compiler.Intermedio.Instruccion;
import compiler.Intermedio.Intermedio;
import compiler.Intermedio.Variable;
import compiler.sintactic.Symbols.Inst;
import compiler.sintactic.TSimbolos;

import java.util.ArrayList;

public class Ensamblador {


    Intermedio intermedio;
    TSimbolos ts;
    private final ArrayList<String> codigo;

    public Ensamblador(Intermedio intermedio, TSimbolos ts) {

        this.ts = ts;
        this.intermedio = intermedio;
        codigo = new ArrayList<>();

    }

    public void generarEnsamblador() {

        codigo.add("\t\tORG\t$1000");
        declararVariables();
        for(Instruccion instruccion: intermedio.getCodigo()){
            instruccionAEnsamblador(instruccion);
        }

    }

    public void declararVariables() {

        codigo.add("*-----------------------------------------------------------");
        codigo.add("* VARIABLES Y CONSTANTES");
        codigo.add("*-----------------------------------------------------------");

        for (Variable var: intermedio.getTv()) {
            if (ts.getSymbol(var.getId()).isConstant()) {
                codigo.add(var.getId() + "\tEQU\t" + var);
            } else {
                codigo.add(var.getId() + "\tDS.W\t1");
            }

        }

    }

    public void instruccionAEnsamblador(Instruccion instruccion) {
        switch(instruccion.getOperacion()) {
            case ASIG:
                ensambladorAsig(instruccion);
                break;
            case SUMA:
                ensambladorSumaResta(instruccion, "ADD");
                break;
            case RESTA:
                ensambladorSumaResta(instruccion, "SUB");
                break;
            case MULTIPLICACION:
                ensambladorMultDivMod(instruccion, "MULTS", false);
                break;
            case DIVISION:
                ensambladorMultDivMod(instruccion, "DIVS", false);
                break;
            case MODULO:
                ensambladorMultDivMod(instruccion, "DIVS", true);
                break;
            case IGUAL:
            case DIFERENTE:
            case MENOR:
            case MENOR_IGUAL:
            case MAYOR:
            case MAYOR_IGUAL:
                ensambladorComp(instruccion, instruccion.getOperacion().toString());
                break;
            case NO:
                ensambladorNo(instruccion);
                break;
            case INDEXADO:
                break;
            case ASIGNADO:
                break;
            case ETIQUETA:
                break;
            case SALTO_INCON:
                break;
            case SALTO_COND:
                break;
            case INICIALIZACION:
                break;
            case LLAMADA:
                if (instruccion.getOperador1() != null) {
                    System.out.println("a");
                } else {
                    System.out.println("b");
                }
                break;
            case RETORNO:
                if (instruccion.getDestino() != null){
                    System.out.println("a");
                } else {
                    System.out.println("b");
                }
                break;
            case PARAMETRO_SIMPLE:
                break;
            case PARAMETRO_ARRAY:
                break;
            case IMPRIMIR:
                break;
            case ENTRADA_ENT:
                break;
            case ENTRADA_BOOL:
                break;
            case ENTRADA_CAR:
                break;
            default:
                break;
        }
    }

    private boolean esNum(String operador) {
        try {
            Integer.parseInt(operador);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void ensambladorSumaResta(Instruccion instruccion, String Op) {

        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\t" + Op + ".W\t" + var2 + ", D0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());

    }

    private void ensambladorMultDivMod(Instruccion instruccion, String op, boolean esMod) {

        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tMOVE.W\t" + var2 + ", D1");
        codigo.add("\tEXT.L\tD1");
        codigo.add("\t" + op + ".W\tD1, D0");
        if (esMod) {
            codigo.add("\tSWAP\tD0");
        }
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());

    }

    private void ensambladorAsig(Instruccion instruccion) {
        String var1 = convertirOperador(instruccion.getOperador1());

        codigo.add("\tMOVE.W\t" + var1 + ", " + instruccion.getDestino());
    }

    private void ensambladorComp(Instruccion instruccion, String op) {
        String var1 = convertirOperador(instruccion.getOperador1());
        codigo.add("\tMOVE.W\t" + var1 + ", D0");
        String var2 = convertirOperador(instruccion.getOperador2());
        codigo.add("\tCMP.W\t" + var2 + ", D0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
        switch (op) {
            case "IGUAL":
                // Scc hace set si se cumple, clear si no, nos viene perfecto
                codigo.add("\tSEQ\tD0");   // Set if equal
                codigo.add("\tEXT.W\tD0"); // No se puede extender de byte a long directamente, se extiende de byte a word
                codigo.add("\tEXT.L\tD0"); // Y después de word a long
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "DIFERENTE":
                codigo.add("\tSNE\tD0");   // Set if NOT equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MENOR":
                codigo.add("\tSLT\tD0");   // Set if less
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MENOR_IGUAL":
                codigo.add("\tSLE\tD0");   // Set if less or equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MAYOR":
                codigo.add("\tSGT\tD0");   // Set if greater
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
            case "MAYOR_IGUAL":
                codigo.add("\tSGE\tD0");   // Set if greater or equal
                codigo.add("\tEXT.W\tD0");
                codigo.add("\tEXT.L\tD0");
                codigo.add("\tMOVE.W D0, " + instruccion.getDestino());
                break;
        }
    }

    private void ensambladorNo(Instruccion instruccion) {
        codigo.add("\tMOVE.W\t" + instruccion.getDestino() + ", D0");
        codigo.add("\tNOT.W\tD0");
        codigo.add("\tMOVE.W\tD0, " + instruccion.getDestino());
    }

    private String convertirOperador(String valor) {
        
        if (intermedio.buscarVariable(valor) == null) {
            if(esNum(valor)) {
                valor = "#" + valor;
            } else {
                valor = "#'" + valor + "'";
            }
        }

        return valor;
    }

}
