package javaapplication;

import collision.OBJ_Chest;
import collision.OBJ_Key;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class UserInterface {
    GamePanel gp;
    Font arial_40;
    Font arial_80;
    Font arial_20;
    BufferedImage keyImage;
    BufferedImage chestImage;
    Graphics2D g2;

    BufferedImage titleBgImage;

    public UserInterface(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.BOLD, 36);
        arial_80 = new Font("Arial", Font.BOLD, 70);
        arial_20 = new Font("Arial", Font.PLAIN, 20);
        OBJ_Key key = new OBJ_Key();
        OBJ_Chest chest = new OBJ_Chest();
        keyImage = key.image;
        chestImage = chest.image;
        try {
            titleBgImage = ImageIO.read(getClass().getResourceAsStream("/javaapplication/title_bg.png"));
        } catch (Exception e) {
            titleBgImage = null;
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        if (!gp.gameStarted) {
            drawTitleScreen();
            drawSoundButton(g2);
            return;
        }

        if (gp.thief != null) {
            int marginX = 20;
            int keyY = 20;
            int chestY = keyY + gp.tileSize + 15;

            g2.setFont(arial_40);

            if (keyImage != null) {
                g2.drawImage(keyImage, marginX, keyY, gp.tileSize, gp.tileSize, null);
                drawShadowedString(" : " + gp.thief.totalKeysCollected + " / " + gp.initialTotalKeys,
                        marginX + gp.tileSize + 5, keyY + gp.tileSize - 10, Color.WHITE);
            }

            if (chestImage != null) {
                g2.drawImage(chestImage, marginX, chestY, gp.tileSize, gp.tileSize, null);
                drawShadowedString(" : " + gp.thief.totalChestsCollected + " / " + gp.initialTotalChests,
                        marginX + gp.tileSize + 5, chestY + gp.tileSize - 10, Color.WHITE);
            }

            int totalSeconds = (gp.currentLevel == 2 && gp.level2Active) ? gp.level2Time : gp.playTime;
            int minutes = Math.max(0, totalSeconds / 60);
            int seconds = Math.max(0, totalSeconds % 60);
            String timeFormatted = String.format("%02d:%02d", minutes, seconds);

            drawShadowedString("Time: " + timeFormatted, gp.tileSize * 9, 55, Color.WHITE);
            drawShadowedString("Level: " + gp.currentLevel, gp.tileSize * 9, 95, Color.WHITE);
            drawSoundButton(g2);
        }

        if (gp.gamestate == gp.pausestate) {
            drawPauseScreen();
        }
    }

    public void drawSoundButton(Graphics2D g2) {
        int btnX = gp.screenWidth - 60;
        int btnY = 15;
        int btnW = 44;
        int btnH = 44;

        // Button background
        g2.setColor(new Color(15, 30, 35, 210));
        g2.fillOval(btnX, btnY, btnW, btnH);
        g2.setColor(gp.soundOn ? new Color(60, 220, 140) : new Color(255, 90, 80));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(btnX, btnY, btnW, btnH);

        // Speaker body
        g2.setColor(Color.WHITE);
        g2.fillRect(btnX + 11, btnY + 17, 7, 10);
        int[] px = {btnX + 18, btnX + 25, btnX + 25, btnX + 18};
        int[] py = {btnX + 17, btnX + 10, btnX + 34, btnX + 27};
        g2.fillPolygon(px, py, 4);

        if (gp.soundOn) {
            // Sound wave arcs
            g2.setColor(new Color(255, 220, 80));
            g2.drawArc(btnX + 22, btnY + 15, 12, 14, -55, 110);
            g2.drawArc(btnX + 21, btnY + 11, 18, 22, -55, 110);
        } else {
            // Red diagonal line crossing out the speaker icon
            g2.setColor(new Color(255, 70, 70));
            g2.setStroke(new BasicStroke(3));
            g2.drawLine(btnX + 10, btnY + 10, btnX + 34, btnY + 34);
        }
    }

    private void drawShadowedString(String text, int x, int y, Color color) {
        g2.setColor(Color.BLACK);
        g2.drawString(text, x + 2, y + 2);
        g2.setColor(color);
        g2.drawString(text, x, y);
    }

    public void drawTitleScreen() {
        if (titleBgImage != null) {
            g2.drawImage(titleBgImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            g2.setColor(new Color(10, 24, 30));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        }

        // Floating ambient firefly particle dots
        long time = System.currentTimeMillis();
        g2.setColor(new Color(255, 230, 140, 180));
        for (int i = 0; i < 12; i++) {
            int px = (int)((i * 67 + time * 0.03 * (i % 3 + 1)) % gp.screenWidth);
            int py = (int)((i * 43 + Math.sin(time * 0.002 + i) * 30 + 100) % gp.screenHeight);
            g2.fillOval(px, py, 4 + (i % 3), 4 + (i % 3));
        }

        // Sleek translucent dark glass card overlay
        int cardX = gp.tileSize * 2;
        int cardY = gp.tileSize * 2 + 10;
        int cardW = gp.screenWidth - gp.tileSize * 4;
        int cardH = gp.screenHeight - gp.tileSize * 4 - 20;

        g2.setColor(new Color(8, 20, 22, 185));
        g2.fillRoundRect(cardX, cardY, cardW, cardH, 25, 25);
        g2.setColor(new Color(230, 190, 90, 160));
        g2.drawRoundRect(cardX, cardY, cardW, cardH, 25, 25);
        g2.setColor(new Color(40, 180, 130, 120));
        g2.drawRoundRect(cardX + 2, cardY + 2, cardW - 4, cardH - 4, 23, 23);

        // Prominent Centered Gold/Emerald Title
        g2.setFont(arial_80);
        String title = "GARDEN STEALTH";
        int x = getCenteredTextX(title);
        int y = gp.screenHeight / 2 - 15;

        // Shadow & Glow
        g2.setColor(new Color(0, 0, 0, 220));
        g2.drawString(title, x + 4, y + 4);
        g2.setColor(new Color(255, 215, 80));
        g2.drawString(title, x, y);

        // Pulsing Start Prompt
        g2.setFont(arial_40);
        String startPrompt = "Press SPACE to Start";
        x = getCenteredTextX(startPrompt);
        y = gp.screenHeight / 2 + 65;

        boolean pulse = (time / 450) % 2 == 0;
        Color promptColor = pulse ? new Color(255, 240, 120) : new Color(255, 170, 40);
        drawShadowedString(startPrompt, x, y, promptColor);
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
