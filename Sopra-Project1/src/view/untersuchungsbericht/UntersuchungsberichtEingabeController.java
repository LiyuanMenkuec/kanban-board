package view.untersuchungsbericht;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import controller.EPAController;
import controller.UntersuchungsberichtController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.FunctionView.ArztMainViewController;

/**
 * The type Untersuchungsbericht eingabe controller.
 */
public class UntersuchungsberichtEingabeController extends BorderPane {


    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="untersuchungsberichtVersicherungsnummer"
    private TextField untersuchungsberichtVersicherungsnummer; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtDatePicker"
    private DatePicker untersuchungsberichtDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtUhrzeit"
    private TextField untersuchungsberichtUhrzeit; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtSymptome"
    private TextField untersuchungsberichtSymptome; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtICD"
    private TextField untersuchungsberichtICD; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtBehandlung"
    private TextField untersuchungsberichtBehandlung; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtMedikamente"
    private TextArea untersuchungsberichtMedikamente; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtNotesField"
    private TextArea untersuchungsberichtNotesField; // Value injected by FXMLLoader

    @FXML // fx:id="versicherungsFehler"
    private Text versicherungsFehler; // Value injected by FXMLLoader

    @FXML // fx:id="iCDFehler"
    private Text iCDFehler; // Value injected by FXMLLoader

    @FXML // fx:id="mehrereFehler"
    private Text mehrereFehler; // Value injected by FXMLLoader

    @FXML // fx:id="uhrzeitFehler"
    private Text uhrzeitFehler; // Value injected by FXMLLoader

    @FXML // fx:id="felderAusfuellen"
    private Text felderAusfuellen; // Value injected by FXMLLoader

    @FXML // fx:id="speicherMessage"
    private Text speicherMessage; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtCheckButton"
    private Button untersuchungsberichtCheckButton; // Value injected by FXMLLoader

    @FXML // fx:id="untersuchungsberichtSaveButton"
    private Button untersuchungsberichtSaveButton; // Value injected by FXMLLoader

    @FXML
    private Button zuruck;

    private EPAController ePAControl;

    private Stage mainStage;

    private UntersuchungsberichtController untersuchungsberichtController;

    private String versicherungsnummer;

    private String uhrzeit;

    private String datum;

    private LocalDateTime timeDate;

    private String symptome;

    private String icd;

    private List<String> med;

    private String diagnose;

    private String behandlung;

    private List<String> notes;

    private String medikamente;

    private String[] medZwischen;

    private String notizen;

    private String[] notizenZwischen;

    /**
     * Instantiates a new Untersuchungsbericht eingabe controller.
     *
     * @param primaryStage the primary stage
     * @param EPAControl   the epa control
     */
    public UntersuchungsberichtEingabeController(Stage primaryStage, EPAController EPAControl) {
        mainStage = primaryStage;
        this.ePAControl = EPAControl;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/untersuchungsbericht/UntersuchungsberichtEingabe.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Bestaetigen.
     */
    @FXML
    void bestaetigen(){
        iCDFehler.setVisible(false);
        versicherungsFehler.setVisible(false);
        mehrereFehler.setVisible(false);
        uhrzeitFehler.setVisible(false);
        speicherMessage.setVisible(false);
        felderAusfuellen.setVisible(false);
        versicherungsnummer = untersuchungsberichtVersicherungsnummer.getText();
        uhrzeit = untersuchungsberichtUhrzeit.getText();
        datum = untersuchungsberichtDatePicker.toString();
        String str = datum+" "+uhrzeit;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        symptome = untersuchungsberichtSymptome.getText();
        icd = untersuchungsberichtICD.getText();
        medikamente = untersuchungsberichtMedikamente.getText();
        medZwischen = medikamente.split("\n");
        for(String x:medZwischen){
            med.add(x);
        }
        diagnose = untersuchungsberichtSymptome.getText();
        behandlung = untersuchungsberichtBehandlung.getText();
        notizen = untersuchungsberichtNotesField.getText();
        notizenZwischen = notizen.split("\n");
        for(String y: notizenZwischen){
            notes.add(y);
        }
        boolean icdFormat = untersuchungsberichtController.iCDUeberpruefen(icd);
        boolean uhrzeitFormat = untersuchungsberichtController.uhrzeitUeberpruefen(uhrzeit);
        boolean versicherungsnummerFormat = untersuchungsberichtController.versicherungsnummerUeberpruefen(versicherungsnummer);
        boolean leer = untersuchungsberichtController.felderLeer(uhrzeit,versicherungsnummer, symptome, icd, behandlung, medikamente);
        if(leer){
            felderAusfuellen.setVisible(true);
        }
        else{
            if(!(icdFormat||versicherungsnummerFormat)||!(uhrzeitFormat||versicherungsnummerFormat)||!(icdFormat||uhrzeitFormat)){
                mehrereFehler.setVisible(true);
            }
            else if(!icdFormat){
                iCDFehler.setVisible(true);
            }
            else if(!versicherungsnummerFormat){
                versicherungsFehler.setVisible(true);
            }
            else if(!uhrzeitFormat){
                uhrzeitFehler.setVisible(true);
            }
            if(icdFormat&&versicherungsnummerFormat&&uhrzeitFormat){
                speicherMessage.setVisible(true);
                untersuchungsberichtCheckButton.setDisable(true);
                untersuchungsberichtCheckButton.setOpacity(0);
                untersuchungsberichtSaveButton.setDisable(false);
                untersuchungsberichtSaveButton.setOpacity(1);
            }
        }
    }

    /**
     * Save.
     */
    @FXML
    void save() throws IOException {
        untersuchungsberichtController.createUntersuchungsbericht(versicherungsnummer, timeDate, icd, med, diagnose, behandlung, notes);
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage,ePAControl)));
    }

    /**
     * Zum main view.
     */
    @FXML
    void zurMainView(){
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage,ePAControl)));
    }
}
