package view.untersuchungsbericht;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * The type Untersuchungsbericht start controller.
 */
public class UntersuchungsberichtStartController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="untersuchungsberichtVersicherungsnummer"
    private TextField untersuchungsberichtVersicherungsnummer; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtNextStep"
    private Button untersuchungsberichtNextStep; // Value injected by FXMLLoader

    /**
     * Initialize.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert untersuchungsberichtVersicherungsnummer != null : "fx:id=\"untersuchungsberichtVersicherungsnummer\" was not injected: check your FXML file 'model.Untersuchungsbericht Start.fxml'.";
        assert untersuchungsberichtNextStep != null : "fx:id=\"untersuchungsberichtNextStep\" was not injected: check your FXML file 'model.Untersuchungsbericht Start.fxml'.";

    }
}

