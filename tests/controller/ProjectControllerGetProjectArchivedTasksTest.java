package controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.ProjectController;
import controller.TaskController;
import model.Company;
import model.Project;
import model.Task;
import model.TestDataFactory;
import view.NotificationAUI;
import view.auis.CompanyAUI;

/**
 * Class ProjectControllerGetProjectArchivedTasksTest
 */
public class ProjectControllerGetProjectArchivedTasksTest {

	private MainController mainController;
	private ProjectController projectController;
	private TaskController taskController;
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
		taskController = new TaskController(mainController);
		projectController = new ProjectController(mainController);
		mainController.setProjectController(projectController);
		mainController.setTaskController(taskController);
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
	public void testParameterNull() throws Exception {

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.getProjectArchivedTasks(null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Testet, ob {@link ProjectController#getProjectArchivedTasks(Project)} das
	 * richtige Ergebnis liefert und die Taskliste nicht bearbeitet.
	 **/
	@Test
	public void testSuccessfullCall() {

		Project project = company.getProjects().get(0);

		Task archivedTask = new Task();
		archivedTask.setCancelled(true);

		taskController.addTask(project, archivedTask);

		Object[] fullTaskList = project.getTasks().toArray();
		ArrayList<Task> archivedTaskList = projectController.getProjectArchivedTasks(project);

		assertEquals("Der ermittelte Task muss dem hinzugefügten archivierten Task entsprechen.",
				archivedTaskList.get(0), archivedTask);
		assertArrayEquals("Die Aufgabenliste des Projekts darf nicht verändert werden.", project.getTasks().toArray(),
				fullTaskList);

	}

}
