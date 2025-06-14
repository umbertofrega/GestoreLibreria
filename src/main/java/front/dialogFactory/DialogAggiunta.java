package front.dialogFactory;


import front.fields.InterfacciaFields;
import front.fields.LibroFields;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import transfer.Libro;
import transfer.LibroBuilder;

public class DialogAggiunta implements DialogFactory{
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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setHeaderText("Ehi...");
                alert.setContentText("Compila bene i campi");
                alert.showAndWait();
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

     static boolean verifica(TextField campo){
        String testo = campo.getText();
         return testo != null && !testo.isBlank();
     }

    static boolean valutaValutazione(TextField campo){
        if(campo.getText() == null || campo.getText().isBlank()) return true;
        String testo = campo.getText().trim();
        int voto = Integer.parseInt(testo);
        return voto <= 10 && voto >= 0;
    }

    static boolean valutaISBN(TextField campo) {
        if(campo.getText() == null || campo.getText().isBlank()) return true;
        String testo = campo.getText().trim();
        if(testo.matches(".*[a-zA-Z].*")) return false;
        if(testo.contains(" ") || testo.contains(",") || testo.contains(".")) return false;
        if(testo.length()!=13) return false;
        return true;
    }

    static boolean valutaFields(LibroFields fields){
        return  valutaISBN(fields.campoISBN) &&
                verifica(fields.campoTitolo) &&
                verifica(fields.campoAutore) &&
                verifica(fields.campoGeneri) &&
                valutaValutazione(fields.campoValutazione);
    }
}
