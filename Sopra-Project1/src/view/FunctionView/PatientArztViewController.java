package view.FunctionView;
import controller.EPAController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Arzt;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The type Patient arzt view controller.
 */
public class PatientArztViewController extends AnchorPane {
    @FXML
    private Text aVorname;

    @FXML
    private Text aNachname;

    @FXML
    private Text aFach;

    @FXML
    private Text aNum;

    @FXML
    private Text aTel;

    @FXML
    private Button toPFunction;
    @FXML
    private ImageView ArztImage;
    private EPAController epaController;
    private Stage mainStage;
    private Arzt hausArzt;

    /**
     * Instantiates a new Patient arzt view controller.
     *
     * @param primaryStage  the primary stage
     * @param hausArzt      the haus arzt
     * @param epaController the epa controller
     */
    public PatientArztViewController(Stage primaryStage, Arzt hausArzt, EPAController epaController){
        mainStage= primaryStage;
        this.hausArzt=hausArzt;
        this.epaController=epaController;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FunctionView/PatientArztView.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void init() throws IOException {
        aVorname.setText(hausArzt.getVorname());
        aVorname.setVisible(true);
        aNachname.setText(hausArzt.getNachname());
        aNachname.setVisible(true);
        aFach.setText(hausArzt.getFachrichtung());
        aFach.setVisible(true);
        aNum.setText(hausArzt.getNum());
        aNum.setVisible(true);
        aTel.setText(hausArzt.getTel());
        aTel.setVisible(true);
        Image icon = SwingFXUtils.toFXImage(ImageIO.read(this.getClass().getClassLoader().getResource("view/image/50115-200.png")), null);
        ArztImage.setImage(icon);
    }

    /**
     * To main function.
     *
     * @param a the a
     */
    @FXML
    void toMainFunction(ActionEvent a){
        mainStage.setScene(new Scene(new PatientMainViewController(mainStage,epaController)));
    }

}
