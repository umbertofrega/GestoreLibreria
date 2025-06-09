package front;

import back.Facade;
import back.Ordinamento;
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

public class paginaPrincipale extends Application {
    private TableView<Libro> table = new TableView();
    private final Facade facade = new Facade();

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
        vbox.setPadding(new Insets(15, 0, 0, 10));
        vbox.getChildren().addAll(label,ricerca,bottoni, table);
        VBox.setVgrow(table, Priority.ALWAYS);

        root.getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private HBox boxRicerca() {
        ComboBox<Ordinamento> filtro = new ComboBox<>();
        filtro.getItems().addAll(Ordinamento.values());
        filtro.setValue(Ordinamento.TITOLO);

        TextField barra = new TextField();
        barra.setEditable(true);
        barra.setOnAction(event -> {
            KeyStroke.getKeyStroke("ENTER");
            table.getItems().clear();
            table.getItems().addAll(facade.cerca(filtro.getValue(), barra.getText()));
        });

        barra.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());

        HBox ricerca = new HBox();
        ricerca.setSpacing(5);

        ricerca.getChildren().addAll(barra, filtro);
        return ricerca;
    }

    private void initTable() {

        table.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
        table.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());

        table.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth()/2);
        table.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight()/2);


        TableColumn<Libro,Integer> ISBN = new TableColumn("ISBN");
        TableColumn<Libro,String> titolo = new TableColumn("Titolo");
        TableColumn<Libro,String> autore = new TableColumn("Autore");
        TableColumn<Libro,Integer> valutazione = new TableColumn("Valutazione");
        TableColumn<Libro, String> generi = new TableColumn("Generi");
        TableColumn<Libro, String> statoLettura = new TableColumn("Stato");

        ISBN.setPrefWidth(table.getMaxWidth()/10);
        titolo.setPrefWidth(table.getMaxWidth()/4);
        autore.setPrefWidth(table.getMaxWidth()/5);
        valutazione.setPrefWidth(table.getMaxWidth()/12);
        generi.setPrefWidth(table.getMaxWidth()/4);
        statoLettura.setPrefWidth(table.getMaxWidth()/9.2);



        titolo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().titolo()));
        autore.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().autore()));
        valutazione.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().valutazione()));
        ISBN.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isbn()));
        generi.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().generi().toString()));
        statoLettura.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().statoLettura().toString()));


        table.getColumns().addAll(ISBN, titolo, autore, valutazione, generi, statoLettura);


    }

    private HBox boxBottoni(Stage stage) {
        Button aggiungiLibro = new Button("Aggiungi Libro");
        Button modificaLibro = new Button("Modifica Libro");
        Button eliminaLibro = new Button("Elimina Libro");

        eliminaLibro.setOnAction(e -> {
            Libro libro = table.getSelectionModel().getSelectedItem();
            if(libro != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Conferma Eliminazione");
            alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            alert.setHeaderText("Confermi?");
            alert.setContentText("Sei sicuro di voler eliminare questo libro?");
            alert.showAndWait();
            if(alert.getResult() == ButtonType.OK) {
                table.getItems().remove(table.getSelectionModel().getSelectedItem());
                facade.rimuoviLibro(libro);
            }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

                alert.setContentText("Devi prima selezionare un libro!");
                alert.show();
            }
        });

        aggiungiLibro.setOnAction(e -> {
            Libro libro = FinestraAggiunta.crea();
            facade.inserisciLibro(libro);
            table.getItems().add(libro);
        });

        modificaLibro.setOnAction(e -> {
            Libro libroNew = FinestraAggiunta.crea();
            Libro libroOld = table.getSelectionModel().getSelectedItem();
            //facade.aggiornaLibro(libroOld, libroNew);
            System.out.println("Fatto");
        });

        final HBox bottoni = new HBox();

        bottoni.getChildren().addAll(aggiungiLibro, modificaLibro, eliminaLibro);
        bottoni.setSpacing(10);

        return bottoni;
    }

    public static void main(String[] args) {
        launch();
    }

}