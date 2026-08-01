package collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;
import javaapplication.GamePanel;

public class OBJ_Gate extends SuperObject {
    public OBJ_Gate() {
        name = "Gate";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/playerclass/door.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }

    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        if (gp.thief == null) return;
        int screenX = worldX - gp.thief.worldX + gp.thief.screenX;
        int screenY = worldY - gp.thief.worldY + gp.thief.screenY;

        if (worldX + gp.tileSize > gp.thief.worldX - gp.thief.screenX &&
            worldX - gp.tileSize < gp.thief.worldX + gp.thief.screenX &&
            worldY + gp.tileSize > gp.thief.worldY - gp.thief.screenY &&
            worldY - gp.tileSize < gp.thief.worldY + gp.thief.screenY) {

            if (image != null) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }

            // Draw custom Fence Gate overlay details
            g2.setColor(new Color(70, 45, 20));
            g2.fillRect(screenX, screenY, 6, gp.tileSize);
            g2.fillRect(screenX + gp.tileSize - 6, screenY, 6, gp.tileSize);

            g2.setColor(new Color(190, 190, 200));
            for (int i = 10; i < gp.tileSize - 6; i += 8) {
                g2.fillRect(screenX + i, screenY + 4, 4, gp.tileSize - 8);
            }

            g2.setColor(new Color(100, 100, 110));
            g2.fillRect(screenX + 4, screenY + 20, gp.tileSize - 8, 6);

            // Padlock
            g2.setColor(Color.YELLOW);
            g2.fillRect(screenX + 20, screenY + 20, 8, 8);
            g2.setColor(Color.BLACK);
            g2.drawRect(screenX + 20, screenY + 20, 8, 8);
        }
    }
}
