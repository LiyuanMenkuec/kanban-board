package view.ueberweisung;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Patient;
import model.Ueberweisung;
import model.Untersuchungsbericht;
import view.FunctionView.ArztMainViewController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Ueberweisungs view controller.
 */
public class UeberweisungsViewController extends ScrollPane {

    @FXML
    private BorderPane ArtzPreviewPane;

    @FXML
    private Text Name;

    @FXML
    private Text Versicherungsnum;

    @FXML
    private Text BirthDay;

    @FXML
    private TextFlow Adress;
    @FXML
    private TextFlow Auftragtext;
    @FXML
    private Text ArtzBez;

    @FXML
    private Text Sex;

    @FXML
    private FlowPane Flow;

    @FXML
    private Button Bestaetigen;

    @FXML
    private Button Zurück;

    @FXML
    private Text ArtzNum;


    @FXML
    private Text Datum;

    private ArrayList<Untersuchungsbericht> berichtListe;
    private Stage mainStage;
    private  EPAController epaController;
    private String neuArzt;
    private String versicherungsNummmer;
    private String auftrag;
    private String time;

    /**
     * Instantiates a new Ueberweisungs view controller.
     *
     * @param versicherung  the versicherung
     * @param neuArztNum    the neu arzt num
     * @param auftrag       the auftrag
     * @param berichtListe  the bericht liste
     * @param time          the time
     * @param primaryStage  the primary stage
     * @param epaController the epa controller
     */
    public UeberweisungsViewController(String versicherung, String neuArztNum, String auftrag, ArrayList<Untersuchungsbericht> berichtListe, String time,Stage primaryStage, EPAController epaController) {
        this.mainStage= primaryStage;
        this.time=time;
        this.versicherungsNummmer=versicherung;
        this.neuArzt= neuArztNum;
        this.epaController=epaController;
        this.auftrag=auftrag;
        this.berichtListe=berichtListe;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/UeberweisungsView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            init();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    private void init(){
        Patient p = epaController.getEPA().getPatient(versicherungsNummmer);
        Name.setText(p.getName());
        Name.setVisible(true);
        Datum.setText(time);
        Datum.setVisible(true);
        Adress.getChildren().add(new Text(p.getAddress()));
        Auftragtext.getChildren().add(new Text(auftrag));
        Versicherungsnum.setText(p.getNum());
        Versicherungsnum.setVisible(true);
        BirthDay.setText(p.getGesburtsDatum());
        BirthDay.setVisible(true);
        Sex.setText(p.getGeschlecht());
        Sex.setVisible(true);
        ArtzNum.setText(epaController.getCurrLoggedIn());
        ArtzNum.setVisible(true);
        ArtzBez.setText(epaController.getEPA().getArzt(neuArzt).getName());
        ArtzBez.setVisible(true);
        for(int i=0; i<berichtListe.size();i++){
            UntersuchungBerichtWahlController uc = new UntersuchungBerichtWahlController(berichtListe.get(i));
            uc.setButtonDisable();
            Flow.getChildren().add(uc);
        }
    }

    /**
     * To main view.
     *
     * @param e the e
     */
    @FXML
    void ToMainView(ActionEvent e) throws IOException {
        // create new Ueberweisung with UeberweisungsController parameter ist atribute ()
        Ueberweisung ueberweisung =epaController.getUeberweisungsController().createUeberweisung(epaController.getCurrLoggedIn(),versicherungsNummmer,neuArzt,auftrag,berichtListe,time);
        epaController.getUeberweisungsController().addUeberweisung(ueberweisung);
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage,epaController)));
    }

    /**
     * To create ueberweisung.
     *
     * @param e the e
     */
    @FXML
    void ToCreateUeberweisung(ActionEvent e){
        mainStage.setScene(new Scene(new UeberweisungCreateViewController(mainStage, epaController)));
    }


}

