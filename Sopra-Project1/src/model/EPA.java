package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.lang.UnsupportedOperationException;
import java.util.List;

/**
 * The type Epa.
 */
public class EPA implements Serializable {

    private ArrayList<Arzt> arztList;

    private ArrayList<Patient> patientList;

	/**
	 * Instantiates a new Epa.
	 */
	public EPA() { // init 2 emtpy list for patient adn arzt
    	this.arztList= new ArrayList<Arzt>();
    	this.patientList= new ArrayList<Patient>();
    }

	/**
	 * Add patient to list.
	 *
	 * @param patient the patient
	 */
	public void addPatientToList(Patient patient){
		this.patientList.add(patient);// add patient to patient list
	}

	/**
	 * Add arztt to list.
	 *
	 * @param arzt the arzt
	 */
	public void addArzttToList(Arzt arzt){
		this.arztList.add(arzt); // add arzt to arzt list
	}

	/**
	 * Get arzt list array list.
	 *
	 * @return the array list
	 */
	public ArrayList<Arzt> getArztList(){return arztList;} // get arztlist

	/**
	 * Gets patient list.
	 *
	 * @return the patient list
	 */
	public ArrayList<Patient> getPatientList() {
		return patientList;
	} // get patient list

	/**
	 * @param num the num
	 * @return Patient patient
	 */
	public Patient getPatient(String num) { // get patient in patient list with versicherungsnummer == num in paramater
		for(int i=0; i< patientList.size(); i++){
			if(patientList.get(i).getNum().equals(num)){return patientList.get(i);}
		}
		return null;
    }


	/**
	 * @param num the num
	 * @return Arzt arzt
	 */
	public Arzt getArzt(String num) { // get arzt wit arzt id == num in parameter
        for(int i=0; i< arztList.size(); i++){
        	if(arztList.get(i).getNum().equals(num)){return arztList.get(i);}
		}
        return null;
    }

	/**
	 * @param num the num
	 * @return boolean boolean
	 */
	public boolean checkNumArzt(String num)  { // check if there is arzt have id == num in arzt list
        for(int i=0; i< arztList.size(); i++){
        	if(arztList.get(i).getNum().equals(num)){return true;}
		}
        return false;
    }

	/**
	 * @param num the num
	 * @return boolean boolean
	 */
	public boolean checkNumPatient(String num)  { // check if there is patient with versiucherungsnummer == num in patient list
		for(int i=0; i< patientList.size(); i++){
			if(patientList.get(i).getNum().equals(num)){return true;}
		}
		return false;
    }

	/**
	 * Remove arzt.
	 *
	 * @param num the num
	 */
	public  void removeArzt(String num){ // remove  arzt have id == num in arzt list
		for(int i=0; i< arztList.size(); i++){
			if(arztList.get(i).getNum().equals(num)){
				arztList.remove(arztList.get(i));
			}
		}
	}

	/**
	 * Remove patient.
	 *
	 * @param num the num
	 */
	public  void removePatient(String num){ //remove patient with versiucherungsnummer == num in patient list
		for(int i=0; i< patientList.size(); i++){
			if(patientList.get(i).getNum().equals(num)){
				patientList.remove(patientList.get(i));
			}
		}
	}
}
