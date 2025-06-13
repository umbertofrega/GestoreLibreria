package back;

import back.gestori.GestoreLibri;
import back.gestori.GestoreRicerche;
import back.stati.Ordinamento;
import back.stati.Stato;
import transfer.Libro;

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

    public List<Libro> filtraPerStato(List<Libro> lista,List<Stato> stati){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraStato(lista,stati);
    }

    public List<Libro> filtraPerGeneri(List<Libro> lista,List<String> generi){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraGeneri(lista,generi);
    }

    public List<Libro> cerca(Ordinamento tipo, String ricerca){
        gestoreRicerche = GestoreRicerche.getInstance();
        if(tipo.equals(Ordinamento.AUTORE)){
            return gestoreRicerche.cercaPerAutore(ricerca);
        }
        return gestoreRicerche.cercaPerTitolo(ricerca);
    }

    public boolean inserisciLibro(Libro libro){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.inserisciLibro(libro);
    }

    public boolean inserisciLibro(List<Libro> libro){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.inserisciLibro(libro);
    }

    public boolean rimuoviLibro(Libro libro){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.rimuoviLibro(libro);
    }

    public boolean aggiornaLibro(Libro libroNew,Libro libroOld){
        gestoreLibri = GestoreLibri.getInstance();
        return gestoreLibri.aggiornaLibro(libroNew,libroOld);
    }

}