package view.rightColumn;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import controller.MainController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

import model.Project;
import view.MainWindow;
import view.auis.CompanyAUI;

public class ViewProjectDetailsController extends SwitchedTitledPane{
	
	private MainController mainController;
	private MainWindow parent;
	private Project latestCurrentProject;
	
	@FXML
	TextField txtProjectDetailsName;
	
	@FXML
	DatePicker datePickerProjectDetailsDeadline;
	
	@FXML
	ChoiceBox<String> choiceBoxProjectDetailsState;
	
	@FXML
	TextArea txtAreaProjectDetailsDescription;
	
	@FXML
	Button btnProjectDetailsShowTeam;
	
	@FXML
	Button btnProjectDetailsDeleteProject;
	
	@FXML
	Button btnProjectDetailsAcceptChanges;
	
	@FXML
	Button btnProjectDetailsQuitChanges;
	
	public ViewProjectDetailsController(MainWindow parent) {
		this.parent=parent;
		latestCurrentProject=null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProjectDetails.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ioExc) {
			
			ioExc.printStackTrace();
		}
		mainController=parent.getCurrentMainController();
		setContentVisible(true);
		
		choiceBoxProjectDetailsState.setOnMouseClicked(event -> {
        	detectChanges(event);
        });
		datePickerProjectDetailsDeadline.setOnMouseClicked(event -> {
			detectChanges(event);
        });
		datePickerProjectDetailsDeadline.setEditable(false);
	}

	@Override
	public void refresh() {
		Project currentProject=parent.getCurrentSelected().getCurrentProject();
		if(currentProject!=null) {
			if(!currentProject.equals(latestCurrentProject)) {
				setExpandDetails(true);
			}else {
				setExpandDetails(false);
			}
			setContentVisible(true);
			this.update(currentProject);
		}else {
			setContentVisible(false);
		}
		latestCurrentProject=currentProject;
	}
	
	private void update(Project currentProject) {
		if(!parent.getCurrentSelected().getCurrentProject().isActive()) {
			btnProjectDetailsShowTeam.setDisable(true);
		}else {
			btnProjectDetailsShowTeam.setDisable(false);
		}
		this.btnProjectDetailsAcceptChanges.setDisable(true);
		this.btnProjectDetailsQuitChanges.setDisable(true);
		
		if(!mainController.getCompany().getProjects().contains(currentProject)) {
			this.txtProjectDetailsName.setText("");
			this.datePickerProjectDetailsDeadline.setValue(LocalDate.now().plusDays(7));
			this.datePickerProjectDetailsDeadline.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
			this.choiceBoxProjectDetailsState.setItems(FXCollections.observableList(projectStateOptions()));
			this.choiceBoxProjectDetailsState.setValue("aktiv");
			this.txtAreaProjectDetailsDescription.setText("");
			
			this.btnProjectDetailsDeleteProject.setDisable(true);
			this.btnProjectDetailsShowTeam.setDisable(true);
		}			
		else {
			this.txtProjectDetailsName.setText(currentProject.getName());
			this.datePickerProjectDetailsDeadline.setValue(currentProject.getDeadline().toLocalDate());
			this.choiceBoxProjectDetailsState.setItems(FXCollections.observableList(projectStateOptions()));
			this.choiceBoxProjectDetailsState.setValue(projectStateOptions().get(projectStateToInt(currentProject)));
			this.txtAreaProjectDetailsDescription.setText(currentProject.getDescription());
		}
		
	}
	
	
	@FXML
	private void showTeam(ActionEvent event) {
		parent.showMainTeam();
		this.btnProjectDetailsDeleteProject.setDisable(false);
	}
	
	@FXML
	private void deleteProject(ActionEvent event) {
		Alert alert = new Alert(AlertType.CONFIRMATION, "Soll dieses Projekt wirklich gelöscht werden?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.showAndWait();
		if (alert.getResult() == ButtonType.YES) {
			mainController.getProjectController().removeProject(parent.getCurrentSelected().getCurrentProject());
			parent.getCurrentSelected().setCurrentProject(null);
			
			ArrayList<CompanyAUI> compAUI = mainController.getProjectController().getCompanyAUI();
			for(CompanyAUI cAUI:compAUI) {
				cAUI.refreshProjectList();
			}
			parent.showMainEmpty();
		}
		
	}
	
	@FXML
	private void saveProjectChanges(ActionEvent event) {
		Project newProject = new Project();
		newProject.setName(txtProjectDetailsName.getText());
		newProject.setDeadline(LocalDateTime.of(datePickerProjectDetailsDeadline.getValue(),LocalTime.MAX));
		newProject.setDescription(txtAreaProjectDetailsDescription.getText());
		
		if(mainController.getCompany().getProjects().contains(parent.getCurrentSelected().getCurrentProject())) {
			newProject.setActive(!projectStateToBoolean(newProject));
			mainController.getProjectController().updateProject(parent.getCurrentSelected().getCurrentProject(),newProject);
			
		}else {
			newProject.setActive(projectStateToBoolean(newProject));
			parent.getCurrentSelected().setCurrentProject(newProject);
			mainController.getProjectController().addProject(newProject);
			
		}
		
		ArrayList<CompanyAUI> compAUI = mainController.getProjectController().getCompanyAUI();
		for(CompanyAUI cAUI:compAUI) {
			cAUI.refreshProjectList();
		}
		this.btnProjectDetailsDeleteProject.setDisable(false);
		this.btnProjectDetailsShowTeam.setDisable(false);
		update(parent.getCurrentSelected().getCurrentProject());
	}
	
	@FXML
	private void quitProjectChanges(ActionEvent event) {
		update(parent.getCurrentSelected().getCurrentProject());
	}
	
	@FXML
	private void detectChanges(Event event) {
		if(txtProjectDetailsName.getText().isEmpty()) {
			btnProjectDetailsAcceptChanges.setDisable(true);
		}
		else{
			btnProjectDetailsAcceptChanges.setDisable(false);
		}
		btnProjectDetailsQuitChanges.setDisable(false);
	}
	
	//Hilfsmethoden
	private List<String> projectStateOptions() {
		List<String> states = new ArrayList<>();
		states.add("aktiv");
		states.add("archiviert");
		return states;
	}
	
	private int projectStateToInt(Project project) {
		if(project.isActive()) {
			return 0;
		}else {
			return 1;
		}
	}
	
	private boolean projectStateToBoolean(Project project) {
		if(choiceBoxProjectDetailsState.getValue().equals("aktiv")) {
			return true;
		}else {
			return false;
		}
	}

}
