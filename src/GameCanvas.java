import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles messages, draws entities and keeps track of your score
 */
public class GameCanvas extends Canvas {
    List<Entity> entities = new ArrayList<>();

    public void drawAll(int score) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());

        // A method to add a background, the css version lags less however
        // drawBackground(gc);
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Obstacle && entity.getY() > this.getHeight() + entity.getHeight()) {
                entities.remove(entity);
            }
            entity.updatePos();
            gc.drawImage(entity.getImage(), entity.getX(), entity.getY(),
                    entity.getWidth(), entity.getHeight());
        }
        showMessage("Current score: " + score, 5, 20);
        gc.save();
    }

    // Unusable unless drawBackground(gc) in drawAll method is uncommented
    public void drawBackground(GraphicsContext gc) {
        gc.drawImage(new Image("/images/background.png"), 0, 0,
                this.getWidth(), this.getHeight());
    }

    public void addEntity(Entity e) {
        entities.add(0, e);
        if (e instanceof Obstacle) {
            entities.add(e);
        } else if (e instanceof Gas) {
            entities.add(e);
        }
    }

    public void showMessage(String message, double posX, double posY) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.fillText(message, posX, posY);
    }
}