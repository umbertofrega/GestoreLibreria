package back.gestori;

import back.stati.Stato;
import back.transfer.Libro;
import back.transfer.LibroBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestoreLibriTest {
    GestoreLibri gestoreLibri;
    List<Libro> libri;
    Libro libroTest;

    @BeforeEach
    void setUp() {
        gestoreLibri = GestoreLibri.getInstance();
        libroTest = new LibroBuilder()
                .isbn(100000000001L).titolo("Test").autore("Autore").generi(List.of("Genere"))
                .statoLettura(Stato.DA_LEGGERE).build();
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

        assertFalse(libri.contains(libroTest),"Il libro è già presente");

        gestoreLibri.inserisciLibro(libroTest);
        libri = GestoreRicerche.getLibri();
        assertTrue(libri.contains(libroTest), "Il libro "+ libroTest +" che si sta cercando di inserire non è presente");
    }

    @Test
    void testRimuoviLibro() {
        gestoreLibri.rimuoviLibro(libroTest);
        libri = GestoreRicerche.getLibri();
        assertFalse(libri.contains(libroTest), "Il libro " + libroTest +" che si sta cercando di rimuovere è presente");
    }

    @Test
    void testAggiornaLibro() {
        Libro libroOld = libroTest;
        Libro libroNew = new LibroBuilder().
                        isbn(libroOld.isbn()).
                        titolo(libroOld.titolo()).
                        valutazione(libroOld.valutazione()).
                        generi(libroOld.generi()).
                        autore(libroOld.autore())
                        .statoLettura(Stato.LETTO).build();
        gestoreLibri.aggiornaLibro(libroOld,libroNew);

        libri = GestoreRicerche.getLibri();

        assertFalse(libri.contains(libroOld), "Il vecchio stato del libro è ancora presente");
        assertTrue(libri.contains(libroNew), "Il nuovo stato del libro non è presente");
    }

}