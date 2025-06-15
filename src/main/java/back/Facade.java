package back;

import back.gestori.GestoreLibri;
import back.gestori.GestoreRicerche;
import back.stati.Ordinamento;
import back.stati.Stato;
import back.transfer.Libro;

import java.util.List;

public class Facade {
    private static GestoreRicerche gestoreRicerche;
    private static GestoreLibri gestoreLibri;


    /**
     * Permette di ricevere la lista dei libri non ordinata
     *
     * @return Una lista di libri
     */
    public List<Libro> getLibri(){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.getLibri();
    }

    /**
     * Permette di filtrare la lista in input per gli stati richiesti
     * @param lista la lista da filtrare
     * @param stati gli stati che si vogliono visualizzare
     * @return la lista filtrata
     */
    public List<Libro> filtraPerStato(List<Libro> lista, List<Stato> stati){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraStato(lista,stati);
    }

    /**
     * Ritorna una lista di libri con solo i generi richiesti
     *
     * @param lista La lista che si vuole filtrare
     * @param generi La lista dei generi che vogliono essere visualizzati
     * @return la lista filtrata
     */
    public List<Libro> filtraPerGeneri(List<Libro> lista, List<String> generi){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraGeneri(lista,generi);
    }

    /**
     *  Ricerca la lista dei libri scritti da un determinato autore
     * @param lista la lista su cui si vuole effettuare la ricerca
     * @param tipo il tipo di ricerca, se per AUTORE o per TITOLO
     * @param ricerca la stringa contenente la ricerca
     * @return la lista dei libri che hanno l'autore richiesto
     */
    public List<Libro> cerca(List<Libro> lista, Ordinamento tipo, String ricerca){
        gestoreRicerche = GestoreRicerche.getInstance();
        if(tipo.equals(Ordinamento.AUTORE)){
            return gestoreRicerche.cercaPerAutore(lista, ricerca);
        }
        return gestoreRicerche.cercaPerTitolo(lista, ricerca);
    }

    /**
     * Permette di salvare un di libro in memoria.
     *
     * @param libro un libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean inserisciLibro(Libro libro){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.inserisciLibro(libro);
    }
    /**
     * Permette di salvare una lista di libri in memoria.
     *
     * @param libri Una lista di libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean inserisciLibro(List<Libro> libri){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.inserisciLibro(libri);
    }

    /**
     * Permette di rimuovere un libro salvato in memoria
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean rimuoviLibro(Libro libro){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.rimuoviLibro(libro);
    }

    /**
     * Permette di aggiornare un libro in memoria.
     *
     * @param libroOld il vecchio stato del libro
     * @param libroNew il nuovo stato del libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean aggiornaLibro(Libro libroNew,Libro libroOld){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.aggiornaLibro(libroNew,libroOld);
    }

}