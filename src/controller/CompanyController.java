package controller;

import java.util.ArrayList;

import model.Project;

/**
 * Der CompanyController, um sich die verschiedenen Projektlisten der Firma zu holen.
 * 
 * @author Anne Stockey
 *
 */
public class CompanyController {

	/**
	 * Die Referenz auf den mainController, der für den Austausch 
	 * zwischen den Controllern und dem Model zuständig ist.
	 */
	final private MainController mainController;

	/**
	 * Konstruktor.
	 * 
	 * @param mainController
	 * 		Die Referenz auf den mainController, der für den Austausch 
	 * 		zwischen den Controllern und dem Model zuständig ist.
	 */
	public CompanyController(final MainController mainController) {
		this.mainController=mainController;
	}

	/**
	 * Erzeugt eine Liste mit allen archivierten Projekten der Firma.
	 * <br>
	 * Falls noch keine Projekte archiviert wurden, wird eine leere 
	 * Liste zurückgegeben.
	 * 
	 * @return Liste mit allen archivierten Projekten der Firma
	 */
	public ArrayList<Project> getArchivedProjects() {
		ArrayList<Project> archivedProjects= new ArrayList<Project>();
		ArrayList<Project> allProjects= mainController.getCompany().getProjects();
		for(Project project:allProjects) {
			if(!project.isActive()) {
				archivedProjects.add(project);
			}
		}
		return archivedProjects;
	}

	/**
	 * Erzeugt eine Liste mit allen aktiven Projekten der Firma.
	 * <br>
	 * Falls noch keine Projekte erstellt wurden, wird eine leere Liste zurückgegeben.
	 * 
	 * @return Liste mit allen aktiven Projekten der Firma
	 */
	public ArrayList<Project> getActiveProjects() {
		ArrayList<Project> activeProjects= new ArrayList<>();
		ArrayList<Project> allProjects= mainController.getCompany().getProjects();
		for(Project project:allProjects) {
			if(project.isActive()) {
				activeProjects.add(project);
			}
		}
		return activeProjects;
	}

}

