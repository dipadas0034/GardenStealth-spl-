import javaapplication.GamePanel;
import javax.swing.JFrame;

public class GardenStealth extends JFrame {
    public static void main(String[] args) {
        GardenStealth frame = new GardenStealth();
        frame.setTitle("GARDEN STEALTH GAME");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}