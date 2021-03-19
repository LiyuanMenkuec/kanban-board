package controller;

import model.Arzt;
import model.EPA;
import model.Ueberweisung;
import model.Untersuchungsbericht;

import java.lang.UnsupportedOperationException;
import java.util.ArrayList;


/**
 * The type Zusammenfassungs controller.
 */
public class ZusammenfassungsController {

    private EPAController ePAController;

	/**
	 * Instantiates a new Zusammenfassungs controller.
	 *
	 * @param e the e
	 */
	public ZusammenfassungsController(EPAController e) {
    	ePAController=e;
    }

	/**
	 * Add to zusammenfassung.
	 *
	 * @param untersuchungsbericht the untersuchungsbericht
	 * @throws UnsupportedOperationException the unsupported operation exception
	 */
	public void addToZusammenfassung(Untersuchungsbericht untersuchungsbericht) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not Yet Implemented!");
    }

	/**
	 * Create zusammenfassung ein arzt list.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @param iCD                 the cd
	 * @return the list
	 */
	public ArrayList<Untersuchungsbericht> createZusammenfassungEinArzt(String versicherungsnummer, String iCD){
		EPA e= ePAController.getEPA();// get epa
		Arzt a = e.getArzt(ePAController.getCurrLoggedIn());// get arzt with arzt id == login nummer
		ArrayList<Untersuchungsbericht>berichtlist= a.getUntersuchungsberichte(versicherungsnummer); // get untersuchungberichtlist of arzt, save in berichtlist
		if(!iCD.equals("leer")){ // wenn icd eingegeben wird
			for(int i=0; i<berichtlist.size();i++){ // check every untersuchungbericht in berichlist
				if(!berichtlist.get(i).getICD().equals(iCD)){ // if icd != icd in parameter
					berichtlist.remove(i);// remove berichte that have icd != icd to find from list
				}
			}
		}
		return berichtlist; // return berichtlist
    }

	/**
	 * Create zusammenfassung alle aerzte array list.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @param iCD                 the cd
	 * @return the array list
	 */
	public ArrayList<Untersuchungsbericht> createZusammenfassungAlleAerzte(String versicherungsnummer, String iCD){
         EPA e= ePAController.getEPA(); // get epa
         Arzt a = e.getArzt(ePAController.getCurrLoggedIn());// get arzt with arzt id == login nummer
		 ArrayList<Untersuchungsbericht>berichtlist= createZusammenfassungEinArzt(versicherungsnummer,iCD); // get untersuchungberichtlist of this arzt, save in berichtlist
		 if(a.getFachrichtung().equals("Hausarzt")){ // if arzt is hausarzt
		 	for(int i=0; i<a.getPatient(versicherungsnummer).getUntersuchungList().size();i++){ // check every untersuchungbericht in untersuchungberichtlist of patient with versicherungsnummer in patient list of arzt
		 		boolean check=false;
		 		for(int j=0;j<berichtlist.size();j++){ // check every untersuchungbericht in berichtlist
		 			if(equalsBericht(berichtlist.get(j),a.getPatient(versicherungsnummer).getUntersuchungList().get(i) )){ // if duplicate
		 				check=true; // check = true
		 				break;
					}
				}
		 		if(check==false){berichtlist.add(a.getPatient(versicherungsnummer).getUntersuchungList().get(i));}// not duplicate -> check == false and save in berichtlist
			}
		 }else{ // if arzt != hausarzt
			 ArrayList<Ueberweisung> ue=a.getPatient(versicherungsnummer).getUeberweisungsList();// get ueberweisungslist from patient with versicherungsnummer, save in ue
			 for(int i=0; i<ue.size(); i++){ // check every ueberweisung in ue
				 if(ue.get(i).getNeuarztnummer().equals(ePAController.getCurrLoggedIn())&&ue.get(i).isDatenStimmZu()) {// get ueberweisung in ue mit neuarztnum == this arztnum && derren Daten wurden schon zugestimmt
					 for(int k=0; k<ue.get(i).getUntersuchungsbericht().size();k++){ // check every untersuchungbericht in untersuchungberichtlist of that ueberweisung
						 boolean check=false;
						 for(int j=0;j<berichtlist.size();j++){
							 if(equalsBericht(berichtlist.get(j), ue.get(i).getUntersuchungsbericht().get(k))){// if duplicate in berichtlist
								 check=true; // check = true
								 break;
							 }
						 }
						 if(check==false){berichtlist.add(ue.get(i).getUntersuchungsbericht().get(k));}// not duplicate -> check == false and save in berichtlist
					 }
				 }
			 }
		 }
		if(!iCD.equals("leer")){ // if ice was given
			for(int i=0; i<berichtlist.size();i++){
				if(!berichtlist.get(i).getICD().equals(iCD)){
					berichtlist.remove(i);// remove untersuchungbericht in berichtlist that have icd != icd to find
				}
			}
		}
         return berichtlist; // return berichtlist
    }
    private boolean equalsBericht(Untersuchungsbericht u1, Untersuchungsbericht u2){ // check every attribut of 2 untersuchungbericht in parameter to find if 2 r the same
		return u1.getBehandenderArzt().equals(u2.getBehandenderArzt())&&u1.getDatum().equals(u2.getDatum())&&u1.getICD().equals(u2.getICD())&&u1.getPatientVersicherungnum().equals(u2.getPatientVersicherungnum())
		&&u1.getBehandlung().equals(u2.getBehandlung())&&u1.getDiagnose().equals(u2.getDiagnose())&&u1.getMed().equals(u2.getMed())&&u1.getNotes().equals(u2.getNotes());

	}
}
