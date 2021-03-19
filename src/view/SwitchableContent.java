package view;

import controller.MainController;
import model.Comment;
import model.Company;
import model.Project;
import model.Task;
import model.Worker;

public interface SwitchableContent {
	public class CurrentSelected {
		Company currentCompany;
		Project currentProject;
		Task currentTask;
		Worker currentWorker;
		Comment currentComment;
		
		SwitchableContent parent;
		
		CurrentSelected(SwitchableContent parent){
			this.parent=parent;
			currentCompany=null;
			currentProject=null;
			currentTask=null;
			currentWorker=null;
			currentComment=null;
			
		}
		public Company getCurrentCompany() {return currentCompany;}
		public Project getCurrentProject() {return currentProject;}
		public Task getCurrentTask() {return currentTask;}
		public Worker getCurrentWorker() {return currentWorker;}
		public Comment getCurrentComment() {return currentComment;}
		
		public void setCurrentCompany(Company currentCompany) {this.currentCompany=currentCompany;parent.loadContent(this);}
		public void setCurrentProject(Project currentProject) {this.currentProject=currentProject;parent.loadContent(this);}
		public void setCurrentTask(Task currentTask) {this.currentTask=currentTask;parent.loadContent(this);}
		public void setCurrentWorker(Worker currentWorker) {this.currentWorker=currentWorker;parent.loadContent(this);}
		public void setCurrentComment(Comment currentComment) {this.currentComment=currentComment;parent.loadContent(this);}
	}
	void loadContent(CurrentSelected selected);
	void showMainKanBanBoard();
	void showMainStatistics();
	void showMainGlobalWorkers();
	void showMainEmpty();
	void showMainTeam();
	void showMainComment(Comment comment);
	void expandCompanyDetails();
	CurrentSelected getCurrentSelected();
	MainController getCurrentMainController();
	
	
}
