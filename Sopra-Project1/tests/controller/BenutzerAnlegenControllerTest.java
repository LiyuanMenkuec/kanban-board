package controller;

import model.Arzt;
import model.EPA;
import model.Patient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
/**
 *Zur überprüfung der Klasse BenutzerAnlegenController.
 */
public class BenutzerAnlegenControllerTest {

    private EPA epa;

    private BenutzerAnlegenController benutzerAnlegenController;

    private Arzt testArzt;

    private Patient testPatient;

    private String vorname;

    private String nachname;

    private String fach;

    private String num;

    private String passwort;

    private String tel;

    private String adresse;

    private String birth;

    /**
     *Setup für gebraucht in den Tests.
     */
    @Before
    public void setUp(){
        vorname="Mark";
        nachname = "Ammermüller";
        fach = "Psychater";
        num = "1248";
        passwort = "1234";
        tel = "0208 123456789";
        adresse = "Linguster weg";
        birth = "20.05.01";
        EPAController epaController = new EPAController();
        epa = epaController.getEPA();
        testArzt = new Arzt(vorname, nachname, fach, num,passwort, tel);
        testPatient = new Patient(num,vorname,nachname,adresse,"m",birth,passwort);
        testPatient.behandeldenArztAendern(testArzt);
        benutzerAnlegenController= epaController.getAktAnlegenController();
    }


    /**
     *Test, ob Arzt objekt richtig angelegt wird.
     */
    @Test
    public void arztAnlegen() throws IOException {
        //test2:
        benutzerAnlegenController.arztAnlegen(vorname, nachname, fach, num, passwort, tel);
        assert(epa.getArzt(num).equals(testArzt));
    }

    /**
     *Test, ob Patient objekt richtig angelegt wird.
     */
    @Test
    public void patientAnlegen() throws IOException {
        System.out.println(testPatient.getBehandelnderArzt());
        //test2:
        benutzerAnlegenController.patientAnlegen(num, vorname, nachname, adresse, false, birth, passwort,"1248");
        assert(epa.getPatient(num).equals(testPatient));
    }

    @After
    public void tearDown(){
        epa.removeArzt(num);
        epa.removePatient(num);

    }
}