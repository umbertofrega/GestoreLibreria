package back;

import tranfer.Libro;

import java.util.List;

public class Facade {
    private static GestoreRicerche gestoreRicerche;
    private static GestoreLibri gestoreLibri;

    public List<Libro> ordinaLibri(Ordinamento o, boolean crescente){
        gestoreRicerche = GestoreRicerche.getInstance();
        switch (o){
            case Ordinamento.AUTORE:
                return gestoreRicerche.ordinaPerAutore(crescente);
            case Ordinamento.TITOLO:
                return gestoreRicerche.ordinaPerTitolo(crescente);
        }
        return null;
    }

    public List<Libro> filtraPer(Stato stato){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraStato(stato);
    }

    public List<Libro> filtraPer(List<String> generi){
        gestoreRicerche = GestoreRicerche.getInstance();
        return gestoreRicerche.filtraGeneri(generi);
    }

    public List<Libro> cerca(Ordinamento tipo,String ricerca){
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