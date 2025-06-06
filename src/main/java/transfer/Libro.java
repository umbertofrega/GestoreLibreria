package transfer;

import back.Stato;

import java.util.List;

public record Libro(
        int isbn,
        String titolo,
        String autore,
        int valutazione,
        List<String> generi,
        Stato statoLettura
) {
    public Libro(LibroBuilder lb){
        this(lb.isbn,lb.titolo, lb.autore, lb.valutazione,lb.generi,lb.statoLettura);
    }
}
