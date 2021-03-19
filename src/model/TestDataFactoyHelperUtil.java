package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * Nur eine kleine Hilfsklasse zum Auslagern einiger Methoden
 * von TestDataFactory
 * @author Gero Grühn
 *
 */
public class TestDataFactoyHelperUtil {
	public static Task createLongTask(Company company,Project project1,LocalDateTime offset) {
		Task task1 = new Task();task1.setTitle("Fabian ärgern");
		task1.setDescription("Fabian ärgern, beispielsweise durch die Implementierung eines nicht-terminierenden Tests.");
		task1.setDeadline(offset.plusDays(15));
		task1.getTransitions().clear();
		task1 = task1(task1,project1, offset);
		Comment comment1Task1 = new Comment();
		comment1Task1.setContent("Das mach immer Spaß");
		comment1Task1.setCreatedAt(offset.minusDays(2).plusHours(3).plusMinutes(12));
		comment1Task1.setWorker(company.getWorkers().get(2));
		Comment comment2Task1 = new Comment();
		comment2Task1.setContent("Man kann auch alle Javadocs löschen...");
		comment1Task1.setCreatedAt(offset.minusHours(5));
		comment2Task1.setWorker(company.getWorkers().get(3));
		ArrayList<Comment> commentsTask1 = new ArrayList<>();
		commentsTask1.add(comment1Task1);
		commentsTask1.add(comment2Task1);
		task1.setComments(commentsTask1);
		return task1;
	}
	/**
	 * Erzeugt einen Task
	 * @param task1
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task1(Task task1,Project project1,LocalDateTime offset) {
		History historyTask1New = new History(offset.minusDays(20));
		historyTask1New.setEnd(offset.minusDays(18));
		historyTask1New.setStage(Stage.NEW);
		task1.getTransitions().add(historyTask1New);
		History historyTask1Analyze = new History(offset.minusDays(18));
		historyTask1Analyze.setEnd(offset.minusDays(17));
		historyTask1Analyze.setStage(Stage.ANALYSE);
		historyTask1Analyze.setWorker(project1.getWorkers().get(0));
		task1.getTransitions().add(historyTask1Analyze);
		History historyTask1AnalyzeDone = new History(offset.minusDays(17));
		historyTask1AnalyzeDone.setEnd(offset.minusDays(16));
		historyTask1AnalyzeDone.setStage(Stage.ANALYSEDONE);
		task1.getTransitions().add(historyTask1AnalyzeDone);
		History historyTask1Implementation = new History(offset.minusDays(16));
		historyTask1Implementation.setEnd(offset.minusDays(16).plusHours(5));
		historyTask1Implementation.setStage(Stage.IMPLEMENTATION);
		History historyTask1ImplementationDone = new History(offset.minusDays(16).plusHours(5));
		historyTask1ImplementationDone.setEnd(offset.minusDays(15));
		historyTask1ImplementationDone.setWorker(project1.getWorkers().get(0));
		historyTask1ImplementationDone.setStage(Stage.IMPLEMENTATIONDONE);
		task1.getTransitions().add(historyTask1ImplementationDone);
		History historyTask1Test = new History(offset.minusDays(15));
		historyTask1Test.setEnd(offset.minusDays(13));
		historyTask1Test.setWorker(project1.getWorkers().get(0));
		historyTask1Test.setStage(Stage.TEST);
		task1.getTransitions().add(historyTask1Test);
		History historyTask1TestDone = new History(offset.minusDays(13));
		historyTask1TestDone.setEnd(offset.minusDays(11));
		historyTask1TestDone.setStage(Stage.TESTDONE);
		task1.getTransitions().add(historyTask1TestDone);
		History historyTask1Done = new History(offset.minusDays(11));
		historyTask1Done.setEnd(offset.minusDays(11));
		historyTask1Done.setStage(Stage.DONE);
		task1.getTransitions().add(historyTask1Done);
		return task1;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task2(Project project1,LocalDateTime offset) {
		Task task2 = new Task();
		task2.setTitle("Klassen kommentieren");
		task2.setDescription("Alle Klassen müssen kommentiert werden, die Spannenste aller Aufgaben.");
		task2.getTransitions().clear();
		project1.setDeadline(offset.minusDays(1));
		History historyTask2New = new History(offset.minusDays(5));
		historyTask2New.setEnd(offset.minusDays(4));
		historyTask2New.setStage(Stage.NEW);
		task2.getTransitions().add(historyTask2New);
		History historyTask2Analyze = new History(offset.minusDays(4));
		historyTask2Analyze.setEnd(offset.minusDays(3));
		historyTask2Analyze.setStage(Stage.ANALYSE);
		historyTask2Analyze.setWorker(project1.getWorkers().get(2));
		task2.getTransitions().add(historyTask2Analyze);
		History historyTask2AnalyzeDone = new History(offset.minusDays(3));
		historyTask2AnalyzeDone.setEnd(offset.minusDays(2));
		historyTask2AnalyzeDone.setStage(Stage.ANALYSEDONE);
		task2.getTransitions().add(historyTask2AnalyzeDone);
		History historyTask2Implementation = new History(offset.minusDays(2));
		historyTask2Implementation.setEnd(offset.minusDays(1));
		historyTask2Implementation.setStage(Stage.IMPLEMENTATION);
		task2.getTransitions().add(historyTask2Implementation);
		History historyTask2ImplementationDone = new History(offset.minusDays(1));
		historyTask2ImplementationDone.setStage(Stage.IMPLEMENTATIONDONE);
		task2.getTransitions().add(historyTask2ImplementationDone);
		return task2;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task3(Company company, Project project1,LocalDateTime offset) {
		Task task3 = new Task();
		task3.setTitle("Fabian zufrieden stellen");
		task3.setDescription("Fabian soll zufrieden gestellt werden.");
		task3.getTransitions().clear();
		task3.setDeadline(offset.minusDays(1));
		History historyTask3New = new History(offset.minusDays(5));
		historyTask3New.setStage(Stage.NEW);
		historyTask3New.setEnd(offset.minusDays(3));
		task3.getTransitions().add(historyTask3New);
		History historyTask3Analyze = new History(offset.minusDays(3));
		historyTask3Analyze.setWorker(project1.getWorkers().get(0));
		historyTask3Analyze.setStage(Stage.ANALYSE);
		task3.getTransitions().add(historyTask3Analyze);
		task3.setCancelled(true);
		Comment comment1Task3 = new Comment();
		comment1Task3.setContent("Ist das überhaupt möglich?");
		comment1Task3.setWorker(company.getWorkers().get(3));
		comment1Task3.setCreatedAt(offset.minusDays(3));
		ArrayList<Comment> commentsTask3 = new ArrayList<Comment>();
		commentsTask3.add(comment1Task3);
		task3.setComments(commentsTask3);
		return task3;
	}
	
	/**
	 * Erzeugt einen Task
	 * @param project2
	 * @param offset
	 * @return Einen Task
	 */
	public static Task createAnotherLongTask(Project project2,LocalDateTime offset) {
		Task task1 = new Task();
		task1.setTitle("Unnötig schwere Klausuren stellen");
		task1.setDescription("Es muss auf jeden Fall genau das abgefragt werden, was niemand gelernt hat.");
		task1.setDeadline(offset.minusDays(1));
		task1.getTransitions().clear();
		return task21(task1,project2,offset);
	}
	/**
	 * Erzeugt einen Task
	 * @param project2
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task21(Task task1,Project project2,LocalDateTime offset) {
		
		History historyTask1New = new History(offset.minusDays(8));
		historyTask1New.setEnd(offset.minusDays(7));
		historyTask1New.setStage(Stage.NEW);
		task1.getTransitions().add(historyTask1New);
		History historyTask1Analyze = new History(offset.minusDays(7));
		historyTask1Analyze.setEnd(offset.minusDays(6));
		historyTask1Analyze.setStage(Stage.ANALYSE);
		historyTask1Analyze.setWorker(project2.getWorkers().get(0));
		task1.getTransitions().add(historyTask1Analyze);
		History historyTask1AnalyzeDone = new History(offset.minusDays(6));
		historyTask1AnalyzeDone.setEnd(offset.minusDays(5));
		historyTask1AnalyzeDone.setStage(Stage.ANALYSEDONE);
		task1.getTransitions().add(historyTask1AnalyzeDone);
		History historyTask1Implementation = new History(offset.minusDays(5));
		historyTask1Implementation.setEnd(offset.minusDays(4));
		historyTask1Implementation.setStage(Stage.IMPLEMENTATION);
		History historyTask1ImplementationDone = new History(offset.minusDays(4));
		historyTask1ImplementationDone.setEnd(offset.minusDays(3));
		historyTask1ImplementationDone.setWorker(project2.getWorkers().get(0));
		historyTask1ImplementationDone.setStage(Stage.IMPLEMENTATIONDONE);
		task1.getTransitions().add(historyTask1ImplementationDone);
		History historyTask1Test = new History(offset.minusDays(3));
		historyTask1Test.setEnd(offset.minusDays(2));
		historyTask1Test.setWorker(project2.getWorkers().get(0));
		historyTask1Test.setStage(Stage.TEST);
		task1.getTransitions().add(historyTask1Test);
		History historyTask1TestDone = new History(offset.minusDays(2));
		historyTask1TestDone.setEnd(offset.minusDays(1));
		historyTask1TestDone.setStage(Stage.TESTDONE);
		task1.getTransitions().add(historyTask1TestDone);
		History historyTask1Done = new History(offset.minusDays(1));
		historyTask1Done.setEnd(offset.minusDays(1));
		historyTask1Done.setStage(Stage.DONE);
		task1.getTransitions().add(historyTask1Done);
		return task1;
	}
	/**
	 * Erzeugt einen Task
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task4(LocalDateTime offset) {
		Task task4 = new Task();
		task4.setTitle("Bugs fixen");
		task4.setDescription("Vor der Projektabgabe müssen alle Bugs gefixt werden.");
		task4.setDeadline(offset.plusDays(10));
		return task4;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task5(Project project1,LocalDateTime offset) {
		Task task5 = new Task();
		task5.setTitle("Qualität des Projekts sicherstellen");
		task5.setDescription("");
		task5.setDeadline(offset.minusDays(2));
		task5.getTransitions().clear();
		History historyTask5New = new History(offset.minusDays(6));
		historyTask5New.setEnd(offset.minusDays(5));
		historyTask5New.setStage(Stage.NEW);
		task5.getTransitions().add(historyTask5New);
		History historyTask5Analyse = new History(offset.minusDays(5));
		historyTask5Analyse.setStage(Stage.ANALYSE);
		historyTask5Analyse.setWorker(project1.getWorkers().get(3));
		task5.getTransitions().add(historyTask5Analyse);
		return task5;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task6(Project project1,LocalDateTime offset) {
		Task task6 = new Task();
		task6.setTitle("Hintergrundbild malen");
		task6.setDescription("Im BigBlueButton muss noch ein Hintergrundbild für das Projekt gemalt werden.");
		task6.setDeadline(offset.minusDays(3));
		task6.getTransitions().clear();
		History historytask6New = new History(offset.minusDays(5));
		historytask6New.setEnd(offset.minusDays(4));
		historytask6New.setStage(Stage.NEW);
		task6.getTransitions().add(historytask6New);
		History historytask6Analyse = new History(offset.minusDays(4));
		historytask6Analyse.setStage(Stage.ANALYSE);
		historytask6Analyse.setWorker(project1.getWorkers().get(3));
		historytask6Analyse.setEnd(offset.minusDays(3));
		task6.getTransitions().add(historytask6Analyse);
		History historyTask6AnalyseDone = new History(offset.minusDays(3));
		historyTask6AnalyseDone.setStage(Stage.ANALYSEDONE);
		task6.getTransitions().add(historyTask6AnalyseDone);
		return task6;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task7(Project project1,LocalDateTime offset) {
		Task task7 = new Task();
		task7.setTitle("Bitmojis erstellen");
		task7.setDescription("Jeder Mitarbeiter muss Bitmojis von sich erstellen.");
		task7.setDeadline(offset.minusDays(5));
		task7.getTransitions().clear();
		History historyTask7New = new History(offset.minusDays(4));
		historyTask7New.setEnd(offset.minusDays(3));
		historyTask7New.setStage(Stage.NEW);
		task7.getTransitions().add(historyTask7New);
		History historyTask7Analyse = new History(offset.minusDays(3));
		historyTask7Analyse.setStage(Stage.ANALYSE);
		historyTask7Analyse.setWorker(project1.getWorkers().get(3));
		historyTask7Analyse.setEnd(offset.minusDays(2));
		task7.getTransitions().add(historyTask7Analyse);
		History historyTask7AnalyseDone = new History(offset.minusDays(2));
		historyTask7AnalyseDone.setEnd(offset.minusDays(1));
		historyTask7AnalyseDone.setStage(Stage.ANALYSEDONE);
		task7.getTransitions().add(historyTask7AnalyseDone);
		History historyTask7Implementation = new History(offset.minusDays(1));
		historyTask7Implementation.setStage(Stage.IMPLEMENTATION);
		historyTask7Implementation.setWorker(project1.getWorkers().get(1));
		task7.getTransitions().add(historyTask7Implementation);
		return task7;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task8(Project project1,LocalDateTime offset) {
		Task task8 = new Task();
		task8.setTitle("Tests durchführen");
		task8.setDescription("Die Aufgabe sollte selbsterklärend sein.");
		task8.setDeadline(offset);
		task8.getTransitions().clear();
		History historyTask8New = new History(offset.minusDays(20));
		historyTask8New.setEnd(offset.minusDays(18));
		historyTask8New.setStage(Stage.NEW);
		task8.getTransitions().add(historyTask8New);
		History historyTask8Analyse = new History(offset.minusDays(18));
		historyTask8Analyse.setStage(Stage.ANALYSE);
		historyTask8Analyse.setWorker(project1.getWorkers().get(2));
		historyTask8Analyse.setEnd(offset.minusDays(17));
		task8.getTransitions().add(historyTask8Analyse);
		History historyTask8AnalyseDone = new History(offset.minusDays(17));
		historyTask8AnalyseDone.setEnd(offset.minusDays(14));
		historyTask8AnalyseDone.setStage(Stage.ANALYSEDONE);
		task8.getTransitions().add(historyTask8AnalyseDone);
		History historyTask8Implementation = new History(offset.minusDays(14));
		historyTask8Implementation.setStage(Stage.IMPLEMENTATION);
		historyTask8Implementation.setWorker(project1.getWorkers().get(1));
		historyTask8Implementation.setEnd(offset.minusDays(10));
		task8.getTransitions().add(historyTask8Implementation);
		History historyTask8ImplementationDone = new History(offset.minusDays(10));
		historyTask8ImplementationDone.setStage(Stage.IMPLEMENTATIONDONE);
		historyTask8ImplementationDone.setEnd(offset.minusDays(8));
		task8.getTransitions().add(historyTask8ImplementationDone);
		History historyTask8Test = new History(offset.minusDays(8));
		historyTask8Test.setStage(Stage.TEST);
		historyTask8Test.setWorker(project1.getWorkers().get(2));
		task8.getTransitions().add(historyTask8Test);
		return task8;
	}
	/**
	 * Erzeugt einen Task
	 * @param project1
	 * @param offset
	 * @return Einen Task
	 */
	public static Task task9(Project project1,LocalDateTime offset) {
		Task task9 = new Task();
		task9.setTitle("Tests durchführen");
		task9.setDescription("Die Aufgabe sollte selbsterklärend sein.");
		task9.setDeadline(offset.plusDays(12));
		task9.getTransitions().clear();
		History historyTask9New = new History(offset.minusDays(12));
		historyTask9New.setEnd(offset.minusDays(10));
		historyTask9New.setStage(Stage.NEW);
		task9.getTransitions().add(historyTask9New);
		History historyTask9Analyse = new History(offset.minusDays(10));
		historyTask9Analyse.setStage(Stage.ANALYSE);
		historyTask9Analyse.setWorker(project1.getWorkers().get(2));
		historyTask9Analyse.setEnd(offset.minusDays(8));
		task9.getTransitions().add(historyTask9Analyse);
		History historyTask9AnalyseDone = new History(offset.minusDays(8));
		historyTask9AnalyseDone.setEnd(offset.minusDays(5));
		historyTask9AnalyseDone.setStage(Stage.ANALYSEDONE);
		task9.getTransitions().add(historyTask9AnalyseDone);
		History historyTask9Implementation = new History(offset.minusDays(5));
		historyTask9Implementation.setStage(Stage.IMPLEMENTATION);
		historyTask9Implementation.setWorker(project1.getWorkers().get(1));
		historyTask9Implementation.setEnd(offset.minusDays(4));
		task9.getTransitions().add(historyTask9Implementation);
		History historyTask9ImplementationDone = new History(offset.minusDays(4));
		historyTask9ImplementationDone.setStage(Stage.IMPLEMENTATIONDONE);
		historyTask9ImplementationDone.setEnd(offset.minusDays(3));
		task9.getTransitions().add(historyTask9ImplementationDone);
		History historyTask9Test = new History(offset.minusDays(3));
		historyTask9Test.setStage(Stage.TEST);
		historyTask9Test.setWorker(project1.getWorkers().get(2));
		historyTask9Test.setEnd(offset.minusDays(2));
		task9.getTransitions().add(historyTask9Test);
		History historyTask9TestDone = new History(offset.minusDays(2));
		historyTask9TestDone.setStage(Stage.TESTDONE);
		task9.getTransitions().add(historyTask9TestDone);
		return task9;
	}
}
