import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Random;

/**
 * @author Philip Strömberg
 * Main engine behind the game. Handles entity creations and collisions
 */
public class Motor extends Application {
    private String carSource = "/images/Car.png";
    private Image[] obstacleImage = {
            new Image("/images/Obstacle1.png"),
            new Image("/images/Obstacle2.png")
    };
    private Image gasImage = new Image("/images/Gas.png");

    private static final int FRAME_RATE = 60; // OK
    private int score = 0;

    private static Random random = new Random();

    private GameCanvas canvas = new GameCanvas();
    protected static Car car;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group g = new Group();
        g.getChildren().add(canvas);
        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);
        scene = new Scene(g);

        // primaryStage.setFullScreen(true);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("press esc to exit");
        primaryStage.show();
        canvas.setWidth(scene.getWidth());
        canvas.setHeight(scene.getHeight());

        car = new Car(new Image(carSource), 50, 50, canvas);
        canvas.addEntity(car);

        Timeline t = new Timeline();
        t.setCycleCount(Timeline.INDEFINITE);
        KeyFrame keyframe = new KeyFrame(
                Duration.seconds((double) 1 / FRAME_RATE),
                event -> {
                    if (hasLost()) {
                        t.stop();
                        return;
                    }
                    generateEntity();
                    score += canvas.drawAll(score);
                }
        );
        t.getKeyFrames().add(keyframe);
        t.playFromStart();
        playSounds();
    }

    // Checks if there was a collision and you therefor lost
    private boolean hasLost() {
        for (Entity e : canvas.entities) {
            if (e instanceof Obstacle) {
                Obstacle obstacle = (Obstacle) e;
                if (obstacle.getY() + obstacle.getHeight() >= car.getY() &&
                        obstacle.getX() + obstacle.getWidth() >= car.getX() &&
                        obstacle.getY() <= car.getY() + car.getHeight() &&
                        obstacle.getX() <= car.getX() + car.getWidth()
                        ) {
                    return true;
                }
            }
        }
        return false;
    }

    // Has a chance to create an obstacle or gas can
    private void generateEntity() {
        int next = random.nextInt(200);
        if (next < 1) {
            Image obstacleImage = getObstacleImage();
            Obstacle o = new Obstacle(obstacleImage, 50, 50, canvas);
            canvas.addEntity(o);
        } else if (next < 2 && next > 1) {
            Image gasImage = getGasImage();
            Gas g = new Gas(gasImage, 50, 50, canvas);
            canvas.addEntity(g);
        }
    }

    private Image getObstacleImage() {
        int i = random.nextInt(obstacleImage.length);
        return obstacleImage[i];
    }

    private Image getGasImage() {
        return gasImage;
    }

    public synchronized void playSound(String soundPath) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream(soundPath));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

    public synchronized void playSounds() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Main.class.getResourceAsStream("/sounds/music.wav"));
                clip.open(inputStream);
                clip.loop(100000000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}