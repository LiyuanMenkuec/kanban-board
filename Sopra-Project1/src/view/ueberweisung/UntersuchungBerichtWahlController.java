package view.ueberweisung;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.TitledPane;
import javafx.fxml.FXML;
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
 * The type Untersuchung bericht wahl controller.
 */
public class UntersuchungBerichtWahlController extends TitledPane {
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
    private VBox Notiz;
    private Untersuchungsbericht untersuchungsbericht;

    /**
     * Instantiates a new Untersuchung bericht wahl controller.
     *
     * @param untersuchungsbericht the untersuchungsbericht
     */
    public UntersuchungBerichtWahlController(Untersuchungsbericht untersuchungsbericht){
        this.untersuchungsbericht=untersuchungsbericht;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ueberweisung/untersuchung.fxml"));
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
     * @return the untersuchungsbericht
     */
    public Untersuchungsbericht getUntersuchung(){return untersuchungsbericht;}

    /**
     * Set button disable.
     */
    public void setButtonDisable(){ChooseButton.setDisable(true);ChooseButton.setVisible(false);}

}
