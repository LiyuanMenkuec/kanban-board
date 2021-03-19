package view.ueberweisung;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Patient;
import model.Ueberweisung;
import view.FunctionView.PatientMainViewController;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Ueberweisungs choose controller.
 */
public class UeberweisungsChooseController extends AnchorPane {
    @FXML
    private FlowPane Flow;

    @FXML
    private Text HiddenText;

    @FXML
    private Button ToFuncView;
    private Stage mainStage;
    private  EPAController epaController;

    /**
     * Instantiates a new Ueberweisungs choose controller.
     *
     * @param primaryStage the primary stage
     * @param EPAControl   the epa control
     */
    public UeberweisungsChooseController(Stage primaryStage, EPAController EPAControl){
        mainStage=primaryStage;
        epaController=EPAControl;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/UeberweisungsChoose.fxml"));
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
        Patient p = epaController.getEPA().getPatient(epaController.getCurrLoggedIn());
        ArrayList<Ueberweisung> PatientUeberweisung= p.getUeberweisungsList();
        if(PatientUeberweisung.size()>0){
            Flow.getChildren().clear();
            for(int i=0; i<PatientUeberweisung.size();i++){
                Button button = new Button();
                button.setText("Überweisung am "+PatientUeberweisung.get(i).getDate()+" vom Arzt "+epaController.getEPA().getArzt(PatientUeberweisung.get(i).getAltArztnummer()).getName()+" zu Arzt "+epaController.getEPA().getArzt(PatientUeberweisung.get(i).getNeuarztnummer()).getName());
                Ueberweisung u=PatientUeberweisung.get(i);
                button.setOnAction(event -> {
                    mainStage.setScene(new Scene(new UeberweisungsAnderungViewController(epaController,mainStage,u)));
                    event.consume();
                });
                button.setPrefWidth(530.0);
                button.setPrefHeight(90.0);
                Flow.getChildren().add(button);
            }
        }
        else{
            Flow.getChildren().clear();
            Flow.getChildren().add(HiddenText);
            HiddenText.setVisible(true);
        }
    }

    /**
     * Zum fuctions view.
     *
     * @param event the event
     */
    @FXML
    void ZumFuctionsView(ActionEvent event) {
        mainStage.setScene(new Scene(new PatientMainViewController(mainStage,epaController)));
    }
}
