package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class Company implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1628199973649223768L;

	/**
	 * Der Name der Firma
	 */
	private String name;

	/**
	 * Die Liste mit den Arbeitern der Firma
	 */
	private ArrayList<Worker> workers;

	/**
	 * Die Liste mit den Projekten der Firma
	 */
	private ArrayList<Project> projects;
	
	/**
	 * Der Konstruktor der Klasse
	 */
	public Company() {
		name="Firma";
		workers=new ArrayList<Worker>();
		projects=new ArrayList<Project>();
	}

	/**
	 * Gibt den Namen der Firma zurück
	 * @return name Der Name der Firma
	 */
	public String getName() {
		return name;
	}

	/** Verändert den Namen der Firma
	 * @param name Der neue Name der Firma
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Liste mit den Arbeitern der Firma zurück
	 * @return workers Die Liste mit den Arbeitern der Firma
	 */
	public ArrayList<Worker> getWorkers() {
		return workers;
	}

	/**
	 * Gibt die Liste der Projekte der Firma zurück
	 * @return projects Die Liste der Projekte der Firma
	 */
	public ArrayList<Project> getProjects() {
		return projects;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, workers, projects);
	}

}
