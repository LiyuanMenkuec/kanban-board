package view.untersuchungsbericht;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * The type Untersuchungs wahl controller.
 */
public class UntersuchungsWahlController {

    @FXML
    private TitledPane Bericht1;

    @FXML
    private Text Date;

    @FXML
    private RadioButton GrundChoose;

    @FXML
    private Text Grund;

    @FXML
    private RadioButton SymtomeChoose;

    @FXML
    private Text Symtome;

    @FXML
    private RadioButton ICDChoose;

    @FXML
    private Text ICD;

    @FXML
    private TextFlow Behandlung;

    @FXML
    private TextFlow Medikamente;

    @FXML
    private TextFlow Notiz;

    @FXML
    private RadioButton BehandlungChoose;

    @FXML
    private RadioButton MedChoose;

    @FXML
    private RadioButton NotizChoose;

    @FXML
    private RadioButton chooseThis;

}


