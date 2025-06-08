package front;

import back.Stato;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import transfer.Libro;
import transfer.LibroBuilder;

import java.util.ArrayList;

public class FinestraAggiunta{
    public static Libro crea(){
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Dialog");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        TextField campoTitolo = new TextField();
        campoTitolo.setPromptText("Titolo");

        TextField campoAutore = new TextField();
        campoAutore.setPromptText("Autore");

        TextField campoValutazione = new TextField();
        campoValutazione.setPromptText("Valutazione (1-5)");

        TextField campoISBN = new TextField();
        campoISBN.setPromptText("ISBN (numero)");

        TextField campoGeneri = new TextField();
        campoGeneri.setPromptText("Generi separati da virgola");

        ComboBox<Stato> campoStato = new ComboBox<>();
        campoStato.getItems().addAll(Stato.values());
        campoStato.setValue(Stato.values()[0]);



        LibroBuilder libroBuilder = new LibroBuilder();

        Button conferma = new Button("Conferma");
        conferma.setOnAction(f -> {
            libroBuilder.isbn(Integer.parseInt(campoISBN.getText()))
                    .titolo(campoTitolo.getText())
                    .autore(campoAutore.getText())
                    .generi(traduci(campoGeneri)).build();

            if(!campoValutazione.getText().isEmpty()){
                libroBuilder.valutazione(Integer.parseInt(campoValutazione.getText()));
            }
            libroBuilder.statoLettura(campoStato.getValue());
            textInputDialog.close();
        });
        root.getChildren().addAll(campoISBN,campoTitolo,campoAutore,campoValutazione,campoGeneri,campoStato,conferma);

        textInputDialog.getDialogPane().setContent(root);
        textInputDialog.showAndWait();

        return libroBuilder.build();
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

}
