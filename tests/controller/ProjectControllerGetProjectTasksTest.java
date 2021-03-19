package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.ProjectController;
import model.Company;
import model.Project;
import model.TestDataFactory;
import view.NotificationAUI;
import view.auis.CompanyAUI;

/**
 * Class ProjectControllerGetProjectTasksTest
 */
public class ProjectControllerGetProjectTasksTest {

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
		projectController.getProjectTasks(null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Testet den Erfolgreichen Aufruf der Methode
	 **/
	@Test
	public void testSuccessfullCall() {

		Project project = company.getProjects().get(0);

		Object[] tasks = project.getTasks().toArray();

		Object[] getterTasks = projectController.getProjectTasks(project).toArray();

		Object[] newTasks = project.getTasks().toArray();

		assertArrayEquals("Die Taskliste muss dem erwarteten Ergebnis entsprechen.", tasks, getterTasks);
		assertArrayEquals("Die Taskliste des Projekts darf sich nicht verändern.", tasks, newTasks);

	}

}
