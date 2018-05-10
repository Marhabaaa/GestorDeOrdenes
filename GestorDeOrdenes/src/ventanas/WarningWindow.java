package ventanas;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WarningWindow {
    @FXML private Button understoodButton;

    @FXML
    private void UnderstoodButtonAction() {
        Stage stage = (Stage) understoodButton.getScene().getWindow();
        stage.close();
    }
}
