package controller;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.Project;
import model.TestDataFactory;

/**
 * Testklasse um Methoden des CompanyController zu überprüfen.
 * 
 * @author Anne Stockey
 *
 */
public class CompanyControllerTest {

	private MainController mainController;
	private CompanyController companyController;

	/**
	 * Test-Company um einfacher Tests durchzuführen
	 */
	private TestDataFactory factory;

	/**
	 * setUp-Methode um Test zu beginnen
	 * 
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		
		mainController = new MainController();
		companyController = new CompanyController(mainController);

		factory = new TestDataFactory();
		mainController.setCompany(factory.getTestCompanyWithMoreProjects());
	}

	/**
	 * Methode um zu überprüfen, dass in der Liste die getArchivedProjects()
	 * zurückgibt nur archivierte Projekte enthalten sind.
	 */
	@Test
	public void testGetArchivedProjects() {
		ArrayList<Project> archivedProjects = companyController.getArchivedProjects();
		boolean justArchivedProjects = true;
		for (Project project : archivedProjects) {
			if (project.isActive()) {
				justArchivedProjects = false;
			}
		}
		assertTrue("In der Liste dürfen keine aktiven Projekte sein", justArchivedProjects);
	}

	/**
	 * Methode um zu überprüfen, dass in der Liste die getActiveProjects()
	 * zurückgibt nur aktive Projekte enthalten sind.
	 */
	@Test
	public void testGetActiveProjects() {
		ArrayList<Project> activeProjects = companyController.getActiveProjects();
		boolean justActiveProjects = true;
		for (Project project : activeProjects) {
			if (!project.isActive()) {
				justActiveProjects = false;
			}
		}
		assertTrue("In der Liste dürfen keine aktiven Projekte sein", justActiveProjects);
	}

}