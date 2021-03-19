package controller;

import model.History;
import model.Project;
import model.Task;
import model.Worker;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Berechnet die Statistiken der Mitarbeiter und Projekte. 
 * @author 
 *
 */
public class StatisticsController {
	/**
	 * 
	 * 
	 */
	public StatisticsController() {
	}

	/**
	 * Gibt ein zweidimensionales Array als Tabelle für das Mitarbeiter Ranking zurück. 
	 * Sortiert ist diese Tabelle nach dem Wert der erledigten Aufgaben.
	 * @param workers die Liste aller Mitarbeiter des Unternehmens
	 * @param projects die Liste aller Projekte des Unternehmens
	 * @return 2D Array als Tabelle des workerRanking
	 */
	public String[][] workerRanking(ArrayList<Worker> workers, ArrayList<Project> projects) {
		String[][] table = new String[workers.size()][6];
		for (Worker worker : workers) {
			long min = Long.MIN_VALUE;
			long max = Long.MAX_VALUE;
			long cumulatedDuration = 0L;
			int numberOfTasks = 0;
			List<History> historyEntries = new ArrayList<History>();
			for (Project project : projects) {
				historyEntries.addAll(this.getHistoryOfWorker(project, worker));
			}

			historyEntries = historyEntries.stream()
					.filter(element -> element.getStart().isAfter(LocalDateTime.now().minusDays(7)))
					.collect(Collectors.toList());
			for (History entry : historyEntries) {
				if (entry.getEnd() != null) {
					long taskDuration = this.getDurationBetween(entry.getStart(), entry.getEnd());
					if (min == Long.MIN_VALUE || min > taskDuration) min = taskDuration;
					if (max == Long.MAX_VALUE || max < taskDuration) max = taskDuration;
					cumulatedDuration += taskDuration;
					numberOfTasks++;
				}
			}

			table[workers.indexOf(worker)][0] = worker.getFirstname(); //Vorname
			table[workers.indexOf(worker)][1] = worker.getLastname(); //Nachname
			table[workers.indexOf(worker)][2] = numberOfTasks + ""; //Punkte
			if (historyEntries.size() > 0) {
				table[workers.indexOf(worker)][3] = (max==Long.MAX_VALUE) ? "-" : this.convertTime(max); //max
				table[workers.indexOf(worker)][4] = (min==Long.MIN_VALUE) ? "-" : this.convertTime(min); //min
				table[workers.indexOf(worker)][5] = this.convertTime(((numberOfTasks!=0) ? (cumulatedDuration/numberOfTasks) : 0)); //avg
			} else {
				table[workers.indexOf(worker)][3] = "-"; //max
				table[workers.indexOf(worker)][4] = "-"; //min
				table[workers.indexOf(worker)][5] = "-"; //avg
			}
		}
		return table;
	}

	/**
	 * holt die Liste von Historien, die zu einem Mitarbeiter in einem Projekt gehören und gibt die zurück.
	 * @param project, in dem Historien gesucht werden.
	 * @param worker, zu dem Historien gesucht werden.
	 * @return Liste der Historien eines Mitarbeiters in einem Projekt. 
	 */
	private List<History> getHistoryOfWorker(Project project, Worker worker) {
		return project.getTasks()
				.stream()
				.map(Task::getTransitions)
				.flatMap(Collection::stream)
				.filter(element -> worker.equals(element.getWorker()))
				.collect(Collectors.toList());
	}

	/**
	 * berechnet die Zeitspanne einer Historie in Sekunden.
	 * @param fromTime Start der Zeitspanne.
	 * @param toTime Ende der Zeitspanne.
	 * @return die Zeitspanne einer Historie. 
	 */
	private long getDurationBetween(LocalDateTime fromTime, LocalDateTime toTime) {
		return ChronoUnit.SECONDS.between(fromTime, toTime);
	}

	/**
	 * konvertiert die Zeit von Sekunden in einen String mit Tagen und Stunden. 
	 * @param seconds übergebene Sekunden. 
	 * @return Die Zeit als String
	 */
	private String convertTime(long seconds) {
		Duration duration = Duration.ofSeconds(seconds);
		return duration.toDaysPart() + "d " + duration.toHoursPart() + "h";
	}
	

