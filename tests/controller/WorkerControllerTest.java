package controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import controller.MainController;
import controller.WorkerController;
import model.Comment;
import model.Company;
import model.Project;
import model.Task;
import model.TestDataFactory;
//import exceptions.EmptyStringParameterException;
//import exceptions.GtdContextlistException;
import model.Worker;
import view.NotificationAUI;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;
import view.auis.WorkerAUI;

/**
 * Testet die Methoden in der Klasse WorkerController.
 *
 * @author Abdulrazzak Shaker
 */
public class WorkerControllerTest {

	private WorkerController workerController;
	private MainController mainController;
	private Company company;
	private ArrayList<Worker> allWorkers;

	/**
	 * Wir brauchen für die Initialisierung des Tests der mainController für den
	 * Zugriff auf alle anderen Controller Wir brauchen die Firma, die in der
	 * TestDataFactory erstellt wurde. allWorkers zum schnellen Zugriff auf alle
	 * Mitarbeiter.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		// Initialisierung des Tests
		mainController = new MainController();
		workerController = new WorkerController(mainController);
		mainController.setWorkerController(workerController);
		company = new TestDataFactory().getTestCompany();
		mainController.setCompany(company);
		allWorkers = mainController.getCompany().getWorkers();
	}

	/**
	 * Wenn der Parameter mainController null ist, wird eine Exception geworfen.
	 * 
	 * @throws Exception wird geworfen
	 */
	@Test(expected = NullPointerException.class)
	public void testWorkerControllerAufNull() throws Exception {
		WorkerController workerController = new WorkerController(null);
	}

	/**
	 * testet ob wirklich alle mitarbeiter der Firma durch die Methode
	 * getAllWorkers() im WorkerController zurückgegeben wird.
	 */
	@Test
	public void testGetAllWorkers() {
		ArrayList<Worker> allWorkersList = workerController.getAllWorkers();
		int i = 0;
		for (Worker currentWorker : allWorkersList) {
			assertThat(currentWorker, is(allWorkers.get(i)));
			i = i + 1;
		}
	}

	/**
	 * testet ob ein gegebene Mitarbeiter wirklcih in die Liste aller Mitarbeiter
	 * addiert wird.
	 */
	@Test
	public void testAddWorker() {
		Worker worker = new Worker();
		worker.setFirstname("Khaled");
		worker.setLastname("Younis");
		worker.setBirthDate(LocalDate.of(1996, 12, 30));
		workerController.addWorker(worker);
		if (!allWorkers.contains(worker)) {
			fail();
		}
	}

	/**
	 * testet ob der Methode getCurrentTask im WorkerController richtig arbeitet
	 * wenn ein Mitarbeiter gegeben wird , der keine CurrentTask hat (keiner Task
	 * zugewiesen ist).
	 */
	@Test
	public void testGetCurrentTaskWithNoTask() {
		Worker worker;
		for (Worker currentWorker : allWorkers) {
			if (currentWorker.getFirstname() == "Anne") {
				worker = currentWorker;
				Task task = workerController.getCurrentTask(worker);
				assertNull(task);
				break;
			}
		}

	}

	/**
	 * Wenn der Parameter worker null ist, wird eine Exception geworfen.
	 * 
	 * @throws Exception wird geworfen
	 */
	@Test(expected = NullPointerException.class)
	public void testGetCurrentTaskAufNull() throws Exception {
		mainController.getWorkerController().getCurrentTask(null);
	}

	/**
	 * testet ob der Methode getCurrentTask im WorkerController richtig arbeitet
	 * wenn ein Mitarbeiter gegeben wird , der eine CurrentTask hat (einer Task
	 * zugewiesen ist).
	 */
	@Test
	public void testGetCurrentTaskWithTask() {
		ArrayList<Project> projects = company.getProjects();
		for (Project currentProject : projects) {
			ArrayList<Task> tasks = currentProject.getTasks();
			for (Task currentTask : tasks) {
				Worker currentWorker = currentTask.getCurrentWorker();
				if (currentWorker.getFirstname() == "Abdulrazzak") {
					Task task = workerController.getCurrentTask(currentWorker);
					assertEquals(task, currentTask);
					break;
				}
			}
		}

	}

	/**
	 * testet ob ein Mitarbeiter wirklcih bearbeitet wird. Ein neuer Mitarbeiter
	 * wird erzeugt und an die Methode updateWorker() im WorkerController weiter
	 * geleitet. und dann wird überprüft, ob der Mitarbeiter im Model schon
	 * bearbeitet wurde.
	 */
	@Test
	public void testUpdateWorker() {
		Worker worker = allWorkers.get(1);

		Worker changes = new Worker();

		changes.setFirstname("Maxim");
		changes.setLastname("Khalil");
		changes.setBirthDate(LocalDate.of(1978, 1, 1));

		workerController.updateWorker(worker, changes);

		assertThat(worker.getFirstname(), is(changes.getFirstname()));
		assertThat(worker.getLastname(), is(changes.getLastname()));
		assertThat(worker.getBirthDate(), is(changes.getBirthDate()));

	}

