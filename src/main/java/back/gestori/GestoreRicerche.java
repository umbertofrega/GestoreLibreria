package back.gestori;

import back.stati.Stato;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import transfer.Libro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GestoreRicerche {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final File documento = new File(Path.of("src","main","resources","data","libri.json").toUri());
    private static final GestoreRicerche instance = new GestoreRicerche();

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getInstance()
     */
    private GestoreRicerche() {}


    /**
     * Permette di ricevere la lista dei libri non ordinata
     *
     * @return Una lista di libri
     */
    public static List<Libro> getLibri(){
        List<Libro> libri;

        try {
            libri = mapper.readValue(documento,new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }

    /**
     * Ritorna una lista di libri con solo i generi richiesti
     *
     * @param genere La lista dei generi che vogliono essere visualizzati
     * @return la lista filtrata
     */
    public List<Libro> filtraGeneri(List<String> genere){
        List<Libro> libri = getLibri();

        libri.removeIf(l -> !l.generi().contains(genere));

        return libri;
    }

    /**
     * Ritorna una lista di libri con lo stato lettura richiesto
     *
     * @param stato Lo stato lettura che vuole essere visualizzato
     * @return la lista filtrata
     */
    public List<Libro> filtraStato(Stato stato){
        List<Libro> libri = getLibri();

        libri.removeIf(l -> !l.statoLettura().equals(stato));

        return libri;
    }


    /**
     *  Ricerca la lista dei libri scritti da un determinato autore
     *
     * @param ricerca la stringa contenente il nome dell'autore
     * @return la lista dei libri che hanno l'autore richiesto
     */
    public List<Libro> cercaPerAutore(String ricerca){
        List<Libro> libri = getLibri();
        List<Libro> risultato = new ArrayList<>();

        ricerca = ".*"+ricerca.toLowerCase().strip()+".*";

        for (Libro l : libri) {
            if(l.autore().toLowerCase().matches(ricerca)){
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
    public List<Libro> cercaPerTitolo(String ricerca){
        List<Libro> libri = getLibri();
        List<Libro> risultato = new ArrayList<>();

        ricerca = ".*"+ricerca.toLowerCase().strip()+".*";

        for (Libro l : libri) {
            if(l.titolo().toLowerCase().matches(ricerca)){
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
    public static GestoreRicerche getInstance() {
        if(instance == null){
            return new GestoreRicerche();
        }
        return instance;
    }
}
