package ejercicios;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ejercicio1 {

    static File archivo = new File("C:\\Users\\manue\\Desktop\\proyectos\\EjerciciosFicheros\\src\\main\\java\\archivos\\hola.txt");

    public static void main(String[] args) {
        int contador = 0;

        if(archivo.exists()){
            if(archivo.isFile()){
                try (BufferedReader br = new BufferedReader(
                        new FileReader(archivo))) {
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        String[] hola = linea.split("hola");
                        for(String hola1 : hola){
                            contador++;
                        }
                    }
                    System.out.println(contador);
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }

            }

        }

    }

}
