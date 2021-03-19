package controller;

import static org.junit.Assert.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import model.Project;
import model.Stage;
import model.Task;
import model.TestDataFactory;
import model.Worker;
import model.History;

/**
 * Class StatisticsControllerTest
 */
public class StatisticsControllerTest {

	private MainController mainController;
	private StatisticsController statisticsController;

	/**
	 * Setzt Daten und Controller vor jedem Test
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		TestDataFactory factory = new TestDataFactory();
		mainController = new MainController();
		mainController.setCompany(factory.getTestCompany());
		statisticsController = new StatisticsController();

	}

	/**
	 * Testet die Aufgabenstatuskalkulation
	 */
	@Test
	public void canCalculateTaskStats() {
		Project project1 = new Project();

		Task task1 = new Task();// Start und enddateum für alle Stages
		task1.setTitle("Start und enddatum für alle Stages");
		ArrayList<History> histories1 = new ArrayList<History>();

		History history1 = new History(LocalDateTime.now().minus(Duration.ofDays(10)));
		history1.setEnd(LocalDateTime.now().minus(Duration.ofDays(9)));
		histories1.add(history1);

		History history2 = new History(LocalDateTime.now().minus(Duration.ofDays(9)));
		history2.setEnd(LocalDateTime.now().minus(Duration.ofDays(8)));
		histories1.add(history2);

		History history3 = new History(LocalDateTime.now().minus(Duration.ofDays(8)));
		history3.setEnd(LocalDateTime.now().minus(Duration.ofDays(7)));
		histories1.add(history3);

		History history4 = new History(LocalDateTime.now().minus(Duration.ofDays(7)));
		history4.setEnd(LocalDateTime.now().minus(Duration.ofDays(6)));
		histories1.add(history4);

		History history5 = new History(LocalDateTime.now().minus(Duration.ofDays(6)));
		history5.setEnd(LocalDateTime.now().minus(Duration.ofDays(5)));
		histories1.add(history5);

		History history6 = new History(LocalDateTime.now().minus(Duration.ofDays(5)));
		history6.setEnd(LocalDateTime.now().minus(Duration.ofDays(4)));
		histories1.add(history6);

		History history7 = new History(LocalDateTime.now().minus(Duration.ofDays(4)));
		history7.setEnd(LocalDateTime.now().minus(Duration.ofDays(3)));
		histories1.add(history7);

		History history8 = new History(LocalDateTime.now().minus(Duration.ofDays(3)));
		history8.setEnd(LocalDateTime.now().minus(Duration.ofDays(2)));
		histories1.add(history8);

		task1.setTransitions(histories1);

		Task task2 = new Task();// End fehlt
		task2.setTitle("End fehlt");
		ArrayList<History> histories2 = new ArrayList<History>();

		History history21 = new History(LocalDateTime.now().minus(Duration.ofDays(10)));
		history21.setEnd(LocalDateTime.now().minus(Duration.ofDays(9)));
		histories2.add(history21);

		History history22 = new History(LocalDateTime.now().minus(Duration.ofDays(9)));
		histories2.add(history22);
		task2.setTransitions(histories2);

		Task task3 = new Task();// start=null
		task3.setTitle("start=null");
		ArrayList<History> histories3 = new ArrayList<History>();

		History history31 = new History(LocalDateTime.now().minus(Duration.ofDays(10)));
		history3.setEnd(LocalDateTime.now().minus(Duration.ofDays(9)));
		histories3.add(history31);

		History history32 = new History(LocalDateTime.now());
		histories3.add(history32);

		task3.setTransitions(histories3);

		Task task4 = new Task();// Keine History Obejekte
		task4.setTitle("Keine history obejekte");
		ArrayList<History> histories41 = new ArrayList<History>();
		task4.setTransitions(histories41);

		project1.getTasks().add(task1);
		project1.getTasks().add(task2);
		project1.getTasks().add(task3);
		project1.getTasks().add(task4);

		project1.setDeadline(LocalDateTime.of(2020, 9, 19, 14, 30));
		project1.setName("SoPra KanBanBoard");
		project1.setDescription("Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan");

		String[][] ar = statisticsController.taskStats(project1);
		
		for(int i=0;i<ar.length;i++) { for(int k=0;k<12;k++) {
		System.out.print(ar[i][k]+"|"); } System.out.println(); }
		 
		System.out.println(); System.out.println("_____________________________");
		System.out.println();
		

	}

