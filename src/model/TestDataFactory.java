package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.scene.image.Image;
import view.MainWindowController;

/**
 * Diese Klasse dient dazu Company-Objekte mit Testwerten zu erstellen
 * 
 * @author Anne, Abdulrazzak, Gero
 *
 */
public class TestDataFactory {
	/**
	 * Der Konstruktor kann leer bleiben, da wir keine Variablen setzen müssen oder
	 * irgendetwas anderes.
	 */
	public TestDataFactory() {

	}

	/**
	 * Erzeugt eine Company mit 4 Mitarbeiter, wobei ein Mitarbeiter einer Aufgabe
	 * zugewiesen ist und einer zu keinem Projekt gehört
	 * 
	 * @return
	 */
	public Company getTestCompany() {
		Company company = new Company();
		company.setName("Unsere Firma");

		Project project1 = new Project();
		project1.setDeadline(LocalDateTime.of(2020, 9, 19, 14, 30));
		project1.setName("SoPra KanBanBoard");
		project1.setDescription("Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan");
		company.getProjects().add(project1);
		addWorker(company);
		Task task1 = new Task();
		task1.setTitle("Fabian ärgern");
		Task task2 = new Task();
		task2.setTitle("Klassen kommentieren");
		Task task3 = new Task();
		task3.setTitle("Fabian zufrieden stellen");
		Comment comment1 = new Comment();
		comment1.setContent("Das macht immer Spaß!");
		comment1.setCreatedAt(LocalDateTime.now());
		comment1.setWorker(company.getWorkers().get(1));
		ArrayList<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		task1.setComments(comments);
		History history1 = new History(LocalDateTime.of(2020, 8, 19, 14, 30));
		history1.setWorker(company.getWorkers().get(1));
		history1.setStage(Stage.ANALYSE);
		task1.getTransitions().add(history1);
		project1.getWorkers().add(company.getWorkers().get(1));
		project1.getWorkers().add(company.getWorkers().get(0));
		project1.getWorkers().add(company.getWorkers().get(2));
		project1.getTasks().add(task1);
		project1.getTasks().add(task2);
		project1.getTasks().add(task3);
		return company;
	}
	/**
	 * Erzeugt einen Mitarbeiter
	 * @return
	 */
	private void addSpecificWorker(Company company) {
		Worker worker1 = new Worker();
		worker1.setFirstname("Anne");
		worker1.setLastname("Stockey");
		worker1.setBirthDate(LocalDate.of(2001, 8, 16));
		worker1.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Anne.jpg")));
		Worker worker2 = new Worker();
		worker2.setFirstname("Abdulrazzak");
		worker2.setLastname("Shaker");
		worker2.setBirthDate(LocalDate.of(1995, 2, 15));
		worker2.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Abdul.jpg")));
		Worker worker3 = new Worker();
		worker3.setFirstname("Gero");
		worker3.setLastname("Grühn");
		worker3.setBirthDate(LocalDate.of(1989, 8, 24));
		worker3.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Gero.jpg")));
		Worker worker4 = new Worker();
		worker4.setFirstname("Will");
		worker4.setLastname("Smith");
		worker4.setBirthDate(LocalDate.of(1968, 9, 25));
		worker4.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Will.jpg")));
		worker4.setActive(false);
		Worker worker5 = new Worker();
		worker5.setFirstname("Elias");
		worker5.setLastname("Zeren");
		worker5.setBirthDate(LocalDate.of(1999, 12, 31));
		worker5.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Elias.jpg")));
		
		company.getWorkers().add(worker1);
		company.getWorkers().add(worker2);
		company.getWorkers().add(worker3);
		company.getWorkers().add(worker4);
		company.getWorkers().add(worker5);
	}
	
	/**
	 * Fügt Worker hinzu
	 * @param company
	 */
	private void addWorker(Company company) {
		addSpecificWorker(company);
		Worker worker6 = new Worker();
		worker6.setFirstname("Julian");
		worker6.setLastname("Adler");
		worker6.setBirthDate(LocalDate.of(1999, 11, 30));
		worker6.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Julian.jpg")));

		Worker worker7 = new Worker();
		worker7.setFirstname("Liyuan");
		worker7.setLastname("Menküc");
		worker7.setBirthDate(LocalDate.of(1999, 10, 31));
		worker7.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Liyuan.jpg")));

		Worker worker8 = new Worker();
		worker8.setFirstname("Tim");
		worker8.setLastname("Bräuker");
		worker8.setBirthDate(LocalDate.of(2000, 9, 30));
		worker8.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Tim.png")));

		Worker worker9 = new Worker();
		worker9.setFirstname("Florian");
		worker9.setLastname("Wellner");
		worker9.setBirthDate(LocalDate.of(2000, 6, 13));
		worker9.setPicture(new Image(MainWindowController.class.getResourceAsStream("/resources/icons/Florian.jpg")));

		
		company.getWorkers().add(worker6);
		company.getWorkers().add(worker7);
		company.getWorkers().add(worker8);
		company.getWorkers().add(worker9);
	}

