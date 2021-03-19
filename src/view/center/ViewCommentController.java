package view.center;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



import controller.MainController;
import model.Comment;
import model.Task;
import model.Worker;
import view.MainWindow;
import view.rightColumn.WorkerCell;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;



public class ViewCommentController extends GridPane {
	@FXML
	private Button btnCommentSaveChanges;

	@FXML
	private Button btnCommentQuitClose;

	@FXML
	private ComboBox<Worker> comboBoxCommentAuthor;

    @FXML
    private TextField txtFieldCommentDatum;

	@FXML
	private TextArea txtAreaCommentDescription;


	private MainWindow parent;
	private MainController mainController;
	private Comment currentComment;
	private Task currentTask;
	
	public ViewCommentController(MainWindow parent) {
		this.parent=parent;
		this.mainController = parent.getCurrentMainController();
		currentComment=null;
		currentTask = parent.getCurrentSelected().getCurrentTask();
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewComment.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ioExc) {

			ioExc.printStackTrace();
		}
		
		comboBoxCommentAuthor.setCellFactory(worker -> new WorkerCell());
		comboBoxCommentAuthor.setButtonCell(new ListCell<Worker>() {
            public void updateItem(Worker worker, boolean empty) {
            	super.updateItem(worker, empty);
                if (empty || worker == null) {
                    setText("Nicht zugewiesen");
                } else {
                    setText(worker.getFirstname()+", "+worker.getLastname());
                }
            }
        });
	}
	public void setComment(Comment comment) {
		currentComment=comment;
	}
	public void refresh() {
		currentTask = parent.getCurrentSelected().getCurrentTask();
		if(currentComment!=null) {
			setVisible(true);
			this.update();
		}
		else 
		{
			setVisible(false);
		}
	}
	
	private void update() {
		mainController=parent.getCurrentMainController();
		
		if(!currentTask.getComments().contains(currentComment)) 
		{
			ArrayList<Worker> allWorker=new ArrayList<>();
			allWorker.addAll(mainController.getWorkerController().getAllWorkers());
			comboBoxCommentAuthor.setItems(FXCollections.observableArrayList(allWorker));
			comboBoxCommentAuthor.getSelectionModel().selectFirst();
			comboBoxCommentAuthor.setDisable(false);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			this.txtFieldCommentDatum.setText(LocalDateTime.now().format(formatter));
			this.txtAreaCommentDescription.setText(currentComment.getContent());
			txtFieldCommentDatum.setEditable(true);
		}
		else
		{
			ArrayList<Worker> allWorker=new ArrayList<>();
			allWorker.add(currentComment.getWorker());
			comboBoxCommentAuthor.setItems(FXCollections.observableArrayList(allWorker));
			comboBoxCommentAuthor.getSelectionModel().selectFirst();
			
			this.txtAreaCommentDescription.setText(currentComment.getContent());
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatDateTime = currentComment.getCreatedAt().format(formatter);
			txtFieldCommentDatum.setText(formatDateTime);
			txtFieldCommentDatum.setEditable(false);
			//bei Bearbeiten eines Kommentars darf nur noch Beschreibung verändert werden nicht das Datum!!!
		}
	}
	

	@FXML
	void onButtonCloseClick(ActionEvent event) {
		parent.getCurrentSelected().setCurrentComment(null);
		Task lastTask = parent.getCurrentSelected().getCurrentTask();
		parent.showMainKanBanBoard();
		parent.getCurrentSelected().setCurrentTask(lastTask);
	}

	@FXML
	void onButtonConfirmClick(ActionEvent event) {
		Comment newComment = new Comment();
		newComment.setContent(txtAreaCommentDescription.getText());
		newComment.setWorker(comboBoxCommentAuthor.getValue());
		currentTask = parent.getCurrentSelected().getCurrentTask();
		
		if(currentTask.getComments().contains(currentComment)) { //Kommentar ist schon vorhanden
			mainController.getCommentController().updateComment(currentComment,newComment);
		}else { //neuer Kommentar wird erstellt
			//newComment.setCreatedAt(LocalDateTime.of(txtFieldCommentDatum.getValue(),LocalTime.now()));
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatDateTime = now.format(formatter);
			txtFieldCommentDatum.setText(formatDateTime);
			newComment.setCreatedAt(now);
			mainController.getCommentController().addComment(currentTask, newComment);
		}
		
		Task lastTask = parent.getCurrentSelected().getCurrentTask();
		parent.showMainKanBanBoard();
		parent.getCurrentSelected().setCurrentTask(lastTask);
	}
	
	@FXML
	void detectChanges(Event event) {
		//ToDo
		this.btnCommentSaveChanges.setDisable(false);
	}
	
	
}
