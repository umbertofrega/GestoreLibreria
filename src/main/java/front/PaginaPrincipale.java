package front;

import back.Facade;
import back.stati.Ordinamento;
import front.dialogFactory.DialogAggiunta;
import front.dialogFactory.DialogFiltro;
import front.dialogFactory.DialogModifica;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import transfer.Libro;

import javax.swing.*;
import java.util.List;

public class PaginaPrincipale extends Application {
    private final Facade facade = new Facade();
    private final TableView<Libro> table = new TableView();

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("Gestore Libreria");
        table.setEditable(false);

        initTable();

        Facade facade = new Facade();

        table.getItems().addAll(facade.getLibri());

        final HBox bottoni = boxBottoni(stage);
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
            switch (cercaPer.getValue()){
                case Ordinamento.TITOLO:
                    table.getItems().clear();
                    table.getItems().addAll(facade.cerca(tabella, Ordinamento.TITOLO, barra.getText()));
                case Ordinamento.AUTORE:
                    table.getItems().clear();
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
            if(risultato!= null) {
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

        barra.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() - ((cercaPer.getMaxWidth())+(filtro.getMaxWidth())+resetFiltri.getMaxWidth()));

        HBox ricerca = new HBox();
        ricerca.setSpacing(5);

        ricerca.getChildren().addAll(barra, cercaPer, filtro, resetFiltri);
        return ricerca;
    }

    private void initTable() {
        table.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
        table.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());

        table.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        table.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());

        table.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        table.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);


        TableColumn<Libro, Long> ISBN = new TableColumn("ISBN*");
        TableColumn<Libro, String> titolo = new TableColumn("Titolo*");
        TableColumn<Libro, String> autore = new TableColumn("Autore*");
        TableColumn<Libro, Integer> valutazione = new TableColumn("Valutazione");
        TableColumn<Libro, String> generi = new TableColumn("Generi*");
        TableColumn<Libro, String> statoLettura = new TableColumn("Stato");

        ISBN.setPrefWidth(table.getMaxWidth() / 10);
        titolo.setPrefWidth(table.getMaxWidth() / 4);
        autore.setPrefWidth(table.getMaxWidth() / 5);
        valutazione.setPrefWidth(table.getMaxWidth() / 12);
        generi.setPrefWidth(table.getMaxWidth() / 4);
        statoLettura.setPrefWidth(table.getMaxWidth() / 10.5);

        titolo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().titolo()));
        autore.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().autore()));
        valutazione.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().valutazione()));
        ISBN.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isbn()));
        generi.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().generiString()));
        statoLettura.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().statoLettura().toString()));


        table.getColumns().addAll(ISBN, titolo, autore, valutazione, generi, statoLettura);
    }

    private HBox boxBottoni(Stage stage) {
        Button aggiungiLibro = new Button("Aggiungi Libro");
        Button modificaLibro = new Button("Modifica Libro");
        Button eliminaLibro = new Button("Elimina Libro");

        eliminaLibro.setOnAction(e -> {
            Libro libro = table.getSelectionModel().getSelectedItem();
            if (libro != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Conferma Eliminazione");
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setHeaderText("Confermi?");
                alert.setContentText("Sei sicuro di voler eliminare questo libro?");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    table.getItems().remove(table.getSelectionModel().getSelectedItem());
                    facade.rimuoviLibro(libro);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setContentText("Devi prima selezionare un libro!");
                alert.show();
            }
        });

        aggiungiLibro.setOnAction(e -> {
            Dialog<Libro> dialog = new DialogAggiunta().creaDialog();
            dialog.showAndWait();
            Libro aggiunta = dialog.getResult();
            if(aggiunta != null && !esiste(aggiunta)){
                table.getItems().add(aggiunta);
                facade.inserisciLibro(aggiunta);
            }
        });

        modificaLibro.setOnAction(e -> {
            Libro libroOld = table.getSelectionModel().getSelectedItem();
            if (libroOld == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setTitle("Attenzione!");
                alert.setContentText("Devi prima selezionare un libro!");
                alert.show();
            } else {
                Dialog<Libro> dialog = new DialogModifica(libroOld).creaDialog();
                dialog.showAndWait();
                Libro libroNew = dialog.getResult();
                table.getItems().remove(libroOld);

                if (libroNew == null || esiste(libroNew)) {
                    table.getItems().add(libroOld);
                } else {
                    table.getItems().add(libroNew);
                    facade.aggiornaLibro(libroOld, libroNew);
                }
            }
        });

        final HBox bottoni = new HBox();

        bottoni.getChildren().addAll(aggiungiLibro, modificaLibro, eliminaLibro);
        bottoni.setSpacing(10);

        return bottoni;
    }

    private boolean esiste(Libro aggiunta) {
        for (Libro l : table.getItems()) {
            if (l.isbn() == aggiunta.isbn()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                alert.setHeaderText("Ehi!");
                alert.setContentText("Un libro con questo codice ISBN è già esistente...");
                alert.showAndWait();
                return true;
            }
        }
        return false;
    }
}