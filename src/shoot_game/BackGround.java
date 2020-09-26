package shoot_game;

import java.awt.image.BufferedImage;

/**
 * 背景类
 * 
 * @author SnobbyVirus1973
 */
public class BackGround extends FlyingObject {
    protected int y2;
    protected int y3;

    BackGround() {
        super(512, 768, 0, 0, 0, 1);
        this.y2 = this.height;
        this.y3 = -this.height;
    }

    public int getY1() {
        return this.y;
    }

    public int getY2() {
        return this.y2;
    }

    public int getY3() {
        return this.y3;
    }

    @Override
    public void step() {
        this.y = resetLocation(y) + 1 * this.ySpeed;
        this.y2 = resetLocation(y2) + 1 * this.ySpeed;
        this.y3 = resetLocation(y3) + 1 * this.ySpeed;
    }

    /**
     * 当背景移出窗口时 将背景移动至最上面
     * 
     * @param y:窗口原点的Y坐标
     * @return 窗口原点应该出现的坐标
     */
    public int resetLocation(int y) {
        return y >= World.HEIGHT ? World.HEIGHT - this.height * 3 : y;
    }

    @Override
    public BufferedImage getImage() {
        return Images.backGround;
    }
}
