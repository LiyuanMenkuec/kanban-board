package view.untersuchungsbericht;
import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The type Untersuchung review controller.
 */
public class UntersuchungReviewController {

    @FXML
    private TitledPane Bericht1;

    @FXML
    private Text Date;

    @FXML
    private Text Grund;

    @FXML
    private Text Symtome;

    @FXML
    private Text ICD;

    @FXML
    private TextFlow Behandlung;

    @FXML
    private TextFlow Medikamente;

    @FXML
    private TextFlow Notiz;

}

