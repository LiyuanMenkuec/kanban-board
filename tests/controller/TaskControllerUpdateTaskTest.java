package controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.Task;
import model.TestDataFactory;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Diese Klasse prüft den Aufruf von updateTask
 * @author Gero Grühn
 *
 */
public class TaskControllerUpdateTaskTest {
	private MainController mainController;
	private TaskController taskController;
	private TestDataFactory factory;

	/**
	 * AUI's werden gesetzt und Variablen instanziiert
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		mainController = new MainController();
		taskController = new TaskController(mainController);

		factory = new TestDataFactory();

		mainController.setCompany(factory.getTestCompany());
		TaskAUI testTaskAUI = new TaskAUI() {
			@Override
			public void refreshCommentList() {
			}

			@Override
			public void refreshTaskData() {
			}
		};
		taskController.getTaskAUI().add(testTaskAUI);
		ProjectAUI testProjectAUI = new ProjectAUI() {
			@Override
			public void refreshProjectData() {
			}

			@Override
			public void refreshTaskList() {
			}

			@Override
			public void refreshTeamList() {
			}
		};
		taskController.getProjectAUI().add(testProjectAUI);
	}

	/**
	 * Testet das Ändern von Tasks
	 */
	@Test
	public void testCanUpdateTask() {
		Project project = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = project.getTasks().get(project.getTasks().size() - 1);

		Task newTask = new Task();
		newTask.setCancelled(false);
		newTask.setComments(null);
		newTask.setDeadline(LocalDateTime.now());
		newTask.setDescription("test");
		newTask.setTitle("testTitel");

		this.taskController.updateTask(task, newTask);

		Task checkTask = project.getTasks().get(project.getTasks().size() - 1);

		assertThat(task.isCancelled(), is(checkTask.isCancelled()));
		assertThat(task.getComments(), is(checkTask.getComments()));
		assertThat(task.getDeadline(), is(checkTask.getDeadline()));
		assertThat(task.getTitle(), is(checkTask.getTitle()));
		assertThat(task.getDescription(), is(checkTask.getDescription()));
		assertThat(task.getTransitions(), is(checkTask.getTransitions()));
		boolean interrupt = false;
		try {
			this.taskController.updateTask(task, task);
		} catch (IllegalStateException isException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
	}

	/**
	 * Testet Tasks Updates mit dem selben Namen
	 */
	@Test
	public void testCanUpdateTaskSameName() {
		Project project = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = project.getTasks().get(project.getTasks().size() - 1);

		Task newTask = new Task();
		newTask.setCancelled(false);
		newTask.setComments(null);
		newTask.setDeadline(LocalDateTime.now());
		newTask.setDescription("test");
		newTask.setTitle(task.getTitle());

		this.taskController.updateTask(task, newTask);

		Task checkTask = project.getTasks().get(project.getTasks().size() - 1);

		assertThat(task.isCancelled(), is(checkTask.isCancelled()));
		assertThat(task.getComments(), is(checkTask.getComments()));
		assertThat(task.getDeadline(), is(checkTask.getDeadline()));
		assertThat(task.getTitle(), is(checkTask.getTitle()));
		assertThat(task.getDescription(), is(checkTask.getDescription()));
		assertThat(task.getTransitions(), is(checkTask.getTransitions()));

	}

	/**
	 * Testet auf Titelduplikation
	 */
	@Test
	public void testCanUpdateTaskNewAlreadyExists() {
		Project project = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = project.getTasks().get(project.getTasks().size() - 1);

		Task newTask = new Task();
		newTask.setCancelled(false);
		newTask.setComments(null);
		newTask.setDeadline(LocalDateTime.now());
		newTask.setDescription("test");
		newTask.setTitle("Klassen kommentieren");
		newTask.setTransitions(null);

		this.taskController.updateTask(task, newTask);

		assertThat(task.getTitle(), not(is(newTask.getTitle())));
	}

	/**
	 * Testet für erwartetes Verhalten bei null
	 */
	@Test
	public void testCanUpdateTaskNullTest() {
		boolean interrupt = false;
		try {
			this.taskController.updateTask(null, null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.updateTask(new Task(), null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.updateTask(null, new Task());
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.updateTask(new Task(), new Task());
		} catch (NoSuchElementException nseException) {
			interrupt = true;
		}
		assertThat(interrupt, is(false));
	}

}
