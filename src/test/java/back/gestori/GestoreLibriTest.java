package back.gestori;

import back.stati.Stato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import transfer.Libro;
import transfer.LibroBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestoreLibriTest {
    GestoreLibri gestoreLibri;
    List<Libro> libri;

    @BeforeEach
    void setUp() {
        gestoreLibri = GestoreLibri.getInstance();
        libri = GestoreRicerche.getLibri();
    }

    @Test
    @DisplayName("Un libro inserito deve essere realmente all'interno della memoria")
    void testInserisciLibro() {

        Libro libro = new LibroBuilder()
                .isbn(9788804670000L)
                .titolo("Il Nome del Vento")
                .autore("Patrick Rothfuss")
                .generi(List.of("Fantasy", "Avventura"))
                .statoLettura(Stato.DA_LEGGERE)
                .build();

        assertFalse(libri.contains(libro),"Il libro è già presente");

        gestoreLibri.inserisciLibro(libro);
        libri = GestoreRicerche.getLibri();
        assertTrue(libri.contains(libro), "Il libro "+ libro+" che si sta cercando di inserire non è presente");
    }

    @Test
    void testRimuoviLibro() {
        Libro libro = libri.getFirst();

        gestoreLibri.rimuoviLibro(libro);
        libri = GestoreRicerche.getLibri();
        assertFalse(libri.contains(libro), "Il libro " + libro+" che si sta cercando di rimuovere è presente");
    }

    @Test
    void testAggiornaLibro() {
        Libro libroOld = libri.getFirst();
        Libro libroNew = new LibroBuilder().
                        isbn(libroOld.isbn()).
                        titolo(libroOld.titolo()).
                        valutazione(libroOld.valutazione()-1).
                        generi(libroOld.generi()).
                        autore(libroOld.autore())
                        .statoLettura(libroOld.statoLettura()).build();
        gestoreLibri.aggiornaLibro(libroOld,libroNew);
        libri = GestoreRicerche.getLibri();

        assertFalse(libri.contains(libroOld), "Il vecchio stato del libro è ancora presente");
        assertTrue(libri.contains(libroNew), "Il nuovo stato del libro non è presente");

    }

}