import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.util.Duration;
import java.util.Iterator;
import java.util.Random;

/**
 * Main engine behind the game. Handles entity creations and collisions
 */
public class Motor extends Application {
    private Image[] obstacleImage = {
            new Image("/images/Obstacle1.png"),
            new Image("/images/Obstacle2.png")
    };
    private String carSource = "/images/Car.png";
    private Image gasImage = new Image("/images/Gas.png");
    private static final int FRAME_RATE = 60;// OK
    private int score = 0;
    private int gas = 50;
    private int fuelCounter = 0;
    private Rectangle fuelbar = new Rectangle(100,10);
    private static Random random = new Random();
    private GameCanvas canvas = new GameCanvas();
    protected static Car car;
    private Scene scene;
    private final int OBSTACLESPAWNRATE = 10;
    private final int GASSPAWNRATE = 4;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group g = new Group();

        ImageView backgroundImageView;
        Pane backgroundLayer;
        backgroundLayer = new Pane();
        backgroundImageView = new ImageView(getClass().getResource("images/background.png").toExternalForm());
        Pane topLayer = new Pane();
        //Rectangle fuel = new Rectangle(50,10);
        backgroundLayer.getChildren().add(backgroundImageView);
        topLayer.getChildren().add(fuelbar);


        g.getChildren().add(backgroundLayer);
        g.getChildren().add(canvas);
        g.getChildren().add(topLayer);
        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        scene = new Scene(g);
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                car.left();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                car.right();
            }
            if (e.getCode() == KeyCode.UP) {
                car.up();
            }
            if (e.getCode() == KeyCode.DOWN) {
                car.down();
            }
        });
        scene.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                car.stopLeft();
            }
            if (e.getCode() == KeyCode.RIGHT) {
                car.stopRight();
            }
            if (e.getCode() == KeyCode.UP) {
                car.stopUp();
            }
            if (e.getCode() == KeyCode.DOWN) {
                car.stopDown();
            }
        });

        primaryStage.setFullScreen(true);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("press esc to exit");
        primaryStage.show();
        canvas.setWidth(scene.getWidth());
        canvas.setHeight(scene.getHeight());

        car = new Car(new Image(carSource), 50, 50, canvas);
        canvas.addEntity(car);
        long startTime = (System.currentTimeMillis()/1000);

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
                    long currentTime = (System.currentTimeMillis()/1000) - startTime;
                    canvas.drawAll(currentTime);
                    tickFuel();
                }
        );
        t.getKeyFrames().add(keyframe);
        t.playFromStart();
        playSounds();
    }

    // Checks if you ran out of fuel or if there was a collision and you therefor lost
    private boolean hasLost() {
        Iterator<Entity> entityIterator = canvas.entities.iterator();
        while (entityIterator.hasNext()) {
            Entity entity = entityIterator.next();
            if (    entity.getY() + entity.getHeight() >= car.getY() &&
                    entity.getX() + entity.getWidth() >= car.getX() &&
                    entity.getY() <= car.getY() + car.getHeight() &&
                    entity.getX() <= car.getX() + car.getWidth()
                    ) {
                if (entity instanceof Obstacle) {
                    return true;
                } else if (entity instanceof Gas && gas < 100) {
                    entityIterator.remove();
                    if(gas < 100) {
                    gas += 10;
                    fuelbar.setWidth(getFuelbarX() + 10);
                    }
                }
                    if (fuelbar.getWidth() == 10) {
                        return true;
                    }
            }
        }
        return false;
    }

    // Has a chance to create an obstacle or gas can
    private void generateEntity() {
        int next = random.nextInt(200);
        if (next < OBSTACLESPAWNRATE) {
            Image obstacleImage = getObstacleImage();
            Obstacle o = new Obstacle(obstacleImage, 50, 50, canvas);
            canvas.addEntity(o);
        }
        if (next < GASSPAWNRATE) {
            Image gasImage = getGasImage();
            Gas g = new Gas(gasImage, 50, 50, canvas);
            canvas.addEntity(g);
        }
    }

    private void incFuel() {
        fuelbar.setWidth(getFuelbarX() + 10);
    }

    private void tickFuel() {
        fuelCounter++;
            if(fuelCounter%5 == 1) {
            double gasDelta = 0.1;
            //gas -= 0.00001;
            fuelbar.setWidth(getFuelbarX() - gasDelta);
        }
    }

    private double getFuelbarX() {
        return fuelbar.getWidth();
    }

    private double getFuelbarY() {
        return fuelbar.getHeight();
    }

    private Image getObstacleImage() {
        int i = random.nextInt(obstacleImage.length);
        return obstacleImage[i];
    }

    private Image getGasImage() {
        return gasImage;
    }

    public synchronized void playSounds() {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                        Motor.class.getResourceAsStream("/sounds/music.wav"));
                clip.open(inputStream);
                clip.loop(100000000);
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
}