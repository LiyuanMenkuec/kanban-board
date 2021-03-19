package controller;

import model.EPA;
import model.Patient;
import model.Untersuchungsbericht;

import javax.sound.midi.InvalidMidiDataException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;

/**
 * personliche Daten bearbeiten
 */

public class PatientDatenController {

	/**
	 *
	 */

	private EPAController ePAController;

	/**
	 * Instantiates a new Patient daten controller.
	 *
	 * @param ePAController the e pa controller
	 */
	public PatientDatenController(EPAController ePAController) {
		this.ePAController = ePAController ;

	}
	/**
	 * *Diese Methode ermauglicht die Daten eines schon existierend Patient zu bearbeiten
	 * @param versicherungsnummer des Patienten
	 * @param vorname Datum und Uhrzeit
	 * @param nachname des Patienten
	 * @param address -ikamente des Patienten
	 * @param geschlecht des Patienten
	 * @param birth des Patienten
	 * @param pass des Patienten
	 */

	public void patientDatenUpdate(String versicherungsnummer, String vorname, String nachname, String address, String geschlecht, String birth, String pass) throws IOException {
		EPA epa = ePAController.getEPA();  // get epa
		Patient patient = epa.getPatient(versicherungsnummer); // get patient with versicherungnummer
		patient.setVorname(vorname); // update new info like parameter with setter
		patient.setNachname(nachname);
		patient.setAddress(address);
		patient.setGeschlecht(geschlecht);
		patient.setBirth(birth);
		patient.setPasswort(pass);
		ePAController.getIO().save(); // save
	}




}
