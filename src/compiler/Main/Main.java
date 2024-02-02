package compiler.Main;

import compiler.Ensamblador.Ensamblador;
import compiler.ErrorC;
import compiler.Intermedio.Intermedio;
import compiler.grammar.Parser;
import compiler.grammar.Scanner;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.SymbolFactory;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("Introduzca el nombre del archivo a compilar: ");

        String archivo = sc.nextLine();
        String rutaArchivo = conseguirPath(archivo);

        String rutaActual = System.getProperty("user.dir");
        String [] aux = rutaArchivo.split("/");
        Files.createDirectories(Paths.get(rutaActual + "/resultados/" + aux[aux.length - 1].split("\\.")[0]));

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

        Parser parser = null;
        try {
            SymbolFactory sf = new ComplexSymbolFactory();
            startTime = System.nanoTime();
            parser = new Parser(scanner, sf);
            parser.parse();
            endTime = System.nanoTime();
            System.out.println("Parser time: " + (endTime - startTime) / 1000000 + "ms");
        } catch (Exception e) {
            if (!ErrorC.hayErrores()) {
                e.printStackTrace();
                System.out.println("Error al parsear el archivo");
                System.exit(0);
            }

        }
        if (ErrorC.hayErrores()) {
            String errores = ErrorC.allErroresToString();
            System.out.println(errores);
            guardarFichero("errores.txt", errores, rutaArchivo);
            System.exit(0);
        } else {
            guardarFichero("tSimbolo.txt", parser.getTSimbolos().toString(), rutaArchivo);
            guardarTokens(scanner.tokens, rutaArchivo);
        }

        Intermedio intermedio = new Intermedio(parser.getTSimbolos());
        try{
            startTime = System.nanoTime();
            parser.getXavaArbol().generarIntermedio(intermedio);
            endTime = System.nanoTime();
            System.out.println("Intermedio time: " + (endTime - startTime) / 1000000 + "ms");
            guardarFichero("intermedio.txt", intermedio.toString(), rutaArchivo);
        } catch (Exception e) {
            System.out.println("Error al generar el intermedio");
            e.printStackTrace();
            System.exit(0);
        }

        startTime = System.nanoTime();
        Ensamblador ensamblador = new Ensamblador(intermedio, parser.getTSimbolos());
        ensamblador.generarEnsamblador();
        endTime = System.nanoTime();
        System.out.println("Ensamblador time: " + (endTime - startTime) / 1000000 + "ms");
        guardarFichero("ensamblador.x68", ensamblador.toString(), rutaArchivo);

        sc.close();

    }

    public static String conseguirPath(String rutaArchivo) {
        String rutaActual = System.getProperty("user.dir");
        System.out.println(rutaActual);

        if (rutaArchivo.startsWith(".\\")) {
            rutaArchivo = rutaArchivo.substring(1);
        }

        // Se comprueba si el archivo está en la carpeta actual
        if (rutaArchivo.contains("\\")) {

            // Comprobamos si la ruta esta en formato /c1/c2/... o c1/c2/...
            if (rutaArchivo.startsWith("\\")) {
                rutaArchivo = rutaActual + rutaArchivo;
            } else {
                rutaArchivo = rutaActual + "\\" + rutaArchivo;
            }

        } else {
            rutaArchivo = rutaActual + "\\src\\programa\\" + rutaArchivo;
        }
        rutaArchivo = rutaArchivo.replace("\\", "/");

        return rutaArchivo;

    }

    public static void guardarTokens(ArrayList<ComplexSymbol> tokens, String nombreXava) {
        try {
            String [] aux = nombreXava.split("/");
            BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados/" + aux[aux.length - 1].split("\\.")[0] + "/tokens.txt"));
            for (ComplexSymbol token : tokens) {
                archivo.write(token.getName() + "\n");
            }
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al guardar el archivo de los tokens");
            System.exit(1);
        }
    }

    public static void guardarFichero(String nombreArchivo, String tabla, String nombreXava) {
        try {
            String [] aux = nombreXava.split("/");
            BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados/" + aux[aux.length - 1].split("\\.")[0] + "/" + nombreArchivo));
            archivo.write(tabla);
            archivo.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Error al guardar el archivo de la tabla de simbolos");
            System.exit(1);
        }
    }

}
