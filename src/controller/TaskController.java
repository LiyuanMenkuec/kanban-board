package controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

import model.History;
import model.Project;
import model.Stage;
import model.Task;
import model.Worker;
import view.auis.ProjectAUI;
import view.auis.TaskAUI;

/**
 * Die Klasse TaskController verwalten alle Operationen auf einem Task
 * 
 * @author Gero Grühn
 *
 */
public class TaskController {
	private static final int MIN_NUMBER_OF_HISTORIES = 1;
	private MainController mainController;

	private ArrayList<TaskAUI> taskAUI;

	private ArrayList<ProjectAUI> projectAUI;

	/**
	 * Der Konstruktor belegt die Variablen mit leer-Werten und erzeugt die Listen
	 * 
	 * @param mainController für den Zugriff auf die anderen Controller und das
	 *                       Company-Objekt
	 */
	public TaskController(final MainController mainController) {
		if (mainController != null) {
			this.mainController = mainController;
			taskAUI = new ArrayList<>();
			projectAUI = new ArrayList<>();
		} else
			throw new NullPointerException("MainController is null!");

	}

	/**
	 * Prüft ob bereits ein Task mit dem selben Titel existiert
	 * 
	 * @param project
	 * @param taskName
	 * @return
	 */
	private boolean existsTaskWithSameName(Project project, String taskName) {
		boolean foundSameName = false;

		ArrayList<Task> tasks = project.getTasks();
		Iterator<Task> itTasks = tasks.iterator();

		while (itTasks.hasNext() && !foundSameName) {
			if (itTasks.next().getTitle().equals(taskName))
				foundSameName = true;
		}
		return foundSameName;
	}

	/**
	 * Fügt einem bestehendem Projekt eine Aufgabe in Stage NEW ohne worker hinzu
	 * 
	 * @param project Hier wird die Aufgabe hinzugefügt
	 * @param task    wird zu project hinzugefügt
	 */
	public void addTask(final Project project, final Task task) {
		if (project != null && task != null) {
			if (task.getCurrentWorker() == null) {
				if (existsTaskWithSameName(project, task.getTitle())) {
					mainController.showNotification("Es gibt bereits eine Aufgabe mit diesem Titel!", true);
					return;
				} else {
					if (task.getCurrentStage() == Stage.NEW) {
						project.getTasks().add(task);
						mainController.showNotification("Aufgabe erfolgreich hinzugefügt.", false);
						projectAUI.forEach(listElement -> listElement.refreshTaskList());
						projectAUI.forEach(listElement -> listElement.refreshProjectData());
						projectAUI.forEach(listElement -> listElement.refreshTeamList());

						taskAUI.forEach((listElement) -> listElement.refreshCommentList());
						taskAUI.forEach(listElement -> listElement.refreshTaskData());
					} else {
						throw new IllegalStateException("Task can just be added within first stage!");
					}
				}
			} else {
				throw new IllegalStateException("Task can just be added without worker!");
			}
		} else {
			throw new NullPointerException("One argument is null(project or task)!");
		}
	}

	/**
	 * Entfernt einen Task, egal in welcher Stage er ist
	 * 
	 * @param project enthält Tasks
	 * @param task    wird von project entfernt
	 */
	public void forceRemoveTask(Project project, Task task) {
		if (project != null && task != null) {
			project.getTasks().remove(task);
			projectAUI.forEach(listElement -> listElement.refreshTaskList());
			projectAUI.forEach(listElement -> listElement.refreshProjectData());
			projectAUI.forEach(listElement -> listElement.refreshTeamList());

			taskAUI.forEach(listElement -> listElement.refreshCommentList());
			taskAUI.forEach(listElement -> listElement.refreshTaskData());
		} else
			throw new NullPointerException("One argument is null(project or task)!");

	}

	/**
	 * Entfernt eine Aufgabe von einem Projekt oder setzt sie auf inaktiv wenn
	 * bereits jemand an der Aufgabe gearbeitet hat
	 * 
	 * @param project
	 * @param task
	 */
	public void removeTask(final Project project, final Task task) {
		if ((project != null) && (task != null)) {
			if (task.getTransitions().size() == MIN_NUMBER_OF_HISTORIES ||
				(task.getCurrentStage().equals(Stage.DONE) && Duration.between(LocalDateTime.now(), task.getTransitions().get(task.getTransitions().size()-1).getEnd()).toDays()>7)) {
				project.getTasks().remove(task);
				projectAUI.forEach(listElement -> listElement.refreshTaskList());
				projectAUI.forEach(listElement -> listElement.refreshProjectData());
				projectAUI.forEach(listElement -> listElement.refreshTeamList());

				taskAUI.forEach(listElement -> listElement.refreshCommentList());
				taskAUI.forEach(listElement -> listElement.refreshTaskData());
			} else {
				removeWorker(task);
				task.setCancelled(true);
				mainController.getNotificationAUI()
						.showNotification("Die Aufgabe kann nicht mehr entfernt werden! Sie ist jetzt inaktiv.", false);
				projectAUI.forEach(listElement -> listElement.refreshTaskList());
				projectAUI.forEach(listElement -> listElement.refreshProjectData());
				projectAUI.forEach(listElement -> listElement.refreshTeamList());

				taskAUI.forEach(listElement -> listElement.refreshCommentList());
				taskAUI.forEach(listElement -> listElement.refreshTaskData());
			}
		} else
			throw new NullPointerException("One argument is null(project or task)!");
	}

