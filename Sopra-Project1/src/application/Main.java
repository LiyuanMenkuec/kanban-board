package application;
	
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.StartView.LoginViewController;

/**
 * The type Main.
 */
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			LoginViewController loginViewController = new LoginViewController(primaryStage);
			Scene scene = new Scene(loginViewController,400,200);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMinWidth(640);
			primaryStage.setMinHeight(480);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
