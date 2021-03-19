package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.UnsupportedOperationException;
import java.util.List;

/**
 * The type Arzt.
 */
public class Arzt implements Serializable {

    private String idNum;

    private ArrayList<Patient> patientList;


    private ArrayList<Untersuchungsbericht> untersuchungsList;

    private String fachRichtung;

    private String vorName;

    private String tel;

	private String nachName;

    private ArrayList<String> revision;

    private ArrayList<Patient> patient;


    private String passWort;

	/**
	 * Instantiates a new Arzt.
	 *
	 * @param vorName  the vor name
	 * @param nachName the nach name
	 * @param fach     the fach
	 * @param idNum    the id num
	 * @param passWort the pass wort
	 * @param tel      the tel
	 */
	public Arzt(String vorName,String nachName, String fach, String idNum, String passWort, String tel) {
    	this.passWort= passWort; // init arzt
    	this.idNum=idNum;
    	this.vorName=vorName;
    	this.nachName=nachName;
    	this.fachRichtung=fach;
    	this.tel=tel;
		patientList=new ArrayList<Patient>();
		untersuchungsList= new ArrayList<Untersuchungsbericht>();
		revision=new ArrayList<String>();
    }

	/**
	 * Add patient to list.
	 *
	 * @param patient the patient
	 */
	public void addPatientToList(Patient patient)  {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter); // get String of time with formatter
    	String s= formattedDateTime+" Patient(in) "+patient.getName()+" mit Versicherungsnummer "+patient.getNum()+" wurde in ihrer Patientenliste hinzugefügt";
    	revision.add(s); // update revision of arzt
        patientList.add(patient); // add patient to list
    }


	/**
	 * Remove from patientlist.
	 *
	 * @param patient the patient
	 */
	public void removeFromPatientlist(Patient patient)  {
		patientList.remove(patient); // remove patient from patient list
    }

	/**
	 * Add untersuchungsberichrt.
	 *
	 * @param untersuchungsbericht the untersuchungsbericht
	 */
	public void addUntersuchungsberichrt(Untersuchungsbericht untersuchungsbericht)  {
        untersuchungsList.add(untersuchungsbericht); // add untersuchungbericht to untersuchungbericht list
    }


	/**
	 * Get fachrichtung string.
	 *
	 * @return the string
	 */
	public String getFachrichtung(){return fachRichtung;} // get fachrichtung


	/**
	 * Add to revision.
	 *
	 * @param re the re
	 */
	public void addToRevision(String re) {
        revision.add(re);
    } // add revision to revision list


	/**
	 * Get patient patient.
	 *
	 * @param num the num
	 * @return the patient
	 */
	public Patient getPatient(String num){ // get patient with versiucherungsnummer from list
		for(int i=0; i< patientList.size(); i++){
			if(patientList.get(i).getNum().equals(num)){return patientList.get(i);}
		}
    	return null;
	}

	/**
	 * Check patient valid boolean.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @return the boolean
	 */
	public boolean checkPatientValid(String versicherungsnummer)  { // check if patient with versicherungnummer exitst in patient list
        for(int i=0; i< patientList.size(); i++){
        	if(patientList.get(i).getNum().equals(versicherungsnummer)){return true;}
		}
        return false;
    }

	/**
	 * Get untersuchungsberichte array list.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @return the array list
	 */
	public ArrayList<Untersuchungsbericht> getUntersuchungsberichte(String versicherungsnummer){ // return list of untersuchungbericht wirh versucherungsnummer == versicherungsnummer in parameter
        ArrayList<Untersuchungsbericht> berichtList= new ArrayList<Untersuchungsbericht>();
        for(int i=0; i< untersuchungsList.size();i++){
        	if(untersuchungsList.get(i).getPatientNum().equals(versicherungsnummer)){berichtList.add(untersuchungsList.get(i));}
		}
        return berichtList;
    }

	/**
	 * Gets num.
	 *
	 * @return the num
	 */
	public String getNum()   {
        return idNum;
    } // get id number of arzt

	/**
	 * Gets revision.
	 *
	 * @return the revision
	 */
	public ArrayList<String> getRevision()  {
        return revision;
    } // get revision list

	/**
	 * Gets name.
	 *
	 * @return the name
	 */
	public String getName()  {
        return vorName+" "+nachName;
    } // get name of arzt

	/**
	 * Get tel string.
	 *
	 * @return the string
	 */
	public String getTel(){return tel;} // get telephone nummer of arzt


	/**
	 * Get vorname string.
	 *
	 * @return the string
	 */
	public String getVorname(){return vorName;} // get vorname of arzt

	/**
	 * Get nachname string.
	 *
	 * @return the string
	 */
	public String getNachname(){return nachName;} // get nachname of arzt

	/**
	 * Get passwort string.
	 *
	 * @return the string
	 */
	public String getPasswort(){return passWort;} // get passwort


	public boolean equals(Arzt o){ // check every attributs of 2 arzt to find out if thay are the same
		Arzt arzt = o;
		return(arzt.getVorname().equals(this.getVorname())&&arzt.getNachname().equals(this.getNachname())&&arzt.getFachrichtung().equals(this.getFachrichtung())&&arzt.getNum().equals(this.getNum())&&arzt.getPasswort().equals(this.getPasswort())&&arzt.getTel().equals(this.getTel()));
	}
}
