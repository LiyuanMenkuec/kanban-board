package view.leftColumn;

import model.Company;
import model.Project;

public class CompanyProjectWrapper{
	public static enum CompanyProjectWrapperType {
		COMPANY,PROJECT,TEXT;
	}
	private Company company;
	private Project project;
	private String  text;
	private CompanyProjectWrapperType type;
	
	public void setType(CompanyProjectWrapperType type) {
		this.type=type;
	}
	public CompanyProjectWrapperType getType() {
		return type;
	}
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	

}
