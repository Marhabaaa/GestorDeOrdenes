package ventanas;

import application.SST;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class FinalizarOrdenController {

    @FXML private Label orderNumber;
    @FXML private Label estimatedTime;
    @FXML private Button finishButton;
    @FXML private Button cancelOrderButton;

    @FXML
    private void cancelOrderButtonAction(){
        Stage stage = (Stage) cancelOrderButton.getScene().getWindow();
        stage.close();
    }

    public void initVariables(String rut, SST sistema){
        rutLabel.setText(rut);
        this.sistema = sistema;
    }

    //yo me llamo laucho
    //Yo me llamo guaren
}

