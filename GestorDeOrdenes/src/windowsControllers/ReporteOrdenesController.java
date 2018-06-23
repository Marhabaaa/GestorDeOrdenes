package windowsControllers;

import application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ReporteOrdenesController {

    @FXML private TableView table;
    @FXML private TableColumn<Orden, Integer> codColumn;
    @FXML private TableColumn<Orden, String> problemaColumn;
    @FXML private TableColumn<Orden, Integer> clienteColumn;
    @FXML private TableColumn<Orden, Integer> tecnicoColumn;
    @FXML private TableColumn<Orden, Integer> gananciaColumn;
    @FXML private TableColumn<Orden, String> entregadaColumn;
    @FXML private Button editButton;
    @FXML private Button deteleButton;

    private SST sistema;

    @FXML
    private void deleteButtonAction() {
        if(table.getSelectionModel().getSelectedItem() != null)
            sistema.removeOrder(((Orden) table.getSelectionModel().getSelectedItem()).getOrderNumber());
        table.setItems(getItems());
        table.refresh();
    }

    @FXML
    private void editButtonAction() throws Exception {
        launchEditarOrdenWindow();
        table.setItems(getItems());
        table.refresh();
    }

    public void initVariables(SST sistema) {

        this.sistema = sistema;

        codColumn.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("orderNumber"));
        problemaColumn.setCellValueFactory(new PropertyValueFactory<Orden, String>("description"));
        clienteColumn.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("clientRut"));
        tecnicoColumn.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("techNumber"));
        gananciaColumn.setCellValueFactory(new PropertyValueFactory<Orden, Integer>("price"));
        entregadaColumn.setCellValueFactory(new PropertyValueFactory<Orden, String>("entregada"));

        table.setItems(getItems());
    }


   private ObservableList<Orden> getItems() {
        ListaOrdenes list = sistema.getListaOrdenes();
        ObservableList<Orden> ordenes = FXCollections.observableArrayList();
        int i = 0;
        while(i < list.size()) {
            ordenes.add(list.get(list.size() - i - 1));
            i++;
        }

        return ordenes;
    }

    private void launchEditarOrdenWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/EditarOrden.fxml"));
        Parent root = loader.load();

        EditarOrdenController editarOrdenController = loader.getController();
        editarOrdenController.initVariables(sistema, (Orden) table.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(editButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();
    }
}
