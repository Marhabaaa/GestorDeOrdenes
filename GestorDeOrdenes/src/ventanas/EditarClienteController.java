package ventanas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class EditarClienteController {

    @FXML private TextField rutField;
    @FXML private TextField nameField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private RadioButton isBusinessButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
}
