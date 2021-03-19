package controller;

import model.Worker;
import view.auis.CompanyAUI;
import view.auis.ProjectAUI;
import view.auis.WorkerAUI;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import model.Project;

import model.Task;

/**
 * In WorkerController werden die Workers in der Firma verwaltet.
 *
 * @author Abdulrazzak Shaker
 */
public class WorkerController {

	/**
	 * ein Attribut mainController zum Zugriff auf alle anderen Controllern. Zwei
	 * AUI Attribute zum Refresh.
	 */
	private MainController mainController;

	/**
	 * Die WorkerAUIs
	 * **/
	private ArrayList<WorkerAUI> workerAUI;

	/**
	 * Die CompanyAUIs
	 * **/
	private ArrayList<CompanyAUI> companyAUI;
	
	/**
	 * Die ProjectAUIs
	 * **/
	private ArrayList<ProjectAUI> projectAUI;

	/**
	 * Constructor zur Zuweisung des Attributs mainController.
	 * 
	 * @param mainController
	 */
	public WorkerController(MainController mainController) {
		if (mainController != null) {
			this.mainController = mainController;
			workerAUI = new ArrayList<>();
			companyAUI = new ArrayList<>();
			projectAUI = new ArrayList<>();
		} else {
			throw new NullPointerException("mainController is null!");
		}

	}

	/**
	 * Ruft die Methoden der CompanyAUIs auf.
	 * **/
	private void refreshCompanyAUIs() {
		companyAUI.forEach(CompanyAUI::refreshWorkerList);
		companyAUI.forEach(CompanyAUI::refreshProjectList);
	}

	/**
	 * hohlt die Liste aller Mitarbeiter in der Firma. Das tut sie, indem sie auf
	 * die Liste der Mitarbeiter zugreift, die in der Company Objekt sind. Das
	 * Company Objekt ist vom mainController zu erreichen.
	 * 
	 * @return Liste aller Mitarbeiter, die in der Firma sind.
	 */
	public ArrayList<Worker> getAllWorkers() {
		return mainController.getCompany().getWorkers();
	}

	/**
	 * 
	 * @param worker, der addiert werden muss. Fügt einen Mitarbeiter zu der Firma
	 *                hinzu, der in dem ViewSchicht erzeugt wurde und als Parameter
	 *                gegeben wurde. Der erzeugte Mitarbeiter wird zu der Liste
	 *                aller Mitarbeiter in Firma hinzugefügt.
	 */
	public void addWorker(Worker worker) { 
		ArrayList<Worker> allWorkers = mainController.getCompany().getWorkers();
		allWorkers.add(worker);
		companyAUI.forEach(CompanyAUI::refreshWorkerList);
	}

	/**
	 * Gibt die Aufgabe eines Mitarbeiters zurück. Erstmal werden alle Task in dem Projekt, wo ein Mitarbeiter
	 * drin ist gehohlt und dann wird jede Task vom Projekt überprüft, ob sie ein Mitarbeiter hat und wenn ja, dann
	 * ob ihr Mitarbeiter schon der currentWorker ist. 
	 * 
	 * @param worker
	 * @return Aufgabe eines Mitarbeiters
	 */
	public Task getCurrentTask(final Worker worker) {
		if (worker != null) {
			ArrayList<Task> tasks = getProjectOfWorker(worker).getTasks();
			for (Task task : tasks) {
				if (task.getCurrentWorker() != null && task.getCurrentWorker().equals(worker))
					return task;
			}
			return null;
		} else {
			throw new NullPointerException("Argument is null (worker)");
		}

	}

	/**
	 * 
	 * @param worker,  der zu bearbeitende Mitarbeiter.
	 * @param changes, die neue Daten, die zu einem Mitarbeiter gepackt werden
	 *                 müssen. überschreibt die Daten eines Mitarbeiters. Hohlt die
	 *                 Liste aller Mitarbeiter durch die Methode getAllWorkers().
	 *                 läuft auf die Liste durch und sucht nach dem Parameter
	 *                 worker. Sobald sie den gefunden hat, dann werden die Daten
	 *                 dieses Mitarbeiters überschrieben durch die set- und
	 *                 getMethoden, die für die Attribute eines Worker-Objekts
	 *                 Vorhanden sind.
	 */
	public void updateWorker(Worker worker, Worker changes) {
		if (worker != null && changes != null) { 
			if (getAllWorkers().contains(changes)) {
				mainController.showNotification("Der Worker existiert bereits!", true); 
				return;
			}
			worker.setFirstname(changes.getFirstname());
			worker.setLastname(changes.getLastname());
			worker.setPicture(changes.getPicture());
			worker.setBirthDate(changes.getBirthDate());
			setWorkerState(changes.isActive(), worker);
			projectAUI.forEach(ProjectAUI::refreshTeamList);
			workerAUI.forEach(WorkerAUI::refreshWorkerData);
			companyAUI.forEach(CompanyAUI::refreshWorkerList);
		} else {
			throw new NullPointerException("One argument is null");
		}

	}

	/**
	 * Gibt das zu dem Worker gehörende Project zurück
	 * 
	 * @param worker
	 * @return Projekt wenn vorhanden, sont null
	 */
	public Project getProjectOfWorker(final Worker worker) {
		ArrayList<Project> projects = mainController.getCompany().getProjects();
		try {
			return projects.stream().filter(listElement -> listElement.getWorkers().contains(worker)).findAny().get();
		} catch (NoSuchElementException nsElementException) {
			return null;
		}

	}

