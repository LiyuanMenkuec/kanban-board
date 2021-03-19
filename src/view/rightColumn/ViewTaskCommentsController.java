package view.rightColumn;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import controller.CommentController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Comment;
import model.Task;
import view.MainWindow;
import view.auis.CommentAUI;
import view.auis.TaskAUI;

public class ViewTaskCommentsController extends SwitchedTitledPane implements CommentAUI, TaskAUI {

	/**
	 * Platzhalter für die Tabelle
	 **/
	private static final Label NO_TASK_SELECTED_LABEL = new Label("Keine Aufgabe ausgewählt!");
	private static final Label NO_COMMENT_IN_CURRENT_TASK = new Label("Diese Aufgabe hat noch keine Kommentare!");

	@FXML
	private TableView<TableData> viewTaskCommentsTable;

	@FXML
	private TableColumn<TableData, String> authorTableColumn;

	@FXML
	private TableColumn<TableData, String> dateTableColumn;

	@FXML
	private Button viewTaskCommentsBtnAdd;

	@FXML
	private Button viewTaskCommentsBtnRemove;

	@FXML
	private Button viewTaskCommentsBtnShow;

	@FXML
	private Label viewTaskCommentsLabelTitle;

	private MainWindow parent;
	private Task currentTask;

	public ViewTaskCommentsController(MainWindow parent) {

		this.parent = parent;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewTaskComments.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ioExc) {

			ioExc.printStackTrace();
		}
		currentTask = null;

