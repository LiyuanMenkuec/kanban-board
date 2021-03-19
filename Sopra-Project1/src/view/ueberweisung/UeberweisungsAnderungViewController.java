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
import model.EPA;
import model.Patient;
import model.Ueberweisung;
import model.Untersuchungsbericht;
import view.FunctionView.PatientMainViewController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Ueberweisungs anderung view controller.
 */
public class UeberweisungsAnderungViewController extends ScrollPane {

    @FXML
    private BorderPane PatientPreviewPane;

    @FXML
    private Text Name;

    @FXML
    private Text Versicherungsnum;

    @FXML
    private Text BirthDay;

    @FXML
    private TextFlow Adress;

    @FXML
    private Text NeuArtz;

    @FXML
    private Text Sex;

    @FXML
    private FlowPane Flow;

    @FXML
    private Button Save;

    @FXML
    private Button Zurück;

    @FXML
    private Text ArtzNum;

    @FXML
    private Text Datum;

    private  Stage mainStage;
    private EPAController epaController;
    private Ueberweisung ueberweisung;
    private ArrayList<Untersuchungsbericht>choosenUntersuchungsberichts=new ArrayList<Untersuchungsbericht>();

    /**
     * Instantiates a new Ueberweisungs anderung view controller.
     *
     * @param EPAControl   the epa control
     * @param primary      the primary
     * @param ueberweisung the ueberweisung
     */
    public UeberweisungsAnderungViewController(EPAController EPAControl, Stage primary, Ueberweisung ueberweisung){
        this.mainStage=primary;
        this.epaController=EPAControl;
        this.ueberweisung=ueberweisung;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/UeberweisungsAnderungView.fxml"));
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
        Patient p= epaController.getEPA().getPatient(epaController.getCurrLoggedIn());
        Name.setText(p.getName());
        Name.setVisible(true);
        Datum.setText(ueberweisung.getDate());
        Datum.setVisible(true);
        Adress.getChildren().add(new Text(p.getAddress()));
        Versicherungsnum.setText(p.getNum());
        Versicherungsnum.setVisible(true);
        BirthDay.setText(p.getGesburtsDatum());
        BirthDay.setVisible(true);
        Sex.setText(p.getGeschlecht());
        Sex.setVisible(true);
        NeuArtz.setText(epaController.getEPA().getArzt(ueberweisung.getNeuarztnummer()).getName());
        NeuArtz.setVisible(true);
        ArtzNum.setText(ueberweisung.getAltArztnummer());
        ArtzNum.setVisible(true);
        ArrayList<Untersuchungsbericht> berichtList=ueberweisung.getUntersuchungsbericht();
        Flow.getChildren().clear();
        for(int i=0; i<berichtList.size(); i++){
            UntersuchungsWahlController uc= new UntersuchungsWahlController(berichtList.get(i));
            Flow.getChildren().add(uc);
        }
    }

    /**
     * Anderungen speichern.
     *
     * @param event the event
     */
    @FXML
    void AnderungenSpeichern(ActionEvent event) {
        for(int i=0; i<Flow.getChildren().size();i++){
            UntersuchungsWahlController uc= (UntersuchungsWahlController)Flow.getChildren().get(i);
            if(uc.BerichtChoosen()){choosenUntersuchungsberichts.add(uc.getUntersuchung());}
        }
        ueberweisung.setUntersuchungsbericht(choosenUntersuchungsberichts);
        ueberweisung.setDatenStimmZu(true);
        Patient patient=epaController.getEPA().getPatient(epaController.getCurrLoggedIn());
        patient.addToRevision(epaController.getTime()+" Sie haben Veranderungen zur Überweisung am "+ueberweisung.getDate()+" gemacht");
        patient.updateDaten();
        System.out.println(patient.isNeuDaten());
        mainStage.setScene(new Scene(new UeberweisungsChooseController(mainStage,epaController)));
    }

    /**
     * To choose ueberweisung.
     *
     * @param event the event
     */
    @FXML
    void ToChooseUeberweisung(ActionEvent event) {
        mainStage.setScene(new Scene(new UeberweisungsChooseController(mainStage,epaController)));
    }



}

