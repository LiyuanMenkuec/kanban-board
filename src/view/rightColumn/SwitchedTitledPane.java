package view.rightColumn;

import javafx.scene.control.TitledPane;

public abstract class SwitchedTitledPane extends TitledPane implements SwitchedContent{
	private boolean visible;
	private boolean isExpanded=false;
	public boolean isContentVisible() {
		return visible;
	}
	protected void setContentVisible(boolean visible) {
		this.visible=visible;
	}
	public boolean isExpandedDetails() {
		return isExpanded;
	}
	public void setExpandDetails(boolean isExpanded) {
		this.isExpanded=isExpanded;
	}
}
