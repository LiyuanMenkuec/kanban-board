package view.auis;

public interface CompanyAUI {

	public void refreshProjectList();

	public abstract void refreshWorkerList();

	public abstract void showError(String error);

}
