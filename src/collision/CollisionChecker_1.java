package collision;

import entity.Playerclass;
import java.awt.Rectangle;
import javaapplication.GamePanel;

public class CollisionChecker_1 {
    GamePanel gp;

    public CollisionChecker_1(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Playerclass entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;
        switch (entity.direction) {
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (entityLeftCol >= 0 && entityRightCol < gp.maxWorldCol && entityTopRow >= 0 && entityTopRow < gp.maxWorldRow) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                break;

            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (entityLeftCol >= 0 && entityRightCol < gp.maxWorldCol && entityBottomRow >= 0 && entityBottomRow < gp.maxWorldRow) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                break;

            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (entityLeftCol >= 0 && entityLeftCol < gp.maxWorldCol && entityTopRow >= 0 && entityBottomRow < gp.maxWorldRow) {
                    tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                break;

            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (entityRightCol >= 0 && entityRightCol < gp.maxWorldCol && entityTopRow >= 0 && entityBottomRow < gp.maxWorldRow) {
                    tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                    tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                    if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                        entity.collisionOn = true;
                    }
                }
                break;
        }
    }

    public int checkObject(Playerclass entity, boolean player) {
        int index = -1;

        for (int i = 0; i < gp.obj.size(); i++) {
            SuperObject o = gp.obj.get(i);
            if (o != null) {
                int entityLeftX = entity.worldX + entity.solidArea.x;
                int entityTopY = entity.worldY + entity.solidArea.y;

                switch (entity.direction) {
                    case "up" -> entityTopY -= entity.speed;
                    case "down" -> entityTopY += entity.speed;
                    case "left" -> entityLeftX -= entity.speed;
                    case "right" -> entityLeftX += entity.speed;
                }

                Rectangle entityNextRect = new Rectangle(entityLeftX, entityTopY, entity.solidArea.width, entity.solidArea.height);
                Rectangle objectRect = new Rectangle(o.worldX + o.solidAreaDefaultX, o.worldY + o.solidAreaDefaultY, o.solidArea.width, o.solidArea.height);

                if (entityNextRect.intersects(objectRect)) {
                    if (o.collision) {
                        entity.collisionOn = true;
                    }
                    if (player) {
                        index = i;
                    }
                }
            }
        }

        return index;
    }
}
