package ventanas;

import application.Orden;
import application.SST;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class FinalizarOrdenController {

    @FXML private Label orderNumberLabel;
    @FXML private Label estimatedDateLabel;
    @FXML private Label clientLabel;
    @FXML private Label techLabel;
    @FXML private Label priceLabel;
    @FXML private Button finishButton;
    @FXML private Button cancelOrderButton;

    private SST sistema;
    private boolean flag;

    @FXML
    private void cancelOrderButtonAction(){
        flag = false;
        Stage stage = (Stage) cancelOrderButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void finishButtonAction(){
        flag = true;
        Stage stage = (Stage) cancelOrderButton.getScene().getWindow();
        stage.close();
    }

    public void initVariables(SST sistema, Orden order) {
        orderNumberLabel.setText(String.valueOf(order.getOrderNumber()));
        clientLabel.setText(sistema.getClient(order.getClientRut()).getName());
        techLabel.setText(sistema.getTechnician(order.getTechNumber()).getName());

        if(order.isChecked()) {
            estimatedDateLabel.setText(order.getDateOut());
            priceLabel.setText(String.valueOf(order.getPrice()));
        }
    }


    public boolean getFlag() {
        return flag;
    }



}

