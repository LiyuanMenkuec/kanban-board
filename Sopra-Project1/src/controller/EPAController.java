package controller;

import model.EPA;

import java.lang.UnsupportedOperationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Epa controller.
 */
public class EPAController {

	private boolean isArzt;

    private String currLoggedIn;

    private EPA ePA;

    private IOController iOController;

    private UntersuchungsberichtController untersuchungsberichtController;

    private UeberweisungsController ueberweisungsController;

    private PatientDatenController patientDatencontroller;

    private BenutzerAnlegenController benutzerAnlegenController;

    private ZusammenfassungsController zusammenfassungsController;

	/**
	 * Instantiates a new Epa controller.
	 */
	public EPAController() { // create epa controller and others controller, save in attributes
    	this.ePA=new EPA();
    	this.benutzerAnlegenController=new BenutzerAnlegenController(this);
    	this.ueberweisungsController=new UeberweisungsController(this);
		this.untersuchungsberichtController=new UntersuchungsberichtController(this);
		this.iOController=new IOController(this);
		this.zusammenfassungsController= new ZusammenfassungsController(this);
		this.patientDatencontroller=new PatientDatenController(this);
		try {
			iOController.load();
		} catch (Exception e) {
		}

	}

	/**
	 * Setis arzt.
	 *
	 * @param b the b
	 */
	public void setisArzt(boolean b){this.isArzt=b;}

	/**
	 * Is arzt boolean.
	 *
	 * @return the boolean
	 */
	public boolean isArzt() {
		return isArzt;
	}

	/**
	 * Gets epa.
	 *
	 * @return the epa
	 */
	public EPA getEPA(){
        return ePA;
    }

	/**
	 * Set epa.
	 *
	 * @param epa the epa
	 */
	public void setEPA(EPA epa){
        this.ePA=epa;
    }

	/**
	 * Get time string.
	 *
	 * @return the string
	 */
	public String getTime(){ // get time, return with format yyyy-MM-dd HH:mm:ss
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
	}

	/**
	 * Gets io.
	 *
	 * @return the io
	 */
	public IOController getIO()  {
        return iOController;
    }


	/**
	 * Gets patient daten controller.
	 *
	 * @return the patient daten controller
	 */
	public PatientDatenController getPatientDatenController()  {
        return patientDatencontroller;
    }

	/**
	 * Gets akt anlegen controller.
	 *
	 * @return the akt anlegen controller
	 */
	public BenutzerAnlegenController getAktAnlegenController()  {
        return  benutzerAnlegenController;
    }

	/**
	 * Gets untersuchungsberichtcontroller.
	 *
	 * @return the untersuchungsberichtcontroller
	 */
	public UntersuchungsberichtController getUntersuchungsberichtcontroller() {
        return untersuchungsberichtController;
    }

	/**
	 * Get ueberweisungs controller ueberweisungs controller.
	 *
	 * @return the ueberweisungs controller
	 */
	public UeberweisungsController getUeberweisungsController()
	{
        return ueberweisungsController;
    }


	/**
	 * Gets zusammenfassungs controller.
	 *
	 * @return the zusammenfassungs controller
	 */
	public ZusammenfassungsController getZusammenfassungsController()
	{
        return zusammenfassungsController;
    }

	/**
	 * Set curr logged in.
	 *
	 * @param num the num
	 */
	public void setCurrLoggedIn(String num)
	{
		currLoggedIn=num;// set current loggin number
	}


	/**
	 * Gets curr logged in.
	 *
	 * @return the curr logged in
	 */
	public String getCurrLoggedIn() throws UnsupportedOperationException
	{
        return currLoggedIn;// ger current loggin number
    }
}
