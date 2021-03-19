package model;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class Task implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2621627338077241669L;

	/**
	 * Der Titel der Aufgabe
	 */
	private String title;

	/**
	 * Die Beschreibung der Aufgabe
	 */
	private String description;

	/**
	 * Die Deadline der Aufgabe
	 */
	private LocalDateTime deadline;

	/**
	 * Der Status der Aufgabe
	 */
	private boolean cancelled;

	/**
	 * Die Liste der Kommentare der Aufgabe
	 */
	private ArrayList<Comment> comments;

	/**
	 * Die Liste der History-Objekte der Aufgabe
	 */
	private ArrayList<History> transitions;
	
	/**
	 * Der Konstruktor der Klasse
	 */
	public Task() {
		setTitle("");
		setDescription("");
		setDeadline(LocalDateTime.now().plusDays(7));
		cancelled=false;
		setComments(new ArrayList<Comment>());
		setTransitions(new ArrayList<History>());
		transitions.add(new History(LocalDateTime.now()));
	}
	
	/**
	 * Gibt den Titel der Aufgabe zurück
	 * @return title Der Titel der Aufgabe
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Verändert den Titel der Aufgabe
	 * @param title Der neue Titel der Aufgabe
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gibt die Beschreibung der Aufgabe zurück
	 * @return description Die Beschreibung der Aufgabe
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Verändert die Beschreibung der Aufgabe
	 * @param description Die neue Beschreibung der Aufgabe
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gibt die Deadline der Aufgabe zurück
	 * @return deadline Die Deadline der Aufgabe
	 */
	public LocalDateTime getDeadline() {
		return deadline;
	}
	
	/**
	 * Verändert die Deadline der Aufgabe
	 * @param deadline Die neue Deadline der Aufgabe
	 */
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	
	/**
	 * Gibt den Status der Aufgabe zurück
	 * @return cancelled Der Status der Aufgabe
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Verändert den Status der Aufgabe
	 * @param cancelled Der neue Status
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	
	/**
	 * Gibt die Liste der Kommnetare der Aufgabe zurück
	 * @return comment Die Liste der Kommentare
	 */
	public ArrayList<Comment> getComments() {
		return comments;
	}
	
	/**
	 * Verändert die Liste der Kommentare
	 * @param comments Die neue Liste der Kommentare
	 */
	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	
	/**
	 * Gibt die Liste mit den History-Objekten zurück
	 * @return history Die Liste mit den History-Objekten
	 */
	public ArrayList<History> getTransitions() {
		return transitions;
	}
	
	/**
	 * Verändert die Liste mit den History-Objekten
	 * @param history Die neue Liste mit den History-Objekten
	 */
	public void setTransitions(ArrayList<History> history) {
		this.transitions = history;
	}
	
	/**
	 * Gibt den aktuellen Status der Augabe zurück
	 * @return Es wird vom letzten History-Objekt die Stage zurückgegeben
	 */
	public Stage getCurrentStage() {
		return transitions.get(transitions.size()-1).getStage();
	}

	/**
	 * Gibt den aktuellen Arbeiter der Aufgabe zurück
	 * @return Es wird vom letzten History-Objekt der Arbeiter zurückgegeben
	 */
	public Worker getCurrentWorker() {
		return transitions.get(transitions.size()-1).getWorker();
	}
	/**
	 * Gibt die verbliebene Zeit zurück
	 * @return Duration mit Zeit bis zur Deadline
	 */
	public Duration getLeftTime() {
		if (deadline != null)
			return Duration.between(LocalDateTime.now(), deadline);
		else
			return null;

	}
	/**
	 * Gibt den Hash-Wert des Objekts zurück
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cancelled, comments, deadline, description, title, transitions);
	}

}
