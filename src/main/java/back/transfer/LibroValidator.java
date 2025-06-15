package back.transfer;

import back.Facade;

import java.util.List;

public class LibroValidator {
    public static boolean esisteISBN(Libro libro){
        return esisteISBN(libro.isbn());
    }

    public static boolean esisteISBN(long ISBN){
        List<Libro> libri = new Facade().getLibri();
        for (Libro l : libri) {
            if (l.isbn() == ISBN) {
                return true;
            }
        }
        return false;
    }

    public static boolean esisteISBN(String ISBN){
        return esisteISBN(Long.parseLong(ISBN));
    }

    public static boolean verifica(String testo){
        return testo != null && !testo.isBlank();
    }

    public static boolean valutaValutazione(String valutazione){
        if(valutazione == null || valutazione.isBlank()) return true;
        int voto = Integer.parseInt(valutazione.trim());
        return voto <= 10 && voto >= 0;
    }

    public static boolean valutaISBN(String ISBN) {
        if(ISBN == null || ISBN.isBlank()) return false;
        String testo = ISBN.trim();
        if(testo.matches(".*[a-zA-Z].*")) return false;
        if(testo.contains(" ") || testo.contains(",") || testo.contains(".")) return false;
        if(testo.length()!=13) return false;
        return true;
    }

}
