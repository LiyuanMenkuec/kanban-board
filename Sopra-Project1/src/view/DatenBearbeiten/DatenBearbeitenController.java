package view.DatenBearbeiten;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;
import view.FunctionView.PatientMainViewController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatenBearbeitenController extends ScrollPane {

    @FXML
    private TextField pVorname;

    @FXML
    private TextField pNachname;

    @FXML
    private DatePicker pBday;

    @FXML
    private RadioButton pIsmale;

    @FXML
    private ToggleGroup sex;

    @FXML
    private RadioButton pIsfemale;

    @FXML
    private TextArea pAdress;

    @FXML
    private TextField pSetnum;
    @FXML
    private TextField neuPass;
    @FXML
    private TextField pSetpass;

    @FXML
    private Button back;

    @FXML
    private Button save;

    @FXML
    private Text HiddenText;
    private Stage mainStage;
    private EPAController epaController;
    public DatenBearbeitenController(Stage primaryStage, EPAController epaController){
        this.epaController= epaController;
        mainStage= primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DatenBearbeiten/DatenBearbeiten.fxml"));
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
        Patient patient= epaController.getEPA().getPatient(epaController.getCurrLoggedIn());
        pVorname.setText(patient.getVorname());
        pNachname.setText(patient.getNachname());
        pAdress.setText(patient.getAddress());
        pBday.setValue(LocalDate.parse(patient.getGesburtsDatum(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        if(patient.getGeschlecht().equals("f")){
            pIsfemale.setSelected(true);
        }
        else{pIsmale.setSelected(true);}
    }
    @FXML
    void InfoAndern(ActionEvent event) throws IOException {
        Patient patient= epaController.getEPA().getPatient(epaController.getCurrLoggedIn());
        if(pSetpass.getText().isEmpty()){HiddenText.setText("Geben Sie bitte ihres Passwort ein");HiddenText.setVisible(true);}
        else if(!pSetpass.getText().equals(patient.getPasswort())){HiddenText.setVisible(true);}
        else{
            String s = pIsfemale.isSelected() ? "f" : "m";
            if(neuPass.getText().isEmpty()){
                epaController.getPatientDatenController().patientDatenUpdate(epaController.getCurrLoggedIn(),pVorname.getText(),pNachname.getText(),pAdress.getText(),s,pBday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),patient.getPasswort());
            }
            else{
                epaController.getPatientDatenController().patientDatenUpdate(epaController.getCurrLoggedIn(),pVorname.getText(),pNachname.getText(),pAdress.getText(),s,pBday.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),neuPass.getText());
            }
            mainStage.setScene(new Scene(new PatientMainViewController(mainStage,epaController)));
        }
    }

    @FXML
    void ZumMainView(ActionEvent event) {
        mainStage.setScene(new Scene(new PatientMainViewController(mainStage,epaController)));
    }

}
