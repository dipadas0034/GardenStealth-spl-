package entity;

import collision.SuperObject;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javaapplication.GamePanel;
import javaapplication.KeyHandler;
import javax.imageio.ImageIO;

public class Player extends Playerclass {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public boolean doorOpened = false;
    public boolean chestCollected = false;

    public int totalKeysCollected = 0;
    public int totalChestsCollected = 0;
    public int chestCount = 0;
    public int keyCount = 0;
    public boolean hasKey;
    public boolean hasChest;

    public int levelKeysCollected = 0;
    public int levelChestsCollected = 0;

    public boolean getHasKey() {
        return hasKey;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public int getChestCount() {
        return chestCount;
    }

    public void collectChest() {
        chestCount++;
    }

    public void collectKey() {
        keyCount++;
    }

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        totalKeysCollected = 0;
        totalChestsCollected = 0;
        doorOpened = false;
        hasKey = false;
        hasChest = false;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        keyCount = 0;
        chestCount = 0;
        spriteNum = 1;
        spriteCounter = 0;
    }

    public void setDefaultValuesForLevel() {
        keyCount = 0;
        chestCount = 0;
        doorOpened = false;
        hasKey = false;
        hasChest = false;
        chestCollected = false;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/theif/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        boolean moved = false;

        if (keyH.upPressed) {
            direction = "up";
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            if (objIndex != -1) pickupObject(objIndex);
            if (!collisionOn) {
                worldY -= speed;
                moved = true;
            }
        } else if (keyH.downPressed) {
            direction = "down";
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            if (objIndex != -1) pickupObject(objIndex);
            if (!collisionOn) {
                worldY += speed;
                moved = true;
            }
        } else if (keyH.leftPressed) {
            direction = "left";
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            if (objIndex != -1) pickupObject(objIndex);
            if (!collisionOn) {
                worldX -= speed;
                moved = true;
            }
        } else if (keyH.rightPressed) {
            direction = "right";
            collisionOn = false;
            gp.cChecker.checkTile(this);
            int objIndex = gp.cChecker.checkObject(this, true);
            if (objIndex != -1) pickupObject(objIndex);
            if (!collisionOn) {
                worldX += speed;
                moved = true;
            }
        }

        if (moved) {
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickupObject(int index) {
        if (index != 999 && index >= 0 && index < gp.obj.size()) {
            if (gp.obj.get(index) == null) return;
            String objectName = gp.obj.get(index).name;

            switch (objectName) {
                case "Key":
                case "Lever":
                    gp.playSE(2); // Play unlock.wav sound
                    keyCount++;
                    totalKeysCollected++;
                    hasKey = true;
                    gp.obj.set(index, null);
                    openNearestGate();
                    break;

                case "Gate":
                case "Door":
                    if (hasKey) {
                        gp.playSE(2); // Play unlock.wav sound
                        gp.obj.set(index, null);
                        doorOpened = true;
                        hasKey = false;
                    } else {
                        collisionOn = true;
                    }
                    break;

                case "Chest":
                    gp.playSE(1);
                    chestCollected = true;
                    collectChest();
                    totalChestsCollected++;
                    gp.obj.set(index, null);
                    break;
            }
        }
    }

    public void openNearestGate() {
        int nearestIndex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < gp.obj.size(); i++) {
            SuperObject o = gp.obj.get(i);
            if (o != null && ("Gate".equals(o.name) || "Door".equals(o.name))) {
                double dist = Math.hypot(o.worldX - worldX, o.worldY - worldY);
                if (dist < minDistance) {
                    minDistance = dist;
                    nearestIndex = i;
                }
            }
        }

        if (nearestIndex != -1) {
            gp.obj.set(nearestIndex, null); // Fence Gate unlocks & clears passage!
            doorOpened = true;
            hasKey = false;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1) ? up1 : up2;
            case "down" -> (spriteNum == 1) ? down1 : down2;
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default -> null;
        };

        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
