package front.bottoni;

import back.Facade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import transfer.Libro;

public class EliminaLibroHandler implements EventHandler<ActionEvent> {

    private final TableView<Libro> table;
    private final Facade facade;

    public EliminaLibroHandler(TableView<Libro> table, Facade facade) {
        this.table = table;
        this.facade = facade;
    }

    @Override
    public void handle(ActionEvent event) {
        Libro libro = table.getSelectionModel().getSelectedItem();

        if (libro == null) {
            mostraAlert("Attenzione!", "Ehi...", "Devi prima selezionare un libro!");
            return;
        }

        Alert conferma = new Alert(Alert.AlertType.CONFIRMATION);
        conferma.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        conferma.setTitle("Conferma Eliminazione");
        conferma.setHeaderText("Confermi?");
        conferma.setContentText("Sei sicuro di voler eliminare questo libro?");

        conferma.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                table.getItems().remove(libro);

                if (!facade.rimuoviLibro(libro)) {
                    mostraAlert("Ehm", "Scusa!", "C'è stato un'errore nell'eliminazione del libro");
                }
            }
        });
    }

    private void mostraAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }
}
