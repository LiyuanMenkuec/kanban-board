package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 */
public class Patient implements Serializable {

	private String versicherungNum;

	private ArrayList<Ueberweisung> ueberweisungsList;

	private ArrayList<Untersuchungsbericht> untersuchungsberichtsList;

	private String vorName;

	private String nachName;

	private String address;

	private String geschlecht;

	private String gesburtsDatum;

	private ArrayList<String> revision;

	private Arzt behandelnderArzt;

	private String nameAltArzt;

    private boolean isNeuDaten=false; // new data added ?

	private String passwort;

	/**
	 *
	 * @param versicherungNum
	 * @param vorName
	 * @param nachName
	 * @param address
	 * @param geschlecht
	 * @param birth
	 * @param passWort
	 */
	public Patient(String versicherungNum, String vorName,String nachName, String address, String geschlecht, String birth, String passWort) {
		this.passwort=passWort; // create new patient with attributes given in parameter
        this.versicherungNum=versicherungNum;
		this.vorName=vorName;
		this.nachName=nachName;
        this.address=address;
        this.geschlecht=geschlecht;
        this.gesburtsDatum=birth;
		this.ueberweisungsList= new ArrayList<Ueberweisung>(); // make empty lists
		this.untersuchungsberichtsList= new ArrayList<Untersuchungsbericht>();
		this.revision= new ArrayList<String>();
	}

	 /**
	 *
	 * @return
	 */
	public String getNum() {
		return versicherungNum;
	}

	/**
	 *
	 * @return
	 */
    public String getGesburtsDatum(){return gesburtsDatum;}

	/**
	 *
	 * @param r
	 */
	public void addToRevision(String r) {
        revision.add(r);
	}// add new revision to revision list

	/**
	 *
	 * @param n
	 */
	public void setVorname(String n) {
        if(n.equals(vorName)){}
        else{ // if n != vorname
        	revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihrer Vorname wurde von "+vorName+" zu "+n+" geandert"); // add revision
			vorName=n; // change vorname to n
		}
	}

	/**
	 *
	 * @param n
	 */
	public void setNachname(String n) {
        if(n.equals(nachName)){}
        else{ // if n != nachname
            revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihrer Vorname wurde von "+nachName+" zu "+n+" geandert"); // add revision
			nachName=n; // change nachname to n
		}
	}

	/**
	 *
	 * @param a
	 */
	public void setAddress(String a) {
		if(a.equals(address)){}
        else{ // if a != address
			revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihre Addresse wurde von "+address+" zu "+a+" geandert"); // add revision
        	address=a; // change address to a
		}
	}

	/**
	 *
	 * @param s
	 */
	public void setGeschlecht(String s) {
		if(geschlecht.equals(s)){} // if s != geschlecht
		else{
			revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihr Geschlecht wurde von "+geschlecht+" zu "+s+" geandert"); // add revision
			geschlecht=s; // set geschelcht to s
		}
	}
	/**
	 *
	 * @param p
	 */
	public void setPasswort(String p){
		if(passwort.equals(p)){}// if p != passwort
		else{
			revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihr Passwort wurde von "+passwort+" zu "+p+" geandert"); // add revision
			passwort=p;// set passwort to p
		}
	}
	/**
	 *
	 * @param b
	 */
	public void setBirth(String b) {
        if(gesburtsDatum.equals(b)){}
        else{// if b != gesburtDatum
        	revision.add(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+" Ihr Gesburtsdatum wurde von "+gesburtsDatum+" zu "+b+" geandert"); // add revision
            gesburtsDatum=b;// set gesburtsDatum to b
		}
	}

	/**
	 *
	 * @param b
	 */
	public void setNeuDaten(boolean b){isNeuDaten=b;}// set if new Data added

	/**
	 *
	 * @return
	 */
	public boolean isNeuDaten(){return isNeuDaten;}

	/**
	 *
	 */
	public void updateDaten(){
		int count=0; // check if all ueberweisung was confirmed
		for(int i=0; i< ueberweisungsList.size();i++){
			if(!ueberweisungsList.get(i).isDatenStimmZu()){
                count++;
			}
		}
		isNeuDaten=(count!=0); // if there is ueberweisung that wasnt confirmed -> isNeuDaten = true
	}

	/**
	 *
	 * @param u
	 */
    public void addUeberweisungsList(Ueberweisung u){
		nameAltArzt= behandelnderArzt.getName();// get name from arzt that make the ueberweisung
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		String s =formattedDateTime+" Sie wurden von Arzt(in) "+nameAltArzt+" überwiesen"; // add revision with time and name of arzt
		revision.add(s);
		setNeuDaten(true); // set new data
		ueberweisungsList.add(u);// add to ueberweisunglist
	}

	/**
	 *
	 * @return
	 */
    public ArrayList<Ueberweisung> getUeberweisungsList(){return ueberweisungsList;}

	/**
	 *
	 * @return
	 */
	public ArrayList<String> getRevision() {
		return revision;
	}

	/**
	 *
	 * @param u
	 */
	public void addUntersuchungsList(Untersuchungsbericht u) {
        untersuchungsberichtsList.add(u);
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Untersuchungsbericht> getUntersuchungList() {
		return untersuchungsberichtsList;
	}

	/**
	 *
	 * @param artzt
	 */
	public void behandeldenArztAendern(Arzt artzt) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = LocalDateTime.now().format(formatter);
		String s =formattedDateTime+" Arzt(in) "+ artzt.getName()+" ist nun ihr(e) Hausarzt(in)";// set new hausarzt, add revision
		revision.add(s);
		this.behandelnderArzt=artzt;
	}

	/**
	 *
	 * @return
	 */
	public Arzt getBehandelnderArzt(){return behandelnderArzt;}

	/**
	 *
	 * @return
	 */
    public String getPasswort(){return passwort;}

	/**
	 *
	 * @return
	 */
	public String getName() {
		return vorName+" "+nachName;
	}

	/**
	 *
	 * @return
	 */
	public String getVorname(){return vorName;}

	/**
	 *
	 * @return
	 */
	public String getNachname(){return nachName;}

	/**
	 *
	 * @return
	 */
	public String getAddress(){return address;}

	/**
	 *
	 * @return
	 */
	public String getGeschlecht(){return geschlecht;}

	/**
	 * Equals boolean.
	 *
	 * @param o the patient
	 * @return the boolean
	 */
	@Override
	public boolean equals(Object o) { // check if 2 patient are the same
		Patient patient = (Patient)o;
		return(patient.getNum().equals(this.getNum())&&patient.getVorname().equals(this.getVorname())&&patient.getNachname().equals(this.getNachname())&&patient.getAddress().equals(this.getAddress())&&patient.getGeschlecht().equals(this.getGeschlecht())&&patient.getGesburtsDatum().equals(this.getGesburtsDatum())&&patient.getPasswort().equals(this.getPasswort())&&patient.getBehandelnderArzt().equals(this.getBehandelnderArzt()));
	}

}
