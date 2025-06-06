package front;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class paginaPrincipale extends Application {
    private TableView table = new TableView();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("PaginaPrincipale");
        table.setEditable(false);
        TableColumn ISBN = new TableColumn("ISBN");
        TableColumn Titolo = new TableColumn("Titolo");
        TableColumn Autore = new TableColumn("Autore");
        TableColumn valutazione = new TableColumn("Valutazione");
        TableColumn generi = new TableColumn("Generi");
        TableColumn statoLettura = new TableColumn("Stato");

        table.getColumns().addAll(ISBN, Titolo, Autore, valutazione, generi, statoLettura);

        Label label = new Label("PaginaPrincipale");

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}