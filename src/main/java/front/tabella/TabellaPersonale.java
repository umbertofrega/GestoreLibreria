package front.tabella;

import back.transfer.Libro;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Screen;

public class TabellaPersonale extends TableView<Libro> {
    public TabellaPersonale() {
        this.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());
        this.setMaxHeight(Screen.getPrimary().getVisualBounds().getHeight());

        this.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        this.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth());

        this.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
        this.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight() / 2);


        TableColumn<Libro, Long> ISBN = new TableColumn("ISBN");
        TableColumn<Libro, String> titolo = new TableColumn("Titolo");
        TableColumn<Libro, String> autore = new TableColumn("Autore");
        TableColumn<Libro, Integer> valutazione = new TableColumn("Valutazione");
        TableColumn<Libro, String> generi = new TableColumn("Generi");
        TableColumn<Libro, String> statoLettura = new TableColumn("Stato");

        ISBN.setPrefWidth(this.getMaxWidth() / 10);
        titolo.setPrefWidth(this.getMaxWidth() / 4);
        autore.setPrefWidth(this.getMaxWidth() / 5);
        valutazione.setPrefWidth(this.getMaxWidth() / 12);
        generi.setPrefWidth(this.getMaxWidth() / 4);
        statoLettura.setPrefWidth(this.getMaxWidth() / 10.5);

        titolo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().titolo()));
        autore.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().autore()));
        valutazione.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().valutazione()));
        ISBN.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isbn()));
        generi.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().generiString()));
        statoLettura.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().statoLettura().toString()));

        this.getColumns().addAll(ISBN, titolo, autore, valutazione, generi, statoLettura);
    }
}
