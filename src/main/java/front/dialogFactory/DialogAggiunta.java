package front.dialogFactory;

import front.fields.AbstractField;
import front.fields.LibroField;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import transfer.Libro;
import transfer.LibroBuilder;

import java.util.ArrayList;

public class DialogAggiunta implements DialogFactory{
    Dialog<Libro> dialog = new Dialog<>();
    LibroField fields = (LibroField) creaFields();

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
    public AbstractField creaFields() {
        return new LibroField();
    }

    @Override
    public void aggiungiRisultato(){
        LibroBuilder libroBuilder = new LibroBuilder();
        dialog.setResultConverter(f -> {
            if(!f.getButtonData().equals(ButtonBar.ButtonData.OK_DONE))
                return null;
            if(!valutaISBN(fields.campoISBN) || !valuta(fields.campoTitolo) || !valuta(fields.campoAutore) || !valuta(fields.campoGeneri)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setHeaderText("Attenzione!");
                alert.setContentText("Compila bene i campi!");
                alert.showAndWait();
            }
            else {
                libroBuilder.isbn(Long.parseLong(fields.campoISBN.getText()))
                        .titolo(fields.campoTitolo.getText())
                        .autore(fields.campoAutore.getText())
                        .generi(traduci(fields.campoGeneri)).build();

                if (!fields.campoValutazione.getText().isEmpty()) {
                    libroBuilder.valutazione(Integer.parseInt(fields.campoValutazione.getText()));
                }
                libroBuilder.statoLettura(fields.campoStato.getValue());
                return libroBuilder.build();
            }
            return null;
        });
    }

    private static ArrayList<String> traduci(TextField campo){
        ArrayList<String> generi = new ArrayList<>();
        String testo = campo.getText();
        if (testo == null || testo.isBlank()) return generi;

        String[] tokens = testo.split("[,\\s]+");

        for (String token : tokens) {
            if (!token.isBlank()) {
                generi.add(token.trim());
            }
        }
        return generi;
    }

    private static boolean valuta(TextField campo){
        String testo = campo.getText();
        if (testo == null || testo.isBlank()) return false;
        return true;
    }

    private static boolean valutaISBN(TextField campo){
        String testo = campo.getText();
        if (testo == null || testo.isBlank()) return false;
        if(testo.matches(".*[a-zA-Z].*")) return false;
        if(testo.contains(" ") || testo.contains(",") || testo.contains(".")) return false;
        if(testo.length()!=13) return false;

        return true;
    }
}
