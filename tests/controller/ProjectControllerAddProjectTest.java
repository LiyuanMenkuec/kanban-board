package controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.Project;
import model.TestDataFactory;
import view.NotificationAUI;
import view.auis.CompanyAUI;

/**
 * Class ProjectControllerAddProjectTest
 */
public class ProjectControllerAddProjectTest {

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

		final ArrayList<CompanyAUI> companyAUI = new ArrayList<>();

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
	 * Wenn der Parameter 'project' null ist, wird eine Fehlermeldung im Programm
	 * angezeigt.
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
		projectController.addProject(null);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());
	}

	/**
	 * Wenn der Parameter 'Description' des Projekts 'project' null ist, wird eine
	 * Fehlermeldung ausgegeben.
	 * 
	 * @throws Exception die geworfen wird
	 */
	@Test
	public void testDescriptionNull() throws Exception {
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
		projectController.addProject(newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Name' des Projekts 'project' null ist, wird eine
	 * Fehlermeldung ausgegeben.
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
		projectController.addProject(newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Wenn der Parameter 'Tasks' des Projekts 'project' null ist, wird eine
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
		projectController.addProject(newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());
	}

	/**
	 * Wenn der Parameter 'Workers' des Projekts 'project' null ist, wird eine
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

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				showErrorCalled.get());
		projectController.addProject(newProject);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());
	}

	/**
	 * Soll ein Projekt in die Projektliste eingefügt werden, welches bereits in der
	 * Projektliste ist, darf sich die Projektliste nicht verändern.
	 **/
	@Test
	public void testAddDuplicate() {

		Object[] projectList = company.getProjects().toArray();

		Project duplicateProject = company.getProjects().get(0);
		projectController.addProject(duplicateProject);

		ArrayList<Project> newProjectList = company.getProjects();

		assertArrayEquals("Die Projektliste darf sich nicht verändern.", projectList, newProjectList.toArray());

	}

	/**
	 * Testet das erfolgreiche Einfügen eines neuen Projekts in die Projektliste.
	 **/
	@Test
	public void testAddProject() {

		assertEquals("Die Testliste muss zu beginn ein Projekt enthalten.", 1, company.getProjects().size());

		Project toAdd = new Project();

		LocalDateTime deadline = toAdd.getDeadline();
		String description = toAdd.getDescription();
		String name = toAdd.getName();
		boolean active = toAdd.isActive();
		Object[] tasks = toAdd.getTasks().toArray();
		Object[] team = toAdd.getWorkers().toArray();

		projectController.addProject(toAdd);

		assertEquals("Nach dem Hinzufügen muss die Firma zwei Projekte besitzen.", 2, company.getProjects().size());

		Project addedProject = company.getProjects().get(1);

		assertEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", description,
				addedProject.getDescription());
		assertEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", deadline, addedProject.getDeadline());
		assertEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", name, addedProject.getName());
		assertEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", active, addedProject.isActive());
		assertArrayEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", tasks,
				addedProject.getTasks().toArray());
		assertArrayEquals("Die Attribute eines Projekts dürfen sich nicht verändern.", team,
				addedProject.getWorkers().toArray());

	}

	/**
	 * Testet, ob die AbstractUserInterfaces nach dem Erzeugen und Einfügen der
	 * Kontextliste aufgerufen wird.
	 *
	 * @throws Exception wird geworfen {@link ProjectController#addProject(Project)}
	 */
	@Test
	public void testRefreshProjectListCalled() throws Exception {

		AtomicBoolean refreshProjectListCalled = new AtomicBoolean(false);

		ArrayList<CompanyAUI> companyAUI = new ArrayList<CompanyAUI>();

		companyAUI.add(new CompanyAUI() {

			@Override
			public void refreshProjectList() {
				refreshProjectListCalled.set(true);

			}

			@Override
			public void refreshWorkerList() {

			}

			@Override
			public void showError(String error) {

			}
		});

		projectController.setCompanyAUI(companyAUI);

		assertFalse("Die CompanyAUI darf vor dem Aufruf von addProject nicht aufgerufen werden.",
				refreshProjectListCalled.get());
		projectController.addProject(new Project());
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", refreshProjectListCalled.get());

	}
}
