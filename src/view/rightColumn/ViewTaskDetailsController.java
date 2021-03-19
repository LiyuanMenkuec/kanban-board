package view.rightColumn;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.MainController;
import controller.TaskController;
import controller.TeamController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Project;
import model.Stage;
import model.Task;
import model.Worker;
import view.MainWindow;

public class ViewTaskDetailsController extends SwitchedTitledPane implements Initializable{
	private MainWindow parent;
	private Task latestCurrentTask;
	private ArrayList<String> choices;
	
	@FXML
	TextField txtName;
	@FXML
	DatePicker dtDeadline;
	@FXML
	ComboBox<Worker> cBoxTaskDetailsWorker;
	@FXML
	ChoiceBox<String> cBoxState;
	@FXML
	TextArea txtAreaDescription;
	@FXML
	Button btnRemoveWorker;
	@FXML
	Button btnFinishedStage;
	@FXML
	Button btnAccept;
	
	public ViewTaskDetailsController(MainWindow parent) {
		this.parent=parent;
		latestCurrentTask=null;
		choices=new ArrayList<>();
		choices.add("aktiv");
		choices.add("abgebrochen");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTaskDetails.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ioExc) {
			
			ioExc.printStackTrace();
		}
	}

	@FXML
	private void onChangedContent() {
		if(txtName.getText().isEmpty() || !parent.getCurrentSelected().getCurrentProject().isActive()) {
			btnAccept.setDisable(true);
		}
		else{
			btnAccept.setDisable(false);
		}
	}
	@FXML
	private void onRemoveWorker() {
		TaskController currentTC = parent.getCurrentMainController().getTaskController();
		currentTC.removeWorker(parent.getCurrentSelected().getCurrentTask());
		update(parent.getCurrentSelected().getCurrentTask());
	}
	@FXML
	private void finishStage() {
		Task currentTask=parent.getCurrentSelected().getCurrentTask();
		parent.getCurrentMainController().getTaskController().taskDone(currentTask);
		update(parent.getCurrentSelected().getCurrentTask());
	}
	@FXML
	private void onRemoveTask() {
		parent.getCurrentMainController().getTaskController().removeTask(parent.getCurrentSelected().getCurrentProject(), parent.getCurrentSelected().getCurrentTask());
		parent.getCurrentSelected().setCurrentTask(null);
	}
	@FXML
	private void accept() {
		Task currentTask=parent.getCurrentSelected().getCurrentTask();
		Task task=new Task();
		Project currentProject=parent.getCurrentSelected().getCurrentProject();
		TaskController currentTC=parent.getCurrentMainController().getTaskController();
		
		task.setCancelled(cBoxState.getValue().equals(choices.get(1)));
		task.setDeadline(dtDeadline.getValue().atStartOfDay());
		task.setTitle(txtName.getText());
		task.setDescription(txtAreaDescription.getText());
		currentTC.addWorker(task, cBoxTaskDetailsWorker.getSelectionModel().getSelectedItem());
		
		if(currentTC.getProjectOfTask(currentTask)!=null) {
			currentTC.updateTask(currentTask, task);
		}else {
			currentTC.addTask(currentProject, task);
		}
		update(currentTask);
		
	}
	@FXML
	private void cancel() {
		Task currentTask=parent.getCurrentSelected().getCurrentTask();
		MainController mainController=parent.getCurrentMainController();
		TaskController currentTC=mainController.getTaskController();
		Project currentProject=currentTC.getProjectOfTask(currentTask);
		if(currentProject!=null) {
			update(parent.getCurrentSelected().getCurrentTask());
		}else {
			parent.getCurrentSelected().setCurrentTask(null);
		}
		
	}
	@Override
	public void refresh() {
		Task currentTask=parent.getCurrentSelected().getCurrentTask();
		if(currentTask!=null) {
			if(!currentTask.equals(latestCurrentTask)) {
				setExpandDetails(true);
			}else {
				setExpandDetails(false);
			}
			setContentVisible(true);
			this.update(currentTask);
		}else {
			setContentVisible(false);
		}
		latestCurrentTask=currentTask;
	}
	
	private void update(Task currentTask) {
		MainController mainController=parent.getCurrentMainController();
		TaskController currentTC=mainController.getTaskController();
		TeamController currentTeamC=mainController.getTeamController();
		Project currentProject=currentTC.getProjectOfTask(currentTask);
		
		txtName.setDisable(false);
		dtDeadline.setDisable(false);
		cBoxTaskDetailsWorker.setDisable(false);
		cBoxState.setDisable(false);
		txtAreaDescription.setDisable(false);
		btnRemoveWorker.setDisable(false);
		btnFinishedStage.setDisable(false);
		
		
		txtName.setText(currentTask.getTitle());
		txtAreaDescription.setText(currentTask.getDescription());
		if(currentTask.getDeadline()!=null) {
			dtDeadline.setValue(currentTask.getDeadline().toLocalDate());
		}else {
			dtDeadline.setValue(LocalDate.now().plusDays(7));
		}
		
		Worker currentWorker=mainController.getTaskController().getWorker(currentTask);
		ArrayList<Worker> allUnassignedWorker=new ArrayList<>();
		
		allUnassignedWorker.add(null);
		if(currentProject!=null) {
			if(currentWorker==null) {
				allUnassignedWorker.addAll(currentTeamC.getUnassignedTaskWorkersFromProject(currentProject));
				cBoxTaskDetailsWorker.setItems(FXCollections.observableArrayList(allUnassignedWorker));
				cBoxTaskDetailsWorker.getSelectionModel().selectFirst();
			}else {
				allUnassignedWorker.add(currentWorker);
				allUnassignedWorker.addAll(currentTeamC.getUnassignedTaskWorkersFromProject(currentProject));
				cBoxTaskDetailsWorker.setItems(FXCollections.observableArrayList(allUnassignedWorker));
				cBoxTaskDetailsWorker.getSelectionModel().selectFirst();
				cBoxTaskDetailsWorker.getSelectionModel().selectNext();
			}
			cBoxTaskDetailsWorker.setDisable(false);
		}else {
			cBoxTaskDetailsWorker.setItems(FXCollections.observableArrayList(allUnassignedWorker));
			cBoxTaskDetailsWorker.getSelectionModel().selectFirst();
			cBoxTaskDetailsWorker.setDisable(true);
		}
		
		
		
		if(currentTask.getCurrentStage()==Stage.TESTDONE) {
			btnFinishedStage.setVisible(true);
			btnRemoveWorker.setVisible(false);
		}else {
			if(currentTask.getCurrentWorker()!=null) {
				btnFinishedStage.setVisible(true);
				btnRemoveWorker.setVisible(true);
			}else {
				btnFinishedStage.setVisible(false);
				btnRemoveWorker.setVisible(false);
			}
		}
		if(currentTask.isCancelled()) {
			txtName.setDisable(true);
			dtDeadline.setDisable(true);
			cBoxTaskDetailsWorker.setDisable(true);
			cBoxState.setDisable(false);
			txtAreaDescription.setDisable(true);
			btnRemoveWorker.setDisable(true);
			btnFinishedStage.setDisable(true);
			
			cBoxState.setValue(choices.get(1));
		}else {
			cBoxState.setValue(choices.get(0));
			cBoxState.setDisable(true);
		}
		btnAccept.setDisable(true);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		dtDeadline.setEditable(false);
		cBoxTaskDetailsWorker.setCellFactory(worker -> new WorkerCell());
		cBoxTaskDetailsWorker.setButtonCell(new ListCell<Worker>() {
            public void updateItem(Worker worker, boolean empty) {
            	super.updateItem(worker, empty);
                if (empty || worker == null) {
                    setText("Nicht zugewiesen");
                } else {
                    setText(worker.getFirstname()+", "+worker.getLastname());
                }
            }
        });
		cBoxState.setItems(FXCollections.observableArrayList(choices));
		cBoxState.setOnMouseClicked(event -> {
        	onChangedContent();
        });
		txtAreaDescription.setWrapText(true);
		
	}
}