	/**
	 * Gibt ein Zweidimensionales Array als Tabelle für die Aufgabenstatistik zurück.
	 * @param project das Projekt für welches die Aufgabenstatistik erstellt wird
	 * @return 2D Array als Tabelle für die taskStats
	 */
	public String[][] taskStats(Project project) {
		ArrayList<Task> tasks = project.getTasks();//Liste aller Aufgaben des projekts
		String[][] table=new String[tasks.size()][12];//2D Array als Tabelle [Zeilen][Spalten]
		for(Task currentTask:tasks) {//if(tasks.size()>=1)
			table[tasks.indexOf(currentTask)][0]=currentTask.getTitle();//Name immer in der ersten Spalte
			
			List<History> historyOfTask=currentTask.getTransitions();
			historyOfTask = historyOfTask.stream()
					.filter(element -> element.getStart().isAfter(LocalDateTime.now().minusDays(7)))
					.collect(Collectors.toList());
			for(History entry:historyOfTask) {
				if (entry.getEnd() != null) {
					table[tasks.indexOf(currentTask)][historyOfTask.indexOf(entry)+1]
						=this.convertTime(getStageDurationOfTask(entry));
				}
			}
			table[tasks.indexOf(currentTask)][9]=this.convertTime(getActiveTime(historyOfTask));
			table[tasks.indexOf(currentTask)][10]=this.convertTime(getIdleTime(historyOfTask));
			table[tasks.indexOf(currentTask)][11]=this.convertTime(getActiveTime(historyOfTask)+getIdleTime(historyOfTask));
		}
		return table;//titles={"NAME","NEW","ANALYSE","ANALYSDONE","IMPLEMENTATION","IMPLEMETATIONDONE","TEST","TESTDONE","DONE","ACTIVE","IDLE","TOTAL"}
	}
	
	/**
	 * Gibt die Duration für die übergebene Stage der übergebenen Task an.
	 * Die Stage geht von 0-7.		
	 * @param history der Stage der Aufgabe
	 * @return die Duration für eine bestimmte stage
	 */
	public long getStageDurationOfTask(History history) {
		return getDurationBetween(history.getStart(), history.getEnd());
	}
	
	/**
	 * Gibt die Zeit während an der Aufgabe nicht gearbeitet wurde zurück.
	 * @param historyList der Aufgabe von der die inaktve Zeit berechnet wird
	 * @return ein Duration Objekt, welcher der inaktven Zeit von task entspricht
	 */
	public long getIdleTime(List<History> historyList) {
		return getDurationOfTask(historyList)-getActiveTime(historyList);
	}

	/**
	 * Gibt die gesamte Bearbeitungszeit der Aufgabe, sowohl aktiv als auch inaktiv zurück.
	 * @param historyList der Aufgabe von der die gesamte Bearbeitungszeit berechnet wird
	 * @return ein Duration Objekt, welcher der gesamten Bearbeitungszeit Zeit von task entspricht
	 */
	public long getDurationOfTask(List<History> historyList) {
		if(!historyList.isEmpty() && historyList.get(0).getStart()!=null) {//falls es ein start in NEW gibt
			LocalDateTime start=historyList.get(0).getStart();
			if(historyList.size()==8 && historyList.get(7).getEnd()!=null) {//falls es ein end im Letzten History Objekt gibt
				LocalDateTime end=historyList.get(7).getEnd();
				return getDurationBetween(start, end);

			}else {//falls es noch keinen finalen Endzeitpunkt gibt wird der jetzige Zeitpunkt genommen
				LocalDateTime now=LocalDateTime.now();
				return getDurationBetween(start,now);
			}
		}
		return 0L;
	}
	
	/**
	 * Gibt die Zeit während an der Aufgabe gearbeitet wurde zurück.
	 * @param historyList der Aufgabe von der die aktive Zeit berechnet wird
	 * @return ein Duration Objekt, welcher der aktiven Zeit von task entspricht
	 */
	public long getActiveTime(List<History> historyList) {
		historyList.stream()
				.filter(element -> element.hasWorker())
				.collect(Collectors.toList());
		long activeDuration = 0L;
		for(History entry: historyList) {
			if (entry.getEnd() != null) {
				activeDuration+= getStageDurationOfTask(entry);
			}
		}
		return activeDuration;
	}

}
