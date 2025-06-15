package front.bottoni;

import back.Facade;
import front.dialogs.DialogModifica;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;
import transfer.Libro;
import transfer.LibroValidator;

public class BottoneModificaHandler implements EventHandler<ActionEvent> {

    private final TableView<Libro> table;
    private final Facade facade;

    public BottoneModificaHandler(TableView<Libro> table, Facade facade) {
        this.table = table;
        this.facade = facade;
    }

    @Override
    public void handle(ActionEvent event) {
        Libro selected = table.getSelectionModel().getSelectedItem();

        if (selected == null) {
            mostraAlert("Attenzione!", "Devi prima selezionare un libro!");
            return;
        }

        Libro libroOld = selected.clone();

        Dialog<Libro> dialog = new DialogModifica(libroOld).creaDialog();
        dialog.showAndWait();

        Libro libroNew = dialog.getResult();

        table.getItems().remove(libroOld);

        if (libroNew == null || LibroValidator.esisteISBN(libroNew,table.getItems())) {
            table.getItems().add(libroOld);
        } else {
            table.getItems().add(libroNew);
            if (!facade.aggiornaLibro(libroOld, libroNew)) {
                mostraAlert("Ehm", "C'è stato un'errore nell'aggiornamento del libro");
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
