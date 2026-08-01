package javaapplication;

import collision.OBJ_Chest;
import collision.OBJ_Key;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class UserInterface {
    GamePanel gp;
    Font arial_40;
    Font arial_80;
    Font arial_20;
    BufferedImage keyImage;
    BufferedImage chestImage;
    Graphics2D g2;

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD, 36);
        arial_80 = new Font("Arial", Font.BOLD, 70);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Key key = new OBJ_Key();
        OBJ_Chest chest = new OBJ_Chest();
        keyImage = key.image;
        chestImage = chest.image;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (!gp.gameStarted) {
            drawTitleScreen();
            return;
        }

        if (gp.thief != null) {
            int marginX = 20;
            int keyY = 20;
            int chestY = keyY + gp.tileSize + 15;

            g2.setFont(arial_40);

            if (keyImage != null) {
                g2.drawImage(keyImage, marginX, keyY, gp.tileSize, gp.tileSize, null);
                drawShadowedString("x " + gp.thief.getKeyCount(), marginX + gp.tileSize + 10, keyY + gp.tileSize - 10, Color.WHITE);
            }

            if (chestImage != null) {
                g2.drawImage(chestImage, marginX, chestY, gp.tileSize, gp.tileSize, null);
                drawShadowedString("x " + gp.thief.getChestCount(), marginX + gp.tileSize + 10, chestY + gp.tileSize - 10, Color.WHITE);
            }

            int totalSeconds = (gp.currentLevel == 2 && gp.level2Active) ? gp.level2Time : gp.playTime;
            int minutes = Math.max(0, totalSeconds / 60);
            int seconds = Math.max(0, totalSeconds % 60);
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);

            drawShadowedString("Time: " + timeFormatted, gp.tileSize * 11, 55, Color.WHITE);
            drawShadowedString("Level: " + gp.currentLevel, gp.tileSize * 11, 95, Color.WHITE);
        }

        if (gp.gamestate == gp.pausestate) {
            drawPauseScreen();
        }
    }

    private void drawShadowedString(String text, int x, int y, Color color) {
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {
        g2.setColor(new Color(15, 25, 35));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(arial_80);
        String title = "GARDEN STEALTH";
        g2.setColor(Color.BLACK);
        int x = getCenteredTextX(title);
        int y = gp.tileSize * 3;
        g2.drawString(title, x + 3, y + 3);
        g2.setColor(new Color(40, 200, 100));
        g2.drawString(title, x, y);

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        String subtitle = "2D Stealth Adventure";
        x = getCenteredTextX(subtitle);
        y += gp.tileSize * 1.5;
        drawShadowedString(subtitle, x, y, Color.WHITE);

        g2.setFont(arial_20);
        g2.setColor(Color.LIGHT_GRAY);
        String[] instructions = {
            "Controls: WASD / Arrow Keys to Move",
            "Objective: Avoid Orc Monsters, Collect Keys & Chests inside Huts",
            "Press P to Pause / Resume Game"
        };
        y += gp.tileSize * 1.5;
        for (String line : instructions) {
            x = getCenteredTextX(line);
            g2.drawString(line, x, y);
            y += 30;
        }

        g2.setFont(arial_40);
        String startPrompt = "Press SPACE to Start";
        x = getCenteredTextX(startPrompt);
        y = gp.screenHeight - gp.tileSize * 2;
        drawShadowedString(startPrompt, x, y, Color.YELLOW);
    }

    public void drawPauseScreen() {
        g2.setFont(arial_80);
        String text = "PAUSED";
        int x = getCenteredTextX(text);
        int y = gp.screenHeight / 2;
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 3, y + 3);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);
    }

    public int getCenteredTextX(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
