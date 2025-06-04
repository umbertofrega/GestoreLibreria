package tranfers;

import back.Stato;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.LinkedList;

public class Libro extends LibroAstratto{
    private int isbn;
    private String titolo;
    private String autore;
    private int valutazione;
    private ArrayList<String> generi;
    private Stato statoLettura;

    /*
     * Richiesto da Jackson
     */
    public Libro() {}

    @Override
    public String toString() {
        return "codice ISBN=" + isbn +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", valutazione=" + valutazione +
                ", generi=" + generi +
                ", statoLettura='" + statoLettura + '\'';
    }

    public int getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getAutore() {
        return autore;
    }

    public int getValutazione() {
        return valutazione;
    }

    public ArrayList<String> getGeneri() {
        return generi;
    }

    public Stato getStatoLettura() {
        return statoLettura;
    }


}
