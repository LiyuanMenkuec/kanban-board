package controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import controller.TeamController;
import controller.MainController;
import model.Worker;
import view.auis.ProjectAUI;
import model.Company;
import model.History;
import model.Project;
import model.Task;
import model.TestDataFactory;

/**
 * @author elias
 *
 */
public class TeamControllerTest {

	private TeamController teamController;
	private MainController mainController;
	private TestDataFactory testDataFactory;
	private Company company;
	Project project;
	Worker newWorker;
	Worker secondNewWorker;

	/**
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;

		mainController = new MainController();
		teamController = new TeamController(mainController);
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
		teamController.getProjectAUI().add(testProjectAUI);
		testDataFactory = new TestDataFactory();
		company = testDataFactory.getTestCompany();
		mainController.setCompany(company);
		newWorker = new Worker();
		secondNewWorker = new Worker();
		project = company.getProjects().get(0);
	}

	/**
	 * Es wird überprüft, ob project gleich null ist
	 */
	@Test
	public void projectNotNull() {
		assertTrue("project darf nicht null sein.", project != null);
		assertTrue("newWorker darf nicht null sein.", newWorker != null);
	}

	/**
	 * Es wird ein neuer Worker dem Projekt hinzugefügt und dann sichergestellt, ob
	 * er in der Liste der Arbeiter enthalten ist
	 */
	@Test
	public void testAddWorkerToProject() {
		teamController.addWorkerToProject(project, newWorker);
		assertTrue("Das Projekt muss den neuen Worker enthalten.", project.getWorkers().contains(newWorker));

		teamController.addWorkerToProject(null, secondNewWorker);
		assertFalse("Das Projekt darf secondNewWorker nicht enthalten.",
				project.getWorkers().contains(secondNewWorker));

		ArrayList<Worker> projectWorkersBefore = project.getWorkers();
		teamController.addWorkerToProject(project, null);
		ArrayList<Worker> projectWorkersAfter = project.getWorkers();
		assertTrue("Die Listen projectWorkersBefore und projectWorkersAfter müssen gleich sein.",
				projectWorkersBefore == projectWorkersAfter);

		teamController.addWorkerToProject(project, newWorker);
		assertTrue("Das Projekt darf newWorker nicht zweimal enthalten.",
				project.getWorkers().indexOf(newWorker) == project.getWorkers().lastIndexOf(newWorker));
	}

	/**
	 * Es wird ein neuer Worker dem Projekt hinzugefügt und wieder entfernt und
	 * geprüft, ob er nicht mehr in der Liste der Arbeiter enthalten ist
	 */
	@Test
	public void testRemoveWorkerFromProject() {
		teamController.addWorkerToProject(project, newWorker);
		teamController.removeWorkerFromProject(project, newWorker);
		assertFalse("Das Projekt darf den neuen Worker nicht enthalten.", project.getWorkers().contains(newWorker));

		teamController.addWorkerToProject(project, newWorker);
		boolean interrupted = false;
		try {
			teamController.removeWorkerFromProject(null, newWorker);
		} catch (NullPointerException e) {
			interrupted = true;
		}
		assertThat(interrupted, is(true));
		assertTrue("Das Projekt muss newWorker enthalten.", project.getWorkers().contains(newWorker));

		teamController.removeWorkerFromProject(project, project.getWorkers().get(0));
		assertTrue("Das Projekt muss newWorker enthalten.", project.getWorkers().contains(newWorker));

		ArrayList<Worker> projectWorkersBefore = project.getWorkers();
		interrupted = false;
		try {
			teamController.removeWorkerFromProject(project, null);
		} catch (NullPointerException e) {
			interrupted = true;
		}
		assertThat(interrupted, is(true));
		ArrayList<Worker> projectWorkersAfter = project.getWorkers();
		assertTrue("Die Listen projectWorkersBefore und projectWorkersAfter müssen gleich sein.",
				projectWorkersBefore == projectWorkersAfter);

		interrupted = false;
		try {
			teamController.removeWorkerFromProject(project, new Worker());
		} catch (NullPointerException e) {
			interrupted = true;
		}
		assertThat(interrupted, is(false));

		interrupted = false;
		try {
			teamController.removeWorkerFromProject(null, null);
		} catch (NullPointerException e) {
			interrupted = true;
		}
		assertThat(interrupted, is(true));
	}

	/**
	 * Es wird geprüft, ob die Liste der Arbeiter des Projekts mit der Liste, die
	 * von der Methode im TeamController zurückgegeben wird, übereinstimmen
	 */
	@Test
	public void testGetWorkersFromProject() {
		assertEquals("Die Workers Liste muss übereinstimmen mit der zurückgegebenen Liste.", project.getWorkers(),
				teamController.getWorkersFromProject(project));
	}

	/**
	 * Es wird geprüft, ob worker2 nicht in der Liste der unbeschäftigten Arbeiter
	 * ist, da dieser einer Aufgabe zugewiesen ist
	 */
	@Test
	public void testGetUnassignedWorkersFromProject() {
		Project project = new Project();

		Worker assignedWorker = new Worker();
		Worker unassignedWorker = new Worker();

		project.getWorkers().add(assignedWorker);
		project.getWorkers().add(unassignedWorker);

		History history = new History(LocalDateTime.now());

		history.setWorker(assignedWorker);

		Task task = new Task();

		task.getTransitions().add(history);

		project.getWorkers().add(assignedWorker);
		project.getWorkers().add(unassignedWorker);
		project.getTasks().add(task);

		mainController.getCompany().getProjects().add(project);

		assertThat(teamController.getUnassignedTaskWorkersFromProject(project), not(hasItem(assignedWorker)));
		assertThat(teamController.getUnassignedTaskWorkersFromProject(project), hasItem(unassignedWorker));
	}

}
