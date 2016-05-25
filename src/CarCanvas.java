import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.transform.Rotate;

/**
 * Handles messages, draws entities and keeps track of your score
 */
public class CarCanvas extends Canvas {
    private double rotation;

    public void draw() {
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.clearRect(0, 0, getWidth(), getHeight());

        Motor.car.updatePos();
        gc.drawImage(Motor.car.getImage(), Motor.car.getX(), Motor.car.getY(),
        Motor.car.getWidth(), Motor.car.getHeight());

        rotation = -Math.toDegrees(Math.atan(Motor.car.xSpeed/Motor.car.ySpeed));

        Rotate r = new Rotate(rotation, Motor.car.getX() + Motor.car.getWidth()/2,
                Motor.car.getY() + Motor.car.getHeight()/2);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());

        gc.save();
    }
}