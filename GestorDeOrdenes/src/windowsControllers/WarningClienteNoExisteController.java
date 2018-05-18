package windowsControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WarningClienteNoExisteController {

	@FXML private Button backButton;
	@FXML private Button createButton;
	private boolean flag;
	
	public boolean getFlag() {
		return flag;
	}
	
	@FXML
	private void BackButtonAction() {
	    Stage stage = (Stage) backButton.getScene().getWindow();
	    flag = false;
	    stage.close();
	    
	}

	@FXML
	private void CreateButtonAction() {
	    Stage stage = (Stage) createButton.getScene().getWindow();
	    flag = true;
	    stage.close();
	}
}
