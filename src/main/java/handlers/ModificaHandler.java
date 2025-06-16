package handlers;

import back.Facade;
import back.transfer.Libro;
import back.transfer.LibroValidator;
import front.dialogs.AlertPersonale;
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
        Libro selezione = table.getSelectionModel().getSelectedItem();

        if (selezione == null) {
            new AlertPersonale(Alert.AlertType.INFORMATION,"Attenzione!","Ehi","Devi prima selezionare un libro!").showAndWait();
            return;
        }

        Libro libroOld = selezione.clone();

        Dialog<Libro> dialog = new DialogModifica(libroOld,table).creaDialog();
        dialog.showAndWait();

        Libro libroNew = dialog.getResult();

        if(libroNew == null) {
            return;
        }

        table.getItems().remove(libroOld);

        if (LibroValidator.esisteISBN(libroNew.isbn(),table.getItems())) {
            String content = "Questo ISBN è già usato da un'altro libro";
            new AlertPersonale(Alert.AlertType.INFORMATION,"Attenzione!","L'ISBN",content).showAndWait();
            table.getItems().clear();
            table.getItems().addAll(facade.getLibri());
        } else {
            if (!facade.aggiornaLibro(libroOld, libroNew)) {
                new AlertPersonale(Alert.AlertType.ERROR,"Ehm","Scusa...","C'è stato un'errore nell'aggiornamento del libro");
                return;
            }
            table.getItems().clear();
            table.getItems().addAll(facade.getLibri());
        }
    }

}
