package view.center;

import java.io.IOException;

import java.util.ArrayList;

import controller.MainController;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import model.Project;

import view.MainWindow;

public class ViewTaskStatsTable extends BorderPane{
	
	private MainController mainController;
	ArrayList<TaskRank> taskRanks= new ArrayList<>();
	Project project;
	
	@FXML
    private TableView<TaskRank> taskStatsTable;

    @FXML
    private TableColumn<TaskRank, String> taskNameColumn;

    @FXML
    private TableColumn<TaskRank, String> taskNewColumn;

    @FXML
    private TableColumn<TaskRank, String> taskAnalyseColumn;

    @FXML
    private TableColumn<TaskRank, String> taskAnalyseDoneColumn;

    @FXML
    private TableColumn<TaskRank, String> taskImplementationColumn;

    @FXML
    private TableColumn<TaskRank, String> taskImplementationDoneColumn;

    @FXML
    private TableColumn<TaskRank, String> taskTestColumn;

    @FXML
    private TableColumn<TaskRank, String> taskTestDoneColumn;
    
    @FXML
    private TableColumn<TaskRank, String> taskDoneColumn;

    @FXML
    private TableColumn<TaskRank, String> taskActiveColumn;

    @FXML
    private TableColumn<TaskRank, String> taskIdleColumn;

    @FXML
    private TableColumn<TaskRank, String> taskTotalColumn;
	
	public ViewTaskStatsTable(MainWindow parent, Project pProject) {
		project=pProject;
		this.mainController = parent.getCurrentMainController();
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTaskStats.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void initialize() {
		taskNameColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("name"));
		taskNewColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageNew"));
		taskAnalyseColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageAnalyse"));
		taskAnalyseDoneColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageAnalyseDone"));
		taskImplementationColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageImplementation"));
		taskImplementationDoneColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageImplementationDone"));
		taskTestColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageTest"));
		taskTestDoneColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageTestDone"));
		taskDoneColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("stageDone"));
		taskActiveColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("active"));
		taskIdleColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("idle"));
		taskTotalColumn.setCellValueFactory(new PropertyValueFactory<TaskRank, String>("total"));
		String[][] taskStats=mainController.getStatisticsController().taskStats(project);
		for(int i=0;i<taskStats.length;i++) {
			taskRanks.add(new TaskRank(taskStats[i][0], taskStats[i][1], taskStats[i][2], taskStats[i][3], taskStats[i][4], taskStats[i][5], taskStats[i][6], taskStats[i][7], taskStats[i][8], taskStats[i][9], taskStats[i][10], taskStats[i][11]));
			//System.out.println("Name: "+taskRanks.get(i).getName()+", new: "+taskRanks.get(i).getStageNew()+", analyse: "+taskRanks.get(i).getStageAnalyse()+",analyseDone: "+taskRanks.get(i).getStageAnalyseDone()+", implementation: "+taskRanks.get(i).getStageImplementation()+", ImplementationDone: "+taskRanks.get(i).getStageImplementationDone()+", Test: "+taskRanks.get(i).getStageTest()+", TestDone: "+taskRanks.get(i).getStageTestDone()+", Done: "+taskRanks.get(i).getStageDone()+", active: "+taskRanks.get(i).getActive()+", Idle: "+taskRanks.get(i).getIdle()+", Total: "+taskRanks.get(i).getTotal());
		}
						
		refreshTaskStats();
	}
	
	public void refreshTaskStats() {
		ObservableList<TaskRank> data=taskStatsTable.getItems();
		data.clear();
		data.setAll(taskRanks);
	}
	
	public class TaskRank{
		
		private String name;
		private String stageNew;
		private String stageAnalyse;
		private String stageAnalyseDone;
		private String stageImplementation;
		private String stageImplementationDone;
		private String stageTest;
		private String stageTestDone;
		private String stageDone;
		private String active;
		private String idle;
		private String total;
		
		public TaskRank(String pName, String pStagenNew, String pStageAnalyse, String pStageAnalyseDone, String pStageImplementation, String pStageImplementationDone, String pStageTest, String pStageTestDone, String pStageDone, String pActive, String pIdle, String pTotal) {
			name=pName;
			stageNew=pStagenNew;
			stageAnalyse=pStageAnalyse;
			stageAnalyseDone=pStageAnalyseDone;
			stageImplementation=pStageImplementation;
			stageImplementationDone=pStageImplementationDone;
			stageTest=pStageTest;
			stageTestDone=pStageTestDone;
			stageDone=pStageDone;
			active=pActive;
			idle=pIdle;
			total=pTotal;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the stageNew
		 */
		public String getStageNew() {
			return stageNew;
		}

		/**
		 * @param stageNew the stageNew to set
		 */
		public void setStageNew(String stageNew) {
			this.stageNew = stageNew;
		}

		/**
		 * @return the stageAnalyse
		 */
		public String getStageAnalyse() {
			return stageAnalyse;
		}

		/**
		 * @param stageAnalyse the stageAnalyse to set
		 */
		public void setStageAnalyse(String stageAnalyse) {
			this.stageAnalyse = stageAnalyse;
		}

		/**
		 * @return the stageAnalysDone
		 */
		public String getStageAnalyseDone() {
			return stageAnalyseDone;
		}

		/**
		 * @param stageAnalysDone the stageAnalysDone to set
		 */
		public void setStageAnalyseDone(String stageAnalysDone) {
			this.stageAnalyseDone = stageAnalysDone;
		}

		/**
		 * @return the stageImplementation
		 */
		public String getStageImplementation() {
			return stageImplementation;
		}

		/**
		 * @param stageImplementation the stageImplementation to set
		 */
		public void setStageImplementation(String stageImplementation) {
			this.stageImplementation = stageImplementation;
		}

		/**
		 * @return the stageImplementationDone
		 */
		public String getStageImplementationDone() {
			return stageImplementationDone;
		}

		/**
		 * @param stageImplementationDone the stageImplementationDone to set
		 */
		public void setStageImplementationDone(String stageImplementationDone) {
			this.stageImplementationDone = stageImplementationDone;
		}

		/**
		 * @return the stageTest
		 */
		public String getStageTest() {
			return stageTest;
		}

		/**
		 * @param stageTest the stageTest to set
		 */
		public void setStageTest(String stageTest) {
			this.stageTest = stageTest;
		}

		/**
		 * @return the stageTestDone
		 */
		public String getStageTestDone() {
			return stageTestDone;
		}

		/**
		 * @param stageTestDone the stageTestDone to set
		 */
		public void setStageTestDone(String stageTestDone) {
			this.stageTestDone = stageTestDone;
		}

		/**
		 * @return the stageDone
		 */
		public String getStageDone() {
			return stageDone;
		}

		/**
		 * @param stageDone the stageDone to set
		 */
		public void setStageDone(String stageDone) {
			this.stageDone = stageDone;
		}

		/**
		 * @return the active
		 */
		public String getActive() {
			return active;
		}

		/**
		 * @param active the active to set
		 */
		public void setActive(String active) {
			this.active = active;
		}

		/**
		 * @return the idle
		 */
		public String getIdle() {
			return idle;
		}

		/**
		 * @param idle the idle to set
		 */
		public void setIdle(String idle) {
			this.idle = idle;
		}

		/**
		 * @return the total
		 */
		public String getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(String total) {
			this.total = total;
		}	
	}

}
