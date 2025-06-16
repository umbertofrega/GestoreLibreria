package front;

import back.Facade;
import back.stati.Ordinamento;
import back.transfer.Libro;
import front.dialogs.DialogFiltro;
import front.tabella.TabellaPersonale;
import handlers.AggiuntaHandler;
import handlers.EliminazioneHandler;
import handlers.ModificaHandler;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;

public class PaginaPrincipale extends Application {
    private final Facade facade = new Facade();
    private TableView<Libro> table;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Gestore Libreria");

        this.table = new TabellaPersonale();
        table.getItems().addAll(facade.getLibri());

        final HBox bottoni = boxBottoni();
        Label label = new Label("Gestore Libreria");

        HBox ricerca = boxRicerca();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(15, 15, 10, 10));
        vbox.getChildren().addAll(label, ricerca, bottoni, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private HBox boxRicerca() {
        ComboBox<Ordinamento> cercaPer = new ComboBox<>();
        cercaPer.getItems().addAll(Ordinamento.values());
        cercaPer.setValue(Ordinamento.TITOLO);
        cercaPer.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() / 15);


        TextField barra = new TextField();
        barra.setEditable(true);
        barra.setOnAction(event -> {
            KeyStroke.getKeyStroke("ENTER");
            List<Libro> tabella = List.copyOf(table.getItems());
            table.getItems().clear();
            switch (cercaPer.getValue()) {
                case Ordinamento.TITOLO:
                    table.getItems().addAll(facade.cerca(tabella, Ordinamento.TITOLO, barra.getText()));
                case Ordinamento.AUTORE:
                    table.getItems().addAll(facade.cerca(tabella, Ordinamento.AUTORE, barra.getText()));
            }
        });

        Button filtro = new Button();
        filtro.setText("Filtra");
        filtro.setOnAction(e -> {
            List<Libro> tabella = List.copyOf(table.getItems());
            Dialog<List<Libro>> dialogFiltro = new DialogFiltro(tabella).creaDialog();
            dialogFiltro.showAndWait();
            List<Libro> risultato = dialogFiltro.getResult();
            if (risultato != null) {
                table.getItems().clear();
                table.getItems().addAll(dialogFiltro.getResult());
            }
        });

        Button resetFiltri = new Button();
        resetFiltri.setText("Resetta ricerca");
        resetFiltri.setOnAction(e -> {
            table.getItems().clear();
            table.getItems().addAll(facade.getLibri());
        });

        barra.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);

        barra.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() - ((cercaPer.getMaxWidth()) + (filtro.getMaxWidth()) + resetFiltri.getMaxWidth()));

        HBox ricerca = new HBox();
        ricerca.setSpacing(5);

        ricerca.getChildren().addAll(barra, cercaPer, filtro, resetFiltri);
        return ricerca;
    }

    private HBox boxBottoni() {
        Button aggiungiLibro = new Button("Aggiungi Libro");
        Button modificaLibro = new Button("Modifica Libro");
        Button eliminaLibro = new Button("Elimina Libro");

        eliminaLibro.setOnAction(new EliminazioneHandler(table));

        aggiungiLibro.setOnAction(new AggiuntaHandler(table));

        modificaLibro.setOnAction(new ModificaHandler(table));

        final HBox bottoni = new HBox();

        bottoni.getChildren().addAll(aggiungiLibro, modificaLibro, eliminaLibro);
        bottoni.setSpacing(10);

        return bottoni;
    }
}