package front.dialogs;


import back.transfer.Libro;
import back.transfer.LibroBuilder;
import back.transfer.LibroValidator;
import front.fields.InterfacciaFields;
import front.fields.LibroFields;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class DialogAggiunta implements DialogInterface {
    Dialog<Libro> dialog = new Dialog<>();
    LibroFields fields = (LibroFields) creaFields();

    public Dialog<Libro> creaDialog(){
        dialog.setHeaderText("Aggiungi Libro");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        aggiungiRisultato();

        ButtonType okButton = new ButtonType("Crea", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(fields.campoISBN,fields.campoTitolo,fields.campoAutore,fields.campoValutazione,fields.campoGeneri,fields.campoStato);

        dialog.getDialogPane().setContent(vbox);
        return dialog;
    }

    @Override
    public InterfacciaFields creaFields() {
        return new LibroFields();
    }

    @Override
    public void aggiungiRisultato(){
        LibroBuilder libroBuilder = new LibroBuilder();
        dialog.setResultConverter(f -> {
            if(!f.getButtonData().equals(ButtonBar.ButtonData.OK_DONE))
                return null;
            if(!valutaFields(fields)){
                String contenuto = "Due libri con lo stesso ISBN non possono esistere oppure devi riempire i campi con l'asterisco!";
                new AlertPersonale(Alert.AlertType.ERROR,"Attenzione!","Compila bene",contenuto).showAndWait();
            }
            else {
                libroBuilder.isbn(Long.parseLong(fields.campoISBN.getText().trim()))
                        .titolo(fields.campoTitolo.getText().trim())
                        .autore(fields.campoAutore.getText().trim())
                        .generi(Libro.traduci(fields.campoGeneri.getText().trim())).build();
                if (fields.campoValutazione != null && !fields.campoValutazione.getText().isBlank()) {
                    libroBuilder.valutazione(Integer.parseInt(fields.campoValutazione.getText().trim()));
                }
                libroBuilder.statoLettura(fields.campoStato.getValue());
                return libroBuilder.build();
            }
            return null;
        });
    }

    private static boolean verifica(TextField campo){
        return LibroValidator.verifica(campo.getText());
     }

    private static boolean valutaValutazione(TextField campo){
        return LibroValidator.valutaValutazione(campo.getText());
    }

    private static boolean valutaISBN(TextField campo) {
        return LibroValidator.valutaISBN(campo.getText()) &&
               !LibroValidator.esisteISBN(campo.getText());
    }

    private static boolean valutaFields(LibroFields fields){
        return  valutaISBN(fields.campoISBN) &&
                verifica(fields.campoTitolo) &&
                verifica(fields.campoAutore) &&
                verifica(fields.campoGeneri) &&
                valutaValutazione(fields.campoValutazione);
    }
}
