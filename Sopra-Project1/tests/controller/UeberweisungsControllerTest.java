package controller;

import controller.EPAController;
import controller.UeberweisungsController;
import model.EPA;
import model.Ueberweisung;
import model.Untersuchungsbericht;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import static org.junit.Assert.*;

/**
 *Zur überpreufung der Klasse UeberweisungsConteoller.
 */

public class UeberweisungsControllerTest {
    private EPA epa ;
    private Ueberweisung ueberweisung ;
    private UeberweisungsController ueberweisungsController;
    private ArrayList<Untersuchungsbericht> untersuchungslist;



    /**
     *Setup ,was in den Test gebraucht wird.
     */

    @Before
    public void setUp() throws Exception {
        EPAController epaController = new EPAController();
        EPA epa= epaController.getEPA();
        UeberweisungsController ueberweisungsController = epaController.getUeberweisungsController();
        ArrayList<Untersuchungsbericht> untersuchungslist  = new ArrayList<Untersuchungsbericht>();
        Ueberweisung ueberweisung = new Ueberweisung();
        ueberweisung.setAltArztNummer("1234").setPatientNummer("1234").setNeuArztNummer("5678").setUntersuchungberichtInit(untersuchungslist).setAuftrag("test").setDate("1.1.2022");
    }
    /**
     *Test , ob Ueberweisung richtig erstellt wird .
     */

    @Test
    public void createUeberweisung() {
        Ueberweisung ueberweisung1 = ueberweisungsController.createUeberweisung("1234","1234","5678","test",untersuchungslist,"1.1.2022");
        assertEquals(ueberweisung.getDate(),ueberweisung1.getDate());
        assertEquals(ueberweisung.getNeuarztnummer(),ueberweisung1.getNeuarztnummer());
        assertEquals(ueberweisung.getAltArztnummer(),ueberweisung1.getAltArztnummer());
        assertEquals(ueberweisung.getPatientnummer(),ueberweisung1.getPatientnummer());
        assertEquals(ueberweisung.getAuftrag(),ueberweisung1.getAuftrag());
        assertEquals(ueberweisung.getUntersuchungsbericht(),ueberweisung1.getUntersuchungsbericht());
    }

    /**
     *Test , ob der Fehler mit falschen Parameter ausgegeben wird.
     */
    @Test
    public void createUeberweisung1() {
        Ueberweisung ueberweisung2 = ueberweisungsController.createUeberweisung("1234","" ,"5678","test",untersuchungslist,"1.1.2022");
        assertEquals(ueberweisung.getDate(),ueberweisung2.getDate());
        assertEquals(ueberweisung.getNeuarztnummer(),ueberweisung2.getNeuarztnummer());
        assertEquals(ueberweisung.getAltArztnummer(),ueberweisung2.getAltArztnummer());
        assertEquals(ueberweisung.getPatientnummer(),ueberweisung2.getPatientnummer());
        assertEquals(ueberweisung.getAuftrag(),ueberweisung2.getAuftrag());
        assertEquals(ueberweisung.getUntersuchungsbericht(),ueberweisung2.getUntersuchungsbericht());
    }

    /**
     *Test , ob die Ueberwisung richtig hinzugefeugt wird.
     */

    @Test
    public void addUeberweisung() throws IOException {
        Ueberweisung ueberweisung2 = ueberweisung.setAltArztNummer("1233").setPatientNummer("1224").setNeuArztNummer("5468").setUntersuchungberichtInit(untersuchungslist).setAuftrag("test").setDate("20.1.2022");
        ueberweisungsController.addUeberweisung(ueberweisung);
        assertEquals(epa.getPatient("1234").getUeberweisungsList().size(),3 );
    }


}