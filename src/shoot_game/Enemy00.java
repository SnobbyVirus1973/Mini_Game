package shoot_game;

import java.awt.image.BufferedImage;

public class Enemy00 extends Enemy {

    Enemy00() {
        super(52, 50, 4);
    }

    Enemy00(int x) {
        super(52, 50, x, 4);
    }

    // 实例代码块
    {
        this.setCollide(1, 20, 50, 16, 0);
        this.setCollide(2, 52, 20, 0, 0);
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
    public int getScore() {
        return 2;
    }

    @Override
    public void levelAction(int i) {
        this.levelTime++;
        switch (i) {
        case 1:
            if (this.levelTime <= 50) {
                this.ySpeed = 3;
            } else if (this.levelTime == 75) {
                this.ySpeed = 0;
                this.shootIndex = 99;
            } else if (this.levelTime == 90) {
                this.ySpeed = 0;
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

    private int bomIndex = 0;

    @Override
    public BufferedImage getImage() {
        if (this.isAlive()) {
            return Images.enemy[0];
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
