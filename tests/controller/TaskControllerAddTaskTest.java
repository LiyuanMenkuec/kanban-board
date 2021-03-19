package controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.Stage;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Diese Klasse dient hauptsächlich dem Testen der addTask()-Methode vom
 * TaskController
 * 
 * @author Gero Grühn
 *
 */
public class TaskControllerAddTaskTest {
	private MainController mainController;
	private TaskController taskController;
	private TestDataFactory factory;

	/**
	 * Der MainController wird gesetzt und ihm wird eine Company zugewiesen,
	 * zusätzlich werden die AUI-Listen mit jeweils einem Dummy-AUI gefüllt
	 * 
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
			/**
			 * Das ist nur eine dummy-Funktion
			 */
			@Override
			public void refreshCommentList() {

			}

			/**
			 * Das ist nur eine dummy-Funktion
			 */
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
	 * Überprüft ob sich die Methode korrekt verhält, falls man eine Aufgabe
	 * hinzufügen möchte, die nicht mehr auf der ersten Stage ist
	 */
	@Test
	public void testCanAddTaskToProjectOnHigherStage() {
		Project currentProject = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = new Task();
		task.getTransitions().get(0).setStage(Stage.ANALYSE);
		task.setTitle("Knoppers essem");
		task.setDescription("iss das Knoppers genau um halb 10");
		task.setDeadline(LocalDateTime.of(2020, 9, 10, 9, 30));
		boolean interrupt = false;
		try {
			this.taskController.addTask(currentProject, task);
		} catch (IllegalStateException isException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));

	}

	/**
	 * Überprüft ob eine Aufgabe auch wirklich hinzugefügt wurde
	 */
	@Test
	public void testCanAddTaskToProject() {
		Project currentProject = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = new Task();
		task.setTitle("Knoppers essem");
		task.setDescription("iss das Knoppers genau um halb 10");
		task.setDeadline(LocalDateTime.of(2020, 9, 10, 9, 30));

		this.taskController.addTask(currentProject, task);

		assertThat(mainController.getCompany().getProjects().get(mainController.getCompany().getProjects().size() - 1)
				.getTasks(), hasItem(task));
		this.taskController.addTask(currentProject, task);
	}

	/**
	 * Überprüft den Aufruf mit null-values
	 */
	@Test
	public void testCanAddTaskToProjectNull() {
		boolean interrupt = false;
		try {
			this.taskController.addTask(null, null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.addTask(new Project(), null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.addTask(null, new Task());
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		Task testTaskWithWorker = new Task();
		testTaskWithWorker.getTransitions().get(0).setWorker(new Worker());
		try {
			this.taskController.addTask(new Project(), testTaskWithWorker);
		} catch (IllegalStateException isException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
	}

}
