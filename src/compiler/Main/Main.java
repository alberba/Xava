package compiler.Main;

import compiler.ErrorC;
import compiler.grammar.Parser;
import compiler.grammar.Scanner;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.SymbolFactory;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.println("Introduzca el nombre del archivo a compilar: ");

        String archivo = sc.nextLine();
        String rutaArchivo = conseguirPath(archivo);
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
            ErrorC.printErrores();
            System.exit(0);
        } else {
            guardarTSimbolo("tSimbolo.txt", parser.getTSimbolos().toString());
            guardarTokens(scanner.tokens);
        }


        sc.close();

    }

    public static String conseguirPath(String rutaArchivo){
        String rutaActual = System.getProperty("user.dir");
        System.out.println(rutaActual);

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
        rutaArchivo = rutaArchivo.replace("\\", "/");

        return rutaArchivo;

    }

    public static void guardarTokens(ArrayList<ComplexSymbol> tokens) {
        try {
            BufferedWriter archivo = new BufferedWriter(new FileWriter("resultados/tokens.txt"));
            for (int i = 0; i < tokens.size(); i++) {
                archivo.write(tokens.get(i).getName() + "\n");
            }
            archivo.close();
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error al guardar el archivo de los tokens");
            System.exit(1);
        }
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
