package shoot_game;

import java.awt.image.BufferedImage;

/**
 * 天空类 用于画天上的云
 * 
 * @author SnobbyVirus1973
 */
public class Sky extends BackGround {

    Sky() {
        super();
        this.ySpeed = 2;
    }

    public BufferedImage getImage(int i) {
        return Images.sky[i];
    }
}
