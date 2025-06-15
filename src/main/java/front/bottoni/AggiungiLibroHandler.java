package front.bottoni;

import back.Facade;
import front.dialogs.DialogAggiunta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import transfer.Libro;
import transfer.LibroValidator;

public class AggiungiLibroHandler implements EventHandler<ActionEvent> {

    private final TableView<Libro> table;
    private final Facade facade;

    public AggiungiLibroHandler(TableView<Libro> table, Facade facade) {
        this.table = table;
        this.facade = facade;
    }

    @Override
    public void handle(ActionEvent event) {
        Dialog<Libro> dialog = new DialogAggiunta().creaDialog();
        dialog.showAndWait();
        Libro aggiunta = dialog.getResult();

        if (aggiunta != null && !LibroValidator.esisteISBN(aggiunta,table.getItems())) {
            table.getItems().add(aggiunta);

            if (!facade.inserisciLibro(aggiunta)) {
                mostraAlert("Ehm", "C'è stato un'errore nell'inserimento del libro");
            }
        }
    }

    private void mostraAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.show();
    }
}
