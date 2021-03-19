package controller;

import model.Task;
import view.auis.CommentAUI;
import view.auis.TaskAUI;

import java.util.ArrayList;

import model.Comment;

/**
 * 
 * @author Julian Adler
 *
 */
public class CommentController {

	private MainController mainController;

	private ArrayList<CommentAUI> commentAUI;

	private ArrayList<TaskAUI> taskAUI;

	/**
	 * @param mainController
	 */
	public CommentController(MainController mainController) {
		this.mainController = mainController;

		commentAUI = new ArrayList<>();
		taskAUI = new ArrayList<>();
	}

	/**
	 * Refreshes the commentAUIs
	 */
	private void refreshCommentAUIs() {
		commentAUI.forEach(listElement -> listElement.refreshCommentData());
	}

	/**
	 * Refreshes the taskAUIs
	 */
	private void refreshTaskAUIs() {
		taskAUI.forEach(listElement -> listElement.refreshCommentList());
		taskAUI.forEach(listElement -> listElement.refreshTaskData());
	}

	/**
	 * Testet den Kommentar auf vollständigkeit und fügt ihn zu der übergebenen Task
	 * hinzu
	 * 
	 * @param task
	 * @param comment precondition Comment hat alle Werte gesetzt
	 */
	public void addComment(final Task task, final Comment comment) {
		if (task != null && comment != null) {
			if (comment.getWorker() != null && comment.getCreatedAt() != null && comment.getContent() != null && !comment.getContent().isEmpty()) {
				task.getComments().add(comment);

				refreshTaskAUIs();
			} else {
				throw new NullPointerException("Comment contains null-values");
			}
		} else {
			throw new NullPointerException("Arguments are null");
		}
	}

	/**
	 * Entfernt den Kommentar von der übergebenen Aufgabe
	 * 
	 * @param task
	 * @param comment
	 */
	public void removeComment(final Task task, final Comment comment) {
		if (task != null && comment != null) {
			task.getComments().remove(comment);

			refreshCommentAUIs();
			refreshTaskAUIs();
		} else {
			throw new NullPointerException("Arguments are null");
		}
	}

	/**
	 * Gibt die Liste der Kommentare in der übergebenen Aufgabe zurück
	 * 
	 * @param task
	 * @return ArrayList of Comment
	 */
	public ArrayList<Comment> getComments(final Task task) {
		if (task != null) {
			return task.getComments();
		} else {
			throw new NullPointerException("Argument is null");
		}
	}

	/**
	 * Setzt den Inhalt des zweiten übergebenen Kommentars auf den ersten übergeben
	 * Kommentar
	 * 
	 * @param comment
	 * @param changes precondtion Inhalt des zweiten Kommentars ist nicht leer
	 */
	public void updateComment(final Comment comment, final Comment changes) {
		if (comment != null && changes != null) {
			if (changes.getContent() != null && !changes.getContent().isEmpty()) {
				comment.setContent(changes.getContent());
				
				refreshCommentAUIs();
				refreshTaskAUIs();
			} else {
				mainController.showNotification("Bitte Namen und Beschreibung für den Kommentar angeben!", true);

				throw new NullPointerException("Comment contains null-value");
			}

		} else {
			throw new NullPointerException("Arguments are null");
		}
	}

	/**
	 * @return the commentAUI
	 */
	public ArrayList<CommentAUI> getCommentAUI() {
		return commentAUI;
	}

	/**
	 * @param commentAUI the commentAUI to set
	 */
	public void setCommentAUI(ArrayList<CommentAUI> commentAUI) {
		this.commentAUI = commentAUI;
	}

	/**
	 * @return the taskAUI
	 */
	public ArrayList<TaskAUI> getTaskAUI() {
		return taskAUI;
	}

	/**
	 * @param taskAUI the taskAUI to set
	 */
	public void setTaskAUI(ArrayList<TaskAUI> taskAUI) {
		this.taskAUI = taskAUI;
	}
	

}
