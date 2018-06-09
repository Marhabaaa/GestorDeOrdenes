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

import java.sql.SQLException;

public class ReporteStockController {

    @FXML private TableView table;
    @FXML private TableColumn<Pieza, Integer> cod;
    @FXML private TableColumn<Pieza, String> descripcion;
    @FXML private TableColumn<Pieza, Integer> cantidad;
    @FXML private TableColumn<Pieza, Integer> complejidad;
    @FXML private TableColumn<Pieza, Integer> precio;

    @FXML private Button newPartButton;
    @FXML private Button editButton;
    @FXML private Button deteleButton;

    private SST sistema;

    public void initVariables(SST sistema) {

        this.sistema = sistema;

        cod.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("code"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Pieza, String>("description"));
        cantidad.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("cant"));
        complejidad.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("complex"));
        precio.setCellValueFactory(new PropertyValueFactory<Pieza, Integer>("price"));

        table.setItems(getItems());
    }

    private ObservableList<Pieza> getItems() {
        ListaPiezas list = sistema.getListaPiezas();
        ObservableList<Pieza> piezas = FXCollections.observableArrayList();
        int i = 0;
        while(i < list.size()) {
            piezas.add(list.get(list.size() - i - 1));
            i++;
        }

        return piezas;
    }

    @FXML
    private void newPartButtonAction() {
        try {
            launchCrearPieza();
            table.setItems(getItems());
            table.refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteButtonAction() {
        if(table.getSelectionModel().getSelectedItem() != null){
            try {
                sistema.removePart(((Pieza) table.getSelectionModel().getSelectedItem()).getCode());
                table.setItems(getItems());
                table.refresh();
                System.out.println("Se ha eliminado la pieza con exito");
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void launchCrearPieza() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/CrearPieza.fxml"));
        Parent root = loader.load();

        NuevaPiezaController nuevaPiezaController = loader.getController();
        nuevaPiezaController.initVariables(sistema);

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(newPartButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

    }

}
