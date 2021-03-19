package controller;

import model.Arzt;
import model.EPA;
import model.Patient;

import java.io.IOException;
import java.lang.UnsupportedOperationException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
/**
 * einen neuen Benutzer anlegen der , Patient oder Arzt sein kann
 */
public class BenutzerAnlegenController {

	/**
	 * 
	 */
    private EPAController ePAController;

	/**
	 * Instantiates a new Benutzer anlegen controller.
	 *
	 * @param e the e
	 */
	public BenutzerAnlegenController(EPAController e) {
    	this.ePAController = e ;
    }

    /**
	 * Diese Methode liegt einen neuen Arzt an
	 * @param Vorname des Arztes
	 * @param Nachname des Arztes
	 * @param fach  des Arztes
	 * @param num des Arztes
	 * @param passwort des Arztes
	 * @param Tel des Arztes
 	 */
    public void arztAnlegen(String Vorname, String Nachname, String fach, String num, String passwort, String Tel) throws IOException {

    	Arzt arzt = new Arzt(Vorname, Nachname, fach, num, passwort, Tel); // create new instance arzt
    	ePAController.getEPA().addArzttToList(arzt);                      // add arzt to list
    	ePAController.getIO().save();                                    // save

    }

    /**
	 *  Diese Methode liegt einen neuen Patient an
	 * @param versicherungsnummer des Patienten
	 * @param Vorname Datum und Uhrzeit
	 * @param Nachname des Patienten
	 * @param Address  des Patienten
	 * @param
	 * @param Birth des Patienten
	 * @param pass des Patienten
	 */
    public void patientAnlegen(String versicherungsnummer, String Vorname, String Nachname, String Address, boolean geschlecht, String Birth, String pass,String hausarztid) throws IOException {

    	String sex = geschlecht ? "f" : "m";  // geschlecht is female ?
		Patient patient = new Patient(versicherungsnummer, Vorname, Nachname, Address, sex, Birth, pass) ;  // create new instance patient
		if(!hausarztid.isEmpty()&&ePAController.getEPA().checkNumArzt(hausarztid)){patient.behandeldenArztAendern(ePAController.getEPA().getArzt(hausarztid));}  // hausarzt id valid and not empty -> set hausarzt for patient
        ePAController.getEPA().addPatientToList(patient);  // add patient to list
		ePAController.getIO().save(); // save

    }
}
