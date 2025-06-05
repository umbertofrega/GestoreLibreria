package back;

import tranfers.Libro;
import java.util.List;

public class Facade {
    private static GestoreFiltri gestoreFiltri;
    private static GestoreLibri gestoreLibri;

    public List<Libro> ordinaLibri(Ordinamento o, boolean crescente){
        gestoreFiltri = GestoreFiltri.getInstance();
        switch (o){
            case Ordinamento.AUTORE:
                return gestoreFiltri.ordinaPerAutore(crescente);
            case Ordinamento.TITOLO:
                return gestoreFiltri.ordinaPerTitolo(crescente);
        }
        return null;
    }

    public List<Libro> filtraPer(Stato stato){
        gestoreFiltri = GestoreFiltri.getInstance();
        return gestoreFiltri.filtraStato(stato);
    }

    public List<Libro> filtraPer(List<String> generi){
        gestoreFiltri = GestoreFiltri.getInstance();
        return gestoreFiltri.filtraGeneri(generi);
    }

    public List<Libro> cerca(boolean tipo,String ricerca){
        gestoreFiltri= GestoreFiltri.getInstance();
        if(tipo){
            return gestoreFiltri.cercaPerAutore(ricerca);
        }
        return gestoreFiltri.cercaPerTitolo(ricerca);
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