import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Philip Strömberg
 * Handles messages, draws entities and keeps track of your score
 */
public class GameCanvas extends Canvas {
    List<Entity> entities = new ArrayList<>();
    List<Obstacle> obstacles = new ArrayList<>();

    public int drawAll(int score) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        // drawBackground(gc);
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Obstacle && entity.getY() > this.getHeight() + entity.getHeight()) {
                entities.remove(entity);
                return 0;
            }
            entity.updatePos();
            gc.drawImage(entity.getImage(), entity.getX(), entity.getY(), entity.getWidth(),
                    entity.getHeight());
        }
        showMessage("Current score: " + score, 5, 20);
        gc.save();
        return score;
    }

    public void drawBackground(GraphicsContext gc) {
        gc.drawImage(new Image("/images/background.png"), 0, 0,
                this.getWidth(), this.getHeight());
    }

    public void addEntity(Entity e) {
        entities.add(0, e);
        if (e instanceof Obstacle) {
            obstacles.add((Obstacle) e);
        } else if (e instanceof Gas) {
            entities.add(e);
        }
    }

    public void showMessage(String message, double posX, double posY) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.fillText(message, posX, posY);
    }
}