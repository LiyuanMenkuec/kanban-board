package view.center;

import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import java.time.Duration;
import model.Task;

public final class TaskCell extends ListCell<Task>{ 
	Duration timeLeft;
	protected void updateItem(Task item, boolean empty){
        super.updateItem(item, empty);
        if(item!=null){
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
        }else {
        	setText(null);
        }
    }
}