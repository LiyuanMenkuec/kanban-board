package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.ProjectController;
import model.Company;
import model.Project;
import model.TestDataFactory;
import view.NotificationAUI;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;

/**
 * Class ProjectControllerUpdateProject
 */
public class ProjectControllerUpdateProject {

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
	public void testProjectParameterNull() throws Exception {

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.updateProject(null, new Project());
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'changes' null ist, wird eine Fehlermeldung angezeigt.
	 */
	@Test
	public void testChangesParameterNull() throws Exception {

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.updateProject(new Project(), null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Description' des Projekts 'changes' null ist, wird eine
	 * Fehlermeldung angezeigt.
	 */
	@Test
	public void testChangesDescriptionNull() throws Exception {
		Project newProject = new Project();
		newProject.setDescription(null);

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.updateProject(new Project(), newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Name' des Projekts 'changes' null ist, wird eine
	 * Fehlermeldung angezeigt.
	 */
	@Test
	public void testNameNull() throws Exception {
		Project newProject = new Project();
		newProject.setName(null);

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.updateProject(new Project(), newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Tasks' des Projekts 'changes' null ist, wird eine
	 * Fehlermeldung angezeigt.
	 */
	@Test
	public void testTasksNull() throws Exception {
		Project newProject = new Project();
		newProject.setTasks(null);

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.updateProject(new Project(), newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Workers' des Projekts 'changes' null ist, wird eine
	 * Fehlermeldung angezeigt.
	 */
	@Test
	public void testTeamNull() throws Exception {
		Project newProject = new Project();
		newProject.setWorkers(null);

		AtomicBoolean showErrorCalled = new AtomicBoolean(false);

		projectController.setTestNotificationAUI(new NotificationAUI() {

			@Override
			public void showNotification(String errMessage, boolean error) {
				showErrorCalled.set(true);

			}
		});

		assertFalse("Die NotificationAUI darf vor dem Aufruf von UpdateProject nicht aufgerufen worden sein.",
				showErrorCalled.get());
		projectController.updateProject(new Project(), newProject);
		assertTrue("Die NotificationAUI muss nach dem Aufruf von updateProject aufgerufen worden sein.",
				showErrorCalled.get());
	}

	/**
	 * Testet die erfolgreiche Aktualisierung eines Projekts.
	 **/
	@Test
	public void testUpdateProject() {

		Project newProject = new Project();
		newProject.setName("TestProject");
		newProject.setDeadline(LocalDateTime.now());

		Project project = company.getProjects().get(0);

		assertNotEquals("Zu Testzwecken müssen sich die Projekte in allen Attributen unterscheiden.",
				project.getDescription(), newProject.getDescription());
		assertNotEquals("Zu Testzwecken müssen sich die Projekte in allen Attributen unterscheiden.", project.getName(),
				newProject.getName());
		assertNotEquals("Zu Testzwecken müssen sich die Projekte in allen Attributen unterscheiden.",
				project.getDeadline(), newProject.getDeadline());

		projectController.updateProject(project, newProject);

		assertEquals("Das zu aktualisierende Projekt muss die aktualisierten Daten enthalten", project.getDescription(),
				newProject.getDescription());
		assertEquals("Das zu aktualisierende Projekt muss die aktualisierten Daten enthalten", project.getName(),
				newProject.getName());
		assertEquals("Das zu aktualisierende Projekt muss die aktualisierten Daten enthalten", project.getDeadline(),
				newProject.getDeadline());

	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Aktualisieren des Projektes
	 * aufgerufen wird.
	 *
	 * @throws Exception wird geworfen {@link ProjectController#addProject(Project)}
	 */
	@Test
	public void testAUIsCalled() throws Exception {

		AtomicBoolean refreshProjectListCalled = new AtomicBoolean(false);
		AtomicBoolean refreshProjectDataCalled = new AtomicBoolean(false);

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

		ArrayList<ProjectAUI> projectAUI = new ArrayList<ProjectAUI>();

		projectAUI.add(new ProjectAUI() {

			@Override
			public void refreshTeamList() {

			}

			@Override
			public void refreshTaskList() {

			}

			@Override
			public void refreshProjectData() {
				refreshProjectDataCalled.set(true);

			}
		});

		projectController.setCompanyAUI(companyAUI);
		projectController.setProjectAUI(projectAUI);

		Project changes = new Project();
		changes.setDeadline(LocalDateTime.now());

		assertFalse("Die AUI darf noch nicht aufgerufen worden sein.", refreshProjectListCalled.get());
		assertFalse("Die AUI darf noch nicht aufgerufen worden sein.", refreshProjectDataCalled.get());
		projectController.updateProject(company.getProjects().get(0), changes);
		assertTrue("Die AUI muss aufgerufen worden sein.", refreshProjectListCalled.get());
		assertTrue("Die AUI muss aufgerufen worden sein.", refreshProjectDataCalled.get());

	}
}