	/**
	 * Prüft den Aufruf von updateWorker mit null-values
	 */
	@Test
	public void testUpdateWorkerAufNull(){
		try {
			mainController.getWorkerController().updateWorker(null, null);
		}catch(NullPointerException exception){}
		
		try {
			Worker changes = new Worker();
			mainController.getWorkerController().updateWorker(null, changes);
		}catch(NullPointerException exception){}
		
		try {
			Worker worker = new Worker();
			mainController.getWorkerController().updateWorker(worker, null);
		}catch(NullPointerException exception){}
		
		Worker worker = new Worker();
		Worker changes = new Worker();
		mainController.getWorkerController().addWorker(worker);
		mainController.getWorkerController().updateWorker(worker, worker);
		
		mainController.getWorkerController().updateWorker(worker, changes);
	}

	/**
	 * testet ob das aktive Attribut eines Mitarbeiters wie gewünscht gesetzt wird.
	 */
	@Test
	public void testSetWorkerState() {
		Worker worker = new Worker();
		mainController.getWorkerController().setWorkerState(false, worker);
		assertThat(worker.isActive(), is(false));
	}

	/**
	 * Wenn der Parameter worker null ist, wird eine Exception geworfen.
	 * 
	 * @throws Exception wird geworfen
	 */
	@Test(expected = NullPointerException.class)
	public void testSetWorkerStateAufNull() throws Exception {
		mainController.getWorkerController().setWorkerState(true, null);
	}

	/**
	 * testet ob ein Mitarbeiter vom active auf inactive wirklich gesetzt wird.
	 */
	@Test
	public void testChangeActiveToInactive() {
		ArrayList<Worker> workers = company.getWorkers();
		Worker testWorker = workers.get(0);
		workerController.changeWorkerState(testWorker);
		assertTrue(!testWorker.isActive());

	}

	/**
	 * testet ob ein Mitarbeiter vom inactive auf active wirklcih gesetzt wird.
	 */
	@Test
	public void testChangeInactiveToActive() {
		for (Worker currentWorker : allWorkers) {
			if (currentWorker.getFirstname() == "Will") {
				workerController.changeWorkerState(currentWorker);
				assertTrue(currentWorker.isActive());
			}
		}

	}

	/**
	 * Wenn der Parameter worker null ist, wird eine Exception geworfen.
	 * 
	 * @throws Exception wird geworfen
	 */
	@Test(expected = NullPointerException.class)
	public void testChangeWorkerStateAufNull() throws Exception {
		mainController.getWorkerController().changeWorkerState(null);
	}

	/**
	 * testet ob die Liste aller Mitarbeiter, die keinem Projekt zugewiesen sind, in
	 * der Methode getUnassignedWorkers() vom WorkerController zurückgegeben wird.
	 */
	@Test
	public void testGetUnassignedWorkers() {
		company.getWorkers().add(new Worker());
		ArrayList<Worker> workers = workerController.getUnassignedProjectWorkers();
		for (Worker currentWorker : allWorkers) {
			if (currentWorker.isActive()
					&& mainController.getWorkerController().getProjectOfWorker(currentWorker) == null) {
				assertThat(workers.contains(currentWorker), is(true));
			}
		}
	}

	/**
	 * testet ob eine Liste von allen inaktiven Mitarbeiter zurückgegeben wird.
	 */
	@Test
	public void testGetInactiveWorkers() {
		ArrayList<Worker> workers = new ArrayList<>();
		for (Worker currentWorker : allWorkers) {
			if (!currentWorker.isActive()) {
				workers.add(currentWorker);
			}
		}
		ArrayList<Worker> testWorkers = mainController.getWorkerController().getInactiveWorkers();
		for (int i = 0; i < testWorkers.size(); i++) {
			assertTrue(workers.get(i) == testWorkers.get(i));
		}
	}

	/**
	 * Testet, ob der Getter für die Company-Liste korrekt funktioniert.
	 **/
	@Test
	public void testCompanyAUIGetter() {

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

		workerController.setCompanyAUI(companyAUI);

		ArrayList<CompanyAUI> getter = workerController.getCompanyAUI();

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
	 * Testet, ob der Getter für die Worker-Liste korrekt funktioniert.
	 **/
	@Test
	public void testworkerAUIGetter() {

		Project project = company.getProjects().get(0);

		LocalDateTime deadline = project.getDeadline();
		String description = project.getDescription();
		String name = project.getName();
		ArrayList<Task> tasks = project.getTasks();
		ArrayList<Worker> workers = project.getWorkers();
		boolean active = project.isActive();

		ArrayList<WorkerAUI> workerAUI = new ArrayList<WorkerAUI>();
		WorkerAUI wAUI = new WorkerAUI() {

			@Override
			public void refreshWorkerData() {

			}

		};

		workerAUI.add(wAUI);

		workerController.setWorkerAUI(workerAUI);

		ArrayList<WorkerAUI> getter = workerController.getWorkerAUI();

		assertArrayEquals("Elemente der CompanyAUI muss der hinzugefügten CompanyAUI entsprechen.", workerAUI.toArray(),
				getter.toArray());
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
	 * Prüft die Getter-Methode der ProjectAUI-Liste
	 */
	@Test
	public void testProjectAUIGetter() {

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

		workerController.setProjectAUI(projectAUI);

		ArrayList<ProjectAUI> getter = workerController.getProjectAUI();

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
}
