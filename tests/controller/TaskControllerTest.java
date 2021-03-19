package controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.History;
import model.Project;
import model.Stage;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Diese Klasse überprüft kleinere Methoden-Aufrufe vom TaskController
 * @author Gero Grühn
 *
 */
public class TaskControllerTest {
	private MainController mainController;
	private TaskController taskController;
	private TestDataFactory factory;

	/**
	 * Erstellt alle nötigen Objekte zum testen
	 * 
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		mainController = new MainController();
		taskController = new TaskController(mainController);
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

		factory = new TestDataFactory();

		mainController.setCompany(factory.getTestCompany());
	}

	/**
	 * Testet den Konstruktor mit null-Aufruf
	 */
	@Test
	public void testConstructor() {
		boolean interrupt = false;
		TaskController testTaskController;
		try {
			testTaskController = new TaskController(null);
			testTaskController.addTask(new Project(), new Task());
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
	}

	/**
	 * Prüft ob ein Worker zurückgegeben wird
	 */
	@Test
	public void testCanGetWorker() {
		Company company = factory.getTestCompany();
		Project project = company.getProjects().get(company.getProjects().size() - 1);
		Task task = project.getTasks().get(0);

		Worker worker = this.taskController.getWorker(task);

		assertThat(worker != null, is(true));
		boolean interrupt = false;
		try {
			this.taskController.getWorker(null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(true));
	}

	/**
	 * Prüft ob ein Mitarbeiter hinzugefügt wurde
	 */
	@Test
	public void testCanAddWorker() {
		Company company = factory.getTestCompany();
		Project project = company.getProjects().get(company.getProjects().size() - 1);
		Task task = project.getTasks().get(0);
		Worker worker = new Worker();
		worker.setActive(true);
		worker.setBirthDate(LocalDate.now());
		worker.setFirstname("test");
		worker.setLastname("Test");
		worker.setPicture(null);

		this.taskController.addWorker(task, worker);

		assertThat(task.getCurrentWorker().equals(worker), is(true));

		Task task2 = project.getTasks().get(1);
		Worker worker2 = new Worker();
		worker2.setActive(true);
		worker2.setBirthDate(LocalDate.now());
		worker2.setFirstname("test2");
		worker2.setLastname("Test2");
		worker2.setPicture(null);

		this.taskController.addWorker(task2, worker2);
		this.taskController.addWorker(task2, worker2);
		assertThat(task2.getCurrentWorker().equals(worker2), is(true));
		boolean interrupt = false;
		try {
			this.taskController.addWorker(null, null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(false));
		interrupt = false;
		try {
			this.taskController.addWorker(new Task(), null);
		} catch (NullPointerException npException) {
			interrupt = true;
		}
		assertThat(interrupt, is(not(true)));
		
		Task newTastInStageTestDone=new Task();
		History histotyStageDone=new History(LocalDateTime.now());
		histotyStageDone.setStage(Stage.TESTDONE);
		newTastInStageTestDone.getTransitions().add(histotyStageDone);
		taskController.addWorker(newTastInStageTestDone, new Worker());
	}

	/**
	 * Überprüft ob eine Aufgabe als erledigt markiert werden kann
	 */
	@Test
	public void testCanSetTaskDone() {
		Company company = factory.getTestCompany();
		Project project = company.getProjects().get(company.getProjects().size() - 1);
		Task task = project.getTasks().get(0);

		Stage currentStage = task.getCurrentStage();

		this.taskController.taskDone(task);

		Stage nextStage = task.getCurrentStage();

		assertThat(currentStage, not(is(nextStage)));

		Task task2 = project.getTasks().get(1);

		Stage currentStage2 = task2.getCurrentStage();

		this.taskController.taskDone(task2);

		Stage nextStage2 = task2.getCurrentStage();

		assertThat(currentStage2, is(nextStage2));
		
		Task lastTask=new Task();
		History lastHistory=new History(LocalDateTime.now());
		lastHistory.setStage(Stage.TESTDONE);
		lastTask.getTransitions().add(lastHistory);
		
		this.taskController.taskDone(lastTask);

	}

	/**
	 * Prüft ob ein Mitarbeiter entfernt wurde
	 */
	@Test
	public void testCanRemoveWorker() {
		Company company = factory.getTestCompany();
		Project project = company.getProjects().get(company.getProjects().size() - 1);
		Task task = project.getTasks().get(0);
		Worker worker = new Worker();
		worker.setActive(true);
		worker.setBirthDate(LocalDate.now());
		worker.setFirstname("test");
		worker.setLastname("Test");
		worker.setPicture(null);

		this.taskController.addWorker(task, worker);
		this.taskController.removeWorker(task);
		this.taskController.removeWorker(new Task());

		assertThat(task.getCurrentWorker() == null, is(true));
	}

	/**
	 * Prüft ob tatsächlich ein Duration-Objekt zurückgegeben wird (und nicht null)
	 */
	@Test
	public void testCanGetLeftTime() {
		Company company = factory.getTestCompany();
		Project project = company.getProjects().get(company.getProjects().size() - 1);
		Task task = project.getTasks().get(0);

		Duration leftTime = this.taskController.getLeftTime(task);
		assertThat(leftTime == null, is(false));
		
		leftTime= task.getLeftTime();
		assertThat(leftTime == null, is(false));
		
		task.setDeadline(null);
		
		leftTime= task.getLeftTime();
		assertThat(leftTime == null, is(true));

		task.setDeadline(LocalDateTime.of(2021, 8, 24, 10, 5));
		
		leftTime = this.taskController.getLeftTime(task);
		assertThat(leftTime != null, is(true));
		
		task.setDeadline(null);
		this.taskController.getLeftTime(task);
		
		this.taskController.updateTaskUnproofed(task, new Task());
		
		History history=new History(LocalDateTime.now());
		history.setStage(Stage.DONE);
		task.getTransitions().add(history);
		
		this.taskController.updateTaskUnproofed(task, new Task());
	}

	/**
	 * Prüft ob der mainController zurückgegeben wird
	 */
	@Test
	public void testCanGetMainController() {

		MainController mainController = this.taskController.getMainController();

		assertThat(mainController.equals(this.mainController), is(true));
	}

	/**
	 * Testet die AUI Helfer
	 */
	@Test
	public void testCanGetTaskAUI() {

		ArrayList<TaskAUI> tasktAUIs = this.taskController.getTaskAUI();

		assertThat(tasktAUIs != null, is(true));
	}

	/**
	 * Testet die AUI Helfer
	 */
	@Test
	public void testCanGetProjectAUI() {

		ArrayList<ProjectAUI> projectAUIs = this.taskController.getProjectAUI();

		assertThat(projectAUIs != null, is(true));
	}

}