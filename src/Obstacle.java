import javafx.scene.image.Image;
import java.util.Random;

/**
 * @author Philip Strömberg
 * Extended class from Entity for meeting cars
 */
public class Obstacle extends Entity {
    private static Random r = new Random();

    public Obstacle(Image img, int width, int height, GameCanvas canvas) {
        super(img, width, height, canvas);
        x = r.nextInt((int) canvas.getWidth());
        y = -height;
        ySpeed = 1 + r.nextInt(4);
    }
}
