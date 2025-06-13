package transfer;

import back.stati.Stato;

import java.util.ArrayList;
import java.util.List;

public record Libro(
        long isbn,
        String titolo,
        String autore,
        int valutazione,
        List<String> generi,
        Stato statoLettura
) {
    public Libro(LibroBuilder lb){
        this(lb.isbn,lb.titolo, lb.autore, lb.valutazione,lb.generi,lb.statoLettura);
    }

    public String generiString(){
        return String.join(", ", generi);
    }

   public static ArrayList<String> traduci(String testo){
        ArrayList<String> generi = new ArrayList<>();
        if (testo == null || testo.isBlank()) return generi;

        String[] tokens = testo.split("[,]+");

        for (String token : tokens) {
            if (!token.isBlank()) {
                generi.add(token.trim());
            }
        }
        return generi;
   }
}
