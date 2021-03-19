package view;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import controller.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Comment;
import model.Company;
import model.TestDataFactory;
import view.center.ViewCommentController;
import view.center.ViewEmptyCenterController;
import view.center.ViewGlobalWorkersController;
import view.center.ViewKanBanBoardController;
import view.center.ViewStatisticsController;
import view.center.ViewTeamWorkersController;
import view.dialogs.ViewAboutController;
import view.dialogs.ViewExportProjectsController;
import view.leftColumn.ViewProjectListControllerdd;
import view.rightColumn.ViewTaskCommentsController;
import view.rightColumn.ViewTaskDetailsController;
import view.rightColumn.SwitchedTitledPane;
import view.rightColumn.ViewCompanyDetailsController;
import view.rightColumn.ViewProjectDetailsController;
import view.rightColumn.ViewWorkerDetailsController;

public class MainWindowController extends MainWindow {
	private MainController mainController;

	private CurrentSelected currentSelected;

	private ViewCommentController viewComment;
	private ViewGlobalWorkersController viewGlobalWorkers;

	private ViewEmptyCenterController viewEmptyCenter;;
	private ViewKanBanBoardController viewKanBanBoard;
	private ViewStatisticsController viewStatistics;
	private ViewTeamWorkersController viewTeamWorkers;

	private ViewProjectListControllerdd viewProjectList;
	
	private ViewTaskCommentsController viewTaskCommentsController;
	private String savePath;
	
	private boolean leftExpand;
	private boolean rightExpand;

	@FXML
	private MenuItem itemSave;
	@FXML
	private Button btnExpandLeft;
	@FXML
	private Button btnExpandRight;
	@FXML
	private GridPane grdContent;
	@FXML
	private ColumnConstraints colLeftContent;
	@FXML
	private ColumnConstraints colRightContent;
	@FXML
	private Label lblNotes;
	@FXML
	private RowConstraints rowContent;
	@FXML
	private GridPane leftContent;
	@FXML
	private Accordion rightContent;
	@FXML
	private GridPane mainContent;

	private ArrayList<SwitchedTitledPane> switchedContents;
	public MainWindowController() {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		savePath=null;
		switchedContents=new ArrayList<>();
		
		currentSelected = new CurrentSelected(this);
		leftExpand = true;
		rightExpand = true;
		
		initialCompany();
		initialSubViews();
		initialAUIS();
		
		showProjectList();
		showMainEmpty();
		loadContent(currentSelected);
		viewProjectList.refresh();
	}
	private void initialAUIS() {
		mainController.getIOController().getCompanyAUI().add(viewProjectList);
		mainController.getTaskController().getProjectAUI().add(viewKanBanBoard);
		mainController.getWorkerController().getProjectAUI().add(viewKanBanBoard);
		mainController.getWorkerController().getCompanyAUI().add(viewGlobalWorkers);
		mainController.getProjectController().getProjectAUI().add(viewKanBanBoard);
		
		
		mainController.getProjectController().getCompanyAUI().add(viewProjectList);
		mainController.getCommentController().getTaskAUI().add(viewTaskCommentsController);
		mainController.getCommentController().getCommentAUI().add(viewTaskCommentsController);
	}
	private void initialCompany() {
		this.mainController = new MainController();
		Company company = new Company();
		mainController.setCompany(company);
		mainController.setNotificationAUI(this);
	}

	private void initialSubViews() {
		viewEmptyCenter = new ViewEmptyCenterController();
		viewComment = new ViewCommentController(this);
		viewGlobalWorkers = new ViewGlobalWorkersController(this);
		viewKanBanBoard = new ViewKanBanBoardController(this);
		viewStatistics = new ViewStatisticsController(this);
		viewTeamWorkers = new ViewTeamWorkersController(this);

		viewProjectList = new ViewProjectListControllerdd(this);
		
		viewTaskCommentsController = new ViewTaskCommentsController(this);


		switchedContents.add(new ViewCompanyDetailsController(this));
		switchedContents.add(new ViewProjectDetailsController(this));
		switchedContents.add(new ViewTaskDetailsController(this));
		switchedContents.add(viewTaskCommentsController);
		switchedContents.add(new ViewWorkerDetailsController(this));
	}

