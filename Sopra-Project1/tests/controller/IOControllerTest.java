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
public class IOControllerTest {
    EPAController epaController=new EPAController();

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void save() throws IOException, ClassNotFoundException {
        EPA epa1=epaController.getEPA();
        epaController.getIO().save();
        epaController.getIO().load();
        EPA epa2=epaController.getEPA();
        assertEquals(epa2.getArztList().size(),epa1.getArztList().size());
        assertEquals(epa2.getPatientList().size(),epa1.getPatientList().size());
        // brauch noch methode um instanz in liste auf gleichtheit zu pruefen

    }

    @Test
    public void load() throws IOException, ClassNotFoundException {
        EPA epa1=epaController.getEPA();
        epaController.getIO().load();
        EPA epa2=epaController.getEPA();
        assertEquals(epa2.getArztList().size(),epa1.getArztList().size());
        assertEquals(epa2.getPatientList().size(),epa1.getPatientList().size());
    }
}