		viewTaskCommentsTable.setOnSort((event) -> {

			TableData currentSelection = viewTaskCommentsTable.getSelectionModel().getSelectedItem();

			if (currentSelection != null) {

				viewTaskCommentsTable.getSelectionModel().clearSelection();
				viewTaskCommentsBtnRemove.setDisable(true);
				viewTaskCommentsBtnShow.setDisable(true);

			}
		});
	}

	/**
	 * Öffnet ein Fenster im 'Center'-Bereich, um einen neuen Kommentar zu erstellen
	 * 
	 * Setzt Current-Comment auf null um dem Comment-Fenster im Center mitzuteilen,
	 * dass ein neuer Kommentar angelegt werden soll und eben nicht der
	 * Current-Kommentar bearbeitet werden soll(?).
	 **/
	@FXML
	private void addComment() {

		Comment newComment = new Comment();
		parent.getCurrentSelected().setCurrentComment(newComment);
		viewTaskCommentsTable.getSelectionModel().clearSelection();
		viewTaskCommentsBtnRemove.setDisable(true);
		viewTaskCommentsBtnShow.setDisable(true);
		parent.showMainComment(newComment);

	}

	/**
	 * Entfernt den ausgewählten Kommentar aus der Aufgabe.
	 **/
	@FXML
	private void removeComment() {

		TableData selectedEntry = viewTaskCommentsTable.getSelectionModel().getSelectedItem();

		if (selectedEntry != null) {

			Comment selectedComment = selectedEntry.getComment();
			
			CommentController commentController = parent.getCurrentMainController().getCommentController();

			Task currentTask = parent.getCurrentSelected().getCurrentTask();

			commentController.removeComment(currentTask, selectedComment);

			parent.getCurrentSelected().setCurrentComment(null);

			viewTaskCommentsBtnRemove.setDisable(true);

			viewTaskCommentsBtnShow.setDisable(true);

			this.update();
			
			Task lastTask = parent.getCurrentSelected().getCurrentTask();
			parent.showMainKanBanBoard();
			parent.getCurrentSelected().setCurrentTask(lastTask);

			this.setExpanded(true);
		}

	}

	/**
	 * Zeigt den aktuell ausgewählten Kommentar in einer neuen Ansicht im
	 * 'Center-Bereich'.
	 **/
	@FXML
	private void showCommentDetails() {

		TableData currentSelection = viewTaskCommentsTable.getSelectionModel().getSelectedItem();

		if (currentSelection != null && currentSelection.getComment() != null) {
			parent.showMainComment(currentSelection.getComment());
		}

	}

	/**
	 * Aktualisiert die Inhalte des Fensters
	 **/
	public void refresh() {
		currentTask = parent.getCurrentSelected().getCurrentTask();
		if (currentTask != null) {
			setContentVisible(true);
			this.update();
		} else {
			setContentVisible(false);
		}
	}

	/**
	 * Füllt die Kommentarliste mit allen vorhandenen Kommentaren. <br>
	 * <br>
	 * Unchecked wegen viewTaskCommentsTable.getColumns().addAll(authorTableColumn,
	 * dateTableColumn).
	 **/
	@SuppressWarnings("unchecked")
	private void update() {

		Task currentTask = parent.getCurrentSelected().getCurrentTask();

		if (currentTask == null) {

			// TODO Umlaut fixen
			viewTaskCommentsTable.getItems().clear();
			viewTaskCommentsTable.setPlaceholder(NO_TASK_SELECTED_LABEL);
			viewTaskCommentsLabelTitle.setText("");

		} else {

			viewTaskCommentsLabelTitle.setText("Kommentare der Aufgabe " + currentTask.getTitle());

			ArrayList<Comment> comments = currentTask.getComments();

			if (comments.size() == 0) {

				viewTaskCommentsTable.getItems().clear();
				viewTaskCommentsTable.setPlaceholder(NO_COMMENT_IN_CURRENT_TASK);

				viewTaskCommentsBtnRemove.setDisable(false);
				viewTaskCommentsBtnShow.setDisable(false);

			} else {

				final ObservableList<TableData> data = FXCollections.observableArrayList();

				for (Comment comment : comments) {

					String author = comment.getWorker().getFirstname() + " " + comment.getWorker().getLastname();

					String date = comment.getCreatedAt()
							.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT));

					data.add(new TableData(author, date, comment));

				}

				authorTableColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
				dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

				viewTaskCommentsTable.setItems(data);
				viewTaskCommentsTable.getColumns().clear();
				viewTaskCommentsTable.getColumns().addAll(authorTableColumn, dateTableColumn);

				viewTaskCommentsBtnRemove.setDisable(true);
				viewTaskCommentsBtnShow.setDisable(true);

			}

		}
	}

	/**
	 * Setzt den ausgewählten Kommentar im MainWindow. Wird ein Kommentar
	 * ausgewählt, werden die Buttons 'Entfernen' und 'Kommentar anzeigen'
	 * freigegeben.
	 **/
	@FXML
	private void onSelectComment() {

		TableData selectedTableEntry = viewTaskCommentsTable.getSelectionModel().getSelectedItem();

		if (selectedTableEntry != null) {

			viewTaskCommentsBtnRemove.setDisable(false);
			viewTaskCommentsBtnShow.setDisable(false);

		}

	}

	/**
	 * CommentAUI-Methode, wird aufgerufen um die Änderung des Autors und des
	 * Erstellungsdatums der Kommentarliste mitzuteilen.
	 **/
	@Override
	public void refreshCommentData() {

		this.update();

	}

	/**
	 * TaskAUI-Methode. Muss aus dem Interface implementiert werden, hat hier keinen
	 * Nutzen.
	 **/
	@Override
	public void refreshTaskData() {

	}

	/**
	 * TaskAUI-Methode. Wird aufgerufen, um der Kommentarliste mitzuteilen, dass
	 * sich etwas an der Kommentarliste geändert hat.
	 **/
	@Override
	public void refreshCommentList() {
		this.update();

	}

	/**
	 * Enthält Einen Eintrag in der Kommentar-Liste. Verwaltet so den angezeigten
	 * Autor, das Erstellungsdatum und das dazugehörige Comment-Objekt.
	 **/
	public class TableData {
		StringProperty author;
		StringProperty date;
		Comment comment;

		TableData(String author, String date, Comment comment) {
			this.author = new SimpleStringProperty(author);
			this.date = new SimpleStringProperty(date);
			this.comment = comment;
		}

		StringProperty authorProperty() {
			return author;
		}

		StringProperty dateProperty() {
			return date;
		}

		public void setDate(String date) {
			this.date.set(date);
		}

		public String getDate() {
			return date.get();
		}

		public String getAuthor() {
			return author.get();
		}

		public void setAuthor(String author) {
			this.author.set(author);
		}

		public Comment getComment() {
			return comment;

		}

		@Override
		public boolean equals(Object o) {

			return (o.getClass() == TableData.class) ? this.getComment().equals(((TableData) o).getComment()) : false;

		}

	}

}
