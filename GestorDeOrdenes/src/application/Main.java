package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import windowsControllers.MainController;

import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

    private static SST sistema;
	
	@Override
	public void start(Stage mainStage) throws IOException {

		try {
			sistema = new SST();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/windows/MainMenu.fxml"));
            Parent root = loader.load();

            MainController mainController = loader.getController();
            mainController.initVariables(sistema);

            Scene scene = new Scene(root);
            //mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.setScene(scene);
            mainStage.resizableProperty().setValue(false);
            mainStage.show();

            //mainStage.setOnCloseRequest(e -> Platform.exit());
		}
		catch (SQLException e){
			System.out.println(e.getMessage());
			System.out.println("\nConexion con base de datos fallida.");
		}
	}
	
	public static void main(String[] args) throws Exception {
		launch(args);
		sistema.updateStock();
	}
}

