package compiler;


import compiler.sintactic.Fase;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Error {
    // Mensaje de error
    private String mensaje;
    // Linea
    private int linea;
    // Fase del compilador
    private Fase fase;

    public Error(String msj, int linea, Fase fase) {
        this.mensaje = msj;
        this.linea = linea;
        this.fase = fase;
    }

    public static void añadirError(Error err) {
        try {
            System.out.println(err);
            BufferedWriter writer = new BufferedWriter(new FileWriter("Resultado/errores.txt", true));
            writer.write(err.toString() + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Error de " + fase.name() + ": " + mensaje + ". Línea: " + linea + ".\n";
    }
}
