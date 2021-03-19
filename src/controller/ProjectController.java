package controller;

import view.NotificationAUI;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;

import java.util.ArrayList;

import model.Company;
import model.Project;
import model.Stage;
import model.Task;
import model.Worker;

/**
 * Der CompanyController verwaltet alle Projekte und führt alle Änderungen an
 * einem Projekt durch.
 * 
 * @author Florian
 **/
public class ProjectController {
	/**
	 * Der MainController
	 **/
	final transient private MainController mainController;
	/**
	 * Die Liste alle projectAUIs.
	 **/
	private ArrayList<ProjectAUI> projectAUI;
	/**
	 * Die Liste aller CompanyAUIs
	 **/
	private ArrayList<CompanyAUI> companyAUI;
	/**
	 * Eine eigene NotificationAUI zum Testen
	 **/
	private NotificationAUI notificationAUI;
	/**
	 * Konstruktor, setzt das MainController-Attruibut und erzeugt leere projectAUI
	 * und companyAUI-Listen.
	 * 
	 * @param mainController Das MainController-Objekt.
	 * @throws NullPointerException Falls <em> <b> mainController </b> == null </em>
	 **/
	public ProjectController(final MainController mainController) {

		this.mainController = mainController;

		if (mainController == null) {
			throw new NullPointerException("MainController is null!");
		}

		notificationAUI = mainController.getNotificationAUI();
		projectAUI = new ArrayList<>();
		companyAUI = new ArrayList<>();
	}
	/**
	 * Fügt der Projektliste der {@link Company} das Projekt <b> project </b> hinzu.
	 * 
	 * @param project Das Projekt, welches zur Projektliste hinzugefügt werden soll.
	 **/
	public void addProject(final Project project) {

		if (project == null || project.getDescription() == null || project.getName() == null
				|| project.getTasks() == null || project.getWorkers() == null) {

			mainController.getNotificationAUI().showNotification(
					"Es fehlen gültige Attribute, um ein Projekt zur Projektliste hinzuzufügen oder es wurde kein Projekt angegeben!",
					true);

			notificationAUI.showNotification("", true);
			return;
		}
		final Company company = mainController.getCompany();
		final ArrayList<Project> projects = company.getProjects();

		if (!projects.contains(project)) {
			projects.add(project);
			companyAUI.forEach(cAUI -> cAUI.refreshProjectList());
		}

	}
	/**
	 * Entfernt das aktuelle Projekt aus der Projektliste der {@link Company}. <br>
	 * Dazu werden zuerst alle Aufgaben und Mitarbeiter aus dem Projekt entfernt,
	 * anschließend wird das Projekt aus der Projektliste entfernt. <br>
	 * Das übergebene Projekt wird <em> unwiederruflich entfernt </em>. Für eine
	 * Archivierung nutze {ProjectController#setProjectState(Project, true)}.
	 * 
	 * @param project Das Projekt, welches gelöscht werden soll.
	 **/
	public void removeProject(final Project project) {

		if (project == null) {
			mainController.getNotificationAUI()
					.showNotification("Es wurde kein Projekt übergeben, welches entfernt werden soll!", true);
			notificationAUI.showNotification("", true);
			return;
		} else {
			final Company company = mainController.getCompany();
			final ArrayList<Project> allProjects = company.getProjects();
			if (allProjects.contains(project)) {
				final ArrayList<Task> tasks = project.getTasks();
				final ArrayList<Worker> team = project.getWorkers();
				if (!tasks.isEmpty()) {
					final TaskController taskController = mainController.getTaskController();
					taskController.forceRemoveTask(project, tasks.get(0));

					while (!tasks.isEmpty()) {
						taskController.forceRemoveTask(project, tasks.get(0));
					}
				}
				if (!team.isEmpty()) {
					final TeamController teamController = mainController.getTeamController();
					teamController.removeWorkerFromProject(project, team.get(0));
					while (!team.isEmpty()) {
						teamController.removeWorkerFromProject(project, team.get(0));
					}
				}
				allProjects.remove(project);
				companyAUI.forEach(cAUI -> {
					cAUI.refreshProjectList();
				});
				projectAUI.forEach(pAUI -> {
					pAUI.refreshTeamList();
					pAUI.refreshProjectData();
					pAUI.refreshTaskList();
				});
			}
		}
	}
	/**
	 * Aktualisiert das Projekt <b> project </b>, indem es <b> project </b> mit den
	 * Inhalten aus <b> changes </b> füllt. <br>
	 * Zu beachten: Es werden nur die Attribute <b> name </b>, <b> description </b>
	 * und <b> deadline </b> aktualisiert. Für die Aufgaben und Mitarbeiter müssen
	 * die Methoden im {@link TaskController} und im {@link WorkerController}
	 * verwendet werden.
	 * 
	 * @param project Das Projekt, welches aktualisiert werden soll.
	 * @param changes Ein Project-Objekt, welches die aktualisierten Daten enthält.
	 **/
	public void updateProject(final Project project, final Project changes) {

		if (project == null || changes == null || changes.getDescription() == null || changes.getName() == null
				|| changes.getDeadline() == null) {

			mainController.getNotificationAUI().showNotification("Die eingegebenen Daten waren nicht gültig!", true);

			notificationAUI.showNotification("", true);

		} else {

			project.setDeadline(changes.getDeadline());
			project.setDescription(changes.getDescription());
			project.setName(changes.getName());
			setProjectState(project, changes.isActive());

			for (final CompanyAUI cAUI : companyAUI) {

				cAUI.refreshProjectList();

			}

			for (final ProjectAUI pAUI : projectAUI) {

				pAUI.refreshProjectData();

			}
		}
	}
	/**
	 * Liefert <em> alle </em> Aufgaben aus dem Projekt <b> project </b> in einer
	 * {@link ArrayList}. Ist <em> <b> project </b> == null </em>, wird eine leere
	 * Task-Liste zurückgegeben.
	 * 
	 * @param project Das Projekt, aus dem alle Aufgaben ermittelt werden sollen.
	 * @return Eine Liste mit allen Aufgaben des Projekts.
	 **/
	public ArrayList<Task> getProjectTasks(final Project project) {
		ArrayList<Task> projectTasks;
		if (project == null) {
			mainController.getNotificationAUI().showNotification(
					"Es wurde kein Projekt angegeben, zu dem alle Aufgaben ausgegeben werden sollen!", true);
			notificationAUI.showNotification("", true);
			projectTasks = new ArrayList<>();
		} else {
			projectTasks = project.getTasks();
		}
		return projectTasks;
	}
	/**
	 * Liefert alle Aufgaben in einer {@link ArrayList}, die archiviert sind, für
	 * die also gilt <em> cancelled == true </em>.
	 * 
	 * @param project Das Projekt, aus dem alle archivierten Aufgaben ermittelt
	 *                werden sollen.
	 * @return Eine Liste mit allen archivierten Aufgaben aus dem Projekt <b>
	 *         project </b>.
	 **/
	public ArrayList<Task> getProjectArchivedTasks(final Project project) {

		final ArrayList<Task> allTasks = getProjectTasks(project);

		final ArrayList<Task> archivedTasks = new ArrayList<>();

		for (final Task task : allTasks) {

			if (task.isCancelled()) {

				archivedTasks.add(task);
			}
		}
		return archivedTasks;
	}
	/**
	 * Liefert alle Aufgaben in einer {@link ArrayList}, die sich in der Stage <b>
	 * stage </b> befinden.
	 * 
	 * @param project Das Projekt, aus dem alle Aufgaben aus der Stage <b> stage
	 *                </b> ermittelt werden sollen.
	 * @param stage   Die Stage, aus der alle Aufgaben aus dem Projekt <b> project
	 *                </b> ermittelt werden sollen.
	 * 
	 * @return Eine Liste mit allen Aufgaben in der Stage <b> stage </b> aus dem
	 *         Projekt <b> project </b>.
	 **/
	public ArrayList<Task> getProjectTasksInStage(final Project project, final Stage stage) {
		final ArrayList<Task> tasksInStage = new ArrayList<>();
		if (project == null || stage == null) {
			mainController.getNotificationAUI()
					.showNotification("Es wurde entweder kein Projekt oder keine Stage angegeben!", true);
			notificationAUI.showNotification("", true);
		} else {
			final ArrayList<Task> allTasks = getProjectTasks(project);
			for (final Task task : allTasks) {
				final Stage stageCurTask = task.getCurrentStage();
				if (stageCurTask.equals(stage)) {
					tasksInStage.add(task);
				}
			}
		}
		return tasksInStage;
	}
	/**
	 * Liefert alle Aufgaben in einer {@link ArrayList}, die sich in der Stage <b>
	 * stage </b> befinden.
	 * 
	 * @param project Das Projekt, aus dem alle Aufgaben aus der Stage <b> stage
	 *                </b> ermittelt werden sollen.
	 * @param stage   Die Stage, aus der alle Aufgaben aus dem Projekt <b> project
	 *                </b> ermittelt werden sollen.
	 * 
	 * @return Eine Liste mit allen Aufgaben in der Stage <b> stage </b> aus dem
	 *         Projekt <b> project </b>.
	 **/
	public ArrayList<Task> getProjectAktiveTasksInStage(final Project project, final Stage stage) {
		final ArrayList<Task> tasksInStage = new ArrayList<>();
		if (project == null || stage == null) {
			mainController.getNotificationAUI()
					.showNotification("Es wurde entweder kein Projekt oder keine Stage angegeben!", true);
			notificationAUI.showNotification("", true);
		} else {
			final ArrayList<Task> allTasks = getProjectTasks(project);
			for (final Task task : allTasks) {
				final Stage stageCurTask = task.getCurrentStage();
				if (stageCurTask.equals(stage) && !task.isCancelled()) {
					tasksInStage.add(task);
				}
			}
		}
		return tasksInStage;
	}
	/**
	 * Aktualisiert den Projektstatus. <br>
	 * Für <em> <b> archived </b> == true </em> wird das Projekt archiviert. Dazu
	 * werden alle Mitarbeiter aus dem Projekt entfernt, vorher wird jedem
	 * Mitarbeiter seine Aufgabe entrissen. <br>
	 * Für <em> <b> archived </b> == false </em> wird das Projekt wieder
	 * reaktiviert.
	 * 
	 * @param project  Das Projekt, dessen Status geändert werden soll.
	 * @param archived Der neue Status des Projekts bezogen auf die Archivierung.
	 * 
	 **/
	public void setProjectState(final Project project, final boolean archived) {
		if (project == null) {
			mainController.getNotificationAUI().showNotification("Es wurde kein projekt übergeben!", true);
			notificationAUI.showNotification("", true);
		} else {
			if (project.isActive() && archived) {
				archiveProject(project);
			} else if (!project.isActive() && !archived) {
				project.setActive(true);
			}
			projectAUI.forEach(pAUI -> {
				pAUI.refreshTeamList();
				pAUI.refreshProjectData();
				pAUI.refreshTaskList();
			});
		}
	}
	/**
	 * Archiviert das Projekt <b> project </b>.
	 * 
	 * @param project Das Projekt, welches archiviert werden soll.
	 * 
	 **/
	private void archiveProject(final Project project) {
		// TODO RemoveWorkerFromProject löschen
		final ArrayList<Task> tasks = getProjectTasks(project);
		if (!tasks.isEmpty()) {
			final TaskController taskController = mainController.getTaskController();
			taskController.removeWorker(tasks.get(0));
			for (final Task task : tasks) {
				if (task.getCurrentWorker() != null) {
					taskController.removeWorker(task);
				}
			}
		}
		final ArrayList<Worker> team = project.getWorkers();
		if (!team.isEmpty()) {
			final TeamController teamController = mainController.getTeamController();
			teamController.removeWorkerFromProject(project, team.get(0));
			while (!team.isEmpty()) {
				teamController.removeWorkerFromProject(project, team.get(0));
			}
		}
		project.setActive(false);
	}
	/**
	 * Liefert den Status des Projekts.
	 * 
	 * @param project Das Projekt, dessen Status ermittel werden soll.
	 * 
	 * @return <b> true </b>, wenn das Projekt nicht archiviert ist. <br>
	 *         <b> false </b>, wenn das Projekt gerade archiviert ist.
	 **/
	public boolean getProjectState(final Project project) {
		boolean active;
		if (project == null) {
			mainController.getNotificationAUI().showNotification("Es wurde kein projekt übergeben!", true);
			notificationAUI.showNotification("", true);
			active = false;
		} else {
			active = project.isActive();
		}
		return active;
	}
	/**
	 * Liefert die Liste mit ProjektAUIs:
	 * 
	 * @return Die Liste aller ProjectAUIs.
	 **/
	public ArrayList<ProjectAUI> getProjectAUI() {
		return projectAUI;
	}
	/**
	 * Liefert die Liste mit CompanyAUIs:
	 * 
	 * @return Die Liste aller CompanyAUIs.
	 **/
	public ArrayList<CompanyAUI> getCompanyAUI() {
		return companyAUI;
	}
	/**
	 * Liefert die NotificationAUIs:
	 * 
	 * @return Die NotificationAUI
	 **/
	public NotificationAUI getNotificationAUI() {
		return notificationAUI;
	}
	/**
	 * Setzt die Liste der ProjectAUIs.
	 * 
	 * @param projectAUI Die neue Liste mit ProjectAUIs.
	 */
	public void setProjectAUI(final ArrayList<ProjectAUI> projectAUI) {
		this.projectAUI = projectAUI;
	}
	/**
	 * Setzt die Liste der CompanyAUIs.
	 * 
	 * @param companyAUI Die neue Liste mit CompanyAUIs.
	 */
	public void setCompanyAUI(final ArrayList<CompanyAUI> companyAUI) {
		this.companyAUI = companyAUI;
	}
	/**
	 * Setter, um dem Controller zum Testen eine eigene NotificationAUI zu
	 * übergeben.
	 * 
	 * @param notificationAUI Die neue Liste mit NotificationAUis.
	 **/
	public void setTestNotificationAUI(NotificationAUI notificationAUI) {
		this.notificationAUI = notificationAUI;
	}
}