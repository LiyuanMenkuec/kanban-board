package view.zusammenfassungderBehandlungsschritt;

import controller.EPAController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Untersuchungsbericht;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Untersuchung bericht wahl controller.
 */
public class UntersuchungBerichtViewController extends AnchorPane {
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
    private VBox Behandlung;

    @FXML
    private VBox Medikamente;
    @FXML
    private Button back;
    @FXML
    private VBox Notiz;
    private Untersuchungsbericht untersuchungsbericht;
    private EPAController epaController;
    private Stage mainStage;
    private ArrayList<Untersuchungsbericht> berichtListe;
    /**
     * Instantiates a new Untersuchung bericht wahl controller.
     *
     * @param untersuchungsbericht the untersuchungsbericht
     */
    public UntersuchungBerichtViewController(ArrayList<Untersuchungsbericht>berichtliste, Untersuchungsbericht untersuchungsbericht, EPAController epaController, Stage mainStage){
        this.untersuchungsbericht=untersuchungsbericht;
        this.epaController=epaController;
        this.mainStage=mainStage;
        this.berichtListe=berichtliste;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/zusammenfassungderBehandlungsschritt/untersuchungberichtview.fxml"));
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
        Date.setText(untersuchungsbericht.getDatum().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());
        Date.setVisible(true);
        Symtome.setText(untersuchungsbericht.getDiagnose());
        Symtome.setVisible(true);
        ICD.setText(untersuchungsbericht.getICD());
        ICD.setVisible(true);
        Behandlung.getChildren().add(new Text(untersuchungsbericht.getBehandlung()));
        List<String> medikamente = untersuchungsbericht.getMed();
        for(int i=0; i< medikamente.size();i++){
            Medikamente.getChildren().add(new Text(medikamente.get(i)));
        }
        List<String> notizen = untersuchungsbericht.getNotes();
        for(int i=0;i< notizen.size();i++){
            Notiz.getChildren().add(new Text(notizen.get(i)));
        }
    }

    /**
     * Is choosen boolean.
     *
     * @return the boolean
     */
    public boolean isChoosen(){return ChooseButton.isSelected();}

    /**
     * Get untersuchung untersuchungsbericht.
     *
     * @return the untersuchungsbericht.
     */

    @FXML
    void zumZusammenfassung(ActionEvent event) {
        mainStage.setScene(new Scene(new zusammenfassungsController(mainStage,epaController,berichtListe)));
    }

    /**
     * Set button disable.
     */
    public void setButtonDisable(){ChooseButton.setDisable(true);ChooseButton.setVisible(false);}

}
