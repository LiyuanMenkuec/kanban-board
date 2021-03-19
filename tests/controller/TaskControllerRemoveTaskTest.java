package controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.Task;
import model.TestDataFactory;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Überprüft die Aufrufe von removeTask() und forceRemoveTask() der Klasse
 * TaskController
 * 
 * @author Gero Grühn
 *
 */
public class TaskControllerRemoveTaskTest {
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
	 * Überprüft den Aufruv von removeTask mit null-values und leeren Objekten
	 */
	@Test
	public void testCanRemoveTaskNull() {
		boolean interrupt = false;
		try {
			this.taskController.removeTask(null, null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));

		interrupt = false;
		try {
			this.taskController.removeTask(new Project(), null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));

		interrupt = false;
		try {
			this.taskController.removeTask(null, new Task());
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));

		interrupt = false;
		try {
			this.taskController.removeTask(new Project(), new Task());
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(false));
	}

	/**
	 * Überprüft, ob eine Aufgabe wirklich nur abgebrochen wird, wenn sie bereits
	 * bearbeitet wurde
	 */
	@Test
	public void testCanRemoveTaskFromProjectCancelling() { 
		Project currentProject = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = currentProject.getTasks().get(currentProject.getTasks().size() - 1);
		this.taskController.addWorker(task, currentProject.getWorkers().get(1));

		this.taskController.removeTask(currentProject, task);

		assertThat(task.isCancelled(), is(true));

	}

	/**
	 * Überprüft, ob eine Aufgabe von einem Projekt entfernt wird (mit Bedingungen)
	 */
	@Test
	public void testCanRemoveTaskFromProject() {
		Project currentProject = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = currentProject.getTasks().get(currentProject.getTasks().size() - 1);

		this.taskController.removeTask(currentProject, task);
		if (task.getTransitions().size() > 1)
			assertThat(task.isCancelled(), is(true));
		else
			assertThat(mainController.getCompany().getProjects()
					.get(mainController.getCompany().getProjects().size() - 1).getTasks(), not(hasItem(task)));
	}

	/**
	 * Überprüft, ob eine Aufgabe von einem Projekt entfernt wird (ohne Bedingungen)
	 */
	@Test
	public void testCanForceRemoveTaskFromProject() {
		Project currentProject = mainController.getCompany().getProjects()
				.get(mainController.getCompany().getProjects().size() - 1);
		Task task = currentProject.getTasks().get(currentProject.getTasks().size() - 1);

		this.taskController.forceRemoveTask(currentProject, task);

		assertThat(mainController.getCompany().getProjects().get(mainController.getCompany().getProjects().size() - 1)
				.getTasks(), not(hasItem(task)));
		boolean interrupt = false;
		try {
			this.taskController.forceRemoveTask(null, null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
		interrupt = false;
		try {
			this.taskController.forceRemoveTask(new Project(), null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
	}

}
