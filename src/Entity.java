import javafx.scene.image.Image;

/**
 | Abstract class for all obstacles and ships
 */
public abstract class Entity {
    protected double x, y, xSpeed, ySpeed;
    private int width, height;
    private Image image;
    protected GameCanvas c;

    // Only usable for Car class
    protected boolean up = false;
    protected boolean down = false;
    protected boolean left = false;
    protected boolean right = false;
    protected boolean boost = false;
    private double speed = 0.2;
    private double boostMultiplier = 10000;

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
        if (this instanceof Car) {

            if (this.boost) {
                speed *= boostMultiplier;
            }
            if (this.up) {
                ySpeed -= speed;
            }
            if (this.down) {
                ySpeed += speed;
            }
            if (this.left) {
                xSpeed -= speed;
            }
            if (this.right) {
                xSpeed += speed;
            }
            if (this.boost) {
                speed /= boostMultiplier;
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
                x = c.getWidth() - width;
            } else if (x < 0) { // Too far left
                xSpeed = 0;
                x = 0;
            }
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