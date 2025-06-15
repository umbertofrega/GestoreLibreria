package front.dialogs;

import javafx.scene.control.Alert;

public class AlertPersonale extends Alert{

    public AlertPersonale(Alert.AlertType tipo, String titolo, String header, String contenuto) {
        super(tipo);
        mostraAlert(titolo,header,contenuto);
    }

    public void mostraAlert(String titolo, String header, String contenuto) {
        this.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        this.setTitle(titolo);
        this.setHeaderText(header);
        this.setContentText(contenuto);
    }
}
