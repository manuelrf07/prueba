package ejercicios.ejercicio4;

public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private int anio;
    private boolean prestado;

    public Libro(int id, String titulo, String autor, int anio, boolean prestado) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.anio = anio;
        this.prestado = prestado;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnio() { return anio; }
    public boolean isPrestado() { return prestado; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setAnio(int anio) { this.anio = anio; }
    public void setPrestado(boolean prestado) { this.prestado = prestado; }

    @Override
    public String toString() {
        return String.format("[%d] %s - %s (%d) - %s",
                id, titulo, autor, anio, prestado ? "PRESTADO" : "disponible");
    }
}