	/**
	 * setzt das Attribut active eines gegebenen Mitarbeiter wie gewünscht. 
	 * @param active, zum Setzen des Attributs active. 
	 * @param worker, der Mitarbeiter, desen Attribut active gesetzt werden muss. 
	 */
	public void setWorkerState(boolean active, Worker worker) {
		if (worker != null) {
			if (worker.isActive() != active) {
				changeWorkerState(worker);
				projectAUI.forEach(ProjectAUI::refreshTeamList);
				workerAUI.forEach(WorkerAUI::refreshWorkerData);
				companyAUI.forEach(CompanyAUI::refreshWorkerList);
			}
		} else {
			throw new NullPointerException("Worker is null!");
		}

	}

	/**
	 * verändert das Attribut aktive eines Mitarbeiters.
	 * 
	 * @param worker, dessen Status verändert werden muss. Hohlt die Liste aller
	 *                Mitarbeiter. läuft auf die Liste durch und sucht nach dem
	 *                Parameter worker. Sobald sie den gefunden hat, wird das
	 *                Attribut dieses Mitarbeiters geändert solange er keinem Projekt
	 *                zugewiesen sind durch die set- und getMethoden,
	 *                die für das Attribut aktive des Worker-Objekts Vorhanden sind.
	 */
	public void changeWorkerState(Worker worker) { 
		if (worker != null) {
			Project currentProject = getProjectOfWorker(worker);
			if (currentProject != null) {
				worker.setActive(false);
				mainController.getTeamController().removeWorkerFromProject(currentProject, worker);

				refreshCompanyAUIs();
			} else {
				worker.setActive(!worker.isActive());
				projectAUI.forEach(ProjectAUI::refreshTeamList);
				refreshCompanyAUIs();
			}
		} else {
			throw new NullPointerException("Worker is null!");
		}

	}

	/**
	 * hohlt erstmal die Liste aller Mitarbeiter in der Firma, die aktiv sind. Läuft durch diese Liste
	 * überprüft mithilfe der Methode getProjectOfWorker(Worker worker), ob der Mitarbeiter in einem Project
	 * arbeitet. Wenn nicht, dann wird zu der Liste unassignedWorkers hinzugefügt.
	 *  
	 * @return unassignedWorkers : Liste der Mitarbeiter, die keinem Projekt zugewiesen sind. 
	 */
	public ArrayList<Worker> getUnassignedProjectWorkers() {
		ArrayList<Worker> allCompanyWorkers = getActiveWorkers();
		ArrayList<Worker> unassignedWorkers = new ArrayList<>();
		for (Worker worker : allCompanyWorkers) {
			if (getProjectOfWorker(worker) == null) {
				unassignedWorkers.add(worker);
			}
		}
		return unassignedWorkers;
	}

	/**
	 *  hoohlt sich die Liste aller Mitarbeiter und sucht in der nach den aktiven.
	 * @return activeWorkers. Alle Mitarbeiter die noch in der Firma arbeiten. 
	 */
	public ArrayList<Worker> getActiveWorkers() {
		ArrayList<Worker> allCompanyWorkers = getAllWorkers();
		ArrayList<Worker> activeWorker = new ArrayList<>();
		for (Worker worker : allCompanyWorkers) {
			if (worker.isActive())
				activeWorker.add(worker);
		}
		return activeWorker;
	}

	/**
	 * hohlt sich die Liste aller Mitarbeiter und sucht in der nach den inaktiven.
	 * @return inActiveWorkers. Alle Mitarbeiter die nicht mehr in der Firma arbeiten. 
	 */
	public ArrayList<Worker> getInactiveWorkers() {
		ArrayList<Worker> allCompanyWorkers = getAllWorkers();
		ArrayList<Worker> inActiveWorker = new ArrayList<>();
		for (Worker worker : allCompanyWorkers) {
			if (!worker.isActive())
				inActiveWorker.add(worker);
		}
		return inActiveWorker;
	}

	/**
	 * @return the workerAUI
	 */
	public ArrayList<WorkerAUI> getWorkerAUI() { 
		return workerAUI;
	}

	/**
	 * Setter für die WorkerAUI
	 * 
	 * @param workerAUI Die neue WorkerAUI-Liste
	 * **/
	public void setWorkerAUI(final ArrayList<WorkerAUI> workerAUI) {
		this.workerAUI = workerAUI;
	}

	/**
	 * @return the companyAUI
	 */
	public ArrayList<CompanyAUI> getCompanyAUI() { 
		return companyAUI;
	}
	/**
	 * Setzt die CompanyAUI
	 * @param companyAUI
	 */
	public void setCompanyAUI(final ArrayList<CompanyAUI> companyAUI) {
		this.companyAUI = companyAUI;
	}

	/**
	 * @return the projectAUI
	 */
	public ArrayList<ProjectAUI> getProjectAUI() {
		return projectAUI;
	}

	/**
	 * @param projectAUI the projectAUI to set
	 */
	public void setProjectAUI(ArrayList<ProjectAUI> projectAUI) {
		this.projectAUI = projectAUI;
	}

}
