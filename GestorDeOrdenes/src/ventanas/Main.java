package ventanas;
	
import application.SST;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	//Probando GitKraken 2
	private SST sistema;
	
	@Override
	public void start(Stage mainStage) throws Exception {
		
		sistema = new SST();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
		Parent root = loader.load();
		
		MainController mainController = (MainController) loader.getController();
		mainController.initVariables(sistema);
		
		Scene scene = new Scene(root);
		//primaryStage.initStyle(StageStyle.UNDECORATED);
		mainStage.setScene(scene);
		mainStage.resizableProperty().setValue(false);
		mainStage.show();
		
		mainStage.setOnCloseRequest(e -> Platform.exit());
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
