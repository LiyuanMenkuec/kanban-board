package application;
	

import controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Company;
import model.TestDataFactory;
import view.MainWindowController;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			MainWindowController mainWindowController = new MainWindowController();
			
			Scene scene = new Scene(mainWindowController);//,Screen.getPrimary().getBounds().getWidth(),Screen.getPrimary().getBounds().getHeight());
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(MainWindowController.class.getResourceAsStream("/resources/fav2.png"))); 
			primaryStage.setTitle("CanBun! 'cause everyone needs a bunny");
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(800);
			primaryStage.setMinHeight(600);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}