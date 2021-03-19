package view.center;

import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.time.Duration;
import model.Task;
import model.Worker;
import view.MainWindow;

public final class TaskWorkerCell extends ListCell<Task>{ 
	MainWindow parent;
	Worker currentWorker;
	ImageView workerImg;
	Duration timeLeft;
	public TaskWorkerCell(MainWindow parent) {
		this.parent=parent;
	}
	protected void updateItem(Task item, boolean empty){
        super.updateItem(item, empty);
        setGraphic(null);
        setText(null);
        
        if(item!=null){
        	Worker currentWorker=item.getCurrentWorker();
        	if(currentWorker!=null) {
        		Button btnWorker=new Button();
        		btnWorker.setPrefHeight(40);
        		btnWorker.setPrefWidth(40);
        		
        		workerImg = new ImageView();
        		workerImg.setImage(currentWorker.getPicture());
            	workerImg.setFitWidth(30);
            	workerImg.setFitHeight(30);
            	btnWorker.setGraphic(workerImg);
                setGraphic(btnWorker);
                btnWorker.setOnMouseClicked(event -> {
                	parent.getCurrentSelected().setCurrentTask(null);
                	parent.getCurrentSelected().setCurrentComment(null);
                    parent.getCurrentSelected().setCurrentWorker(currentWorker);
                });
                Tooltip toolTip=new Tooltip();
                toolTip.setText("Mitarbeiter: "+currentWorker.getFirstname()+", "+currentWorker.getLastname());
                this.setTooltip(toolTip);
        	}
        	
        	timeLeft=item.getLeftTime();
        	if(timeLeft!=null) {
        		if(timeLeft.isNegative()) {
        			this.textFillProperty().set(Color.DARKRED);
        		}else {
        			if(timeLeft.toHours()>24) {
        				this.textFillProperty().set(Color.GREEN);
        			}
                	else {
                		this.textFillProperty().set(Color.RED);
                	}
        		}
        	}else {
        		this.textFillProperty().set(Color.BLACK);
        	}
        	
            setText(item.getTitle());
        }
	}
}