	private void showProjectList() {
		leftContent.getChildren().clear();
		leftContent.getChildren().add(viewProjectList);

	}
	@FXML
	private void loadTestCompany() {
		mainController.setCompany(new TestDataFactory().getTestCompanyWithEvenMoreProjects());
		viewProjectList.refresh();
		showMainEmpty();
		mainController.setNotificationAUI(this);
	}
	@FXML
	private void expandLeftContent() {
		if (leftExpand) {
			btnExpandLeft.setText(">");
			colLeftContent.setMaxWidth(0);
			leftContent.setMaxWidth(0);
			leftContent.setVisible(false);
			leftExpand = false;
		} else {
			btnExpandLeft.setText("<");
			colLeftContent.setMaxWidth(300);
			leftContent.setMaxWidth(300);
			leftContent.setVisible(true);
			leftExpand = true;
		}

	}

	@FXML
	private void showAbout(ActionEvent event) {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		try {
			ViewAboutController viewAboutController = new ViewAboutController();
			Scene scene = new Scene(viewAboutController);
			dialog.getIcons().add(new Image(MainWindowController.class.getResourceAsStream("/resources/fav2.png")));
			dialog.setTitle("Über CanBun");
			dialog.setScene(scene);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void showManual(ActionEvent event) {
		
		File handBookFile=new File("./Produktbeschreibung.pdf");
		open(handBookFile);
	}
	
	public static boolean open(File file)
	{
	    try
	    {
	        if (OSDetector.isWindows()){
	            Runtime.getRuntime().exec(new String[]
	            {"rundll32", "url.dll,FileProtocolHandler",
	             file.getAbsolutePath()});
	            return true;
	        } else if (OSDetector.isLinux() || OSDetector.isMac())
	        {
	            Runtime.getRuntime().exec(new String[]{"/usr/bin/open",
	                                                   file.getAbsolutePath()});
	            return true;
	        } else
	        {
	            if (Desktop.isDesktopSupported())
	            {
	                Desktop.getDesktop().open(file);
	                return true;
	            }
	            else
	            {
	                return false;
	            }
	        }
	    } catch (Exception e)
	    {
	        e.printStackTrace(System.err);
	        return false;
	    }
	}
	
	@FXML
	private void showBoard(ActionEvent event) {
		this.showMainKanBanBoard();
	}

	@FXML
	private void showStatistics(ActionEvent event) {
		this.showMainStatistics();
	}

	@FXML
	private void exportBoards(ActionEvent event) {
		Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);

		try {
			ViewExportProjectsController viewExportProjectsController = new ViewExportProjectsController(
					mainController.getCompany().getProjects(),mainController.getIOController());
			Scene scene = new Scene(viewExportProjectsController);
			dialog.getIcons().add(new Image(MainWindowController.class.getResourceAsStream("/resources/fav2.png")));
			dialog.setTitle("Export projects as PDF");
			dialog.setScene(scene);
			dialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void saveCompanyAs(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Pfad wählen um zu Firma speichern");
		File file = fileChooser.showSaveDialog(this.getScene().getWindow());
		if (file != null) {
			savePath=file.getAbsolutePath();
			itemSave.setDisable(false);
			mainController.getIOController().save(savePath);
			showNotification("Die Firma wurde gespeichert", false);
		} else {
			showNotification("Firma konnte nicht gespeichert werden.", true);
		}
	}

	@FXML
	private void saveCompany(ActionEvent event) {
		if(savePath!=null) {
			mainController.getIOController().save(savePath);
		}
	}

	@FXML
	private void closeApplication(ActionEvent event) {
		((Stage) this.getScene().getWindow()).close();
	}

	@FXML
	public void openCompany() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Wählen Sie eine Firma!");
		File file = fileChooser.showOpenDialog(this.getScene().getWindow());
		if (file != null) {
			mainController.getIOController().load(file.getAbsolutePath());
			currentSelected.setCurrentComment(null);
			currentSelected.setCurrentProject(null);
			currentSelected.setCurrentTask(null);
			currentSelected.setCurrentWorker(null);
			currentSelected.setCurrentCompany(mainController.getCompany());
			showMainEmpty();
			showNotification("Firma wurde geöffnet.", false);
		} else {
			showNotification("Datei konnte nicht geöffnet werden.", true);
		}
	}

	@FXML
	public void newCompany() {
		Company company=new Company();
		mainController.setCompany(company);
		currentSelected.setCurrentWorker(null);
		currentSelected.setCurrentTask(null);
		currentSelected.setCurrentProject(null);
		currentSelected.setCurrentComment(null);
		currentSelected.setCurrentCompany(company);
		viewProjectList.refresh();
		showMainEmpty();
	}

	@FXML
	private void expandRightContent() {
		if (rightExpand) {
			btnExpandRight.setText("<");
			colRightContent.setMaxWidth(0);
			rightContent.setMaxWidth(0);
			rightContent.setVisible(false);
			rightExpand = false;
		} else {
			btnExpandRight.setText(">");
			colRightContent.setMaxWidth(300);
			rightContent.setMaxWidth(300);
			rightContent.setVisible(true);
			rightExpand = true;
		}

	}
	
	
	@Override
	public void loadContent(CurrentSelected selected) {
		rightContent.getPanes().clear();
		for(SwitchedTitledPane swContent:switchedContents) {
			swContent.refresh();
			if(swContent.isContentVisible())rightContent.getPanes().add(swContent);
		}
		rightContent.getPanes().forEach(pane -> {
			if(((SwitchedTitledPane)pane).isExpandedDetails()) {
				pane.setExpanded(true);
			}else {
				pane.setExpanded(false);
			}
		});
	}
	
	
	@Override
	public CurrentSelected getCurrentSelected() {
		return currentSelected;
	}

	@Override
	public void showNotification(String msg, boolean isError) {
		if (isError) {
			msg = "Error: " + msg;
			lblNotes.setTextFill(Color.RED);
		} else {
			msg = "Note: " + msg;
			lblNotes.setTextFill(Color.BLACK);
		}
		lblNotes.setText(msg);
	}

	@Override
	public MainController getCurrentMainController() {
		return this.mainController;
	}
	@Override
	public void expandCompanyDetails() {
		switchedContents.get(0).setExpanded(true);
	}
	@Override
	public void showMainKanBanBoard() {
		mainContent.getChildren().clear();
		mainContent.getChildren().add(viewKanBanBoard);
		viewKanBanBoard.refresh();
	}

	@Override
	public void showMainStatistics() {
		viewStatistics = new ViewStatisticsController(this);
		mainContent.getChildren().clear();
		mainContent.getChildren().add(viewStatistics);
	}

	@Override
	public void showMainGlobalWorkers() {
		mainContent.getChildren().clear();
		mainContent.getChildren().add(viewGlobalWorkers);
		viewGlobalWorkers.refresh();
		
	}

	@Override
	public void showMainEmpty() {
		mainContent.getChildren().clear();
		mainContent.getChildren().add(viewEmptyCenter);
	}

	@Override
	public void showMainTeam() {
		mainContent.getChildren().clear();
		mainContent.getChildren().add(viewTeamWorkers);
		viewTeamWorkers.refresh();
	}

	@Override
	public void showMainComment(Comment comment) {
		mainContent.getChildren().clear();
		viewComment.setComment(comment);
		mainContent.getChildren().add(viewComment);
		viewComment.refresh();
	}
}