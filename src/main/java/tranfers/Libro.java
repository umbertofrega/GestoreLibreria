package tranfers;

import java.util.LinkedList;

public class Libro {
    private int isbn;
    private String titolo;
    private String autore;
    private int valutazione;
    private LinkedList<String> generi;
    private String statoLettura;

    public Libro(int id, String titolo, String autore, int valutazione, LinkedList<String> generi, String statoLettura) {
        this.isbn = id;
        this.titolo = titolo;
        this.autore = autore;
        this.valutazione = valutazione;
        this.generi = (LinkedList<String>) generi.clone();
        this.statoLettura = statoLettura;
    }
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

    public LinkedList<String> getGeneri() {
        return generi;
    }

    public String getStatoLettura() {
        return statoLettura;
    }
}
