package shoot_game;

import java.awt.image.BufferedImage;

public class EnemyBullet extends Bullet {
    private final static int RED_BULLET = 0;
    private final static int BLUE_BULLET = 1;
    private int bulletType = 0;

    EnemyBullet(int x, int y, int bulletType) {
        super(20, 20, x, y, 0, 7);
        this.bulletType = bulletType % 2; // 偶数时为红色子弹
    }

    EnemyBullet(int x, int y, int xSpeed, int ySpeed, int bulletType) {
        super(20, 20, x, y, xSpeed, ySpeed);
        this.bulletType = bulletType % 2; // 偶数时为红色子弹
    }

    // 实例代码块
    {
        this.setCollide(1, 10, 18, 5, 1);
        this.setCollide(2, 18, 10, 1, 5);
    }

    public int getBulletType() {
        return bulletType % 2;
    }

    public boolean isRedBullet() {
        return bulletType == RED_BULLET;
    }

    public boolean isBlueBullet() {
        return bulletType == BLUE_BULLET;
    }

    @Override
    public BufferedImage getImage() {
        if (this.isAlive()) {
            if (this.isRedBullet()) {
                return Images.enemyBullet[0];
            } else {
                return Images.enemyBullet[1];
            }
        } else {
            return null;
        }
    }
}
