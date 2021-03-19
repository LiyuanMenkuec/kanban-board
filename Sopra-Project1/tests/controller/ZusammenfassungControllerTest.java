
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


public class ZusammenfassungControllerTest {
    EPAController epaController=new EPAController();

    @Before
    public void setUp() throws Exception {
        epaController.setCurrLoggedIn("1234");
    }

    @Test
    public void createzusammenfassungEinArzte() {
        ArrayList<Untersuchungsbericht> untersuchungsberichts=epaController.getZusammenfassungsController().createZusammenfassungEinArzt("1234","leer");
        assertEquals(untersuchungsberichts.size(),1);
    }

    @Test
    public void createzusammenfassungAllerArzt() {
        ArrayList<Untersuchungsbericht> untersuchungsberichts1=epaController.getZusammenfassungsController().createZusammenfassungAlleAerzte("1234","leer");
        assertEquals(untersuchungsberichts1.size(),3);
    }
}

