package controller;

import model.Company;
import view.NotificationAUI;

/**
 * 
 * @author Gruppe08
 *
 */
public class MainController {
	private NotificationAUI notificationAUI;

	private Company company;

	private CompanyController companyController;

	private WorkerController workerController;

	private TaskController taskController;

	private StatisticsController statisticsController;

	private CommentController commentController;

	private IOController iOController;

	private ProjectController projectController;

	private TeamController teamController;

	public static boolean isTest = false;

	/**
	 * 
	 */
	public MainController() {
		notificationAUI = (msg, isError) -> {
			if (isError) {
				System.err.println("Error: " + msg);
			} else {
				System.out.println("Note: " + msg); // Damit notificationAUI nie null ist
			}
		};
		company = new Company();
		companyController = new CompanyController(this);
		workerController = new WorkerController(this);
		taskController = new TaskController(this);
		statisticsController = new StatisticsController();
		commentController = new CommentController(this);
		iOController = new IOController(this);
		projectController = new ProjectController(this);
		teamController = new TeamController(this);
	}
	
	/**
	 * Zeigt eine Nachricht über AUI
	 * @param msg
	 * @param isError
	 */
	public void showNotification(String msg, boolean isError) {
		if (notificationAUI != null) {
			notificationAUI.showNotification(msg, isError);
		}
	}

	/**
	 * @param iOController the iOController to set
	 */
	public void setiOController(IOController iOController) {
		this.iOController = iOController;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		if(company!=null) {
			this.company = company;
		}else {
			throw new NullPointerException("company never ever can be null!");
		}
		
	}

	/**
	 * @param companyController the companyController to set
	 */
	public void setCompanyController(CompanyController companyController) {
		this.companyController = companyController;
	}

	/**
	 * @param workerController the workerController to set
	 */
	public void setWorkerController(WorkerController workerController) {
		this.workerController = workerController;
	}

	/**
	 * @param taskController the taskController to set
	 */
	public void setTaskController(TaskController taskController) {
		this.taskController = taskController;
	}

	/**
	 * @param statisticsController the statisticsController to set
	 */
	public void setStatisticsController(StatisticsController statisticsController) {
		this.statisticsController = statisticsController;
	}

	/**
	 * @param commentController the commentController to set
	 */
	public void setCommentController(CommentController commentController) {
		this.commentController = commentController;
	}

	/**
	 * @param projectController the projectController to set
	 */
	public void setProjectController(ProjectController projectController) {
		this.projectController = projectController;
	}

	/**
	 * @param teamController the teamController to set
	 */
	public void setTeamController(TeamController teamController) {
		this.teamController = teamController;
	}

	/**
	 * 
	 * @return
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * 
	 * @return
	 */
	public ProjectController getProjectController() {
		return projectController;
	}

	/**
	 * 
	 * @return
	 */
	public TaskController getTaskController() {
		return taskController;
	}

	/**
	 * 
	 * @return
	 */
	public WorkerController getWorkerController() {
		return workerController;
	}

	/**
	 * 
	 * @return
	 */
	public StatisticsController getStatisticsController() {
		return statisticsController;
	}

	/**
	 * 
	 * @return
	 */
	public IOController getIOController() {
		return iOController;
	}

	/**
	 * 
	 * @return
	 */
	public CommentController getCommentController() {
		return commentController;
	}

	/**
	 * 
	 * @return
	 */
	public CompanyController getCompanyController() {
		return companyController;
	}

	/**
	 * 
	 * @return
	 */
	public TeamController getTeamController() {
		return teamController;
	}

	/**
	 * @return the notificationAUI
	 */
	public NotificationAUI getNotificationAUI() {
		return notificationAUI;
	}

	/**
	 * @param notificationAUI the notificationAUI to set
	 */
	public void setNotificationAUI(NotificationAUI notificationAUI) {
		this.notificationAUI = notificationAUI;
	}

}
