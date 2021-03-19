package view.zusammenfassungderBehandlungsschritt;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Untersuchungsbericht;
import view.FunctionView.ArztMainViewController;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * The type Eingabe seite controller.
 */
public class EingabeSeiteController extends AnchorPane {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button zumZusammenfassung;

    @FXML
    private Button zumArztMainView;

    @FXML
    private RadioButton aktuellerArzt;

    @FXML
    private RadioButton alleÄrzte;

    @FXML
    private TextField Versicherungsnum;

    @FXML
    private TextField ICD;

    /**
     * Zum arzt main view.
     *
     * @param event the event
     */
    @FXML
    void zumArztMainView(ActionEvent event) {
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage, EPAControll)));
    }

    /**
     * Zum zusammenfassung.
     *
     * @param event the event
     */
    @FXML
    void zumZusammenfassung(ActionEvent event) {
        if(!Versicherungsnum.getText().isEmpty()&&EPAControll.getEPA().getArzt(EPAControll.getCurrLoggedIn()).checkPatientValid(Versicherungsnum.getText())&&(aktuellerArzt.isSelected()||alleÄrzte.isSelected())){
            String icd= ICD.getText().isEmpty() ? "leer" : ICD.getText();
            ArrayList<Untersuchungsbericht> berichtListe;
            if(aktuellerArzt.isSelected()){
                berichtListe= EPAControll.getZusammenfassungsController().createZusammenfassungEinArzt(Versicherungsnum.getText(),icd);
            }
            else{
                berichtListe=EPAControll.getZusammenfassungsController().createZusammenfassungAlleAerzte(Versicherungsnum.getText(),icd);
            }
            mainStage.setScene(new Scene(new zusammenfassungsController(mainStage,EPAControll,berichtListe)));
        }
    }

    /**
     * Initialize.
     */
    @FXML
    void initialize() {
        assert zumZusammenfassung != null : "fx:id=\"zumZusammenfassung\" was not injected: check your FXML file 'EingabeSeite.fxml'.";
        assert zumArztMainView != null : "fx:id=\"zumArztMainView\" was not injected: check your FXML file 'EingabeSeite.fxml'.";
        assert aktuellerArzt != null : "fx:id=\"aktuellerArzt\" was not injected: check your FXML file 'EingabeSeite.fxml'.";
        assert alleÄrzte != null : "fx:id=\"alleÄrzte\" was not injected: check your FXML file 'EingabeSeite.fxml'.";

    }

    @FXML
    private TextField pVersicherungsnummer;

    @FXML
    private TextField pICDCode;

    private Stage mainStage;
    private EPAController EPAControll;

    /**
     * Instantiates a new Eingabe seite controller.
     *
     * @param primaryStage the primary stage
     * @param EPAControll  the epa controll
     */
    public EingabeSeiteController(Stage primaryStage, EPAController EPAControll) {
        this.EPAControll= EPAControll;
        mainStage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/zusammenfassungderBehandlungsschritt/EingabeSeite.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
