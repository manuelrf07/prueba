package ejercicios.ejercicio4;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            BibliotecaXml biblioteca = new BibliotecaXml("C:\\Users\\manue\\Desktop\\proyectos\\EjerciciosFicheros\\src\\main\\java\\archivos\\biblioteca.xml");
            int opcion;
            do {
                menu();
                opcion = leerEntero("Opción: ");
                try {
                    switch (opcion) {
                        case 1 -> registrar(biblioteca);
                        case 2 -> modificar(biblioteca);
                        case 3 -> buscarTitulo(biblioteca);
                        case 4 -> buscarAutor(biblioteca);
                        case 5 -> prestamo(biblioteca, true);
                        case 6 -> prestamo(biblioteca, false);
                        case 7 -> mostrar(biblioteca.listar());
                        case 0 -> System.out.println("Hasta pronto.");
                        default -> System.out.println("Opción no válida.");
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } while (opcion != 0);
        } catch (Exception e) {
            System.out.println("No se pudo abrir biblioteca.xml: " + e.getMessage());
        }
    }

    private static void menu() {
        System.out.println("\n=== BIBLIOTECA ===");
        System.out.println("1. Registrar libro");
        System.out.println("2. Modificar libro");
        System.out.println("3. Buscar por título");
        System.out.println("4. Buscar por autor");
        System.out.println("5. Marcar como prestado");
        System.out.println("6. Marcar como disponible");
        System.out.println("7. Listar catálogo");
        System.out.println("0. Salir");
    }

    private static void registrar(BibliotecaXml b) throws Exception {
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        int anio = leerEntero("Año de publicación: ");
        Libro l = b.registrar(titulo, autor, anio);
        System.out.println("Registrado: " + l);
    }

    private static void modificar(BibliotecaXml b) throws Exception {
        int id = leerEntero("ID del libro: ");
        Libro actual = b.buscarPorId(id);
        if (actual == null) { System.out.println("No existe ese ID."); return; }
        System.out.println("Actual: " + actual);
        System.out.println("(Deja vacío para mantener el valor)");
        String titulo = leerTexto("Nuevo título: ");
        String autor = leerTexto("Nuevo autor: ");
        String anioTxt = leerTexto("Nuevo año: ");
        Integer anio = null;
        if (!anioTxt.isBlank()) {
            try { anio = Integer.parseInt(anioTxt.trim()); }
            catch (NumberFormatException e) { System.out.println("Año no válido, se mantiene el actual."); }
        }
        b.modificar(id, titulo, autor, anio);
        System.out.println("Actualizado: " + b.buscarPorId(id));
    }

    private static void buscarTitulo(BibliotecaXml b) {
        mostrar(b.buscarPorTitulo(leerTexto("Título (o parte): ")));
    }

    private static void buscarAutor(BibliotecaXml b) {
        mostrar(b.buscarPorAutor(leerTexto("Autor (o parte): ")));
    }

    private static void prestamo(BibliotecaXml b, boolean prestar) throws Exception {
        int id = leerEntero("ID del libro: ");
        Libro l = b.buscarPorId(id);
        if (l == null) { System.out.println("No existe ese ID."); return; }
        if (l.isPrestado() == prestar) {
            System.out.println("El libro ya estaba " + (prestar ? "prestado." : "disponible."));
            return;
        }
        b.cambiarEstado(id, prestar);
        System.out.println("Estado actualizado: " + b.buscarPorId(id));
    }

    private static void mostrar(List<Libro> lista) {
        if (lista.isEmpty()) { System.out.println("Sin resultados."); return; }
        lista.forEach(System.out::println);
    }

    private static String leerTexto(String msg) {
        System.out.print(msg);
        return teclado.nextLine();
    }

    private static int leerEntero(String msg) {
        while (true) {
            if (!teclado.hasNextLine()) return 0;
            System.out.print(msg);
            try { return Integer.parseInt(teclado.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Introduce un número."); }
        }
    }
}
