package shoot_game;

import java.awt.image.BufferedImage;

public class Enemy01 extends Enemy implements EnemyAwardItem {

    Enemy01() {
        super(58, 50, 4);
        this.xSpeed = this.x > (World.WIDTH - this.width) / 2 ? -1 : 1;
    }

    Enemy01(int x) {
        super(58, 50, x, 4);
        this.xSpeed = (this.x + this.width / 2) > World.WIDTH / 2 ? -1 : 1;
    }

    // 实例代码块
    {
        this.setCollide(1, 24, 50, 17, 0);
        this.setCollide(2, 58, 24, 0, 0);
    }

    @Override
    public EnemyBullet[] shoot() {
        shootIndex++;
        if (shootIndex % 100 == 0) {
            return super.shoot();
        } else {
            return null;
        }
    }

    @Override
    public void levelAction(int i) {
        this.levelTime++;
        switch (i) {
            case 1:
                if (this.levelTime <= 50) {
                    this.ySpeed = 3;
                } else if (this.levelTime == 75) {
                    this.shootIndex = 99;
                } else if (this.levelTime == 90) {
                    this.shootIndex = 99;
                } else if (this.levelTime == 105) {
                    this.shootIndex = 99;
                } else if (this.levelTime == 200) {
                    this.ySpeed = 2;
                } else if (this.levelTime == 400) {
                    this.ySpeed = 3;
                }
        }
    }

    @Override
    public int getScore() {
        return 2;
    }

    private int bomIndex = 0;

    @Override
    public BufferedImage getImage() {
        if (this.isAlive()) {
            return Images.enemy[1];
        } else if (this.isDead()) {
            if (bomIndex < Images.bom.length) {
                return Images.bom[bomIndex++];
            } else {
                this.setRemove();
                return null;
            }
        } else {
            return null;
        }
    }
}