	/**
	 * Gibt das zu dem Task gehörende Project zurück
	 * 
	 * @param task
	 * @return Projekt wenn vorhanden, sont NoSuchElementException
	 */
	public Project getProjectOfTask(final Task task) {
		ArrayList<Project> projects = mainController.getCompany().getProjects();
		try {
			return projects.stream().filter(listElement -> listElement.getTasks().contains(task)).findAny().get();
		} catch (NoSuchElementException nseException) {
			return null;
		}

	}

	/**
	 * Überschreibt eine Aufgabe mit einer geänderten Aufgabe
	 * 
	 * @param task
	 * @param changes
	 */
	public void updateTask(final Task task, final Task changes) {
		if (task != null && changes != null) {
			Project currentProject = getProjectOfTask(task);
			if (currentProject != null && currentProject.getTasks().contains(changes)) {
				throw new IllegalStateException(
						"Just update a task with a task which isn't contained by the project already");
			} else {
				if (!(task.getTitle().equals(changes.getTitle()))
						&& existsTaskWithSameName(currentProject, changes.getTitle())) {
					mainController.getNotificationAUI()
							.showNotification("Es gibt bereits eine Aufgabe mit diesem Titel!", true);
					return;
				}
				updateTaskUnproofed(task, changes);
			}
		} else
			throw new NullPointerException("One argument is null (task or changes)!");

	}

	/**
	 * Helper, der die Parameter von task mit denen von changes überschreibt
	 */
	public void updateTaskUnproofed(final Task task, final Task changes) {
		task.setTitle(changes.getTitle());
		task.setCancelled(changes.isCancelled());
		task.setComments(changes.getComments());
		task.setDeadline(changes.getDeadline());
		task.setDescription(changes.getDescription());
		if(!(task.getCurrentStage()==Stage.DONE)) {
			addWorker(task, changes.getCurrentWorker());
			mainController.getNotificationAUI().showNotification("Mitarbeiter erfolgreich geändert.", false);
		}
		projectAUI.forEach(listElement -> listElement.refreshTaskList());
		projectAUI.forEach(listElement -> listElement.refreshProjectData());
		projectAUI.forEach(listElement -> listElement.refreshTeamList());

		taskAUI.forEach(listElement -> listElement.refreshCommentList());
		taskAUI.forEach(listElement -> listElement.refreshTaskData());
		
	}

	/**
	 * Gibt den Arbeiter einer Aufgabe zurück
	 * 
	 * @param task
	 * @return Arbeiter einer Aufgabe
	 */
	public Worker getWorker(final Task task) {
		if (task != null)
			return task.getCurrentWorker();
		else
			throw new NullPointerException("Argument is null (task)!");

	}

	/**
	 * Fügt einer Aufgabe einen Mitarbeiter hinzu und ändert jeweils die Stage der
	 * Aufgabe
	 * 
	 * @param task
	 * @param worker
	 */
	public void addWorker(final Task task, final Worker worker) {
		if (task != null) {
			if(task.getCurrentStage()==Stage.TESTDONE) {
				return;
			}
			if (worker == null) {
				removeWorker(task);
				return;
			}
			if (!worker.equals(task.getCurrentWorker())) {

				final LocalDateTime now = LocalDateTime.now();
				final ArrayList<History> transitions = task.getTransitions();
				History latestHistory = transitions.get(transitions.size() - 1);

				latestHistory.setEnd(now);
				final History newHistory = new History(now);
				newHistory.setWorker(worker);
				if (latestHistory.hasWorker()) {
					newHistory.setStage(latestHistory.getStage());
				} else {
					newHistory.setStage(Stage.values()[latestHistory.getStage().ordinal() + 1]);
				}
				transitions.add(newHistory);
				taskAUI.forEach(taskAui -> taskAui.refreshCommentList());
				taskAUI.forEach(taskAui -> taskAui.refreshTaskData());

				projectAUI.forEach(projectAui -> projectAui.refreshTaskList());
				projectAUI.forEach(projectAui -> projectAui.refreshProjectData());
				projectAUI.forEach(projectAui -> projectAui.refreshTeamList());
				
			}
		} 

	}

