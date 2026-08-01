/*package Obstracle;

import entity.Playerclass;
import java.util.Random;
import javaapplication.gardenstealth1;

public class MON_GreenSlime extends Playerclass{
    public  MON_GreenSlime(gardenstealth1 gp){
        super(gp);
        name="Green Slime";
        speed=1;
     
        solidArea.x = 3;
        solidArea.y=18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        getImage();
    }
     public void getImage(){
         up1=setup("/monster/orc_down_1.png");
         up2=setup("/monster/orc_up_1.png");
         down1=setup("/monster/orc_up_1.png");
         down2=setup("/monster/orc_up_1.png");
         left1=setup("/monster/orc_up_1.png");
         left2=setup("/monster/orc_up_1.png");
         right1=setup("/monster/orc_up_1.png");
         right2=setup("/monster/orc_up_1.png");
    }
     public void setAction(){
         actionLockCounter++;
         if(actionLockCounter == 120){
             Random random =new Random();
             int i = random.nextInt(100)+1;
             if(i<=25){
                 direction="up";
             }
             if(i>25 && i<=50){
                 direction="down";
             }
             if(i>50 && i<=75){
                 direction="left";
             }
             if(i>75 && i<=100){
                 direction="right";
             }
             actionLockCounter=0;
         }
     }
}*/

