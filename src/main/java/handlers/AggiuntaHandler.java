package handlers;

import back.Facade;
import back.transfer.Libro;
import back.transfer.LibroValidator;
import front.dialogs.DialogAggiunta;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

public class AggiuntaHandler implements EventHandler<ActionEvent> {
    private final TableView<Libro> table;
    private final Facade facade;

    public AggiuntaHandler(TableView<Libro> table) {
        this.table = table;
        this.facade = new Facade();
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
