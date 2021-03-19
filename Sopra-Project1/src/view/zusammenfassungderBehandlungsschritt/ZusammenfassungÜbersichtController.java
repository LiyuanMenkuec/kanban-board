package view.zusammenfassungderBehandlungsschritt;
import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Arzt;
import model.Patient;
import model.Untersuchungsbericht;
import view.FunctionView.ArztMainViewController;

public class ZusammenfassungÜbersichtController {


    @FXML // fx:id="pInfomation"
    private Label pInfomation; // Value injected by FXMLLoader

    @FXML // fx:id="pVorname"
    private Text pVorname; // Value injected by FXMLLoader

    @FXML // fx:id="pNachname"
    private Text pNachname; // Value injected by FXMLLoader

    @FXML // fx:id="pGeschlecht"
    private Text pGeschlecht; // Value injected by FXMLLoader

    @FXML // fx:id="pVersicherungsnum"
    private Text pVersicherungsnum; // Value injected by FXMLLoader

    @FXML // fx:id="Uebersicht"
    private TableView<?> Uebersicht; // Value injected by FXMLLoader

    @FXML // fx:id="pTermin"
    private TableColumn<?, ?> pTermin; // Value injected by FXMLLoader

    @FXML // fx:id="pArzt"
    private TableColumn<?, ?> pArzt; // Value injected by FXMLLoader

    @FXML // fx:id="pUntersuchungsbericht"
    private TableColumn<?, ?> pUntersuchungsbericht; // Value injected by FXMLLoader

    @FXML // fx:id="zumArztMainView"
    private Button zumArztMainView; // Value injected by FXMLLoader

    @FXML // fx:id="zumEingabeSeite"
    private Button zumEingabeSeite; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert pInfomation != null : "fx:id=\"pInfomation\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pVorname != null : "fx:id=\"pVorname\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pNachname != null : "fx:id=\"pNachname\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pGeschlecht != null : "fx:id=\"pGeschlecht\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pVersicherungsnum != null : "fx:id=\"pVersicherungsnum\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert Uebersicht != null : "fx:id=\"Uebersicht\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pTermin != null : "fx:id=\"pTermin\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pArzt != null : "fx:id=\"pArzt\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert pUntersuchungsbericht != null : "fx:id=\"pUntersuchungsbericht\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert zumArztMainView != null : "fx:id=\"zumArztMainView\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";
        assert zumEingabeSeite != null : "fx:id=\"zumEingabeSeite\" was not injected: check your FXML file 'Zusammenfassungübersicht.fxml'.";

    }

    @FXML
    private Stage mainStage;
    private EPAController EPAControl;
    private Untersuchungsbericht Ub;
    void ZumArztMainView(ActionEvent actionEvent){
        mainStage.setScene(new Scene(new ArztMainViewController(mainStage, EPAControl)));
    }

    @FXML
    void ZumEingabeSeite(ActionEvent actionEvent){
        mainStage.setScene(new Scene(new EingabeSeiteController(mainStage, EPAControl)));
    }


}
