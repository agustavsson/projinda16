import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Obstacle && entity.getY() > getHeight() + entity.getHeight()) {
                entities.remove(entity);
            }
            entity.updatePos();
            gc.drawImage(entity.getImage(), entity.getX(), entity.getY(),
                    entity.getWidth(), entity.getHeight());
        }
        showMessage("Current score: " + score, 5, 20);
        gc.save();
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void showMessage(String message, double posX, double posY) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.fillText(message, posX, posY);
    }
}