package javaapplication;

import collision.OBJ_Chest;
import collision.OBJ_Key;
import collision.SuperObject;
import entity.Monster;

public class Level2manager {
    GamePanel gp;

    public Level2manager(GamePanel gp) {
        this.gp = gp;
    }

    public void startLevel2() {
        gp.currentLevel = 2;
        gp.level2Active = true;
        gp.thief.worldX = gp.tileSize * 2;
        gp.thief.worldY = gp.tileSize * 2;

        gp.thief.levelChestsCollected = 0;
        gp.thief.levelKeysCollected = 0;
        gp.thief.hasKey = false;
        gp.thief.hasChest = false;
        gp.tileM.loadMap("/maps/map01.txt");

        gp.obj.clear();
        gp.monster.clear();

        setObjectsForLevel2();
        setMonstersForLevel2();

        gp.initialTotalChests = countChests();
        gp.initialTotalKeys = countKeys();
        gp.expectedChests = gp.initialTotalChests;

        gp.gameFinished = false;
        gp.gameStarted = true;
        gp.level2Time = 120;
        gp.lastTime = System.currentTimeMillis();

        gp.stopMusic();
        gp.playmusic(0);
    }

    private void setObjectsForLevel2() {
        addChest(7, 22);
        addChest(7, 6);
        addKey(7, 9);
        addKey(3, 11);
        addChest(4, 56);
        addChest(12, 57);
        addChest(17, 37);
        addChest(17, 27);
        addChest(27, 28);
        addChest(30, 27);
        addChest(36, 36);
        addChest(46, 36);
        addChest(20, 7);
        addChest(24, 3);
        addChest(20, 46);
        addChest(46, 46);
        addChest(48, 5);
        addKey(18, 15);
    }

    private void setMonstersForLevel2() {
        addMonster(10, 10);
        addMonster(15, 12);
        addMonster(25, 8);
        addMonster(35, 10);
        addMonster(45, 8);
        addMonster(21, 17);
        addMonster(45, 40);
        addMonster(26, 23);
        addMonster(38, 44);
        addMonster(5, 7);
    }

    private void addMonster(int col, int row) {
        Monster m = new Monster(gp);
        m.worldX = gp.tileSize * col;
        m.worldY = gp.tileSize * row;
        gp.monster.add(m);
    }

    private void addChest(int col, int row) {
        OBJ_Chest chest = new OBJ_Chest();
        chest.name = "Chest";
        chest.worldX = gp.tileSize * col;
        chest.worldY = gp.tileSize * row;
        chest.collision = true;
        gp.obj.add(chest);
    }

    private void addKey(int col, int row) {
        OBJ_Key key = new OBJ_Key();
        key.name = "Key";
        key.worldX = gp.tileSize * col;
        key.worldY = gp.tileSize * row;
        gp.obj.add(key);
    }

    private int countChests() {
        int count = 0;
        for (SuperObject o : gp.obj) {
            if (o instanceof OBJ_Chest) count++;
        }
        return count;
    }

    private int countKeys() {
        int count = 0;
        for (SuperObject o : gp.obj) {
            if (o instanceof OBJ_Key) count++;
        }
        return count;
    }
}
