package transfer;

import back.Stato;

import java.util.List;

public class LibroBuilder {
    long isbn;
    String titolo;
    String autore;
    List<String> generi;
    int valutazione = 0;
    Stato statoLettura = Stato.DA_LEGGERE;

    public LibroBuilder generi(List<String> generi) {
        this.generi = generi;
        return this;
    }

    public LibroBuilder valutazione(int valutazione) {
        this.valutazione = valutazione;
        return this;
    }

    public LibroBuilder statoLettura(Stato statoLettura) {
        this.statoLettura = statoLettura;
        return this;
    }

    public LibroBuilder isbn(long isbn) {
        this.isbn = isbn;
        return this;
    }

    public LibroBuilder titolo(String titolo) {
        this.titolo = titolo;
        return this;
    }
    public LibroBuilder autore(String autore) {
        this.autore = autore;
        return this;
    }

    public Libro build() {
        return new Libro(this);
    }

}
