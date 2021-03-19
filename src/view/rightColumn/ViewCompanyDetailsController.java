package view.rightColumn;

import java.io.IOException;
import java.util.ArrayList;

import controller.MainController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Company;
import view.MainWindow;
import view.auis.CompanyAUI;

/**
 * @author Abdulrazzak Shaker && Julian Adler
 */
public class ViewCompanyDetailsController extends SwitchedTitledPane{

	@FXML
	private TextField txtCompanyDetailsName;
	
	@FXML
	private Button btnCompanyDetailsSaveChanges;
	
	@FXML
	private Button btnCompanyDetailsQuitChanges;


	private MainWindow parent;
	private MainController mainController;
	private Company currentCompany;

	public ViewCompanyDetailsController(MainWindow parent) {
		this.parent = parent;
		this.mainController = parent.getCurrentMainController();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewCompanyDetails.fxml"));

		try {
			loader.setRoot(this);
			loader.setController(this);

			loader.load();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	@FXML 
	public void companySaveChanges() {
		currentCompany.setName(txtCompanyDetailsName.getText());
		ArrayList<CompanyAUI> compAUI = mainController.getProjectController().getCompanyAUI();
		for(CompanyAUI cAUI:compAUI) {
			cAUI.refreshProjectList();
		}
		update();
	}
	
	@FXML
	public void companyQuitChanges() {
		txtCompanyDetailsName.setText(currentCompany.getName());
		update();
	}
	
	@FXML
	public void onCompanyNameChange(Event event) {
		if(txtCompanyDetailsName.getText().isEmpty()) {
			btnCompanyDetailsSaveChanges.setDisable(true);
		}else {
			btnCompanyDetailsSaveChanges.setDisable(false);
			btnCompanyDetailsQuitChanges.setDisable(false);
		}
		
	}

	public void refresh() {
		currentCompany = parent.getCurrentSelected().getCurrentCompany();
		if (currentCompany != null) {
			txtCompanyDetailsName.setText(currentCompany.getName());
			setContentVisible(true);
			update();
		} else {
			setContentVisible(false);
		}
	}
	
	public void update() {
		btnCompanyDetailsSaveChanges.setDisable(true);
		btnCompanyDetailsQuitChanges.setDisable(true);
	}
}
