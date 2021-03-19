package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class Comment implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1113885344929100430L;

	/**
	 * Der Inhalt des Kommentars
	 */
	private String content;

	/**
	 * Der Erstellzeitpunkt des Kommentars
	 */
	private LocalDateTime createdAt;

	/**
	 * Der Verfasser des Kommentars
	 */
	private Worker worker;
	
	/**
	 * Der Konstruktor der Klasse
	 */
	public Comment() {
		content="";
		createdAt=LocalDateTime.now();
		worker=null;
	}

	/**
	 * Gibt den Inhalt des Kommentars zurück
	 * @return content Der Inhalt des Kommentars
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Verändert den Inhalt des Kommentars
	 * @param content Der neue Inhalt
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Gibt den Ertellzeitpunkt des Kommentars zurück
	 * @return createdAt Der Erstellzeitpunkt des Kommentars
	 */
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	/**
	 * Verändert den Erstellzeitpunkt des Kommentars
	 * @param createdAt Der neu Erstellzeitpunkt
	 */
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gibt den Verfasser des Kommnetars zurück
	 * @return worker Der Verfasser des Kommentars
	 */
	public Worker getWorker() {
		return worker;
	}

	/**
	 * Verändert den Verfasser des Kommentars
	 * @param worker Der neue Verfasser des Kommentars
	 */
	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createdAt, worker);
	}

}
