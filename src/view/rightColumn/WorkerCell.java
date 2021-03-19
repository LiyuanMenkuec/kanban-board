package view.rightColumn;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import model.Worker;
/**
 * Diese Klasse implementiert die ListCell um die Arbeiter in einer
 * ListView besser darstellen zu können und direkt ein Objekt vom
 * Typ Worker zu erhalten
 * @author Gero Grühn
 *
 */
public final class WorkerCell extends ListCell<model.Worker> {
	/**
	 * Überschriebene Methode
	 */
    protected void updateItem(Worker item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null && !empty) {
            ImageView imageView = new ImageView();
            imageView.setImage(item.getPicture());

            imageView.setFitWidth(40);
            imageView.setFitHeight(40);

            setGraphic(imageView);
            setText(item.getFirstname() + " " + item.getLastname());
        } else {
            setGraphic(null);
            setText("Nicht zugewiesen");
        }
    }
}