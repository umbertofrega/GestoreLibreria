package back.gestori;

import back.transfer.Libro;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public abstract class Gestore {
     static final ObjectMapper mapper = new ObjectMapper();
     static final File documento = apriJSON();

    private static File apriJSON(){
        File file = new File("libri.json");
        if (file.exists()) {
            return file;
        }

        file = new File("src/main/resources/data/libri.json");
        if (file.exists()) {
            return file;
        }
        throw new RuntimeException("File JSON non trovato! Dovrebbe essere in data/resources");
    }

    /**
     * Permette di ricevere la lista dei libri non ordinata
     *
     * @return Una lista di libri
     */
    public static List<Libro> getLibri(){
        List<Libro> libri;

        try {
            libri = mapper.readValue(documento,new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }
}
