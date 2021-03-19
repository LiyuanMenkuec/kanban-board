package view.ueberweisung;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Untersuchungsbericht;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * The type Untersuchungs wahl controller.
 */
public class UntersuchungsWahlController extends TitledPane {
    @FXML
    private Text Date;

    @FXML
    private RadioButton GrundChoose;

    @FXML
    private Text Grund;

    @FXML
    private RadioButton SymtomeChoose;

    @FXML
    private Text Symtome;

    @FXML
    private RadioButton ICDChoose;

    @FXML
    private Text ICD;

    @FXML
    private VBox Behandlung;

    @FXML
    private VBox Medikamente;

    @FXML
    private VBox Notiz;

    @FXML
    private RadioButton BehandlungChoose;

    @FXML
    private RadioButton MedChoose;

    @FXML
    private RadioButton NotizChoose;

    @FXML
    private RadioButton chooseThis;
    private Untersuchungsbericht untersuchung;

    /**
     * Instantiates a new Untersuchungs wahl controller.
     *
     * @param
     */
    public UntersuchungsWahlController(Untersuchungsbericht untersuchung){
        this.untersuchung=untersuchung;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/UntersuchungsWahl.fxml"));
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
        Date.setText(untersuchung.getDatum().format( DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString());
        Date.setVisible(true);
        Symtome.setText(untersuchung.getDiagnose());
        Symtome.setVisible(true);
        ICD.setText(untersuchung.getICD());
        ICD.setVisible(true);
        Behandlung.getChildren().add(new Text(untersuchung.getBehandlung()));
        List<String> medikamente = untersuchung.getMed();
        for(int i=0; i< medikamente.size();i++){
            Medikamente.getChildren().add(new Text(medikamente.get(i)));

        }
        List<String> notizen = untersuchung.getNotes();
        for(int i=0;i< notizen.size();i++){
            Notiz.getChildren().add(new Text(notizen.get(i)));
        }
    }

    /**
     * Bericht choosen boolean.
     *
     * @return the boolean
     */
//  write method to check radiobuttons and set attribute in untersuchungsbericht
    public boolean BerichtChoosen(){return chooseThis.isSelected();}

    /**
     * Get untersuchung untersuchungsbericht.
     *
     * @return the untersuchungsbericht
     */
    public Untersuchungsbericht getUntersuchung(){return untersuchung;}
}


