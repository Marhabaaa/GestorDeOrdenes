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
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button nextButton;

    private ListaPiezas partsList;

    @FXML
    private void addButtonAction() throws Exception {


    }

    @FXML
    private void deleteButtonAction() throws Exception{

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