	/**
	 * Entfernt den Mitarbeiter von einer Aufgabe
	 * 
	 * @param task
	 */
	public void removeWorker(final Task task) {
		if (task.getCurrentWorker() != null) {
			final LocalDateTime now = LocalDateTime.now();

			final ArrayList<History> transitions = task.getTransitions();
			final History latestHistory = transitions.get(transitions.size() - 1);

			latestHistory.setEnd(now);
			final History newHistory = new History(now);

			newHistory.setStage(Stage.values()[latestHistory.getStage().ordinal() - 1]);

			transitions.add(newHistory);
			taskAUI.forEach(taskAui -> taskAui.refreshCommentList());
			taskAUI.forEach(taskAui -> taskAui.refreshTaskData());

			projectAUI.forEach(projectAui -> projectAui.refreshTaskList());
			projectAUI.forEach(projectAui -> projectAui.refreshProjectData());
			projectAUI.forEach(projectAui -> projectAui.refreshTeamList());
			mainController.getNotificationAUI().showNotification("Mitarbeiter entfernt und Stage zurückgesetzt.",
					false);
		}
	}

	/**
	 * Entfernt den Mitarbeiter und setzt die Aufgabe auf die nächste Stage
	 * 
	 * @param task
	 */
	public void taskDone(final Task task) {
		if (task.getCurrentWorker() != null) {
			final LocalDateTime now = LocalDateTime.now();
			final ArrayList<History> transitions = task.getTransitions();
			final History lastTransition = transitions.get(transitions.size() - 1);
			lastTransition.setEnd(now);
			final History newTransition = new History(now);
			newTransition.setWorker(null);
			newTransition.setStage(Stage.values()[lastTransition.getStage().ordinal() + 1]);
			transitions.add(newTransition);
			taskAUI.forEach(taskAui -> taskAui.refreshCommentList());
			taskAUI.forEach(taskAui -> taskAui.refreshTaskData());
			projectAUI.forEach(projectAui -> projectAui.refreshTaskList());
			projectAUI.forEach(projectAui -> projectAui.refreshProjectData());
			projectAUI.forEach(projectAui -> projectAui.refreshTeamList());
			mainController.getNotificationAUI().showNotification("Aufgabe erledigt.", false);
		} else {
			if(task.getCurrentStage()!=Stage.TESTDONE) {
				mainController.getNotificationAUI()
				.showNotification("Der Aufgabe muss zuerst ein Mitarbeiter zugewiesen werden.", true);
			}else {
				final LocalDateTime now = LocalDateTime.now();
				final ArrayList<History> transitions = task.getTransitions();
				final History lastTransition = transitions.get(transitions.size() - 1);
				lastTransition.setEnd(now);
				final History newTransition = new History(now);
				newTransition.setWorker(null);
				newTransition.setStage(Stage.values()[lastTransition.getStage().ordinal() + 1]);
				newTransition.setEnd(now);
				transitions.add(newTransition);
				taskAUI.forEach(taskAui -> taskAui.refreshCommentList());
				taskAUI.forEach(taskAui -> taskAui.refreshTaskData());
				projectAUI.forEach(projectAui -> projectAui.refreshTaskList());
				projectAUI.forEach(projectAui -> projectAui.refreshProjectData());
				projectAUI.forEach(projectAui -> projectAui.refreshTeamList());
				mainController.getNotificationAUI().showNotification("Aufgabe erledigt.", false);
			}
		}
	}

	/**
	 * Gibt die verbleibene Zeit bis zur Deadline zurück
	 * 
	 * @param task
	 * @return Duration Zeit bis zur Deadline oder null
	 */
	public Duration getLeftTime(final Task task) {
		if (task.getDeadline() != null)
			return Duration.between(task.getDeadline(), LocalDateTime.now());
		else {
			return null;
		}
	}

	/**
	 * Gibt den mainController zurück
	 * 
	 * @return the mainController
	 */
	public MainController getMainController() {
		return mainController;
	}

	/**
	 * Gibt Liste der TaskAUI's zurück
	 * 
	 * @return the taskAUI
	 */
	public ArrayList<TaskAUI> getTaskAUI() {
		return taskAUI;
	}

	/**
	 * Gibt Liste der ProjectAUI's zurück
	 * 
	 * @return the projectAUI
	 */
	public ArrayList<ProjectAUI> getProjectAUI() {
		return projectAUI;
	}

}
