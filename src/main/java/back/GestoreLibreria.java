package back;

import com.fasterxml.jackson.databind.ObjectMapper;
import tranfers.Libro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class GestoreLibreria {

    private ObjectMapper mapper = new ObjectMapper();
    private File documento = new File(Path.of("src","main","resources","data","libri.json").toUri());

    /**
     * Permette di salvare un nuovo libro in memoria.
     *
     *
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean inserisciLibro(Libro libro){
        try {
            mapper.writeValue(documento,Libro.class);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    /**
     * Permette di aggiornare un libro in memoria.
     *
     *
     * @param libro Un'oggetto di tipo libro
     * @return True se l'operazione è andata a buon fine <br> False altrimenti
     */
    public boolean aggiornaLibro(Libro libro){
        return inserisciLibro(libro);
    }

}
