package back.gestori;

import back.transfer.Libro;

import java.io.IOException;
import java.util.List;

/**
 *  Una classe Singleton che gestisce il
 *  salvataggio in memoria secondaria dei libri.
 */
public class GestoreLibri extends Gestore{


    private static GestoreLibri instance = null;


    /**
     * Permette di salvare un nuovo libro in memoria.
     *
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean inserisciLibro(Libro libro) {
        assert libro != null : "Non si può inserire un libro nullo";
        List<Libro> libri = getLibri();
        libri.add(libro);
        return scrivi(libri);
    }


    /**
     * Permette di salvare una lista di libri in memoria.
     *
     * @param libri Una lista di libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean inserisciLibro(List<Libro> libri) {
        assert libri != null : "Non si può inserire un libro nullo";
        return scrivi(libri);
    }


    /**
     * Permette di rimuovere un libro salvato in memoria
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean rimuoviLibro(Libro libro) {
        assert libro != null : "Non si può rimuovere un libro nullo";
        List<Libro> libri = getLibri();
        libri.remove(libro);
        return scrivi(libri);
    }

    /**
     * Permette di aggiornare un libro in memoria.
     *
     * @param libroOld il vecchio stato del libro
     * @param libroNew il nuovo stato del libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean aggiornaLibro(Libro libroOld, Libro libroNew) {
        assert libroOld != null : "Non si può modificare un libro nullo";
        assert libroNew != null : "Non si può inserire un libro nullo";
        assert libroNew != libroOld : "Non c'è nessun aggiornamento";
        List<Libro> libri = getLibri();
        libri.remove(libroOld);
        libri.add(libroNew);
        return scrivi(libri);
    }

    private static boolean scrivi(List<Libro> libri) {
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
    public static synchronized GestoreLibri getInstance() {
        if (instance == null) {
            return new GestoreLibri();
        }
        return instance;
    }

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getInstance()
     */
    private GestoreLibri() {}

}
