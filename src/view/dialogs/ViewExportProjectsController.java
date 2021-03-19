package view.dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Project;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.IOController;


public class ViewExportProjectsController extends GridPane implements Initializable {

    private List<Project> projectList;
    private IOController iocontroller;
    @FXML
    private ListView<Project> lstSelectProjects;

    public ViewExportProjectsController(List<Project> projects, IOController iocontroller) {
    	// set local variables first
        this.projectList = projects;
        this.iocontroller = iocontroller;
    	
        // this calls initialize
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dialogs/ViewExportProjects.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // setup content of lists etc
        this.refresh();
    }
    
    @FXML
    void onButtonExportClick(ActionEvent event) {
        List<Project> selectedProjects = lstSelectProjects.getSelectionModel().getSelectedItems();
        ArrayList<Project> projects=new ArrayList<Project>();
        projects.addAll(selectedProjects);
        
        FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Bitte Dateinamen und Pfad angeben");
		fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf")
            );
		File file = fileChooser.showSaveDialog(this.getScene().getWindow());

        iocontroller.createReport(projects, file.getAbsolutePath());
        ((Stage) this.getScene().getWindow()).close();
    }

    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // set cell factory for project items
        lstSelectProjects.setCellFactory(cell -> new ListCell<>() {
            public void updateItem(Project project, boolean empty) {
            	super.updateItem(project, empty);
                if (empty || project == null) {
                    setText(null);
                } else {
                    setText(project.getName());
                }
            }
        });
        // allow selection of multiple entries
        lstSelectProjects.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    public void refresh() {
        lstSelectProjects.getItems().clear();
        lstSelectProjects.getItems().addAll(this.projectList);
    }
}
