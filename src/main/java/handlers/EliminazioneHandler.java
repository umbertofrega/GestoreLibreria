package handlers;

import back.Facade;
import back.transfer.Libro;
import front.dialogs.AlertPersonale;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;

public class EliminazioneHandler implements EventHandler<ActionEvent> {

    private final TableView<Libro> table;
    private final Facade facade;

    public EliminazioneHandler(TableView<Libro> table) {
        this.table = table;
        this.facade = new Facade();
    }

    @Override
    public void handle(ActionEvent event) {
        Libro libro = table.getSelectionModel().getSelectedItem();
        if (libro == null) {
            new AlertPersonale(Alert.AlertType.CONFIRMATION,"Attenzione!", "Ehi...", "Devi prima selezionare un libro!").showAndWait();
            return;
        }

        Alert conferma = new AlertPersonale(Alert.AlertType.CONFIRMATION,"Conferma Eliminazione","Confermi?","Sei sicuro di voler eliminare questo libro?");
        conferma.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) {
                table.getItems().remove(libro);
                if (!facade.rimuoviLibro(libro)) {
                    new AlertPersonale(Alert.AlertType.ERROR,"Ehm", "Scusa!", "C'è stato un'errore nell'eliminazione del libro").showAndWait();
                }
            }
        });
    }
}
