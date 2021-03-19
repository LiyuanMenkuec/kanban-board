package controller;

import java.util.ArrayList;

import model.Project;
import model.Worker;
import view.auis.ProjectAUI;
import model.Task;

/**
 * @author elias
 *
 */
public class TeamController {

	/**
	 * Das projectAUI-Interface der View
	 */
	private ArrayList<ProjectAUI> projectAUI;

	/**
	 * Der MainController
	 */
	private MainController mainController;

	/**
	 * Konstruktor der Klasse
	 * @param mainController Der MainController
	 */
	public TeamController(MainController mainController) {
		this.mainController = mainController;
		projectAUI=new ArrayList<>();
	}

	/**
	 * Fügt einen Arbeiter in ein Projekt hinzu
	 * @param project Das Projekt dem der Arbeiter hinzugefügt werden soll
	 * @param worker Der Arbeiter der hinzugefügt werden soll
	 */
	public void addWorkerToProject(Project project, Worker worker) {
		if(project != null && worker != null && !project.getWorkers().contains(worker)) {
			project.getWorkers().add(worker);
			projectAUI.forEach(listElement -> listElement.refreshTeamList());
		}
	}

	/**
	 * Entfernt einen Arbeiter von einem Projekt
	 * @param project Das Projekt aus dem der Arbeiter entfernt werden soll
	 * @param worker Der Arbeiter der entfernt werden soll
	 */
	public void removeWorkerFromProject(Project project, Worker worker) {
		if(project != null && worker != null) {
			if (project.getWorkers().contains(worker)) {
				Task currentTask = mainController.getWorkerController().getCurrentTask(worker);
				if (currentTask != null) {
					mainController.getTaskController().removeWorker(currentTask);
				}
				project.getWorkers().remove(worker);
				projectAUI.forEach(listElement -> listElement.refreshTeamList());
			} else {
				mainController.showNotification("Worker ist nicht in dem Projekt.", true);
			}
		} else {
			throw new NullPointerException();
		}
	}

	/**
	 * Gibt eine Liste der Arbeiter zurück, die an einem Projekt arbeiten 
	 * @param project Das Projekt von welchem man die Arbeiter haben möchte
	 * @return workers Die Liste der Arbeiter, die an dem Projekt beteiligt sind
	 */
	public ArrayList<Worker> getWorkersFromProject(Project project) {
		return project.getWorkers();
	}

	/**
	 * Gibt eine Liste der Arbeiter zurück, denen grad im Projekt keine Aufgabe zugeordnet ist
	 * @param project Das Projekt von welchem man die Arbeiter ohne Beschäftigung haben möchte
	 * @return workers Die Liste der Arbeiter, die an dem Projekt beteiligt sind aber nicht beschäftigt sind
	 */
	public ArrayList<Worker> getUnassignedTaskWorkersFromProject(Project project) {
		ArrayList<Worker> workers = getWorkersFromProject(project);
		ArrayList<Worker> unassignedWorkers = new ArrayList<>();
		for (Worker worker : workers) {
			if (mainController.getWorkerController().getCurrentTask(worker)==null) {
				unassignedWorkers.add(worker);
			}
		}
		return unassignedWorkers;
	}

	/**
	 * @return the projectAUI
	 */
	public ArrayList<ProjectAUI> getProjectAUI() {
		return projectAUI;
	}


}
