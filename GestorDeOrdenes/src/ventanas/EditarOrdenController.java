package ventanas;

import application.Pieza;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class EditarOrdenController {

    @FXML private TableView tableA;
    @FXML private TableColumn<Pieza, Integer> codA;
    @FXML private TableColumn<Pieza, String> descriptionA;
    @FXML private TableColumn<Pieza, Integer> cantA;

    @FXML private TableView tableB;
    @FXML private TableColumn<Pieza, Integer> codB;
    @FXML private TableColumn<Pieza, String> descriptionB;
    @FXML private TableColumn<Pieza, Integer> cantB;

    @FXML private Button addButton;
    @FXML private Button deleteButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    @FXML private TextField technicianField;


}
