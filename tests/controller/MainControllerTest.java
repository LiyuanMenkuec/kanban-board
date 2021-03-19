package controller;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author Gero Grühn
 *
 */
public class MainControllerTest {
	private MainController mainController;

	/**
	 * Setzt den MainController vor Tests
	 */
	@Before
	public void setUp() {
		MainController.isTest = true;
		
		mainController = new MainController();
	}

	/**
	 * Testet die Getter und Setter auf Exceptions
	 */
	@Test
	public void testMainControllerGettersAndSetters() {
		this.mainController.getCompany().getName();

		this.mainController.setCommentController(this.mainController.getCommentController());
		this.mainController.setCompanyController(this.mainController.getCompanyController());
		this.mainController.setiOController(this.mainController.getIOController());
		this.mainController.setNotificationAUI(this.mainController.getNotificationAUI());
		this.mainController.setProjectController(this.mainController.getProjectController());
		this.mainController.setStatisticsController(this.mainController.getStatisticsController());
		this.mainController.setTaskController(this.mainController.getTaskController());
		this.mainController.setTeamController(this.mainController.getTeamController());
		this.mainController.setWorkerController(this.mainController.getWorkerController());
		try {
			this.mainController.setCompany(null);
		} catch (NullPointerException npException) {

		}
		this.mainController.setNotificationAUI(null);
		try {
			this.mainController.showNotification("wird eh nicht angezeigt", true);
		} catch (NullPointerException npException) {

		}

	}

}
