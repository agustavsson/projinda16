import javafx.scene.image.Image;

/**
 * @author Philip Strömberg
 * Special class for the player controled car
 */
public class Car extends Entity {

    Car(Image image, int width, int height, GameCanvas canvas) {
        super(image, width, height, canvas);
        x = canvas.getWidth() / 2;
        y = canvas.getHeight() - height;
    }

    public void left() { left = true; }
    public void right() { right = true; }
    public void up() {
        up = true;
    }
    public void down() {
        down = true;
    }
    public void stopLeft() { left = false; }
    public void stopRight() { right = false; }
    public void stopUp() {
        up = false;
    }
    public void stopDown() {
        down = false;
    }

    @Override
    public void updatePos() {
        super.updatePos();
    }
}
