package shoot_game;

import java.awt.image.BufferedImage;

public class HeroAirplane extends FlyingObject {
    private int life = 3;
    private int shapeType = 0;
    private int power = 0;
    private int shootLevel = 1;
    private int tripleBulletTime = 0;

    // 实例代码块
    {
        this.setCollide(1, 19, 75, 28, 0);
        this.setCollide(2, 75, 32, 0, 32);
    }

    HeroAirplane() {
        super(75, 75, 170, 500, 6, 6);
    }

    @Override
    public void step() {
    }

    public HeroBullet[] shoot() {
        int xOffset = this.width / 4;
        int yOffset = this.height / 2;
        HeroBullet[] heroBullet = null;
        if (tripleBulletTime <= 0) {
            switch (shootLevel) {
                case 1:
                    heroBullet = new HeroBullet[1];
                    heroBullet[0] = new HeroBullet(this.x + 2 * xOffset, this.y + yOffset);
                    break;
                case 2:
                    heroBullet = new HeroBullet[2];
                    heroBullet[0] = new HeroBullet(this.x + xOffset, this.y + yOffset);
                    heroBullet[1] = new HeroBullet(this.x + 3 * xOffset, this.y + yOffset);
                    break;
            }
        } else {
            tripleBulletTime--;
            heroBullet = new HeroBullet[3];
            heroBullet[0] = new HeroBullet(this.x + xOffset, this.y + yOffset);
            heroBullet[1] = new HeroBullet(this.x + 2 * xOffset, this.y + yOffset);
            heroBullet[2] = new HeroBullet(this.x + 3 * xOffset, this.y + yOffset);
        }
        return heroBullet;
    }

    public SuperBullet shootSuperBullet() {
        if (this.power >= 100) {
            this.power = 0;
            int xOffset = this.width / 2;
            int yOffset = this.height / 4;
            return new SuperBullet(this.x + xOffset, this.y + yOffset);
        } else {
            return null;
        }
    }

    public void chageShape() {
        shapeType = ++shapeType % 2;
    }

    public int getShapeType() {
        return shapeType % 2;
    }

    public void powerRecharge(int i) {
        this.power += i;
        if (this.power > 100)
            this.power = 100;
        // System.out.println("当前火力已充能到" + this.power);
    }

    public int getPower() {
        return this.power / 10;
    }

    public void clearPower(){
        this.power = 0;
    }

    @Override
    public BufferedImage getImage() {
        if (shapeType % 2 == 0) {
            return Images.heroAirplane[0];
        } else {
            return Images.heroAirplane[1];
        }
    }

    public void eatBullet(EnemyBullet enemyBullet) {
        enemyBullet.setRemove();
        if (this.getShapeType() == enemyBullet.getBulletType()) {
            // System.out.println("吸收了子弹");
            this.powerRecharge(10);
        } else {
            // System.out.println("受到伤害");
            this.lostLife();
        }
    }

    public void moveTo(int x, int y) {// 目前只能横向纵向和45度角移动//已优化
        x = x - this.width / 2;
        y = y - this.height / 2;
        if (Math.abs(this.x - x) > 1) {
            this.x = (int) (this.xSpeed * (x - this.x)
                    / Math.sqrt((y - this.y) * (y - this.y) + (x - this.x) * (x - this.x)) + this.x);
        }
        if (Math.abs(this.y - y) > 1) {
            this.y = (int) (this.ySpeed * (y - this.y)
                    / Math.sqrt((y - this.y) * (y - this.y) + (x - this.x) * (x - this.x)) + this.y);

        }
        /*
         * if(this.x!=x) { if(x>this.x) { if(e.getKeyCode()==KeyEvent.VK_UP) {
         *
         * } if(x>this.x+this.xSpeed) this.x = this.x+this.xSpeed; else this.x++; }else
         * { if(x<this.x-this.xSpeed) this.x = this.x-this.xSpeed; else this.x--; } }
         * if(this.y!=y) { if(y>this.y) { if(y>this.y+this.ySpeed) this.y =
         * this.y+this.ySpeed; else this.y++; }else { if(y<this.y-this.ySpeed) this.y =
         * this.y-this.ySpeed; else this.y--; } }
         */
    }

    public void move(int x, int y) {
        this.x += x * this.xSpeed;
        this.y += y * this.ySpeed;
        if (this.x < 0) this.x = 0;
        if (this.x > World.WIDTH - this.width) this.x = World.WIDTH - this.width;
        if (this.y < 0) this.y = 0;
        if (this.y > World.HEIGHT - this.height) this.y = World.HEIGHT - this.height;
    }

    public int getLife() {
        return this.life;
    }

    public void addLife() {
        if (this.life < 3) {
            this.life++;
        }
    }

    public void lostLife() {
        this.life--;
        this.clearPower();
        this.resetShootLevel();
        if (this.life == 0)
            this.setDead();
    }

    public void shootLevelUp() {
        if (shootLevel < 2) {
            this.shootLevel++;
        }else {
            this.tripleBulletTime = 100;
        }
    }

    public void resetShootLevel() {
        this.shootLevel = 1;
        this.tripleBulletTime = 0;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }
}
