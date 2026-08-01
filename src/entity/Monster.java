package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javaapplication.GamePanel;
import javax.imageio.ImageIO;

public class Monster extends Playerclass {

    Random random = new Random();
    int actionLockCounter = 0;

    public Monster(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/orc_up_1.png");
        up2 = setup("/monster/orc_up_2.png");
        down1 = setup("/monster/orc_down_1.png");
        down2 = setup("/monster/orc_down_2.png");
        left1 = setup("/monster/orc_left_1.png");
        left2 = setup("/monster/orc_left_2.png");
        right1 = setup("/monster/orc_right_1.png");
        right2 = setup("/monster/orc_right_2.png");
    }

    public BufferedImage setup(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public void update() {
        if (detectPlayer(250)) {
            chasePlayer();
        } else {
            wanderRandomly();
        }

        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false); // Monster cannot pass through solid objects like Doors or Chests

        if (collisionOn) {
            tryDetour();
        } else {
            switch (direction) {
                case "up" -> worldY -= speed;
                case "down" -> worldY += speed;
                case "left" -> worldX -= speed;
                case "right" -> worldX += speed;
            }
        }

        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public boolean detectPlayer(int range) {
        if (gp.thief == null) return false;
        int dx = Math.abs(worldX - gp.thief.worldX);
        int dy = Math.abs(worldY - gp.thief.worldY);
        return dx < range && dy < range;
    }

    public void chasePlayer() {
        if (gp.thief == null) return;
        int dx = gp.thief.worldX - worldX;
        int dy = gp.thief.worldY - worldY;

        if (Math.abs(dx) > Math.abs(dy)) {
            direction = (dx > 0) ? "right" : "left";
        } else {
            direction = (dy > 0) ? "down" : "up";
        }
    }

    private void tryDetour() {
        String originalDir = direction;
        String[] options = switch (originalDir) {
            case "up", "down" -> new String[]{"left", "right"};
            case "left", "right" -> new String[]{"up", "down"};
            default -> new String[]{"down", "up"};
        };

        for (String option : options) {
            direction = option;
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this, false);
            if (!collisionOn) {
                switch (direction) {
                    case "up" -> worldY -= speed;
                    case "down" -> worldY += speed;
                    case "left" -> worldX -= speed;
                    case "right" -> worldX += speed;
                }
                break;
            }
        }
    }

    private void wanderRandomly() {
        actionLockCounter++;
        if (actionLockCounter >= 120) {
            int i = random.nextInt(4);
            direction = switch (i) {
                case 0 -> "up";
                case 1 -> "down";
                case 2 -> "left";
                default -> "right";
            };
            actionLockCounter = 0;
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

        if (gp.thief != null) {
            int screenX = worldX - gp.thief.worldX + gp.thief.screenX;
            int screenY = worldY - gp.thief.worldY + gp.thief.screenY;

            if (image != null) {
                g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
