package front.fields;

import back.stati.Stato;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;

public class FiltroFields implements InterfacciaFields{
    public CheckComboBox<Stato> stati = new CheckComboBox<>();
    public TextField generi = new TextField();

    public FiltroFields() {
        stati.getItems().addAll(Stato.values());
        generi.setPromptText("Filtra per generi separati da virgola!");
    }
}
