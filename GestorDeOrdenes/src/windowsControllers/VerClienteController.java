package windowsControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.Cliente;

public class VerClienteController {

    @FXML private Label rutLabel;
    @FXML private Label nameLabel;
    @FXML private Label phoneLabel;
    @FXML private Label mailLabel;
    @FXML private Label isEmpressLabel;
    @FXML private Button backButton;


    @FXML

    private void backButtonAction(){
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void initVariables(Cliente cliente) {

        rutLabel.setText(String.valueOf(cliente.getRut()));
        nameLabel.setText(String.valueOf(cliente.getName()));
        phoneLabel.setText(String.valueOf(cliente.getPhoneNumber()));
        mailLabel.setText(String.valueOf(cliente.geteMail()));

        if(cliente.isBusiness()){
            isEmpressLabel.setText("Si");
        }else{
            isEmpressLabel.setText("No");
        }
    }
}