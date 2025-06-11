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
    LibroField fields = (LibroField) aggiungiFields();

    public static void main(String[] args) {
        Dialog<Libro> dialog = new Dialog<>();
        dialog.showAndWait();
        System.out.println(dialog.getResult());
    }

    public Dialog<Libro> creaDialog(){

        dialog.setHeaderText("Aggiungi Libro");
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        aggiungiRisultato();

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Button conferma = new Button("Conferma");
        vbox.getChildren().addAll(fields.campoISBN,fields.campoTitolo,fields.campoAutore,fields.campoValutazione,fields.campoGeneri,fields.campoStato,conferma);

        dialog.getDialogPane().setContent(vbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        return dialog;
    }

    @Override
    public AbstractField aggiungiFields() {
        return new LibroField();
    }

    public void aggiungiRisultato(){
        LibroBuilder libroBuilder = new LibroBuilder();
        dialog.setResultConverter(f -> {
            if(!valuta(fields.campoISBN) || !valuta(fields.campoTitolo) || !valuta(fields.campoAutore)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.getDialogPane().getStylesheets().add(Thread.currentThread().getContextClassLoader().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setHeaderText("Attenzione!");
                alert.setContentText("Devi compilare i campi obbligatori! (Quelli con *)");
                alert.showAndWait();
            }
            else {
                libroBuilder.isbn(Integer.parseInt(fields.campoISBN.getText()))
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
}
