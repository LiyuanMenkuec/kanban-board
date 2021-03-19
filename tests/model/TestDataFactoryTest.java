package model;

import org.junit.Before;
import org.junit.Test;

import controller.MainController;

/**
 * Diese Klasse dient hauptsächlich dem Erhöhen der Coverage ;)
 * 
 * @author Gero Grühn
 *
 */
public class TestDataFactoryTest{
	
	private TestDataFactory factory;

	/**
	 * Erstellt eine neue TestDataFactory
	 * 
	 * @throws Exception wird geworfen
	 */
	@Before
	public void setUp() throws Exception {
		MainController.isTest = true;
		factory = new TestDataFactory();
	}

	/**
	 * Ruft alle Methoden der TestDataFactory auf
	 */
	@Test
	public void testConstructor() {
		factory.getTestCompany();
		factory.getTestCompanyWithMoreProjects();
		factory.getTestCompanyWithEvenMoreProjects();
	}
}

