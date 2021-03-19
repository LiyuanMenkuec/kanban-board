package controller;

import java.time.LocalDateTime;
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
import model.Worker;
import view.NotificationAUI;
import view.auis.CompanyAUI;

/**
 * Class ProjectControllerSetProjectStateTest
 */
public class ProjectControllerSetProjectStateTest {

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
		projectController.setProjectState(null, true);
		assertTrue("Die CompanyAUI muss aufgerufen worden sein.", showErrorCalled.get());

	}

	/**
	 * Testet das erfolgreiche Archivieren eines Projekts.
	 **/
	@Test
	public void testArchievingProject() {

		Project project = company.getProjects().get(0);
		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();

		taskController.addWorker(project.getTasks().get(1), project.getWorkers().get(0));

		Object[] tasks = project.getTasks().toArray();

		assertTrue("Projekt muss aktiv sein.", project.isActive());

		projectController.setProjectState(project, true);

		assertFalse("Projekt muss als archiviert markiert worden sein.", project.isActive());
		assertTrue("Da Projekt darf keine Mitarbeiter mehr zugewiesen haben.", project.getWorkers().isEmpty());
		assertArrayEquals("Die Attribute des Projekts dürfen sich nicht verändern.", tasks,
				project.getTasks().toArray());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());

	}

	/**
	 * Testet das Erfolgreiche Archivieren eines Projekts ohne Mitarbeiter und
	 * Aufgaben
	 **/
	@Test
	public void testArchievingProjectWithEmptyLists() {

		Project project = company.getProjects().get(0);
		project.setTasks(new ArrayList<Task>());
		project.setWorkers(new ArrayList<Worker>());

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();

		Object[] tasks = project.getTasks().toArray();

		assertTrue("Projekt muss aktiv sein.", project.isActive());

		projectController.setProjectState(project, true);

		assertFalse("Projekt muss als archiviert markiert worden sein.", project.isActive());
		assertTrue("Da Projekt darf keine Mitarbeiter mehr zugewiesen haben.", project.getWorkers().isEmpty());
		assertArrayEquals("Die Attribute des Projekts dürfen sich nicht verändern.", tasks,
				project.getTasks().toArray());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Die Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());

	}

	/**
	 * Testet, ob ein Projekt wieder korrekt reaktiviert werden kann.
	 **/
	@Test
	public void testRestoreProject() {

		Project project = new Project();
		project.setActive(false);
		taskController.addTask(project, new Task());

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		Object[] tasks = project.getTasks().toArray();

		projectController.addProject(project);

		assertFalse("Das Projekt darf nicht aktiv sein.", project.isActive());

		projectController.setProjectState(project, false);

		assertTrue("Projekt muss wieder als aktiv markiert sein.", project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks, project.getTasks().toArray());
		assertTrue("Das Projekt darf keine Mitarbeiter zugewiesen haben.", project.getWorkers().isEmpty());

	}

	/**
	 * Testet, ob ein Projekt aktiv bleibt, wenn setProjectState mit false
	 * aufgerufen wird und das Projekt bereits aktiv ist.
	 * 
	 **/
	@Test
	public void testProjectStayActive() {

		Project project = mainController.getCompany().getProjects().get(0);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		Object[] tasks = project.getTasks().toArray();
		Object[] workers = project.getWorkers().toArray();

		assertTrue("Eingegebenes Projekt muss aktiv sein.", project.isActive());

		projectController.setProjectState(project, false);

		assertTrue("Eingegebenes Projekt muss aktiv bleiben.", project.isActive());

		assertTrue("Attribute des Projekts dürfen sich nicht verändern.", project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks, project.getTasks().toArray());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", workers,
				project.getWorkers().toArray());

	}

	/**
	 * Testet, ob ein Projekt archiviert bleibt, wenn setProjectState mit false
	 * aufgerufen wird und das Projekt bereits aktiv ist.
	 * 
	 **/
	@Test
	public void testProjectStayArchived() {

		Project project = mainController.getCompany().getProjects().get(0);

		project.setActive(false);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		Object[] tasks = project.getTasks().toArray();
		Object[] workers = project.getWorkers().toArray();

		assertFalse("Eingegebenes Projekt muss inaktiv sein.", project.isActive());

		projectController.setProjectState(project, true);

		assertFalse("Eingegebenes Projekt muss archiviert bleiben bleiben.", project.isActive());

		assertFalse("Attribute des Projekts dürfen sich nicht verändern.", project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks, project.getTasks().toArray());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", workers,
				project.getWorkers().toArray());

	}

}
