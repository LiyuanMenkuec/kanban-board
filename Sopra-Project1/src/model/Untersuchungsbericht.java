package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The type Untersuchungsbericht.
 */
public class Untersuchungsbericht implements Serializable {

	private String patientVersicherungnum;

	private LocalDateTime datum;

	private List<String> notes;

	private String iCD;

	private List<String> med;

	private String diagnose;

	private String behandlung;

	private String behandenderArzt;

	private boolean weiterSchicken;

	/**
	 * Sets patient versicherungnum.
	 *
	 * @param patientVersicherungnum the patient versicherungnum
	 */
	public void setPatientVersicherungnum(String patientVersicherungnum) {
		this.patientVersicherungnum = patientVersicherungnum;
	}

	/**
	 * Sets datum.
	 *
	 * @param datum the datum
	 */
	public void setDatum(LocalDateTime datum) {
		this.datum = datum;
	}

	/**
	 * Sets notes.
	 *
	 * @param notes the notes
	 */
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}

	/**
	 * Sets cd.
	 *
	 * @param iCD the cd
	 */
	public void setiCD(String iCD) {
		this.iCD = iCD;
	}

	/**
	 * Sets med.
	 *
	 * @param med the med
	 */
	public void setMed(List<String> med) {
		this.med = med;
	}

	/**
	 * Sets diagnose.
	 *
	 * @param diagnose the diagnose
	 */
	public void setDiagnose(String diagnose) {
		this.diagnose = diagnose;
	}

	/**
	 * Sets behandlung.
	 *
	 * @param behandlung the behandlung
	 */
	public void setBehandlung(String behandlung) {
		this.behandlung = behandlung;
	}

	/**
	 * Sets behandender arzt.
	 *
	 * @param behandenderArzt the behandender arzt
	 */
	public void setBehandenderArzt(String behandenderArzt) {
		this.behandenderArzt = behandenderArzt;
	}

	/**
	 * Instantiates a new Untersuchungsbericht.
	 *
	 * @param versicherungsnummer the versicherungsnummer
	 * @param behandelnderArzt    the behandelnder arzt
	 * @param datum               the datum
	 * @param iCD                 the cd
	 * @param med                 the med
	 * @param diagnose            the diagnose
	 * @param behandlung          the behandlung
	 * @param notes               the notes
	 */
	public Untersuchungsbericht(String versicherungsnummer, String behandelnderArzt, LocalDateTime datum, String iCD, List<String> med, String diagnose, String behandlung, List<String> notes) {
		patientVersicherungnum = versicherungsnummer;
		this.behandenderArzt = behandelnderArzt;
		this.datum = datum;
		this.iCD = iCD;
		this.med = med;
		this.diagnose = diagnose;
		this.behandlung = behandlung;
		this.notes = notes;
	}

	/**
	 * Gets patient num.
	 *
	 * @return the patient num
	 */
	public String getPatientNum() {
		return patientVersicherungnum;
	}

	/**
	 * Sets info.
	 *
	 * @param diagnose   the diagnose
	 * @param Med        the med
	 * @param Behandlung the behandlung
	 * @param icd        the icd
	 */
	public void setInfo(String diagnose, List<String> Med, String Behandlung, String icd) {

	}

	/**
	 * todo: name
	 * @return
	 */
	public String toString(){
		String ausgabe="";
		ausgabe+="Name: "+""+"/n";
		ausgabe+="Datum: "+datum  +"/n";
		ausgabe+="Behandelnder Arzt: "+""+"/n";
		ausgabe+="ICD: "+iCD+"/n";
		ausgabe+="Diagnose: "+diagnose+"/n";
		ausgabe+="verabreichte Medikamente:/n";
		for (String x:med) {
			ausgabe+="-"+x+"/n";
		}
		ausgabe+="/n";
		ausgabe+="Behandlung: "+behandlung+"/n";
		ausgabe+="Notizen:/n";
		for (String y:notes) {
			ausgabe+="-"+y+"/n";
		}
		return ausgabe;
	}

	/**
	 * Gets patient versicherungnum.
	 *
	 * @return the patient versicherungnum
	 */
	public String getPatientVersicherungnum() {
		return patientVersicherungnum;
	}

	/**
	 * Gets icd.
	 *
	 * @return the icd
	 */
	public String getICD() {
		return iCD;
	}

	/**
	 * Gets datum.
	 *
	 * @return the datum
	 */
	public LocalDateTime getDatum() {
		return datum;
	}

	/**
	 * Gets notes.
	 *
	 * @return the notes
	 */
	public List<String> getNotes() {
		return notes;
	}

	/**
	 * Gets med.
	 *
	 * @return the med
	 */
	public List<String> getMed() {
		return med;
	}

	/**
	 * Gets diagnose.
	 *
	 * @return the diagnose
	 */
	public String getDiagnose() {
		return diagnose;
	}

	/**
	 * Gets behandlung.
	 *
	 * @return the behandlung
	 */
	public String getBehandlung() {
		return behandlung;
	}

	/**
	 * Gets behandender arzt.
	 *
	 * @return the behandender arzt
	 */
	public String getBehandenderArzt() {
		return behandenderArzt;
	}

	/**
	 * Is weiter schicken boolean.
	 *
	 * @return the boolean
	 */
	public boolean isWeiterSchicken() {
		return weiterSchicken;
	}

	/**
	 * Sets weiter schicken.
	 *
	 * @param weiterSchicken the weiter schicken
	 */
	public void setWeiterSchicken(boolean weiterSchicken) {
		this.weiterSchicken = weiterSchicken;
	}
}
