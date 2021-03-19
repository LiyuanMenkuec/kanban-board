package view.center;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import view.auis.CompanyAUI;
import view.MainWindow;
import model.Worker;
import view.auis.WorkerAUI;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class ViewGlobalWorkersController extends GridPane implements WorkerAUI, CompanyAUI {

	@FXML
	private ListView<Worker> allWorkersList;
	
	private MainWindow parent;

	public ViewGlobalWorkersController(MainWindow parent) {
		this.parent = parent;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewGlobalWorkers.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	@FXML
	void initialize() {
		allWorkersList.setCellFactory(worker -> new WorkerCell());
	

	}
	@FXML
	public void onSelectWorker(MouseEvent event) {
		Worker selectedWorker=allWorkersList.getSelectionModel().getSelectedItem();
		parent.getCurrentSelected().setCurrentWorker(selectedWorker);		
	}

	
	@FXML
	void createWorker(ActionEvent event) {
		parent.getCurrentSelected().setCurrentWorker(new Worker());
	}
	
	
	@Override
	public void refreshProjectList() {

	}

	@Override
	public void refreshWorkerList() {
		refresh();

	}

	@Override
	public void showError(String error) {

	}

	@Override
	public void refreshWorkerData() {
		// TODO Auto-generated method stub
		
	}
	public void refresh() {
		ArrayList<Worker> allWorkers=parent.getCurrentMainController().getWorkerController().getAllWorkers();
		allWorkersList.getItems().clear();
		allWorkersList.getItems().addAll(allWorkers);
	}
}