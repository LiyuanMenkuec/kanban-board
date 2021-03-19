package controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.Before;
import model.Comment;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import view.auis.CommentAUI;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Class CommentControllerTest
 */
public class CommentControllerTest {

	private MainController mainController;
	private CommentController commentController;

	/**
	 * Setzt die Controller vor jedem Test
	 * 
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		mainController = new MainController();
		commentController = new CommentController(mainController);
		TaskAUI testTaskAUI = new TaskAUI() {
			@Override
			public void refreshCommentList() {
			}

			@Override
			public void refreshTaskData() {
			}
		};
		commentController.getTaskAUI().add(testTaskAUI);
		CommentAUI testCommentAUI = new CommentAUI() {
			@Override
			public void refreshCommentData() {
				
			}
		};
		commentController.getCommentAUI().add(testCommentAUI);
	}

	/**
	 * Hinzufügen von Kommentaren
	 */
	@Test
	public void testCanAddComment() {
		Comment comment = new Comment();

		comment.setContent("Test Content");
		comment.setCreatedAt(LocalDateTime.now());
		comment.setWorker(new Worker());

		Task task = new Task();

		this.commentController.addComment(task, comment);

		assertThat(task.getComments(), hasItem(comment));
	}

	/**
	 * Kann keine nicht ausgefüllten Kommentare hinzufügen
	 */
	@Test(expected = NullPointerException.class)
	public void testCantAddPartialComment() {
		Comment comment = new Comment();

		Task task = new Task();

		try {
			this.mainController.getCommentController().addComment(task, comment);
		} catch (NullPointerException exception) {
			assertThat(task.getComments(), not(hasItem(comment)));

			throw exception;
		}

	}

	/**
	 * Hat erwartetes Verhalten bei null
	 */
	@Test(expected = NullPointerException.class)
	public void testCantAddNullComment() {
		this.mainController.getCommentController().addComment(null, null);
	}

	/**
	 * Entfernen von Kommentaren
	 */
	@Test
	public void testCanRemoveComment() {
		Comment comment = new Comment();

		ArrayList<Comment> comments = new ArrayList<>(Arrays.asList(comment));

		Task task = new Task();

		task.setComments(comments);

		this.commentController.removeComment(task, comment);

		assertThat(task.getComments(), not(hasItem(comment)));
		
		try {
			this.commentController.removeComment(task, null);
		} catch (NullPointerException exception) {
			assertThat(task.getComments(), not(hasItem(comment)));
		}
		
		try {
			this.commentController.removeComment(null, comment);
		} catch (NullPointerException exception) {
			assertThat(task.getComments(), not(hasItem(comment)));
		}
		
		try {
			this.commentController.removeComment(null, null);
		} catch (NullPointerException exception) {
			assertThat(task.getComments(), not(hasItem(comment)));
		}
	}

	/**
	 * Kann alle Kommentare bekommen
	 */
	@Test
	public void testCanGetComments() {
		ArrayList<Comment> comments = new ArrayList<>(Arrays.asList(new Comment()));

		Task task = new Task();

		task.setComments(comments);

		assertThat(this.commentController.getComments(task), is(comments));
		
		try {
			this.commentController.getComments(null);
		} catch (NullPointerException exception) {
			
		}
	}

	/**
	 * Kann den Content von Kommentare updaten
	 */
	@Test
	public void testCanUpdateComment() {
		Comment comment = new Comment();

		comment.setContent("Test Content");
		comment.setCreatedAt(LocalDateTime.now());
		comment.setWorker(new Worker());

		Comment changes = new Comment();

		changes.setContent("Neuer Content");
		changes.setCreatedAt(LocalDateTime.now().plusMinutes(1));
		changes.setWorker(new Worker());

		this.mainController.getCommentController().updateComment(comment, changes);

		assertThat(comment.getContent(), is(changes.getContent()));
		assertThat(comment.getCreatedAt(), is(not(changes.getCreatedAt())));
		assertThat(comment.getWorker(), is(not(changes.getWorker())));
	}

