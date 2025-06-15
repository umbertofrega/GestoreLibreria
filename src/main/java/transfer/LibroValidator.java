package transfer;

import java.util.List;

public class LibroValidator {
    public static boolean esisteISBN(Libro libro, List<Libro> libri){
        for (Libro l : libri) {
            if (l.isbn() == libro.isbn()) {
                return true;
            }
        }
        return false;
    }

    public static boolean verifica(String testo){
        return testo != null && !testo.isBlank();
    }

    public static boolean valutaValutazione(String valutazione){
        if(valutazione == null || valutazione.isBlank()) return false;
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
