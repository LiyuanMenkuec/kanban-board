package view.center;

import java.io.IOException;

import java.util.ArrayList;

import controller.MainController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;


import model.Project;

import model.Worker;
import view.MainWindow;


public class ViewStatisticsController extends BorderPane {//borderpane
	
	private MainWindow parent;
	private MainController mainController;
	ArrayList<WorkerRank> workerRanks=new ArrayList<>();
	
    @FXML
    private TableView<WorkerRank> workerRankingTable;
	
    @FXML
    private TableColumn<WorkerRank, String> workerFirstNameColumn;

    @FXML
    private TableColumn<WorkerRank, String> workerLastNameColumn;

    @FXML
    private TableColumn<WorkerRank, String> workerPointsColumn;

    @FXML
    private TableColumn<WorkerRank, String> maxTimeColumn;

    @FXML
    private TableColumn<WorkerRank, String> minTimeColumn;

    @FXML
    private TableColumn<WorkerRank, String> avgTimeColumn;
    
    @FXML
    private TabPane taskTabPane;


	public ViewStatisticsController(MainWindow parent) {
		this.parent = parent;
		this.mainController = parent.getCurrentMainController();
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewStatistics.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@FXML
	public void initialize() {
		workerFirstNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("firstname"));
		workerLastNameColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("lastname"));
		workerPointsColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("points"));
		maxTimeColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("max"));
		minTimeColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("min"));
		avgTimeColumn.setCellValueFactory(new PropertyValueFactory<WorkerRank, String>("avg"));
		ArrayList<Worker> workers= new ArrayList<>();
		workers.addAll(mainController.getWorkerController().getAllWorkers());
		ArrayList<Project> projects= new ArrayList<>();
		projects.addAll(mainController.getCompanyController().getActiveProjects());
		projects.addAll(mainController.getCompanyController().getArchivedProjects());
		String[][] ranking=mainController.getStatisticsController().workerRanking(workers, projects);
		for(int i=0;i<ranking.length;i++) {
			workerRanks.add(new WorkerRank(ranking[i][0], ranking[i][1], ranking[i][2], ranking[i][3], ranking[i][4], ranking[i][5]));
			//System.out.println("Vorname: "+workerRanks.get(i).getFirstname()+", Nachname: "+workerRanks.get(i).getLastname()+", Points: "+workerRanks.get(i).getPoints()+", max: "+workerRanks.get(i).getMax()+", min: "+workerRanks.get(i).getMin()+", avg: "+workerRanks.get(i).getAvg());	
		}
		refreshWorkerRanking();
		
		//ENDE WORKER RANKING - ANFANG TASKSTATS
		
		ArrayList<Tab> tabs=new ArrayList<>();
		for(int i=0;i<projects.size();i++) {
			Tab tab =new Tab(""+projects.get(i).getName());
			tab.setContent(new ViewTaskStatsTable(parent,projects.get(i)));
			tabs.add(tab);
		}
		taskTabPane.getTabs().addAll(tabs);
 	}
		
	public void refreshWorkerRanking() {
		ObservableList<WorkerRank> data=workerRankingTable.getItems();
		data.clear();
		data.setAll(workerRanks);
	}
	
	public void refreshTaskStats() {

	}
	
	public class WorkerRank{
		private String firstname;
		private String lastname;
		private String points;
		private String max;
		private String min;
		private String avg;
		
		public WorkerRank(String pFirstname, String pLastname, String pPoints, String pMax, String pMin, String pAvg) {
			firstname=pFirstname;
			lastname=pLastname;
			points=pPoints;
			max=pMax;
			min=pMin;
			avg=pAvg;
		}

		/**
		 * @return the firstname
		 */
		public String getFirstname() {
			return firstname;
		}

		/**
		 * @param firstname the firstname to set
		 */
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		/**
		 * @return the lastname
		 */
		public String getLastname() {
			return lastname;
		}

		/**
		 * @param lastname the lastname to set
		 */
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}

		/**
		 * @return the points
		 */
		public String getPoints() {
			return points;
		}

		/**
		 * @param points the points to set
		 */
		public void setPoints(String points) {
			this.points = points;
		}

		/**
		 * @return the max
		 */
		public String getMax() {
			return max;
		}

		/**
		 * @param max the max to set
		 */
		public void setMax(String max) {
			this.max = max;
		}

		/**
		 * @return the min
		 */
		public String getMin() {
			return min;
		}

		/**
		 * @param min the min to set
		 */
		public void setMin(String min) {
			this.min = min;
		}

		/**
		 * @return the avg
		 */
		public String getAvg() {
			return avg;
		}

		/**
		 * @param avg the avg to set
		 */
		public void setAvg(String avg) {
			this.avg = avg;
		}
	}
	
}


	
	
