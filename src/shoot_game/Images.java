package shoot_game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Images {
    public static BufferedImage[] sky = new BufferedImage[3];
    public static BufferedImage backGround;
    public static BufferedImage hud;
    public static BufferedImage heroBullet;
    public static BufferedImage[] enemyBullet = new BufferedImage[2];
    public static BufferedImage[] superBullet = new BufferedImage[11];
    public static BufferedImage[] heroAirplane = new BufferedImage[2];
    public static BufferedImage[] enemy = new BufferedImage[4];
    public static BufferedImage[] bom = new BufferedImage[4];
    public static BufferedImage lifeIcon;
    public static BufferedImage start;
    public static BufferedImage pause;
    public static BufferedImage gameOver;
    public static BufferedImage power;
    public static BufferedImage items;
    public static BufferedImage itemMissile;
    public static BufferedImage itemScore;
    public static BufferedImage itemDoubleScore;
    public static BufferedImage itemShootLevelUp;
    public static BufferedImage itemLife;

    static {
        for (int i = 0; i < sky.length; i++) {
            sky[i] = loadImage("sky" + i + ".png");
        }
        backGround = loadImage("background.jpg");
        hud = loadImage("hud.png");
        heroBullet = loadImage("bullet.png");
        enemyBullet[0] = loadImage("bullet_red.png");
        enemyBullet[1] = loadImage("bullet_blue.png");
        for (int i = 0; i < superBullet.length; i++) {
            superBullet[i] = loadImage("bullet_super" + File.separator + i + ".png");
        }
        heroAirplane[0] = loadImage("hero_red.png");
        heroAirplane[1] = loadImage("hero_blue.png");
        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = loadImage("enemy0" + i + ".png");
        }
        for (int i = 0; i < bom.length; i++) {
            bom[i] = loadImage("bom" + i + ".png");
        }
        lifeIcon = loadImage("life.png");
        start = loadImage("start.png");
        pause = loadImage("pause.png");
        gameOver = loadImage("gameover.png");
        power = loadImage("recharge.png");

        items = loadImage("items.png"); // 512*329
        itemMissile = items.getSubimage(129, 83, 78, 50);
        itemScore = items.getSubimage(373, 0, 78, 50);
        itemDoubleScore = items.getSubimage(0, 104, 78, 50);
        itemShootLevelUp = items.getSubimage(212, 76, 78, 50);
        itemLife = items.getSubimage(211, 0, 80, 74);
    }

    public static BufferedImage loadImage(String imageFileName) {
        try {
            BufferedImage img = ImageIO.read(Images.class.getResource("image" + File.separator + imageFileName));
            return img;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
