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

    public static List<Libro> getLibri(){
        List<Libro> libri = new ArrayList<>();

        try {
            libri = mapper.readValue(documento,new TypeReference<List<Libro>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return libri;
    }





}
