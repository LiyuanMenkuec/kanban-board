package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.imageio.ImageIO;

import controller.MainController;
import javafx.embed.swing.SwingFXUtils;

/**
 * Diese Klasse dient als WrapperKlasse für ein BufferedImage, damit die Bilder
 * beim serialisieren mit gespeichert werden können.
 * 
 * @author Gero Grühn
 *
 */
class WorkerImage implements Serializable {

    private static final long serialVersionUID = -7227196900881906929L;
    transient BufferedImage image;

    /**
     * Der Konstruktor lädt das default-Bild und speichert es als BufferdImage ab
     */
    public WorkerImage() {
    	super();
        setImage(MainController.isTest == false ? new javafx.scene.image.Image("/resources/person.png") : null);
    }

    /**
     * Wird für das Abspeichern benötigt
     * 
     * @param out
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (image != null) {
            ImageIO.write(image, "png", out); // png is lossless
        }
    }

    /**
     * Wird ebenfalls für das Abspeichern benötigt
     * 
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream inComing) throws IOException, ClassNotFoundException {
    	inComing.defaultReadObject();
        image = ImageIO.read(inComing);
    }

    /**
     * Konvertiert das übergebene JavaFX-Image zu einem BufferedImage und speichert
     * es
     * 
     * @param image Bild das aus der GUI kommt
     */
    public void setImage(javafx.scene.image.Image image) {
        if (image != null) {
            this.image = SwingFXUtils.fromFXImage(image, null);
        } else {
            image = null;
        }
    }

    /**
     * Konvertiert das BufferedImage zu einem JavaFX-Image und gibt dieses zurück
     * 
     * @return JavaFX-Image zum anzeigen in der GUI
     */
    public javafx.scene.image.Image getImage() {
        if (image != null) {
            return SwingFXUtils.toFXImage(image, null);
        } else {
            return null;
        }
    }
}
