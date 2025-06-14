package front.dialogs;

import back.Facade;
import back.transfer.Libro;
import front.fields.FiltroFields;
import front.fields.InterfacciaFields;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

import java.util.List;

public class DialogFiltro implements DialogInterface {
    Dialog<List<Libro>> dialog = new Dialog<>();
    FiltroFields fields = new FiltroFields();
    List<Libro> libri;

    public DialogFiltro(List<Libro> libri){
        this.libri =  libri;
    }

    @Override
    public Dialog<List<Libro>> creaDialog() {
        dialog.setHeaderText("Scegli come filtrare!");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        dialog.setWidth(fields.generi.getMaxWidth());
        aggiungiRisultato();

        ButtonType okButton = new ButtonType("Filtra", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(fields.stati,fields.generi);

        dialog.getDialogPane().setContent(vbox);

        return dialog;
    }

    @Override
    public InterfacciaFields creaFields() {
        return new FiltroFields();
    }

    @Override
    public void aggiungiRisultato() {
        dialog.setResultConverter(r -> {
            List<Libro> result = null;
            if (r.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                Facade facade = new Facade();

                if(fields.generi.getText().isEmpty() || fields.generi == null)
                    result = facade.filtraPerStato(libri,fields.stati.getCheckModel().getCheckedItems());
                else if (fields.stati.getCheckModel().getCheckedItems().isEmpty())
                    result = facade.filtraPerGeneri(libri, Libro.traduci(fields.generi.getText()));
                else{
                    result = facade.filtraPerGeneri(libri, Libro.traduci(fields.generi.getText()));
                    result = facade.filtraPerStato(result,fields.stati.getCheckModel().getCheckedItems());
                }
            }
            return result;
        });
    }
}
