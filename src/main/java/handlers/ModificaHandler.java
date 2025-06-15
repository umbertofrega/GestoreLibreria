package handlers;

import back.Facade;
import back.transfer.Libro;
import back.transfer.LibroValidator;
import front.dialogs.DialogModifica;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

public class ModificaHandler implements EventHandler<ActionEvent> {

    private final TableView<Libro> table;
    private final Facade facade;

    public ModificaHandler(TableView<Libro> table) {
        this.table = table;
        this.facade = new Facade();
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
