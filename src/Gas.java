import javafx.scene.image.Image;
import java.util.Random;

/**
 * @author Philip Strömberg
 * Special class for gas cans on the road
 */
public class Gas extends Entity {
    private static Random r = new Random();

    public Gas(Image img, int width, int height, GameCanvas canvas) {
        super(img, width, height, canvas);
        x = r.nextInt((int) canvas.getWidth());
        y = -height;
        ySpeed = 1 + r.nextInt(4);
    }



}
