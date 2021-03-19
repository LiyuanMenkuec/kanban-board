package view.center;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.ProjectController;
import controller.TeamController;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;

import model.Project;
import model.Stage;
import model.Task;
import model.Worker;
import view.MainWindow;
import view.auis.ProjectAUI;

public class ViewKanBanBoardController extends GridPane implements ProjectAUI,Initializable{
	private MainWindow parent;
	
	@FXML
	private ListView<Task> lstNew;
	@FXML
	private ListView<Task> lstAnalyse;
	@FXML
	private ListView<Task> lstAnalyseDone;
	@FXML
	private ListView<Task> lstImpl;
	@FXML
	private ListView<Task> lstImplDone;
	@FXML
	private ListView<Task> lstTest;
	@FXML
	private ListView<Task> lstTestDone;
	@FXML
	private ListView<Task> lstDone;
	@FXML
	private ListView<Worker> lstUnassignedTeamWorkers;
	@FXML
	private ListView<Task> lstCancelledTasks;
	@FXML
	private Button newTaskButton;
	
	public ViewKanBanBoardController(MainWindow parent) {
		this.parent=parent;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewKanBanBoard.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	@FXML
	private void onChangedTasks(Event event) {
		Object list = event.getSource();
		
		if(list instanceof ListView<?>) {
			@SuppressWarnings("unchecked")
			Task task=(Task) ((ListView<Task>) list).getSelectionModel().getSelectedItem();
			parent.getCurrentSelected().setCurrentWorker(null);
			parent.getCurrentSelected().setCurrentComment(null);
			parent.getCurrentSelected().setCurrentTask(task);
		}
	}
	
	@FXML
    private void onChangedWorkers(Event event) {
		Object list = event.getSource();
		
		if(list instanceof ListView<?>) {
			@SuppressWarnings("unchecked")
			Worker worker=(Worker)((ListView<Worker>) list).getSelectionModel().getSelectedItem();
			parent.getCurrentSelected().setCurrentTask(null);
			parent.getCurrentSelected().setCurrentComment(null);
			parent.getCurrentSelected().setCurrentWorker(worker);
		}
    }
	
	@FXML
	private void createNewTask(Event event) {
		parent.getCurrentSelected().setCurrentWorker(null);
		parent.getCurrentSelected().setCurrentComment(null);
		parent.getCurrentSelected().setCurrentTask(new Task());
	}
	
	public void refresh() {
		Project currentProject=parent.getCurrentSelected().getCurrentProject();
		if(currentProject!=null) {
			ProjectController proController=parent.getCurrentMainController().getProjectController();
			TeamController teamController=parent.getCurrentMainController().getTeamController();
			
			
			lstNew.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.NEW)));
			lstAnalyse.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.ANALYSE)));
			lstAnalyseDone.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.ANALYSEDONE)));
			lstImpl.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.IMPLEMENTATION)));
			lstImplDone.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.IMPLEMENTATIONDONE)));
			lstTest.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.TEST)));
			lstTestDone.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.TESTDONE)));
			lstDone.setItems(FXCollections.observableArrayList(proController.getProjectAktiveTasksInStage(currentProject, Stage.DONE)));
			lstUnassignedTeamWorkers.setItems(FXCollections.observableArrayList(teamController.getUnassignedTaskWorkersFromProject(currentProject)));
			lstCancelledTasks.setItems(FXCollections.observableArrayList(proController.getProjectArchivedTasks(currentProject)));
			
			newTaskButton.setDisable(!currentProject.isActive());
			
		}else {
			parent.showNotification("Sie müssen erst ein Projekt auswählen!", true);
			parent.showMainEmpty();
		}
		
	}

	@Override
	public void refreshTeamList() {
		refresh();
	}

	@Override
	public void refreshProjectData() {
		
	}

	@Override
	public void refreshTaskList() {
		refresh();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lstNew.setCellFactory(tasks -> new TaskCell());
		lstAnalyse.setCellFactory(tasks -> new TaskWorkerCell(parent));
		lstAnalyseDone.setCellFactory(tasks -> new TaskCell());
		lstImpl.setCellFactory(tasks -> new TaskWorkerCell(parent));
		lstImplDone.setCellFactory(tasks -> new TaskCell());
		lstTest.setCellFactory(tasks -> new TaskWorkerCell(parent));
		lstTestDone.setCellFactory(tasks -> new TaskCell());
		lstDone.setCellFactory(tasks -> new TaskCell());
		lstUnassignedTeamWorkers.setCellFactory(tasks -> new WorkerCell());
		lstCancelledTasks.setCellFactory(tasks -> new TaskCell());	
	}
	
}
