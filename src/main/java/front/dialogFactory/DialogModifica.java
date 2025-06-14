package front.dialogFactory;

import front.fields.InterfacciaFields;
import front.fields.LibroFields;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import transfer.Libro;
import transfer.LibroBuilder;

import static front.dialogFactory.DialogAggiunta.valutaFields;
import static front.dialogFactory.DialogAggiunta.valutaValutazione;

public class DialogModifica implements DialogFactory{
    Dialog<Libro> dialog = new Dialog<>();
    private Libro libro;
    private LibroFields fields;

    public DialogModifica(Libro libro) {
        this.libro = libro;
    }

    public Dialog<Libro> creaDialog(){
        dialog.setHeaderText("Modifica Libro");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        aggiungiRisultato();
        this.fields = (LibroFields) creaFields();
        ButtonType okButton = new ButtonType("Salva", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(fields.campoISBN,fields.campoTitolo,fields.campoAutore,fields.campoValutazione,fields.campoGeneri,fields.campoStato);

        dialog.getDialogPane().setContent(vbox);
        return dialog;
    }

    @Override
    public InterfacciaFields creaFields() {
        fields = new LibroFields();
        fields.campoISBN.setText(String.valueOf(libro.isbn()).trim());
        fields.campoTitolo.setText(libro.titolo().trim());
        fields.campoAutore.setText(libro.autore().trim());
        fields.campoValutazione.setText(String.valueOf(libro.valutazione()).trim());
        fields.campoGeneri.setText(libro.generiString().trim());
        fields.campoStato.setValue(libro.statoLettura());
        return fields;
    }

    @Override
    public void aggiungiRisultato() {
        LibroBuilder libroBuilder = new LibroBuilder();
        dialog.setResultConverter(f -> {
            if(!f.getButtonData().equals(ButtonBar.ButtonData.OK_DONE))
                return null;
            if(!valutaFields(fields)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setHeaderText("Attenzione!");
                alert.setContentText("Compila bene i campi!");
                alert.showAndWait();
            } else {
                libroBuilder.isbn(Long.parseLong(fields.campoISBN.getText()))
                        .titolo(fields.campoTitolo.getText())
                        .autore(fields.campoAutore.getText())
                        .generi(Libro.traduci(fields.campoGeneri.getText())).build();
                if (valutaValutazione(fields.campoValutazione)) {
                    libroBuilder.valutazione(Integer.parseInt(fields.campoValutazione.getText()));
                }
                libroBuilder.statoLettura(fields.campoStato.getValue());
                return libroBuilder.build();
            }
            return null;
        });
    }
}
