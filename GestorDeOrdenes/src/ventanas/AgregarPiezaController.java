package ventanas;

import application.ListaPiezas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AgregarPiezaController {

    @FXML private ScrollPane scrollPane;
    @FXML private Button addButton;
    @FXML private Button cancelButton;
    @FXML private Button nextButton;

    private ListaPiezas partsList;

    @FXML
    private void addButtonAction() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PiezasEnStock.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(addButton.getScene().getWindow());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.resizableProperty().setValue(false);
        stage.showAndWait();

    }

    @FXML
    private void cancelButtonAction() {
        //flag = false;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void nextButtonAction() {

    }

    private void initVariables() {
        partsList = new ListaPiezas();
    }
}
