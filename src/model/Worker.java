package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * @author elias
 *
 */
public class Worker implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6716618161873112798L;

	/**
	 * Der Vorname des Arbeiters
	 */
	private String firstname;

	/**
	 * Das Bild des Arbeiters
	 */
	private WorkerImage picture;

	/**
	 * Der Status des Arbeiters
	 */
	private boolean active;

	/**
	 * Der Nachname des Arbeiters
	 */
	private String lastname;
	
	/**
	 * Das Geburtsdatum des Arbeiters
	 */
	private LocalDate birthDate;
	
	/**
	 * Der Konstruktor der Klasse
	 */
	public Worker() {
		setFirstname("");
		setLastname("");
		picture=new WorkerImage();
		active=true;
		birthDate=null;
	}

	/**
	 * Gibt den Vornamen des Arbeiters zurück
	 * @return firstname Der Vorname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Verändert den Vornamen des Arbeiters
	 * @param firstname Der neue Vorname 
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Gibt das Bild des Arbeiters zurück
	 * @return picture Das Bild des Arbeiters
	 */
	public javafx.scene.image.Image getPicture() {
		return picture.getImage();
	}

	/**
	 * Verändert das Bild des Arbeiters
	 * @param img Das neue Bild des Arbeiters
	 */
	public void setPicture(javafx.scene.image.Image img) {
		picture.setImage(img);
	}

	/**
	 * Gibt den Status des Arbeiters zurück
	 * @return active Der Status des Arbeiters
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Verändert den Status des Arbeiters
	 * @param active Der neue Status des Arbeiters
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Gibt den Nachnamen des Arbeiters zurück
	 * @return lastname Der Nachname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * Verändert den Nachnamen des Arbeiters
	 * @param lastname Der neue Nachname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * Gibt das Geburtsdatum des Arbeiters zurück
	 * @return birthDate Das Geburtsdatum des Arbeiters
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/**
	 * Verändert das Geburtsdatum des Arbeiters
	 * @param birthDate Das neue Geburtsdatum des Arbeiters
	 */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public int hashCode() {
		return Objects.hash(active, birthDate, firstname, lastname);
	}

}
