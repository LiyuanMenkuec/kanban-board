package view.leftColumn;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

import model.Project;
import view.MainWindow;
import view.auis.CompanyAUI;

public class ViewProjectListControllerdd extends GridPane implements CompanyAUI,Initializable{
	private MainWindow parent;
	
	@FXML 
	TreeView<CompanyProjectWrapper> treeViewProjectList;
	
	@FXML
	Button btnProjectListAddProject;
	
	public ViewProjectListControllerdd(MainWindow parent) {
		this.parent=parent;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewProjectList.fxml"));
		loader.setRoot(this);
		loader.setController(this);
		try {
			loader.load();
		} catch (IOException ioExc) {
			ioExc.printStackTrace();
		}
		
	}
	
	@FXML
	private void selectProject(MouseEvent event) {
		TreeItem<CompanyProjectWrapper> item = treeViewProjectList.getSelectionModel().getSelectedItem();

		if (item != null) {
			switch(item.getValue().getType()) {
				case PROJECT:
					parent.getCurrentSelected().setCurrentTask(null);
					parent.getCurrentSelected().setCurrentWorker(null);
					parent.getCurrentSelected().setCurrentComment(null);
					parent.getCurrentSelected().setCurrentProject(item.getValue().getProject());
					parent.showMainKanBanBoard();
					break;
				case COMPANY:
					parent.getCurrentSelected().setCurrentTask(null);
					parent.getCurrentSelected().setCurrentWorker(null);
					parent.getCurrentSelected().setCurrentComment(null);
					parent.getCurrentSelected().setCurrentProject(null);
					parent.getCurrentSelected().setCurrentCompany(item.getValue().getCompany());
					parent.expandCompanyDetails();
					parent.showMainGlobalWorkers();
					break;
				default:
					break;
			}
		}
	}
	
	@FXML
	private void addProject(ActionEvent event) {
		Project newProject = new Project();
		parent.getCurrentSelected().setCurrentProject(newProject);
	}

	@Override
	public void refreshProjectList() {
		refresh();
	}

	@Override
	public void refreshWorkerList() {
		
	}

	@Override
	public void showError(String error) {
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		treeViewProjectList.setCellFactory(param -> new TreeCell<CompanyProjectWrapper>() {
            @Override
            protected void updateItem(CompanyProjectWrapper item, boolean empty) {
            	super.updateItem(item, empty);
                setText(null);
                if(item!=null){
                    switch(item.getType()) {
                    case PROJECT:
                    	setText(item.getProject().getName());
                    	break;
                    case COMPANY:
                    	setText(item.getCompany().getName());
                    	break;
                    case TEXT:
                    	setText(item.getText());
                    	break;
                    }
                }
            }
        });
		
	}
	public void refresh() {
		CompanyProjectWrapper company=new CompanyProjectWrapper();
		ArrayList<Project> activeProjectsList=parent.getCurrentMainController().getCompanyController().getActiveProjects();
		ArrayList<Project> archivedProjectsList=parent.getCurrentMainController().getCompanyController().getArchivedProjects();

		company.setCompany(parent.getCurrentMainController().getCompany());
		company.setType(CompanyProjectWrapper.CompanyProjectWrapperType.COMPANY);
		treeViewProjectList.setRoot(new CompanyProjectTreeItem(company));
		treeViewProjectList.getRoot().setExpanded(true);
		
		CompanyProjectWrapper activeProjects=new CompanyProjectWrapper();
		activeProjects.setType(CompanyProjectWrapper.CompanyProjectWrapperType.TEXT);
		activeProjects.setText("Aktive Projekte");
		CompanyProjectTreeItem activeProjectsTrIt=new CompanyProjectTreeItem(activeProjects);
		activeProjectsTrIt.setExpanded(true);
		activeProjectsList.forEach(item -> {
			CompanyProjectWrapper wrapper=new CompanyProjectWrapper();
			wrapper.setType(CompanyProjectWrapper.CompanyProjectWrapperType.PROJECT);
			wrapper.setProject(item);
			activeProjectsTrIt.getChildren().add(new CompanyProjectTreeItem(wrapper));
		});
		treeViewProjectList.getRoot().getChildren().add(activeProjectsTrIt);
		
		CompanyProjectWrapper archivedProjects=new CompanyProjectWrapper();
		archivedProjects.setType(CompanyProjectWrapper.CompanyProjectWrapperType.TEXT);
		archivedProjects.setText("Archivierte Projekte");
		CompanyProjectTreeItem archivedProjectsTrIt=new CompanyProjectTreeItem(archivedProjects);
		archivedProjectsTrIt.setExpanded(true);
		archivedProjectsList.forEach(item -> {
			CompanyProjectWrapper wrapper=new CompanyProjectWrapper();
			wrapper.setType(CompanyProjectWrapper.CompanyProjectWrapperType.PROJECT);
			wrapper.setProject(item);
			archivedProjectsTrIt.getChildren().add(new CompanyProjectTreeItem(wrapper));
		});
		treeViewProjectList.getRoot().getChildren().add(archivedProjectsTrIt);
	}
}
