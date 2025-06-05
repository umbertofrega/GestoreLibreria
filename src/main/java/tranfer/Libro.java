package tranfer;

import back.Stato;

import java.util.List;

public record Libro(
        int isbn,
        String titolo,
        String autore,
        int valutazione,
        List<String> generi,
        Stato statoLettura
) { }
