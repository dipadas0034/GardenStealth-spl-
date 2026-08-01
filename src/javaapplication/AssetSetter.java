package javaapplication;

import collision.OBJ_Chest;
import collision.OBJ_Door;
import collision.OBJ_Key;
import entity.Monster;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        if (gp.currentLevel != 1) return;
        gp.obj.clear();

        // Keys scattered in the garden
        addKey(16, 40);
        addKey(13, 28);
        addKey(35, 12);

        // Hut 1: Door at front, Chest inside hut
        addDoor(17, 16);
        addChest(17, 15);

        // Hut 2: Door at front, Chest inside hut
        addDoor(3, 47);
        addChest(3, 46);

        // Hut 3: Door at front, Chest inside hut
        addDoor(46, 20);
        addChest(46, 19);
    }

    public void setMonster() {
        if (gp.currentLevel != 1) return;
        gp.monster.clear();

        addMonster(13, 8);
        addMonster(15, 18);
        addMonster(8, 5);
        addMonster(49, 3);
        addMonster(21, 12);
        addMonster(10, 32);
    }

    private void addKey(int col, int row) {
        OBJ_Key key = new OBJ_Key();
        key.worldX = col * gp.tileSize;
        key.worldY = row * gp.tileSize;
        gp.obj.add(key);
    }

    private void addChest(int col, int row) {
        OBJ_Chest chest = new OBJ_Chest();
        chest.worldX = col * gp.tileSize;
        chest.worldY = row * gp.tileSize;
        gp.obj.add(chest);
    }

    private void addDoor(int col, int row) {
        OBJ_Door door = new OBJ_Door();
        door.worldX = col * gp.tileSize;
        door.worldY = row * gp.tileSize;
        gp.obj.add(door);
    }

    private void addMonster(int col, int row) {
        Monster m = new Monster(gp);
        m.worldX = col * gp.tileSize;
        m.worldY = row * gp.tileSize;
        gp.monster.add(m);
    }
}
