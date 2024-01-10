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
    // Fase del compilador
    private Fase fase;

    public ErrorC(String msj, int linea, Fase fase) {
        this.mensaje = msj;
        this.linea = linea;
        this.fase = fase;
    }

    public static void añadirError(ErrorC err) {
        try {
            errores.add(err);
            BufferedWriter writer = new BufferedWriter(new FileWriter("Resultado/errores.txt", true));
            writer.write(err.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hayErrores() {
        return !errores.isEmpty();
    }

    public static void printErrores() {
        for (ErrorC errorC : errores) {
            System.out.println(errorC);
        }
    }

    @Override
    public String toString() {
        return "Error de " + fase.name() + ": " + mensaje + ". Línea: " + linea + ".";
    }
}
