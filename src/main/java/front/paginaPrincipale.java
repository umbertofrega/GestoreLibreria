package front;

import back.Facade;
import back.Stato;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import transfer.Libro;

import java.util.List;

public class paginaPrincipale extends Application {
    private TableView<Libro> table = new TableView();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("PaginaPrincipale");
        table.setEditable(false);

        initTable();

        Facade facade = new Facade();

        table.getItems().addAll(facade.getLibri());

        Label label = new Label("PaginaPrincipale");

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    private void initTable() {
        TableColumn<Libro,Integer> ISBN = new TableColumn("ISBN");
        TableColumn<Libro,String> titolo = new TableColumn("Titolo");
        TableColumn<Libro,String> autore = new TableColumn("Autore");
        TableColumn<Libro,Integer> valutazione = new TableColumn("Valutazione");
        TableColumn<Libro, List<String>> generi = new TableColumn("Generi");
        TableColumn<Libro, Stato> statoLettura = new TableColumn("Stato");

        titolo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().titolo()));
        autore.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().autore()));
        valutazione.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().valutazione()));
        ISBN.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isbn()));
        generi.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().generi()));
        statoLettura.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().statoLettura()));

        table.getColumns().addAll(ISBN, titolo, autore, valutazione, generi, statoLettura);
    }


    public static void main(String[] args) {
        launch();
    }

}