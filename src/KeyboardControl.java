import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author Philip Strömberg
 * Class for keyboard controls
 */
public class KeyboardControl extends KeyAdapter{

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            Motor.car.up();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            Motor.car.down();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            Motor.car.left();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            Motor.car.right();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Boost maybe?
            // TODO
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyReleased (KeyEvent e) {}
}
