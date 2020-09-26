package shoot_game;

/**
 * 子弹类 是英雄机和敌人发射的子弹的超类
 * 
 * @author soft01
 */
public abstract class Bullet extends FlyingObject {

    Bullet(int width, int height, int x, int y, int xSpeed, int ySpeed) {
        super(width, height, x - width / 2, y - height / 2, xSpeed, ySpeed);
        // 传入的x、y坐标减去长、宽的一半，使子弹的正中心出现在x、y位置，而不是左上角
    }

    @Override
    public void step() {
        this.x = this.x + (1 * this.xSpeed);
        this.y = this.y + (1 * this.ySpeed);
    }
}
