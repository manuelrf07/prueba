package ejercicios.ejercicio4;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaXml {

    private final File archivo;
    private final List<Libro> libros = new ArrayList<>();

    public BibliotecaXml(String ruta) throws Exception {
        this.archivo = new File(ruta);
        if (archivo.exists()) {
            cargar();
        } else {
            guardar();
        }
    }

    private void cargar() throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.parse(archivo);
        doc.getDocumentElement().normalize();

        libros.clear();
        NodeList nodos = doc.getElementsByTagName("libro");
        for (int i = 0; i < nodos.getLength(); i++) {
            Element e = (Element) nodos.item(i);
            int id = Integer.parseInt(e.getAttribute("id"));
            String titulo = texto(e, "titulo");
            String autor = texto(e, "autor");
            int anio = Integer.parseInt(texto(e, "anio"));
            boolean prestado = "prestado".equalsIgnoreCase(texto(e, "estado"));
            libros.add(new Libro(id, titulo, autor, anio, prestado));
        }
    }

    private String texto(Element padre, String etiqueta) {
        Node n = padre.getElementsByTagName(etiqueta).item(0);
        return n == null ? "" : n.getTextContent().trim();
    }

    private void guardar() throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = db.newDocument();
        Element raiz = doc.createElement("biblioteca");
        doc.appendChild(raiz);

        for (Libro l : libros) {
            Element e = doc.createElement("libro");
            e.setAttribute("id", String.valueOf(l.getId()));
            e.appendChild(elemento(doc, "titulo", l.getTitulo()));
            e.appendChild(elemento(doc, "autor", l.getAutor()));
            e.appendChild(elemento(doc, "anio", String.valueOf(l.getAnio())));
            e.appendChild(elemento(doc, "estado", l.isPrestado() ? "prestado" : "disponible"));
            raiz.appendChild(e);
        }

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        t.transform(new DOMSource(doc), new StreamResult(archivo));
    }

    private Element elemento(Document doc, String nombre, String valor) {
        Element e = doc.createElement(nombre);
        e.setTextContent(valor);
        return e;
    }
    public Libro registrar(String titulo, String autor, int anio) throws Exception {
        int nuevoId = libros.stream().mapToInt(Libro::getId).max().orElse(0) + 1;
        Libro l = new Libro(nuevoId, titulo, autor, anio, false);
        libros.add(l);
        guardar();
        return l;
    }

    public boolean modificar(int id, String titulo, String autor, Integer anio) throws Exception {
        Libro l = buscarPorId(id);
        if (l == null) return false;
        if (titulo != null && !titulo.isBlank()) l.setTitulo(titulo);
        if (autor != null && !autor.isBlank()) l.setAutor(autor);
        if (anio != null) l.setAnio(anio);
        guardar();
        return true;
    }

    public boolean cambiarEstado(int id, boolean prestado) throws Exception {
        Libro l = buscarPorId(id);
        if (l == null) return false;
        l.setPrestado(prestado);
        guardar();
        return true;
    }

    public Libro buscarPorId(int id) {
        for (Libro l : libros) if (l.getId() == id) return l;
        return null;
    }

    public List<Libro> buscarPorTitulo(String texto) {
        List<Libro> r = new ArrayList<>();
        for (Libro l : libros)
            if (l.getTitulo().toLowerCase().contains(texto.toLowerCase())) r.add(l);
        return r;
    }

    public List<Libro> buscarPorAutor(String texto) {
        List<Libro> r = new ArrayList<>();
        for (Libro l : libros)
            if (l.getAutor().toLowerCase().contains(texto.toLowerCase())) r.add(l);
        return r;
    }

    public List<Libro> listar() {
        return new ArrayList<>(libros);
    }
}