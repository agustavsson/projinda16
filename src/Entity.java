import javafx.scene.image.Image;

/**
 * @author Philip Strömberg
 * Abstract class for all obstacles and ships
 */
public abstract class Entity {
    protected double x, y, xSpeed, ySpeed;
    private int width, height;
    private Image image;
    protected GameCanvas c;

    Entity(Image image, int width, int height, GameCanvas c) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.c = c;
    }

    Image getImage() {
        return image;
    }

    void updatePos() {
        if (!(this instanceof Car)) {
            return;
        }
        if (y > c.getHeight() - height) { // Too far Car
            ySpeed = -ySpeed * 0.3;
            y = c.getHeight() - height;
        } else if (y < 0) {  // Too far up
            ySpeed = -ySpeed * 0.3;
            y = 0;
        }
        y += ySpeed;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }
}
