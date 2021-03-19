package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Company;
import model.Project;
import model.TestDataFactory;

import static org.hamcrest.CoreMatchers.*;

/**
 * Class IOControllerTest
 */
public class IOControllerTest {

    private MainController mainController;
    private IOController ioController;

    /**
     * Setzen der Controller und Testdaten vor jedem Test
     * 
     * @throws Exception die geworfen wird
     */
    @Before
    public void setUp() throws Exception {
        MainController.isTest = true;
        
        mainController = new MainController();
        ioController = new IOController(mainController);

        TestDataFactory factory = new TestDataFactory();

        mainController.setCompany(factory.getTestCompany());
    }

    /**
     * Testet das Speichert und lädt die gesamte Application. Überprüft daraufhin,
     * dass der Hash der Daten gleich ist.
     */
    @Test
    public void testCanSaveAndLoadCompany() {
        try {
            File file = File.createTempFile("java_", ".save");

            ioController.save(file.getAbsolutePath());

            Company company = mainController.getCompany();

            mainController.setCompany(new Company());

            ioController.load(file.getAbsolutePath());

            /*
             * Process p = Runtime .getRuntime()
             * .exec("rundll32 url.dll,FileProtocolHandler c:\\Java-Interview.pdf");
             * p.waitFor();
             */

            assertThat(mainController.getCompany().hashCode(), is(company.hashCode()));

        } catch (IOException exception) {
            fail("Threw IOException");
        }
    }

    /**
     * Testen ob die Datei nicht gelöscht wird
     * 
     * @throws IOException die geworfen wird
     */
    @Test
    public void testCreateReport() throws IOException {
        File file = File.createTempFile("java_", ".pdf");

        ArrayList<Project> projects = mainController.getCompany().getProjects();

        ioController.createReport(projects, file.getAbsolutePath());

        assertTrue("Die PDF Datei muss vorhanden sein.", file.exists());

    }

}