package back;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import tranfers.Libro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GestoreFiltri {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File documento = new File(Path.of("src","main","resources","data","libri.json").toUri());
    private static final GestoreFiltri instance = new GestoreFiltri();

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getInstance()
     */
    private GestoreFiltri() {}


    /**
     * Permette di ricevere la lista dei libri non ordinata
     *
     * @return Una lista di libri
     */
    List<Libro> getLibri(){
        List<Libro> libri;

        try {
            libri = mapper.readValue(documento,new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }

    /**
     * Ritorna una lista di libri ordinata per titolo
     *
     * @param crescente True la lista è ordinata in ordine crescente <br> False in ordine decrescente.
     * @return la lista oridinata.
     */
    List<Libro> ordinaPerTitolo(boolean crescente){
        List<Libro> libri = getLibri();
        libri.sort(Comparator.comparing(Libro::getTitolo));

        if(!crescente)
            libri.reversed();

        return libri;
    }

    /**
     * Ritorna una lista di libri ordinata per autore
     *
     * @param crescente True la lista è ordinata in ordine crescente <br> False in ordine decrescente.
     * @return la lista oridinata.
     */
    List<Libro> ordinaPerAutore(boolean crescente){
        List<Libro> libri = getLibri();
        libri.sort(Comparator.comparing(Libro::getAutore));

        if(!crescente)
            libri.reversed();

        return libri;
    }

    /**
     * Ritorna una lista di libri con solo i generi richiesti
     *
     * @param genere La lista dei generi che vogliono essere visualizzati
     * @return la lista filtrata
     */
    List<Libro> filtraGeneri(List<String> genere){
        List<Libro> libri = getLibri();

        libri.removeIf(l -> !l.getGeneri().contains(genere));

        return libri;
    }

    /**
     * Ritorna una lista di libri con lo stato lettura richiesto
     *
     * @param stato Lo stato lettura che vuole essere visualizzato
     * @return la lista filtrata
     */
    List<Libro> filtraStato(Stato stato){
        List<Libro> libri = getLibri();

        libri.removeIf(l -> !l.getStatoLettura().equals(stato));

        return libri;
    }


    /**
     *  Ricerca la lista dei libri scritti da un determinato autore
     *
     * @param ricerca la stringa contenente il nome dell'autore
     * @return la lista dei libri che hanno l'autore richiesto
     */
    List<Libro> cercaPerAutore(String ricerca){
        List<Libro> libri = getLibri();
        List<Libro> risultato = new ArrayList<>();

        for (Libro l : libri) {
            if(l.getAutore().equals(ricerca)){
                risultato.add(l);
            }
        }
        return risultato;
    }

    /**
     *  Ricerca la lista dei libri scritti da un determinato autore
     *
     * @param ricerca la stringa contenente il nome dell'autore
     * @return la lista dei libri che hanno l'autore richiesto
     */
    List<Libro> cercaPerTitolo(String ricerca){
        List<Libro> libri = getLibri();
        List<Libro> risultato = new ArrayList<>();

        for (Libro l : libri) {
            if(l.getTitolo().equals(ricerca)){
                risultato.add(l);
            }
        }
        return risultato;
    }


    /**
     * Permette di ricevere l'accesso all'istanza di GestoreFiltri, se non esiste la crea.
     *
     * @return L'istanza di GestoreFiltri
     */
    static GestoreFiltri getInstance() {
        if(instance == null){
            return new GestoreFiltri();
        }
        return instance;
    }
}
