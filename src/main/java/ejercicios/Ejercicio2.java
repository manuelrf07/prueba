
package ejercicios;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Ejercicio2 {

    static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Ingrese el path de la carpeta origen: ");
        String path = teclado.nextLine();

        File carpeta = new File(path);

        File copiaDeSeguridad = new File(
                "C:\\Users\\manue\\Desktop\\proyectos\\EjerciciosFicheros\\src\\main\\java\\archivos\\copiaDeSeguridad"
        );

        if (carpeta.exists() && carpeta.isDirectory()) {

            copiaDeSeguridad.mkdirs();

            File[] elementos = carpeta.listFiles();

            try {
                if(elementos != null) {


                    for (File archivo : elementos) {

                        if (archivo.isFile()) {

                            File destino = new File(copiaDeSeguridad, archivo.getName());

                            Files.copy(
                                    archivo.toPath(),
                                    destino.toPath(),
                                    StandardCopyOption.REPLACE_EXISTING
                            );
                        }
                    }
                }

                System.out.println("Se han copiado correctamente");

            } catch (IOException e) {
                System.out.println("No se han copiado correctamente");
            }

        } else {
            System.out.println("No se ha encontrado la carpeta");
        }
    }
}
