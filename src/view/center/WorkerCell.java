package view.center;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import model.Worker;

public final class WorkerCell extends ListCell<model.Worker> {
    protected void updateItem(Worker worker, boolean empty) {
        super.updateItem(worker, empty);
        if (empty || worker == null) {
            setText(null);
            setGraphic(null);
        } else {
        	ImageView imageView = new ImageView();
            imageView.setImage(worker.getPicture());

            imageView.setFitWidth(40);
            imageView.setFitHeight(40);

            setGraphic(imageView);
            setText(worker.getFirstname() + " " + worker.getLastname());
        }
    }
}