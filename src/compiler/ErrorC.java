package compiler;


import compiler.sintactic.Fase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ErrorC {

    private static ArrayList<ErrorC> errores = new ArrayList<>();
    // Mensaje de error
    private String mensaje;
    // Linea
    private int linea;
    private int columna;
    // Fase del compilador
    private Fase fase;

    public ErrorC(String msj, int linea, Fase fase) {
        this.mensaje = msj;
        this.linea = linea;
        this.fase = fase;
    }

    public ErrorC(String msj, int linea, int columna, Fase fase) {
        this.mensaje = msj;
        this.linea = linea;
        this.fase = fase;
        this.columna = columna;
    }

    public static void añadirError(ErrorC err) {
        errores.add(err);
    }

    public static boolean hayErrores() {
        return !errores.isEmpty();
    }

    @Override
    public String toString() {
        if (columna != 0)
            return "Error de " + fase.name() + ": " + mensaje + ". Línea: " + linea + ". Columna: " + columna + ".";
        else {
            return "Error de " + fase.name() + ": " + mensaje + ". Línea: " + linea + ".";
        }
    }

    public static String allErroresToString(){
        StringBuilder errores = new StringBuilder();
        for (ErrorC errorC : ErrorC.errores) {
            errores.append(errorC.toString()).append("\n");
        }
        return errores.toString();
    }
}
