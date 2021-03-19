package view.ueberweisung;
import controller.EPAController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Ueberweisung create view controller.
 */
public class UeberweisungCreateViewController extends ScrollPane {

    @FXML
    private BorderPane MainPane1;

    @FXML
    private TextField Versicherungsnummer;

    @FXML
    private TextField ArztBezeich;

    @FXML
    private Button FortFahren;

    @FXML
    private Button toFunctionView;

    @FXML
    private Button CheckButton;

    @FXML
    private Text HiddenText;

    @FXML
    private TextArea Auftrag;

    @FXML
    private FlowPane Flow;

    @FXML
    private TitledPane Bericht1;

    @FXML
    private RadioButton ChooseButton;

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

    @FXML
    private TitledPane Bericht11;

    @FXML
    private RadioButton ChooseButton1;

    @FXML
    private Text Date1;

    @FXML
    private Text Grund1;

    @FXML
    private Text Symtome1;

    @FXML
    private Text ICD1;

    @FXML
    private TextFlow Behandlung1;

    @FXML
    private TextFlow Medikamente1;

    @FXML
    private TextFlow Notiz1;

    @FXML
    private TitledPane Bericht12;

    @FXML
    private RadioButton ChooseButton2;

    @FXML
    private Text Date2;

    @FXML
    private Text Grund2;

    @FXML
    private Text Symtome2;

    @FXML
    private Text ICD2;

    @FXML
    private TextFlow Behandlung2;

    @FXML
    private TextFlow Medikamente2;

    @FXML
    private TextFlow Notiz2;
    private EPAController epaController;
    private Stage mainStage;
    private boolean versicherungsnummerValid=false;
    private String patientVersucherungnum="";
    private ArrayList<Untersuchungsbericht> BerichtListe=new ArrayList<Untersuchungsbericht>();

    /**
     * Instantiates a new Ueberweisung create view controller.
     *
     * @param primaryStage  the primary stage
     * @param epaController the epa controller
     */
    public UeberweisungCreateViewController(Stage primaryStage, EPAController epaController) {
        this.mainStage= primaryStage;
        this.epaController=epaController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/UeberweisungCreateView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check versicherungsnum.
     */
    @FXML
    void checkVersicherungsnum(){
        if(Versicherungsnummer.getText().isEmpty()||!(epaController.getEPA().checkNumPatient(Versicherungsnummer.getText()))){
            HiddenText.setText("Versicherungsnummer ist invalid");
            HiddenText.setVisible(true);
            Flow.getChildren().clear();
        }
        else{
            Flow.getChildren().clear();
            Patient p =epaController.getEPA().getPatient(Versicherungsnummer.getText());
            ArrayList<Untersuchungsbericht> berichtliste= epaController.getEPA().getArzt(epaController.getCurrLoggedIn()).getUntersuchungsberichte(Versicherungsnummer.getText());
            ArrayList<Ueberweisung> ueberweisungslist= p.getUeberweisungsList();
            for(int i=0;i<ueberweisungslist.size();i++){
                if(ueberweisungslist.get(i).getNeuarztnummer().equals(epaController.getCurrLoggedIn())&&ueberweisungslist.get(i).isDatenStimmZu()){
                    berichtliste.addAll(ueberweisungslist.get(i).getUntersuchungsbericht());
                }
            }
            for(int i= 0; i< berichtliste.size(); i++){
                UntersuchungBerichtWahlController uc= new UntersuchungBerichtWahlController(berichtliste.get(i));
                Flow.getChildren().add(uc);
            }
            HiddenText.setText("Versicherungsnummer ist valid");
            HiddenText.setVisible(true);
            patientVersucherungnum=Versicherungsnummer.getText();
            versicherungsnummerValid=true;
        }
    }

    /**
     * To main view.
     */
    @FXML
    void toMainView(){
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage,epaController)));
    }

    /**
     * Ueberweisung review.
     */
    @FXML
    void UeberweisungReview(){
         if(versicherungsnummerValid&&patientVersucherungnum.equals(Versicherungsnummer.getText())){
             if(!(Versicherungsnummer.getText().isEmpty()||ArztBezeich.getText().isEmpty()||ArztBezeich.getText().equals(epaController.getCurrLoggedIn())||Auftrag.getText().isEmpty())){
                 if(epaController.getEPA().checkNumArzt(ArztBezeich.getText())){
                     for(int i= 0; i < Flow.getChildren().size(); i++){
                         UntersuchungBerichtWahlController uc = (UntersuchungBerichtWahlController) Flow.getChildren().get(i);
                         if(uc.isChoosen()){
                             BerichtListe.add(uc.getUntersuchung());
                         }
                     }
                     String time=epaController.getTime();
                     mainStage.setScene(new Scene(new UeberweisungsViewController(Versicherungsnummer.getText(),ArztBezeich.getText(),Auftrag.getText(),BerichtListe,time,mainStage,epaController)));
                 }
             }
         }
    }


}

