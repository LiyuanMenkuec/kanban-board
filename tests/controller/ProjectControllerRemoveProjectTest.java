package controller;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.ProjectController;
import model.Company;
import model.Project;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import view.NotificationAUI;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;

/**
 * Class ProjectControllerRemoveProjectTest
 */
public class ProjectControllerRemoveProjectTest {

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
	 * Testet, ob erfolgreich eine Fehlermeldung angezeigt wird, wenn der
	 * Übergabeparameter null ist.
	 **/
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
		projectController.removeProject(null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Testet, ob das Löschen eines Projekts, welches nicht in der Projektliste ist
	 * die Projektliste verändert.
	 **/
	@Test
	public void testRemoveNewProject() {

		Object[] projectList = company.getProjects().toArray();

		projectController.removeProject(new Project());

		Object[] newProjectList = company.getProjects().toArray();

		assertArrayEquals("Die Projektliste darf sich nicht verändern.", projectList, newProjectList);

	}

	/**
	 * Testet das erfolgreiche Löschen eines Projekts aus der Projektliste.
	 **/
	@Test
	public void testRemoveProject() {

		Project toDelete = company.getProjects().get(0);

		projectController.removeProject(toDelete);

		assertFalse("Das zu löschende Projekt darf nicht mehr in der Projektliste vorhanden sein.",
				company.getProjects().contains(toDelete));

	}

	/**
	 * Testet das Löschen eines Projekts ohne Mitarbeiter und Projekte.
	 **/
	@Test
	public void testRemoveProjectWithEmptyLists() {

		Project toDelete = company.getProjects().get(0);
		toDelete.setWorkers(new ArrayList<Worker>());
		toDelete.setTasks(new ArrayList<Task>());

		projectController.removeProject(toDelete);

		assertFalse("Das zu löschende Projekt darf nicht mehr in der Projektliste vorhanden sein.",
				company.getProjects().contains(toDelete));

	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Löschen eines Projektes aufgerufen wird.
	 *
	 * @throws Exception wird geworfen {@link ProjectController#removeProject(Project)}
	 */
	@Test
	public void testRefreshProjectListCalled() throws Exception {

		AtomicBoolean refreshProjectListCalled = new AtomicBoolean(false);
		AtomicBoolean refreshTeamListCalled = new AtomicBoolean(false);
		AtomicBoolean refreshProjectDataCalled = new AtomicBoolean(false);
		AtomicBoolean refreshTaskListCalled = new AtomicBoolean(false);

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
				refreshProjectListCalled.set(true);

			}
		});
		
		ArrayList<ProjectAUI> projectAUI = new ArrayList<>();
		
		projectAUI.add(new ProjectAUI() {
			
			@Override
			public void refreshTeamList() {
				
				refreshTeamListCalled.set(true);
				
			}
			
			@Override
			public void refreshTaskList() {

					refreshTaskListCalled.set(true);
				
			}
			
			@Override
			public void refreshProjectData() {
				
				refreshProjectDataCalled.set(true);
				
			}
		});

		projectController.setCompanyAUI(companyAUI);
		projectController.setProjectAUI(projectAUI);

		assertFalse("Die CompanyAUI dürfen noch nicht aufgerufen worden sein.", refreshProjectListCalled.get());
		assertFalse("Die ProjectAUI dürfen noch nicht aufgerufen worden sein.", refreshTeamListCalled.get());
		assertFalse("Die ProjectAUI dürfen noch nicht aufgerufen worden sein.", refreshTaskListCalled.get());
		assertFalse("Die ProjectAUI dürfen noch nicht aufgerufen worden sein.", refreshProjectDataCalled.get());
		projectController.removeProject(company.getProjects().get(0));
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", refreshProjectListCalled.get());
		assertTrue("Die ProjectAUI müssen aufgerufen worden sein.", refreshTeamListCalled.get());
		assertTrue("Die ProjectAUI müssen aufgerufen worden sein.", refreshTaskListCalled.get());
		assertTrue("Die ProjectAUI müssen aufgerufen worden sein.", refreshProjectDataCalled.get());

	}

}
