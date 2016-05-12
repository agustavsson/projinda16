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
        if (y > c.getHeight() - height) { // Too far down
            ySpeed = 0;
            y = c.getHeight() - height;
        } else if (y < 0) {  // Too far up
            ySpeed = 0;
            y = 0;
        }
        if (x > c.getWidth() - width) { // Too far right
            xSpeed = 0;
            x = c.getWidth() -width;
        } else if (x < 0) { // Too far left
            xSpeed = 0;
            x = 0;
        }
        y += ySpeed;
        x += xSpeed;
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