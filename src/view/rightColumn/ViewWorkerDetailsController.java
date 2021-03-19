package view.rightColumn;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import model.Worker;
import view.MainWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class ViewWorkerDetailsController extends SwitchedTitledPane implements Initializable{

	@FXML
	private Button btnAccept;
	
	@FXML
	private ImageView imgViewWorker;

	@FXML
	private TextField txtFirstName;

	@FXML
	private TextField txtLastName;

	@FXML
	private DatePicker dtBirthdate;

	@FXML
	private ChoiceBox<String> chBxState;

	private MainWindow parent;
	private MainController mainController;
	private Worker latestCurrentWorker;
	private ObservableList<String> statusChoiceBox;

	public ViewWorkerDetailsController(MainWindow parent) {
		this.parent = parent;
		this.mainController = parent.getCurrentMainController();
		latestCurrentWorker = null;
		statusChoiceBox = FXCollections.observableArrayList("aktiv", "inaktiv");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewWorkerDetails.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ioExc) {

			ioExc.printStackTrace();
		}
	}
	@FXML
	void onChangedContent() {
		if(txtFirstName.getText().isEmpty() || txtLastName.getText().isEmpty()) {
			btnAccept.setDisable(true);
		}else {
			btnAccept.setDisable(false);
		}
	}

	@FXML
	void addWorkerImage() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bitte ein Bild auswählen");
		fileChooser.getExtensionFilters()
				.addAll(new FileChooser.ExtensionFilter("PNG , JPEG , JPG", "*.png", "*.jpeg", "*.jpg"));
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());
		if (file != null) {
			Image image=new Image(file.toURI().toString());
			imgViewWorker.setImage(image);
		}

	}

	@FXML
	void cancel() {
		refresh();
	}

	@FXML
	void accept() {
		if (txtFirstName.getText() != null && txtLastName.getText() != null && dtBirthdate.getValue() != null
				&& chBxState.getValue() != null && imgViewWorker.getImage() != null) {
			Worker newWorker = new Worker();
			if (chBxState.getValue() == "inaktiv") {
				newWorker.setActive(false);
			}
			newWorker.setFirstname(txtFirstName.getText());
			newWorker.setLastname(txtLastName.getText());
			newWorker.setBirthDate(dtBirthdate.getValue());
			newWorker.setPicture(imgViewWorker.getImage());

			if (mainController.getWorkerController().getAllWorkers()
					.contains(parent.getCurrentSelected().getCurrentWorker())) {
				mainController.getWorkerController().updateWorker(parent.getCurrentSelected().getCurrentWorker(),
						newWorker);
			} else {
				mainController.getWorkerController().addWorker(newWorker);
			}

		}
		update(parent.getCurrentSelected().getCurrentWorker());
	}

	@Override
	public void refresh() {
		Worker currentWorker=parent.getCurrentSelected().getCurrentWorker();
		if(currentWorker!=null) {
			if(!currentWorker.equals(latestCurrentWorker)) {
				setExpandDetails(true);
			}else {
				setExpandDetails(false);
			}
			setContentVisible(true);
			this.update(currentWorker);
		}else {
			setContentVisible(false);
		}
		latestCurrentWorker=currentWorker;
	}

	private void update(Worker currentWorker) {
		btnAccept.setDisable(false);
		txtFirstName.setText(currentWorker.getFirstname());
		txtLastName.setText(currentWorker.getLastname());
		imgViewWorker.setImage(currentWorker.getPicture());
		dtBirthdate.setValue(currentWorker.getBirthDate());
		if (currentWorker.isActive()) {
			chBxState.setValue("aktiv");
		} else {
			chBxState.setValue("inaktiv");
		}
		if (latestCurrentWorker != currentWorker) {
			this.setExpanded(true);
		}
		onChangedContent();
			
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dtBirthdate.setEditable(false);
		chBxState.setItems(statusChoiceBox);
	}
}
