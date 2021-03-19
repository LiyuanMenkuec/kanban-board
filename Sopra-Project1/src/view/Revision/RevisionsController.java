package view.Revision;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.EPAController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import view.FunctionView.ArztMainViewController;
import view.FunctionView.PatientMainViewController;

/**
 * The type Revisions controller.
 */
public class RevisionsController extends BorderPane {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;


    @FXML // fx:id="reviewBackButton"
    private Button reviewBackButton; // Value injected by FXMLLoader

    @FXML // fx:id="reviewOutput"
    private FlowPane reviewOutput; // Value injected by FXMLLoader

    /**
     * Initialize.
     */
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert reviewBackButton != null : "fx:id=\"reviewBackButton\" was not injected: check your FXML file 'Revision.fxml'.";
        assert reviewOutput != null : "fx:id=\"reviewOutput\" was not injected: check your FXML file 'Revision.fxml'.";

    }
    private Stage mainStage;
    private EPAController epaController;

    /**
     * Instantiates a new Revisions controller.
     *
     * @param mainStage     the main stage
     * @param epaController the epa controller
     */
    public RevisionsController( Stage mainStage,EPAController epaController){
        this.epaController=epaController;
        this.mainStage=mainStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Revision/Revision.fxml"));
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
        if(epaController.isArzt()){
            ArrayList<String> revisionList= epaController.getEPA().getArzt(epaController.getCurrLoggedIn()).getRevision();
            for(int i=0; i< revisionList.size(); i++){
                reviewOutput.getChildren().add(new Text(revisionList.get(i)));
            }
        }
        else{
            ArrayList<String> revisionList= epaController.getEPA().getPatient(epaController.getCurrLoggedIn()).getRevision();
            for(int i=0; i< revisionList.size(); i++){
                reviewOutput.getChildren().add(new Text(revisionList.get(i)));
            }
        }
    }

    /**
     * Zu main view.
     */
    @FXML
    void zuMainView(){
        if(epaController.isArzt()){
            mainStage.setScene(new Scene(new ArztMainViewController(mainStage,epaController)));
        }
        else{
            mainStage.setScene(new Scene(new PatientMainViewController(mainStage,epaController)));
        }
    }



}
