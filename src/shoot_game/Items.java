package shoot_game;

import java.awt.image.BufferedImage;

public class Items extends FlyingObject {
    private int awardType;

    public Items(int x, int y, int awardType) {
        super(78, 50, x - 39, y - 25, 0, 2);
        // 传入的x、y坐标减去长、宽的一半，使道具的正中心出现在x、y位置，而不是左上角
        this.setAwardType(awardType);
    }

    // 实例代码块
    {
        this.setCollide(1, 78, 50, 0, 0);
        this.setCollide(2, 78, 50, 0, 0);
    }

    public int getAwardType() {
        return awardType;
    }

    public void setAwardType(int awardType) {
        this.awardType = awardType;
    }

    @Override
    public void step() {
        this.y = this.y + 1 * this.ySpeed;
    }

    @Override
    public BufferedImage getImage() {
        if (this.isAlive()) {
            BufferedImage image = null;
            switch (this.getAwardType()) {
                case EnemyAwardItem.MISSILE:
                    image = Images.itemMissile;
                    break;
                case EnemyAwardItem.SCORE:
                    image = Images.itemScore;
                    break;
                case EnemyAwardItem.DOUBLE_SCORE:
                    image = Images.itemDoubleScore;
                    break;
                case EnemyAwardItem.SHOOT_LEVEL_UP:
                    image = Images.itemShootLevelUp;
                    break;
                case EnemyAwardItem.LIFE:
                    image = Images.itemLife;
                    break;
                default:
            }
            return image;
        }
        return null;
    }
}
