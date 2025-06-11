package front.fields;

import back.Stato;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LibroFields implements InterfacciaFields {
    public final TextField campoTitolo = new TextField();
    public final TextField campoAutore = new TextField();
    public final TextField campoValutazione = new TextField();
    public final TextField campoISBN = new TextField();
    public final TextField campoGeneri = new TextField();
    public final ComboBox<Stato> campoStato = new ComboBox<>();


    public LibroFields(){
        campoTitolo.setPromptText("Titolo*");
        campoAutore.setPromptText("Autore*");
        campoValutazione.setPromptText("Valutazione (1-10)");
        campoISBN.setPromptText("ISBN (numero)*");
        campoGeneri.setPromptText("Generi separati da virgola");
        campoStato.getItems().addAll(Stato.values());
        campoStato.setValue(Stato.values()[0]);
    }
}
