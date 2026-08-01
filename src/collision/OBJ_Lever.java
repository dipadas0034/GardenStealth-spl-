package collision;

import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Lever extends SuperObject {
    public OBJ_Lever() {
        name = "Key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/playerclass/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = false;
        solidArea = new Rectangle(0, 0, 48, 48);
        solidAreaDefaultX = 0;
        solidAreaDefaultY = 0;
    }
}
