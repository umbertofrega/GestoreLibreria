package transfer;

import back.Stato;

import java.util.List;

public record Libro(
        long isbn,
        String titolo,
        String autore,
        int valutazione,
        List<String> generi,
        Stato statoLettura
) {
    public Libro(LibroBuilder lb){
        this(lb.isbn,lb.titolo, lb.autore, lb.valutazione,lb.generi,lb.statoLettura);
    }

    public String generiString(){
        return String.join(", ", generi);
    }
}
