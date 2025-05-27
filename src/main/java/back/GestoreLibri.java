package back;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import tranfers.Libro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GestoreLibri {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File documento = new File(Path.of("src", "main", "resources", "data", "libri.json").toUri());
    private static final GestoreLibri istance = null;


    /**
     * Permette di salvare un nuovo libro in memoria.
     *
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public static boolean inserisciLibro(Libro libro) {
        Collection<Libro> libri = getLibri();
        libri.add(libro);
        return scrivi(libri);
    }

    /**
     * Permette di salvare una lista di libri in memoria.
     *
     * @param libri Una lista di libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public static boolean inserisciLibri(Collection<Libro> libri) {
        return scrivi(libri);
    }

    /**
     * Permette di aggiornare un libro in memoria.
     *
     * @param libroOld il vecchio stato del libro
     * @param libroNew il nuovo stato del libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public static boolean aggiornaLibro(Libro libroOld, Libro libroNew) {
        ArrayList<Libro> libri = (ArrayList<Libro>) getLibri();
        libri.remove(libroOld);
        libri.add(libroNew);
        return scrivi(libri);
    }

    private static boolean scrivi(Collection<Libro> libri) {
        try {
            mapper.writeValue(documento, libri);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Permette di ricevere l'accesso all'istanza di Gestore Libri, se non esiste la crea.
     *
     * @return L'istanza di GestoreLibri
     */
    public static synchronized GestoreLibri getIstance() {
        if (istance == null) {
            return new GestoreLibri();
        }
        return istance;
    }

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getIstance()
     */
    private GestoreLibri() {}

    /*
     * Riporto il metodo getLibri() di GestoreFiltri qui per diminuire la coesione tra le
     * classi differenti.
     */
    private static List<Libro> getLibri(){
        List<Libro> libri = new ArrayList<>();

        try {
            libri = mapper.readValue(documento,new TypeReference<List<Libro>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }
}
