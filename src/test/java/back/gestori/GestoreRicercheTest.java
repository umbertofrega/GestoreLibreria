package back.gestori;

import back.stati.Stato;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import transfer.Libro;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestoreRicercheTest {
    GestoreRicerche gestoreRicerche;
    List<Libro> libri;

    @BeforeEach
    void setUp() {
        GestoreRicerche gestoreRicerche = GestoreRicerche.getInstance();
        List<Libro> libri = gestoreRicerche.getLibri();
    }

    @Test
    @DisplayName("La lista non dovrebbe avere valori null")
    void testGetLibri() {
        assertFalse(libri.isEmpty(), "La lista dei libri è vuota");
        for (Libro libro : libri) {
            assertNotNull(libro, "Trovato un libro null nella lista");
        }
    }

    @Test
    @DisplayName("I libri all'interno della lista filtrata dovrebbero avere tutti almeno uno degli stati richiesti")
    void testFiltraStato() {
        List<Stato> stati = List.of(Stato.DA_LEGGERE);
        List<Libro> filtrati = gestoreRicerche.filtraStato(libri, stati);

        assertFalse(filtrati.isEmpty(), "Nessun libro filtrato");
        for (Libro libro : filtrati) {
            assertTrue(stati.contains(libro.statoLettura()), "Libro con stato sbagliato"+ libro);
        }
    }

    @Test
    @DisplayName("I libri all'interno della lista filtrata dovrebbero avere almeno uno dei generi richiesti")
    void testFiltraGenere(){
        List<String> generiRichiesti = List.of("Romanzo", "Fantascientifico");
        List<Libro> filtrati = gestoreRicerche.filtraGeneri(libri, generiRichiesti);

        assertFalse(filtrati.isEmpty(), "Nessun libro filtrato");

        for (Libro libro : filtrati) {
            List<String> genere = libro.generi();
            boolean almenoUno = false;
            for (String g : genere) {
                if (generiRichiesti.contains(g)) {
                    almenoUno = true;
                    break;
                }
            }
            assertTrue(almenoUno, "Libro con generi sbagliati: ");
        }
    }


    @Test
    @DisplayName("I libri all'interno della lista filtrata dovrebbero avere tutti l'autore richiesto")
    void cercaPerAutore() {
        List<Libro> filtrati = gestoreRicerche.cercaPerAutore(libri,"Tolkien");

        assertFalse(filtrati.isEmpty(), "Nessun libro filtrato");
        for (Libro libro : filtrati) {
            assertEquals("Tolkien", libro.autore(), "Libro con autore errato"+ libro);
        }
    }

    @Test
    @DisplayName("I libri all'interno della lista filtrata dovrebbero avere tutti il titolo richiesto")
    void cercaPerTitolo() {
        List<Libro> filtrati = gestoreRicerche.cercaPerTitolo(libri,"Dune");

        assertFalse(filtrati.isEmpty(), "Nessun libro filtrato");
        for (Libro libro : filtrati) {
            assertEquals("Dune", libro.titolo(), "Libro con titolo errato" + libro);
        }
    }

}