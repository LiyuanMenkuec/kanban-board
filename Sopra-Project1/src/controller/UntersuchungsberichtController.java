package controller;

import model.EPA;
import model.Untersuchungsbericht;
import model.Arzt;
import model.Patient;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Untersuchungsbericht controller.
 */
public class UntersuchungsberichtController {

    private EPAController ePAController;


	/**
	 * Instantiates a new Untersuchungsbericht controller.
	 *
	 * @param epaController the epa controller
	 */
	public UntersuchungsberichtController(EPAController epaController) {
		ePAController = epaController;
    }

	/**
	 * Create untersuchungsbericht.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @param dateTime            the date time
	 * @param icd                 the icd
	 * @param med                 the med
	 * @param diagnose            the diagnose
	 * @param behandlung          the behandlung
	 * @param notes               the notes
	 */

	public void createUntersuchungsbericht(String versicherungsnummer, LocalDateTime dateTime, String icd, List<String> med, String diagnose, String behandlung, List<String> notes) throws IOException {
		Untersuchungsbericht bericht = new Untersuchungsbericht(versicherungsnummer, ePAController.getCurrLoggedIn(),dateTime, icd, med, diagnose, behandlung, notes); // create new instance untersuchung
		addUntersuchungbericht(versicherungsnummer, bericht); // add untersuchung to lists
    }

	/**
	 * Add untersuchungbericht.
	 *
	 * @param versicherungsnummer  the versicherungsnummer
	 * @param untersuchungsbericht the untersuchungsbericht
	 */
	public void addUntersuchungbericht(String versicherungsnummer, Untersuchungsbericht untersuchungsbericht) throws IOException {
		EPA epa = ePAController.getEPA(); // get epa
        Arzt arzt = epa.getArzt(ePAController.getCurrLoggedIn()); // get arzt instance of user
        Patient patient = epa.getPatient(versicherungsnummer); // get patient with versicherungsnummer
        arzt.addUntersuchungsberichrt(untersuchungsbericht); // add untersuchungbericht to list of arzt
        patient.addUntersuchungsList(untersuchungsbericht); // add untersuchungbericht to list of patient
		ePAController.getIO().save(); // save
    }

	/**
	 * Cd ueberpruefen boolean.
	 *
	 * @param icd the icd
	 * @return the boolean
	 */
	public boolean iCDUeberpruefen(String icd){
		return icd.matches("[A-Z]\\d{1,2}(\\.\\d{1,2}){0,1}");// check icd with format alphabet then  1 or 2 decimal ,  or alphabet then 1 or 2 decimal then . then 1 or 2 decimal
    }

	/**
	 * Versicherungsnummer ueberpruefen boolean.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @return the boolean
	 */
	public boolean versicherungsnummerUeberpruefen(String versicherungsnummer){
		EPA epa = ePAController.getEPA();
		return epa.checkNumPatient(versicherungsnummer);
	}


	/**
	 * Uhrzeit ueberpruefen boolean.
	 *
	 * @param uhrzeit the uhrzeit
	 * @return the boolean
	 */
	public boolean uhrzeitUeberpruefen(String uhrzeit){
		return uhrzeit.matches("([2][0-3]|[0-1][0-9]):[0-5][0-9]");// check time with format hh:mm
	}

	/**
	 * Felder leer boolean.
	 *
	 * @param uhrzeit             the uhrzeit
	 * @param versicherungsnummer the versicherungsnummer
	 * @param symptome            the symptome
	 * @param icd                 the icd
	 * @param behandlung          the behandlung
	 * @param medikamente         the medikamente
	 * @return the boolean
	 */
	public  boolean felderLeer(String uhrzeit, String versicherungsnummer, String symptome, String icd, String behandlung,String medikamente){// check leer felder
		return uhrzeit==null||uhrzeit==""||versicherungsnummer==null||versicherungsnummer==""||symptome==null||symptome==""||icd==null||icd=="" ||behandlung==null||behandlung==""||medikamente==null||medikamente=="";
	}
}
