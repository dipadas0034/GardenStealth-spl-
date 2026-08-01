package collision;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javaapplication.GamePanel;

public class SuperObject {
    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

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
        }
    }
}
