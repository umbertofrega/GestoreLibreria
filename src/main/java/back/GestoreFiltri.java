package back;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import tranfers.Libro;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class GestoreFiltri {

    private static ObjectMapper mapper = new ObjectMapper();
    private static File documento = new File(Path.of("src","main","resources","data","libri.json").toUri());
    private static GestoreFiltri instance = new GestoreFiltri();

    /*
     *  Rendo il costruttore private così che gli accessi alla
     *  classe siano regolati solo dal metodo getInstance()
     */
    private GestoreFiltri() {}


    /**
     * Permette di ricevere la lista dei libri non ordinata
     *
     * @return Una lista di libri
     */
    public List<Libro> getLibri(){
        List<Libro> libri;

        try {
            libri = mapper.readValue(documento,new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }


    public static GestoreFiltri getInstance() {
        if(instance == null){
            return new GestoreFiltri();
        }
        return instance;
    }
}