	/**
	 * Kann Kommentare nicht leer machen
	 */
	@Test(expected = NullPointerException.class)
	public void testCantUpdateToEmptyComment() {
		Comment comment = new Comment();

		comment.setContent("Test Content");
		comment.setCreatedAt(LocalDateTime.now());
		comment.setWorker(new Worker());

		Comment changes = new Comment();

		changes.setContent("");
		changes.setCreatedAt(LocalDateTime.now().plusMinutes(1));
		changes.setWorker(new Worker());

		try {
			this.mainController.getCommentController().updateComment(comment, changes);
		} catch (Exception exception) {
			assertThat(comment.getContent(), is(not(changes.getContent())));
			assertThat(comment.getCreatedAt(), is(not(changes.getCreatedAt())));
			assertThat(comment.getWorker(), is(not(changes.getWorker())));

			throw exception;
		}
	}

	/**
	 * Hat erwartetes Verhalten bei null
	 */
	@Test(expected = NullPointerException.class)
	public void testCantUpdateToNullComment() {
		Comment comment = new Comment();

		comment.setContent("Test Content");
		comment.setCreatedAt(LocalDateTime.now());
		comment.setWorker(new Worker());

		int hash = comment.hashCode();

		try {
			this.mainController.getCommentController().updateComment(comment, null);
		} catch (NullPointerException exception) {
			assertThat(hash, is(comment.hashCode()));

			throw exception;
		}
	}

	/**
	 * Testen der AUI Helfer Funktionen
	 */
	@Test
	public void testCanAccessAUIs() {
		ArrayList<CommentAUI> commentAUI = this.mainController.getCommentController().getCommentAUI();

		this.mainController.getCommentController().setCommentAUI(null);

		assertThat(commentAUI, not(this.mainController.getCommentController().getCommentAUI()));

		this.mainController.getCommentController().setCommentAUI(commentAUI);

		assertThat(commentAUI, is(this.mainController.getCommentController().getCommentAUI()));

		ArrayList<TaskAUI> taskAUI = this.mainController.getCommentController().getTaskAUI();

		this.mainController.getCommentController().setTaskAUI(null);

		assertThat(taskAUI, not(this.mainController.getCommentController().getTaskAUI()));

		this.mainController.getCommentController().setTaskAUI(taskAUI);

		assertThat(taskAUI, is(this.mainController.getCommentController().getTaskAUI()));
	}
	/**
	 * Zusätzliche Tests um die Coverage für Sonderfälle zu testen
	 */
	@Test
	public void testAdditionalConditions() {
		
		boolean isInterrupted=false;
		try {
			this.commentController.updateComment(null, null);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		isInterrupted=false;
		try {
			this.commentController.updateComment(null, new Comment());
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		isInterrupted=false;
		try {
			this.commentController.updateComment(new Comment(), null);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		isInterrupted=false;
		
		Comment comment = new Comment();
		
		comment.setContent(null);
		isInterrupted=false;
		try {
			this.commentController.updateComment(new Comment(), comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent("");
		isInterrupted=false;
		try {
			this.commentController.updateComment(new Comment(), comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		
		isInterrupted=false;
		try {
			this.commentController.addComment(null, null);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		isInterrupted=false;
		try {
			this.commentController.addComment(new Task(), null);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		isInterrupted=false;
		try {
			this.commentController.addComment(null, new Comment());
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		
		comment.setWorker(null);
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		
		comment.setCreatedAt(null);
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent(null);
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent("");
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setWorker(new Worker());
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent(null);
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent("Test");
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setCreatedAt(LocalDateTime.now());
		isInterrupted=false;
		try {
			this.commentController.addComment(null, comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(true));
		comment.setContent("Hallo");
		comment.setCreatedAt(LocalDateTime.now());
		comment.setWorker(new Worker());
		isInterrupted=false;
		try {
			this.commentController.addComment(new Task(), comment);
		}catch(NullPointerException npException) {
			isInterrupted=true;
		}
		assertThat(isInterrupted,is(false));
		
	}
}
