package back.gestori;

import back.stati.Stato;
import back.transfer.Libro;

import java.util.ArrayList;
import java.util.List;

public class GestoreRicerche extends Gestore{

    private static final GestoreRicerche instance = new GestoreRicerche();

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getInstance()
     */
    private GestoreRicerche() {}


    /**
     * Ritorna una lista di libri con solo i generi richiesti
     *
     * @param libri La lista che si vuole filtrare
     * @param genere La lista dei generi che vogliono essere visualizzati
     * @return la lista filtrata
     */
    public List<Libro> filtraGeneri(List<Libro> libri, List<String> genere){
        List<Libro> ris = new ArrayList<>();

        for (Libro l : libri) {
            if(contiene(l.generi(),genere)){
                ris.add(l);
            }
        }

        return ris;
    }

    /**
     * Ritorna una lista di libri con lo stato lettura richiesto
     *
     * @param libri la lista che si vuole filtrare
     * @param stati Gli stati lettura che voglioni essere visualizzati
     * @return la lista filtrata
     */
    public List<Libro> filtraStato(List<Libro> libri, List<Stato> stati){
        List<Libro> ris = new ArrayList<>();
        for (Libro l : libri){
            for(Stato s : stati){
                if(l.statoLettura().equals(s)){
                    ris.add(l);
                    break;
                }
            }
        }
        return ris;
    }


    /**
     * Ricerca la lista dei libri scritti da un determinato autore
     *
     * @param libri la lista su cui si vuole effettuare la ricerca
     * @param ricerca la stringa contenente il nome dell'autore
     * @return la lista dei libri che hanno l'autore richiesto
     */
    public List<Libro> cercaPerAutore(List<Libro> libri, String ricerca){
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
     * @param libri la lista su cui si vuole effettuare la ricerca
     * @param ricerca la stringa contenente il nome dell'autore
     * @return la lista dei libri che hanno l'autore richiesto
     */
    public List<Libro> cercaPerTitolo(List<Libro> libri,String ricerca){
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

    /**
     * Un metodo che permette di vedere se il genere cercato è contenuto nella lista dei generi in maniera meno stretta del contains
     *
     * @param generi La lista dei generi
     * @param ricerca Il genere che si sta ricercando
     * @return True se il genere è presente, <br> False altrimenti
     */
    private boolean contiene(List<String> generi, List<String> ricerca){
        for (String g : generi) {
            for(String r : ricerca){
                r = ".*"+r.toLowerCase().strip();
                if(g.toLowerCase().matches(r)){
                    return true;
                }
            }
        }
        return false;
    }
}
