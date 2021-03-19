package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The type Ueberweisung.
 */
public class Ueberweisung implements Serializable {

	private String patientnummer;

	private String neuarztnummer;

	private String altArztnummer;

	private ArrayList<Untersuchungsbericht> untersuchungsbericht;

	private String auftrag;

	private String date;

	private boolean datenStimmZu=false;

	/**
	 * Instantiates a new Ueberweisung.
	 *
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 * @param
	 */
	public Ueberweisung setPatientNummer(String patientnummer){this.patientnummer=patientnummer;return this;}

	public Ueberweisung setNeuArztNummer(String neuarztnummer){this.neuarztnummer=neuarztnummer;return this;}

	public Ueberweisung setAltArztNummer(String altArztnummer){this.altArztnummer=altArztnummer;return this;}

	public Ueberweisung setUntersuchungberichtInit( ArrayList<Untersuchungsbericht> untersuchungsList){this.untersuchungsbericht=untersuchungsList;return this;}

	public Ueberweisung setAuftrag(String auftrag){this.auftrag=auftrag;return this;}

	public Ueberweisung setDate(String date){this.date=date;return this;}

	/**
	 * Gets neuarztnummer.
	 *
	 * @return the neuarztnummer
	 */
	public String getNeuarztnummer() {
		return neuarztnummer;
	}

	/**
	 * Gets alt arztnummer.
	 *
	 * @return the alt arztnummer
	 */
	public String getAltArztnummer() {
		return altArztnummer;
	}

	/**
	 * Gets auftrag.
	 *
	 * @return the auftrag
	 */
	public String getAuftrag() {
		return auftrag;
	}

	/**
	 * Gets date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Is daten stimm zu boolean.
	 *
	 * @return the boolean
	 */
	public boolean isDatenStimmZu() {
		return datenStimmZu;
	}

	/**
	 * Set daten stimm zu.
	 *
	 * @param b the b
	 */
	public void setDatenStimmZu(boolean b){this.datenStimmZu=b;}

	/**
	 * Gets patientnummer.
	 *
	 * @return the patientnummer
	 */
	public String getPatientnummer() {
		return patientnummer;
	}

	/**
	 * Gets untersuchungsbericht.
	 *
	 * @return the untersuchungsbericht
	 */
	public ArrayList<Untersuchungsbericht> getUntersuchungsbericht() {
		return untersuchungsbericht;
	}

	/**
	 * Sets untersuchungsbericht.
	 *
	 * @param untersuchungsbericht the untersuchungsbericht
	 */
	public void setUntersuchungsbericht(ArrayList<Untersuchungsbericht> untersuchungsbericht) {
		this.untersuchungsbericht = untersuchungsbericht;
	}
}
