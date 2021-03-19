package view.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class ViewAboutController extends GridPane implements Initializable{
	@FXML
	private ImageView imgViewLogo;
	@FXML
	private TextArea txtAreaAbout;
	public ViewAboutController() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dialogs/ViewAbout.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		imgViewLogo.setImage(new Image("/resources/fav2.png"));
		txtAreaAbout.setText("Version 1.0.300\n\nEntwickelt von: Anne Stockey, Liyuan Menküc, "
				+ "Abdulrazzak Shaker, Elias Zeren, Florian Wellner, "
				+ "Gero Grühn, Julian Adler, Tim Lukas Bräuker UND Fabian Kemper\n"
				+ "\nBesonderen Dank an das SoPra-Team SS2020");
		
	}
}
