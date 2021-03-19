package controller;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.History;
import model.Project;
import model.Stage;
import model.Task;
import model.TestDataFactory;
import view.NotificationAUI;
import view.auis.CompanyAUI;

/**
 * Class ProjectControllergetProjectAktiveTasksInStageTest
 */
public class ProjectControllerGetProjectAktiveTasksInStageTest {

	private MainController mainController;
	private ProjectController projectController;
	private Company company;

	/**
	 * Erzeugt vor jeder Test-Methode eine neue Testumgebung.
	 *
	 * @throws Exception wird geworfen Mögliche Exceptions beim setUp.
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;

		mainController = new MainController();
		projectController = new ProjectController(mainController);
		mainController.setProjectController(projectController);
		company = new TestDataFactory().getTestCompany();
		mainController.setCompany(company);

		ArrayList<CompanyAUI> companyAUI = new ArrayList<CompanyAUI>();

		companyAUI.add(new CompanyAUI() {

			@Override
			public void showError(String error) {

			}

			@Override
			public void refreshWorkerList() {

			}

			@Override
			public void refreshProjectList() {

			}
		});

		projectController.setCompanyAUI(companyAUI);
	}

	/**
	 * Wenn der Parameter 'project' null ist, wird eine Fehlermeldung angezeigt.
	 */
	@Test
	public void testParameterProjectNull() throws Exception {

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.getProjectAktiveTasksInStage(null, Stage.NEW);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'stage' null ist, wird eine Fehlermeldung angezeigt.
	 */
	@Test
	public void testParameterStageNull() throws Exception {
		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.getProjectAktiveTasksInStage(new Project(), null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Testet den Erfolgreichen Aufruf der Methode
	 **/
	@Test
	public void testSuccessfullRun() {

		Project project = mainController.getCompany().getProjects().get(0);
		
		Task task1 = new Task();
		task1.setCancelled(true);
		
		Task task2 = new Task();
		History history1 = new History(LocalDateTime.of(2020, 8, 19, 14, 30));
		history1.setWorker(company.getWorkers().get(1));
		history1.setStage(Stage.ANALYSE);
		task2.getTransitions().add(history1);
		task2.setCancelled(true);
		
		project.getTasks().add(task1);
		project.getTasks().add(task2);

		Object[] allTasks = project.getTasks().toArray();

		ArrayList<Task> tasksInStage = projectController.getProjectAktiveTasksInStage(project, Stage.ANALYSE);

		Object[] expectedTasks = { project.getTasks().get(0) };

		assertArrayEquals("Die Taskliste des Projekts darf sich icht verändern.", allTasks,
				project.getTasks().toArray());

		assertArrayEquals("Als Ergebnis muss der erwartete Task aus der TestDataFactory geliefert werden.",
				expectedTasks, tasksInStage.toArray());

	}

}
