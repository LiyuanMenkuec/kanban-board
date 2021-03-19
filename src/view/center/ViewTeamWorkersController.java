package view.center;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import model.Project;
import model.Worker;
import view.MainWindowController;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;

public class ViewTeamWorkersController extends GridPane implements Initializable, ProjectAUI, CompanyAUI {

	public MainWindowController parent;

	private final URL viewFile = getClass().getResource("/view/center/ViewTeamWorkers.fxml");

	@FXML
	private ListView<Worker> lstSelectTeamWorkers;

	@FXML
	private ListView<Worker> lstUnassignedWorkersList;

	@FXML
	private Button btnTeamWorkerAdd;

	@FXML
	private Button btnTeamWorkerRemove;

	public ViewTeamWorkersController(MainWindowController parent) {
		this.parent = parent;

		try {
			FXMLLoader loader = new FXMLLoader(this.viewFile);

			loader.setRoot(this);
			loader.setController(this);

			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.refresh();
	}

	@FXML
	public void onListSelection(Event event) {
		@SuppressWarnings("unchecked")
		ListView<Worker> listView = (ListView<Worker>) event.getSource();

		Worker worker = listView.getSelectionModel().getSelectedItem();

		this.parent.getCurrentSelected().setCurrentWorker(worker);
	}

	@FXML
	public void onAddWorker(Event event) {
		Worker worker = lstUnassignedWorkersList.getSelectionModel().getSelectedItem();
		if (worker != null) {
			Project project = parent.getCurrentSelected().getCurrentProject();

			this.parent.getCurrentMainController().getTeamController().addWorkerToProject(project, worker);
		}
	}

	@FXML
	public void onRemoveWorker(Event event) {
		Worker worker = lstSelectTeamWorkers.getSelectionModel().getSelectedItem();
		if (worker != null) {
			Project project = parent.getCurrentSelected().getCurrentProject();

			this.parent.getCurrentMainController().getTeamController().removeWorkerFromProject(project, worker);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lstSelectTeamWorkers.setCellFactory(cell -> new WorkerCell());
		lstUnassignedWorkersList.setCellFactory(cell -> new WorkerCell());

		parent.getCurrentMainController().getTeamController().getProjectAUI().add(this);
	}

	public void refresh() {
		if (this.parent.getCurrentSelected().getCurrentProject() != null) {
			ArrayList<Worker> workers = this.parent.getCurrentSelected().getCurrentProject().getWorkers();
			ArrayList<Worker> unassignedWorkers = this.parent.getCurrentMainController().getWorkerController()
					.getUnassignedProjectWorkers();

			lstSelectTeamWorkers.getItems().clear();
			lstSelectTeamWorkers.getItems().addAll(workers);

			lstUnassignedWorkersList.getItems().clear();
			lstUnassignedWorkersList.getItems().addAll(unassignedWorkers);

			btnTeamWorkerAdd.setDisable(unassignedWorkers.isEmpty());
			btnTeamWorkerRemove.setDisable(workers.isEmpty());
		}
	}

	@Override
	public void refreshProjectList() {
		this.refresh();
	}

	@Override
	public void refreshProjectData() {
		this.refresh();
	}

	@Override
	public void refreshTaskList() {
		this.refresh();
	}

	@Override
	public void refreshWorkerList() {
		this.refresh();
	}

	@Override
	public void showError(String error) {
		this.refresh();
	}

	@Override
	public void refreshTeamList() {
		this.refresh();
	}
}
