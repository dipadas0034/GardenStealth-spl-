
package collision;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Door extends SuperObject {
    public OBJ_Door(){
        name ="Door";
        try{
            image=ImageIO.read(getClass().getResourceAsStream("/playerclass/door.png"));
        
        }
        catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
         solidArea = new Rectangle(0, 0, 48, 48); // দরজার সাইজ অনুযায়ী
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}  