	/**
	 * Erzeugt eine Company mit mehreren Projekten
	 * 
	 * @return zweite Test-Company
	 */
	public Company getTestCompanyWithMoreProjects() {
		Company company = getTestCompany();

		Project project2 = new Project();
		project2.setDeadline(LocalDateTime.of(2020, 9, 19, 15, 00));
		project2.setName("SoPra KanBanBoard2");
		project2.setDescription(
				"Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan - die Zweite");
		project2.setActive(false);
		company.getProjects().add(project2);

		Project project3 = new Project();
		project3.setDeadline(LocalDateTime.of(2020, 9, 19, 15, 30));
		project3.setName("SoPra KanBanBoard3");
		project3.setDescription(
				"Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan - die Dritte");
		company.getProjects().add(project3);

		return company;
	}

	/**
	 * Erzeugt eine Company mit noch mehreren Projekten
	 * 
	 * @return dritte Test-Company
	 */
	public Company getTestCompanyWithEvenMoreProjects() {

		Company company = new Company();
		company.setName("Unsere Firma");
		addWorker(company);
		
		LocalDateTime offset = LocalDateTime.now();
		
		initProject1(company, offset);
		initProject2(company, offset);
		initProject3(company, offset);

		return company;

	}
	
	
	
	
	/**
	 * Projekt1 initialisieren
	 * 
	 * @param company Company des zu initialisierenden Projekts
	 * @param offset aktuelle LocalDateTime
	 */
	private void initProject1(Company company, LocalDateTime offset) {
		Project project1 = new Project();
		project1.setDeadline(offset.plusDays(20));
		project1.setName("SoPra KanBanBoard");
		project1.setDescription("Ein Programm zum Verwalten von Projekten, Aufgaben und Mitarbeitern mittels KanBan");
		company.getProjects().add(project1);
		project1.getWorkers().add(company.getWorkers().get(0));
		project1.getWorkers().add(company.getWorkers().get(1));
		project1.getWorkers().add(company.getWorkers().get(2));
		project1.getWorkers().add(company.getWorkers().get(4));
		Task task1 = TestDataFactoyHelperUtil.createLongTask(company, project1, offset);
		Task task2 = TestDataFactoyHelperUtil.task2(project1, offset);
		Task task3 = TestDataFactoyHelperUtil.task3(company, project1, offset);
		Task task4 = TestDataFactoyHelperUtil.task4(offset);
		Task task5 = TestDataFactoyHelperUtil.task5(project1, offset);
		Task task6 = TestDataFactoyHelperUtil.task6(project1, offset);
		Task task7 = TestDataFactoyHelperUtil.task7(project1, offset);
		Task task8 = TestDataFactoyHelperUtil.task8(project1, offset);
		Task task9 = TestDataFactoyHelperUtil.task9(project1, offset);
		project1.getTasks().add(task1);
		project1.getTasks().add(task2);
		project1.getTasks().add(task3);
		project1.getTasks().add(task4);
		project1.getTasks().add(task5);
		project1.getTasks().add(task6);
		project1.getTasks().add(task7);
		project1.getTasks().add(task8);
		project1.getTasks().add(task9);
	}
	
	/**
	 * Erzeugt einen Task
	 * @param company
	 * @param project2
	 * @param offset
	 * @return
	 */
	private Task task22(Project project2,LocalDateTime offset) {
		Task task2 = new Task();
		task2.setTitle("Klausur planen");
		task2.setDeadline(offset.minusDays(4));
		task2.getTransitions().clear();
		History historyTask2New = new History(offset.minusDays(12));
		historyTask2New.setStage(Stage.NEW);
		historyTask2New.setEnd(offset.minusDays(3));
		task2.getTransitions().add(historyTask2New);
		History historyTask2Analyse = new History(offset.minusDays(3));
		historyTask2Analyse.setWorker(project2.getWorkers().get(0));
		historyTask2Analyse.setStage(Stage.ANALYSE);
		task2.getTransitions().add(historyTask2Analyse);
		
		return task2;
	}
	/**
	 * Projekt2 initialisieren
	 * 
	 * @param company Company des zu initialisierenden Projekts
	 * @param offset aktuelle LocalDateTime
	 */
	private void initProject2(Company company, LocalDateTime offset) {
		Project project2 = new Project();
		project2.setDeadline(offset.minusDays(1));
		project2.setName("Klausurverwaltung");
		project2.setDescription("Die Verwaltung der Klausuren wird hier organisiert.");
		company.getProjects().add(project2);
		project2.getWorkers().add(company.getWorkers().get(7));
		Task task1 = TestDataFactoyHelperUtil.createAnotherLongTask(project2, offset);
		Task task2 = task22(project2, offset);
		Task task3 = new Task();
		task3.setTitle("Klausur durchführen");
		Comment comment1 = new Comment();
		comment1.setContent("Das macht immer Spaß!");
		comment1.setCreatedAt(offset);
		comment1.setWorker(company.getWorkers().get(3));
		ArrayList<Comment> comments = new ArrayList<>();
		comments.add(comment1);
		task1.setComments(comments);
		project2.getTasks().add(task1);
		project2.getTasks().add(task2);
		project2.getTasks().add(task3);
	}
	/**
	 * Projekt3 initialisieren
	 * 
	 * @param company Company des zu initialisierenden Projekts
	 * @param offset aktuelle LocalDateTime
	 */
	private void initProject3(Company company, LocalDateTime offset) {
		Project project2 = new Project();
		project2.setDeadline(offset.plusDays(100));
		project2.setName("Urlaubsplanung");
		project2.setDescription("Die Urlaubsverwaötung für alle Mitarbeitenden.");
		project2.setActive(false);
		company.getProjects().add(project2);
		
	}
}
