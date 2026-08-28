package javaapplication;

import collision.CollisionChecker_1;
import collision.SuperObject;
import entity.Monster;
import entity.Player;
import entity.Playerclass;
import entity.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
    final int originalTitleSize = 16;
    final int scale = 3;
    public final int tileSize = originalTitleSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    public int currentLevel = 1;
    int FPS = 60;

    public KeyHandler keyH = new KeyHandler(this);
    public TileManager tileM = new TileManager(this);

    public SoundManager sound = new SoundManager();
    public SoundManager music = new SoundManager();
    public CollisionChecker_1 cChecker = new CollisionChecker_1(this);
    public AssetSetter asetter = new AssetSetter(this);
    public UserInterface ui;

    Thread gameThread;

    public Player thief = new Player(this, keyH);

    public ArrayList<SuperObject> obj = new ArrayList<>();
    public ArrayList<Monster> monster = new ArrayList<>();

    public int gamestate;
    public final int playstate = 1;
    public final int pausestate = 2;
    public final int winState = 3;

    public int initialTotalKeys;
    public int initialTotalChests;
    public int expectedChests;

    public boolean gameStarted = false;
    public boolean gameFinished = false;

    public int playTime = 60;
    public int level2Time = 120;
    public long lastTime = System.currentTimeMillis();

    public boolean level2Active = false;
    public int lastCompletedLevel = 0;

    public GamePanel() {
        setBackground(new Color(80, 160, 60));
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setDoubleBuffered(true);
        addKeyListener(keyH);
        setFocusable(true);

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int mx = e.getX();
                int my = e.getY();
                int btnX = screenWidth - 60;
                int btnY = 15;
                int btnW = 44;
                int btnH = 44;
                if (mx >= btnX && mx <= btnX + btnW && my >= btnY && my <= btnY + btnH) {
                    toggleSound();
                }
            }
        });

        ui = new UserInterface(this);
        asetter = new AssetSetter(this);
    }

    public void setupGame() {
        asetter.setObject();
        asetter.setMonster();
        initialTotalKeys = totalKeys();
        initialTotalChests = totalChests();
        playTime = 60;
        level2Time = 120;
        level2Active = false;
        gameFinished = false;
        gameStarted = false;
        currentLevel = 1;
        lastTime = System.currentTimeMillis();
        playmusic(0);
        gamestate = playstate;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();

            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateTimer() {
        if (gamestate == playstate && gameStarted && !gameFinished) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= 1000) {
                lastTime = currentTime;

                if (currentLevel == 2 && level2Active) {
                    level2Time--;
                    if (level2Time <= 0) {
                        gameFinished = true;
                        stopMusic();
                    }
                } else {
                    playTime--;
                    if (playTime <= 0) {
                        gameFinished = true;
                        stopMusic();
                    }
                }
            }
        }
    }

    public void update() {
        if (!gameStarted && keyH.spacePressed) {
            gameStarted = true;
            gameFinished = false;
            keyH.spacePressed = false;
        }

        if (gameFinished && keyH.spacePressed) {
            if (currentLevel == 2) {
                restartLevel2();
            } else {
                restartLevel1();
            }
            keyH.spacePressed = false;
        }

        if (currentLevel == 1 && thief.totalChestsCollected >= initialTotalChests && thief.totalKeysCollected >= initialTotalKeys) {
            thief.totalChestsCollected = 0;
            thief.totalKeysCollected = 0;
            lastCompletedLevel = 1;
            new Level2manager(this).startLevel2();
            return;
        }

        if (currentLevel == 2 && thief.totalChestsCollected >= initialTotalChests && initialTotalChests > 0) {
            gamestate = winState;
            stopMusic();
        }

        if (keyH.pPressed) {
            gamestate = (gamestate == playstate) ? pausestate : playstate;
            keyH.pPressed = false;
            lastTime = System.currentTimeMillis();
        }

        if (keyH.mPressed) {
            toggleSound();
            keyH.mPressed = false;
        }

        if (gameStarted && !gameFinished && gamestate == playstate) {
            thief.update();
            updateTimer();

            for (Monster m : monster) {
                if (m != null) {
                    m.update();
                    if (checkMonsterPlayerCollision(m)) {
                        gameFinished = true;
                        stopMusic();
                        break;
                    }
                }
            }

            if (isGameWon()) {
                gameFinished = true;
                stopMusic();
            } else if (isGameLost()) {
                gameFinished = true;
                stopMusic();
            }
        }
    }

    public void restartLevel1() {
        currentLevel = 1;
        thief.setDefaultValues();
        obj.clear();
        monster.clear();
        asetter.setObject();
        asetter.setMonster();
        initialTotalChests = totalChests();
        initialTotalKeys = totalKeys();
        playTime = 60;
        level2Active = false;
        gameStarted = true;
        gameFinished = false;
        gamestate = playstate;
        lastTime = System.currentTimeMillis();
        playmusic(0);
    }

    public void restartLevel2() {
        currentLevel = 2;
        new Level2manager(this).startLevel2();
        thief.setDefaultValues();
        gameFinished = false;
        level2Time = 120;
        gamestate = playstate;
        gameStarted = true;
        lastTime = System.currentTimeMillis();
        playmusic(0);
        repaint();
    }

    public boolean isGameWon() {
        boolean hasAllChests = thief.totalChestsCollected >= initialTotalChests && initialTotalChests > 0;
        boolean hasAllKeys = thief.totalKeysCollected >= initialTotalKeys && initialTotalKeys > 0;
        boolean doorIsOpened = thief.doorOpened;
        return hasAllChests && hasAllKeys && (currentLevel == 2 || doorIsOpened);
    }

    public boolean isGameLost() {
        int remainingTime = (currentLevel == 2 && level2Active) ? level2Time : playTime;
        return remainingTime <= 0;
    }

    public boolean checkMonsterPlayerCollision(Monster m) {
        Rectangle playerRect = new Rectangle(
            thief.worldX + thief.solidArea.x,
            thief.worldY + thief.solidArea.y,
            thief.solidArea.width,
            thief.solidArea.height
        );

        Rectangle monsterRect = new Rectangle(
            m.worldX + m.solidArea.x,
            m.worldY + m.solidArea.y,
            m.solidArea.width,
            m.solidArea.height
        );

        return playerRect.intersects(monsterRect);
    }

    public int totalKeys() {
        int count = 0;
        for (SuperObject o : obj) {
            if (o != null && "Key".equals(o.name)) count++;
        }
        return count;
    }

    public int totalChests() {
        int count = 0;
        for (SuperObject o : obj) {
            if (o != null && "Chest".equals(o.name)) count++;
        }
        return count;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Fill background with seamless grass green color instead of black void
        g2.setColor(new Color(80, 160, 60));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        tileM.draw(g2);

        if (ui != null) {
            ui.draw(g2);
        }

        if (gameStarted) {
            thief.draw(g2);

            for (SuperObject o : obj) {
                if (o != null) {
                    o.draw(g2, this);
                }
            }

            for (Monster m : monster) {
                if (m != null) {
                    m.draw(g2);
                }
            }

            if (gameFinished) {
                String text = isGameWon() ? "YOU WIN!" : "GAME OVER! Press SPACE to Restart";
                Color color = isGameWon() ? Color.YELLOW : Color.RED;
                Font font = new Font("Arial", Font.BOLD, 36);
                g2.setFont(font);

                int textWidth = g2.getFontMetrics().stringWidth(text);
                int x = (screenWidth - textWidth) / 2;
                int y = screenHeight / 2;

                // Draw text shadow for perfect contrast
                g2.setColor(Color.BLACK);
                g2.drawString(text, x + 2, y + 2);
                g2.setColor(color);
                g2.drawString(text, x, y);
            }

            if (gamestate == winState) {
                String winText = "🎉 LEVEL 2 COMPLETED!";
                Font font = new Font("Arial", Font.BOLD, 36);
                g2.setFont(font);

                int textWidth = g2.getFontMetrics().stringWidth(winText);
                int x = (screenWidth - textWidth) / 2;
                int y = screenHeight / 2;

                // Draw text shadow
                g2.setColor(Color.BLACK);
                g2.drawString(winText, x + 2, y + 2);
                g2.setColor(Color.GREEN);
                g2.drawString(winText, x, y);
            }
        }

        g2.dispose();
    }

    public boolean soundOn = true;

    public void toggleSound() {
        soundOn = !soundOn;
        if (!soundOn) {
            stopMusic();
        } else {
            playmusic(0);
        }
    }

    public void playmusic(int i) {
        if (!soundOn) return;
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        if (!soundOn) return;
        music.setFile(i);
        music.play();
    }
}
