package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class Project implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -6627548432248947471L;

	/**
	 * Status des Projektes
	 */
	private boolean active;

	/**
	 * Deadline des Projekts
	 */
	private LocalDateTime deadline;

	/**
	 * Beschreibung des Projekts
	 */
	private String description;

	/**
	 * Name des Projekts
	 */
	private String name;

	/**
	 * Liste der Aufgaben des Projekts
	 */
	private ArrayList<Task> tasks;

	/**
	 * Liste der Arbeiter des Projekts
	 */
	private ArrayList<Worker> workers;
	
	/**
	 * Der Konstruktor der Klasse
	 */
	public Project() {
		active=true;
		setDeadline(null);
		setDescription("");
		setName("");
		tasks=new ArrayList<>();
		workers=new ArrayList<>();
	}

	/**
	 * Gibt den Status des Projekts zurück
	 * @return active Status des Projekts
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Verändert den Status des Projekts
	 * @param active Der neue Status des Projekts
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gibt die Deadline des Projektes zurück
	 * @return deadline Die Deeadline des Projekts
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}

	/**
	 * Verändert die Deadline des Projekts
	 * @param deadline Die neue Deadline des Projekts
	 */
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	/**
	 * Gibt die Beschreibung des Projekts zurück
	 * @return description Die Beschreibung des Projekts
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Verändert die Beschreibung des Projekts
	 * @param description Die neue Beschreibung des Projekts
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gibt den Namen des Projekts zurück
	 * @return name Der Name des Projekts
	 */
	public String getName() {
		return name;
	}

	/**
	 * Verändert den Namen des Projekts
	 * @param name Der neue Name des Projekts
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gibt die Liste der Aufgaben des Projekts zurück
	 * @return tasks Die Liste der Aufgaben
	 */
	public ArrayList<Task> getTasks() {
		return tasks;
	}

	/**
	 * Verändert die Liste der Aufgaben des Projekts
	 * @param tasks Die neue Liste der Aufgaben
	 */
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * Gibt die Liste der Arbeiter des Projekts zurück
	 * @return workers Die Liste der Arbeiter
	 */
	public ArrayList<Worker> getWorkers() {
		return workers;
	}

	/**
	 * Verändert die Liste der Arbeiter des Projekts
	 * @param workers Die neue Liste der Arbeiter
	 */
	public void setWorkers(ArrayList<Worker> workers) {
		this.workers = workers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, deadline, description, name, tasks, workers);
	}

}
