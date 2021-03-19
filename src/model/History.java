package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class History implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5222590893155527423L;


	/**
	 * Der Startzeitpukt der Aufgabe
	 */
	private LocalDateTime start;

	/**
	 * Der Endzeitpunkt der Aufgabe
	 */
	private LocalDateTime end;

	/**
	 * Der Bearbeiter der Aufgabe
	 */
	private Worker worker;
	
	/**
	 * Der Status der Aufgabe
	 */
	private Stage stage;
	
	/**
	 * Der Konstruktor der Aufgabe
	 * @param start Der Startzeitpunkt
	 */
	public History(final LocalDateTime start) {
		this.start=start;
		end=null;
		worker=null;
		stage=Stage.NEW;
	}

	/**
	 * Gibt den Bearbeiter zurück
	 * @return worker Der Bearbeiter der Aufgabe
	 */
	public Worker getWorker() {
		return worker;
	}
	/**
	 * Gibt zurück ob ein Mitarbeiter zugewiesen wurde
	 * @return true wenn worker verhanden ist, sonst false
	 */
	public boolean hasWorker() {
		return worker!=null;
	}

	/**
	 * Verändert den Bearbeiter Der Aufgabe
	 * @param worker Der neue Bearbeiter
	 */
	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	/**
	 * Gibt den Status der Aufgabe zurück
	 * @return stage Der Status der Aufgabe
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Verändert den Status der Aufgabe
	 * @param stage Der neue Status der Aufgabe
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Gibt den Startzeitpunkt zurück
	 * @return start Der Startzeitpunkt
	 */
	public LocalDateTime getStart() {
		return start;
	}

	/**
	 * Gibt den Endzeitpunkt zurück
	 * @return end Der Endzeitpunkt
	 */
	public LocalDateTime getEnd() {
		return end;
	}

	/**
	 * Verändert den Endzeitpunkt
	 * @param end Der neue Endzeitpunkt
	 */
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	@Override
	public int hashCode() {
		return Objects.hash(end, stage, start, worker);
	}

}
