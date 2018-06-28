package windowsControllers;

import javafx.fxml.FXML;
import application.SST;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import application.Cliente;
import exceptions.RutInvalidoException;
import application.SST;
import exceptions.TelefonoInvalidoException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
