package controller;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.Project;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import view.NotificationAUI;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;

/**
 * Class ProjectControllerAUISettersTest
 */
public class ProjectControllerAUISettersTest {

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
	}

	/**
	 * Testet, ob der Setter für die ProjectAUI-Liste korrekt funktioniert.
	 **/
	@Test
	public void testProjectAUISetter() {

		Project project = company.getProjects().get(0);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		ArrayList<Task> tasks = project.getTasks();
		ArrayList<Worker> workers = project.getWorkers();
		boolean active = project.isActive();

		ArrayList<ProjectAUI> projectAUI = new ArrayList<ProjectAUI>();
		ProjectAUI pAUI = new ProjectAUI() {

			@Override
			public void refreshTeamList() {

			}

			@Override
			public void refreshTaskList() {

			}

			@Override
			public void refreshProjectData() {

			}
		};

		projectAUI.add(pAUI);

		projectController.setProjectAUI(projectAUI);

		ArrayList<ProjectAUI> getter = projectController.getProjectAUI();

		assertArrayEquals("Elemente der ProjectAUI muss der hinzugefügten ProjectAUI entsprechen.",
				projectAUI.toArray(), getter.toArray());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks.toArray(),
				project.getTasks().toArray());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", workers.toArray(),
				project.getWorkers().toArray());

	}

	/**
	 * Testet, ob der Setter für die Company-Liste korrekt funktioniert.
	 **/
	@Test
	public void testCompanyAUISetter() {

		Project project = company.getProjects().get(0);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		ArrayList<Task> tasks = project.getTasks();
		ArrayList<Worker> workers = project.getWorkers();
		boolean active = project.isActive();

		ArrayList<CompanyAUI> companyAUI = new ArrayList<CompanyAUI>();
		CompanyAUI cAUI = new CompanyAUI() {

			@Override
			public void showError(String error) {

			}

			@Override
			public void refreshWorkerList() {

			}

			@Override
			public void refreshProjectList() {

			}
		};

		companyAUI.add(cAUI);

		projectController.setCompanyAUI(companyAUI);

		ArrayList<CompanyAUI> getter = projectController.getCompanyAUI();

		assertArrayEquals("Elemente der CompanyAUI muss der hinzugefügten CompanyAUI entsprechen.",
				companyAUI.toArray(), getter.toArray());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks.toArray(),
				project.getTasks().toArray());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", workers.toArray(),
				project.getWorkers().toArray());

	}

	/**
	 * Testet, ob der Setter für die ProjectAUI-Liste korrekt funktioniert.
	 **/
	@Test
	public void testNotificationAUISetter() {

		Project project = company.getProjects().get(0);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		ArrayList<Task> tasks = project.getTasks();
		ArrayList<Worker> workers = project.getWorkers();
		boolean active = project.isActive();

		NotificationAUI notificationAUI = new NotificationAUI() {

			@Override
			public void showNotification(String msg, boolean isError) {

			}
		};

		projectController.setTestNotificationAUI(notificationAUI);

		NotificationAUI getter = projectController.getNotificationAUI();

		assertEquals("Elemente der ProjectAUI muss der hinzugefügten ProjectAUI entsprechen.", notificationAUI, getter);
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", active, project.isActive());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", deadline, project.getDeadline());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", description, project.getDescription());
		assertEquals("Attribute des Projekts dürfen sich nicht verändern.", name, project.getName());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", tasks.toArray(),
				project.getTasks().toArray());
		assertArrayEquals("Attribute des Projekts dürfen sich nicht verändern.", workers.toArray(),
				project.getWorkers().toArray());

	}

}
