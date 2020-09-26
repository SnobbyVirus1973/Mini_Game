package shoot_game;

import java.awt.image.BufferedImage;

public class SuperBullet extends Bullet {
    private int index = 0;

    // 实例代码块
    {
        this.setCollide(1, 100, 100, 0, 0);
        this.setCollide(2, 100, 100, 0, 0);
    }

    SuperBullet(int x, int y) {
        super(100, 100, x, y, 0, -2);
    }

    @Override
    public BufferedImage getImage() {
        index++;
        return Images.superBullet[index / 5 % Images.superBullet.length];
    }
}
