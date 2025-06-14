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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        alert.setTitle("Attenzione!");
        alert.setHeaderText("Attenzione!");
        dialog.setResultConverter(f -> {
            if(!f.getButtonData().equals(ButtonBar.ButtonData.OK_DONE))
                return null;
            if(!valutaISBN(fields.campoISBN) || !verifica(fields.campoTitolo) || !verifica(fields.campoAutore) || !verifica(fields.campoGeneri)){
                alert.setContentText("Compila bene i campi!");
                alert.showAndWait();
            }
            else {
                libroBuilder.isbn(Long.parseLong(fields.campoISBN.getText().trim()))
                        .titolo(fields.campoTitolo.getText().trim())
                        .autore(fields.campoAutore.getText().trim())
                        .generi(Libro.traduci(fields.campoGeneri.getText().trim())).build();

                if (!valutaValutazione(fields.campoValutazione)) {
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
        if (testo == null || testo.isBlank()) return false;
        return true;
    }

    static boolean valutaValutazione(TextField campo){
        String testo = campo.getText();
        if (testo == null || testo.isBlank()) return false;
        testo=testo.trim();
        int voto = Integer.parseInt(testo);
        if(voto > 10 || voto < 0) return false;
        return true;
    }

    static boolean valutaISBN(TextField campo){
        String testo = campo.getText().trim();
        if (testo == null || testo.isBlank()) return false;
        if(testo.matches(".*[a-zA-Z].*")) return false;
        if(testo.contains(" ") || testo.contains(",") || testo.contains(".")) return false;
        if(testo.length()!=13) return false;
        return true;
    }
}
