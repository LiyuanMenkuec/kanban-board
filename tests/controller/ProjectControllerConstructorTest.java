package controller;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import org.junit.Test;

import controller.MainController;
import controller.ProjectController;

/**
 * Class ProjectControllerConstructorTest
 */
public class ProjectControllerConstructorTest {

	/**
	 * Konstruktortest, wenn null übergeben wird, wird eine NullPointerException
	 * geworfen.
	 * 
	 * @throws Exception wird geworfen {@link ProjectController#ProjectController(MainController)}
	 */
	@Test(expected = NullPointerException.class)
	public void testParameterNull() throws Exception {
		new ProjectController(null);
	}

	/**
	 * Konstruktortest.
	 */
	@Test
	public void test() {
		// Vorbereitung
		MainController.isTest = true;
		
		MainController mainController = new MainController();
		// Test
		ProjectController projectController = new ProjectController(mainController);
		try {
			Field fieldMainController = ProjectController.class.getDeclaredField("mainController");
			fieldMainController.setAccessible(true);
			MainController mainControllerField = (MainController) fieldMainController.get(projectController);
			assertNotNull("Hinzugefügter MainController darf nicht null sein.", mainControllerField);
			assertEquals(mainController, mainControllerField);
		} catch (Exception exception) {
			System.out.println("Diese Exception sollte niemals auftreten.");
			exception.printStackTrace();
		}
	}

}
