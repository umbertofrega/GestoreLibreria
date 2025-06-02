package tranfers;

import back.Stato;

import java.util.ArrayList;
import java.util.LinkedList;

public abstract class LibroAstratto{
    protected int isbn;
    protected String titolo;
    protected String autore;
    protected int valutazione;
    protected ArrayList<String> generi;
    protected Stato statoLettura;


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

    public void setStatoLettura(Stato statoLettura) {
        this.statoLettura = statoLettura;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public void setGeneri(ArrayList<String> generi) {
        this.generi = (ArrayList<String>) generi.clone();
    }
}
