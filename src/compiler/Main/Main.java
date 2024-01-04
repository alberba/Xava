package compiler.Main;

import compiler.grammar.Parser;
import compiler.grammar.Scanner;
import java_cup.runtime.SymbolFactory;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {

    public static void main(String[] args) throws Exception {

        String rutaArchivo = conseguirPath(args[0]);
        FileReader programa = null;
        try {
            programa = new FileReader(rutaArchivo);
        } catch (Exception e) {
            System.out.println(rutaArchivo);
            System.out.println(e.toString());
            System.out.println("Error al abrir el archivo");
            System.exit(1);
        }

        long startTime = System.nanoTime();
        Scanner scanner = new Scanner(programa);
        long endTime = System.nanoTime();
        System.out.println("Scanner time: " + (endTime - startTime) / 1000000 + "ms");

        SymbolFactory sf = null;
        startTime = System.nanoTime();
        Parser parser = new Parser(scanner, sf);
        parser.parse();
        endTime = System.nanoTime();
        System.out.println("Parser time: " + (endTime - startTime) / 1000000 + "ms");

        guardarTSimbolo("tSimbolo.txt", parser.getTSimbolos().toString());


    }

    public static String conseguirPath(String rutaArchivo){
        String rutaActual = System.getProperty("user.dir");

        if (rutaArchivo.startsWith(".\\")) {
            rutaArchivo = rutaArchivo.substring(1);
        }

        // Se comprueba si el archivo esta en la carpeta actual
        if (rutaArchivo.contains("\\")) {

            // Comprobamos si la ruta esta en formato /home/usuario/... o home/usuario/...
            if (rutaArchivo.startsWith("\\")) {
                rutaArchivo = rutaActual + rutaArchivo;
            } else {
                rutaArchivo = rutaActual + "\\" + rutaArchivo;
            }

        } else {
            rutaArchivo = rutaActual + "\\" + rutaArchivo;
        }

        return rutaArchivo;

    }

    public static void guardarTSimbolo(String nombreArchivo, String tabla) {
        try {
            BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados/" + nombreArchivo));
            archivo.write(tabla);
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al guardar el archivo de la tabla de simbolos");
            System.exit(1);
        }
    }

}
