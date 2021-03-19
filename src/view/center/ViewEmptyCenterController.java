package view.center;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ViewEmptyCenterController extends GridPane {
	@FXML
	private ImageView imgViewCenter;
	public ViewEmptyCenterController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewEmptyCenter.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		Image titleImage=new Image("/resources/titlescreen.png");
		imgViewCenter.setImage(titleImage);
		imgViewCenter.setFitHeight(titleImage.getHeight());
		imgViewCenter.setFitWidth(titleImage.getWidth());
		imgViewCenter.setPreserveRatio(true);
		imgViewCenter.fitHeightProperty().bind(this.heightProperty());
	}
}
