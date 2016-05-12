import javafx.scene.image.Image;

/**
 * @author Philip Strömberg
 * Special class for the player controled car
 */
public class Car extends Entity {
    private int speed = 1;

    Car(Image image, int width, int height, GameCanvas canvas) {
        super(image, width, height, canvas);
        x = canvas.getWidth() / 2;
        y = canvas.getHeight() - height;
    }

    public void left() {
        xSpeed -= speed;
    }

    public void right() {
        xSpeed += speed;
    }

    public void up() {
        ySpeed -= speed;
    }

    public void down() {
        ySpeed += speed;
    }

    @Override
    public void updatePos() {
        super.updatePos();
    }
}