	/**
	 * Testet die Arbeiterrankingkalkulation
	 */
	@Test
	public void canCalculateWorkerRanking() {
		ArrayList<Project> projects = new ArrayList<>();
		ArrayList<Worker> workers = new ArrayList<>();

		// Worker für liste erstellen

		Worker worker1 = new Worker();// 5 Stages davon 2 aktive und beendet
		worker1.setFirstname("Jonas");
		worker1.setLastname("Kaup");
		workers.add(worker1);

		Worker worker2 = new Worker();// 3 Stages davon 1 aktive und beendet
		worker2.setFirstname("Sarah");
		worker2.setLastname(null);
		workers.add(worker2);

		Worker worker3 = new Worker();// 1 Stage inaktive
		worker3.setFirstname(null);
		worker3.setLastname("Heinrich");
		workers.add(worker3);

		Worker worker4 = new Worker();// 1 Stage end fehlt
		worker4.setFirstname(null);
		worker4.setLastname(null);
		workers.add(worker4);

		Worker worker5 = new Worker();// 2 stages beide nicht gewertet
		worker5.setFirstname("Fynn");
		worker5.setLastname("Debus");
		workers.add(worker5);

		Worker worker6 = new Worker();// unused
		worker6.setFirstname("Robin");
		worker6.setLastname("Kollbach");
		workers.add(worker6);

		// Projekte für Liste erstellen

		Project project1 = new Project();

		Task task1 = new Task();// Start und enddateum für alle Stages
		task1.setTitle("Start und enddatum für alle Stages");
		ArrayList<History> histories1 = new ArrayList<History>();

		History history1 = new History(LocalDateTime.now().minus(Duration.ofDays(10)));
		history1.setEnd(LocalDateTime.now().minus(Duration.ofDays(9)));
		history1.setStage(Stage.NEW);
		histories1.add(history1);
		//history1.setWorker(worker1);

		History history2 = new History(LocalDateTime.now().minus(Duration.ofDays(9)));
		history2.setEnd(LocalDateTime.now().minus(Duration.ofHours(204)));// 12 h aufgabe
		history1.setStage(Stage.ANALYSE);
		history2.setWorker(worker1);
		histories1.add(history2);

		History history3 = new History(LocalDateTime.now().minus(Duration.ofDays(8)));
		history3.setEnd(LocalDateTime.now().minus(Duration.ofDays(7)));
		history1.setStage(Stage.ANALYSEDONE);
		histories1.add(history3);
		//history3.setWorker(worker1);

		History history4 = new History(LocalDateTime.now().minus(Duration.ofDays(7)));
		history4.setEnd(LocalDateTime.now().minus(Duration.ofDays(6)));
		history1.setStage(Stage.IMPLEMENTATION);
		history4.setWorker(worker1);
		histories1.add(history4);


		History history5 = new History(LocalDateTime.now().minus(Duration.ofDays(6)));
		history5.setEnd(LocalDateTime.now().minus(Duration.ofDays(5)));
		history1.setStage(Stage.IMPLEMENTATIONDONE);
		histories1.add(history5);
		//history5.setWorker(worker1);

		History history6 = new History(LocalDateTime.now().minus(Duration.ofDays(5)));
		history6.setEnd(LocalDateTime.now().minus(Duration.ofDays(3)));
		history1.setStage(Stage.TEST);
		history6.setWorker(worker2);
		histories1.add(history6);

		History history7 = new History(LocalDateTime.now().minus(Duration.ofDays(3)));
		history7.setEnd(LocalDateTime.now().minus(Duration.ofDays(3)));
		history1.setStage(Stage.TESTDONE);
		histories1.add(history7);
		//history7.setWorker(worker2);

		History history8 = new History(LocalDateTime.now().minus(Duration.ofDays(3)));
		history8.setEnd(LocalDateTime.now().minus(Duration.ofDays(2)));
		history1.setStage(Stage.DONE);
		histories1.add(history8);
		//history8.setWorker(worker2);

		task1.setTransitions(histories1);

		Task task2 = new Task();// End fehlt
		task2.setTitle("End fehlt");
		ArrayList<History> histories2 = new ArrayList<History>();

		History history21 = new History(LocalDateTime.now().minus(Duration.ofDays(10)));
		history21.setEnd(LocalDateTime.now().minus(Duration.ofDays(5)));
		history21.setStage(Stage.NEW);
		histories2.add(history21);
		//history21.setWorker(worker3);

		History history22 = new History(LocalDateTime.now().minus(Duration.ofDays(5)));
		history22.setStage(Stage.ANALYSE);
		histories2.add(history22);
		history22.setWorker(worker4);

		task2.setTransitions(histories2);
		

		Task task3 = new Task();// Keine History Obejekte
		task3.setTitle("Keine history objekte");
		ArrayList<History> histories31 = new ArrayList<History>();
		task3.setTransitions(histories31);

		project1.getTasks().add(task1);
		project1.getTasks().add(task2);
		project1.getTasks().add(task3);

		project1.setDeadline(LocalDateTime.of(2020, 9, 19, 14, 30));
		project1.setName("SoPra KanBanBoard");
		project1.setDescription("Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan");

		projects.add(project1);
		
		String[][] ar=statisticsController.workerRanking(workers, projects);
		
		for(int i=0;i<ar.length;i++) {
			for(int k=0;k<6;k++) {
				System.out.print(ar[i][k]+"|"); 
			} 
			System.out.println(); 
		}
	}

}
