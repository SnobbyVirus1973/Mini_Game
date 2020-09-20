package shoot_game;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
	public static BufferedImage[] sky = new BufferedImage[3];
	public static BufferedImage backGround;
	public static BufferedImage hud;
	public static BufferedImage heroBullet;
	public static BufferedImage[] enemyBullet = new BufferedImage[2];
	public static BufferedImage[] superBullet = new BufferedImage[2];
	public static BufferedImage[] heroAirplane = new BufferedImage[2];
	public static BufferedImage[] enemy = new BufferedImage[4];
	public static BufferedImage[] bom = new BufferedImage[4];
	public static BufferedImage lifeIcon;
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage gameOver;
	public static BufferedImage power;
	
	static {
		for(int i=0; i<sky.length; i++) {
			sky[i] = loadImage("sky"+i+".png");
		}
		backGround = loadImage("background.png");
		hud = loadImage("hud.png");
		heroBullet = loadImage("bullet.png");
		enemyBullet[0] = loadImage("bullet_red.png");
		enemyBullet[1] = loadImage("bullet_blue.png");
		superBullet[0] = loadImage("bullet_super0.png");
		superBullet[1] = loadImage("bullet_super1.png");
		heroAirplane[0] = loadImage("hero_red.png");
		heroAirplane[1] = loadImage("hero_blue.png");
		for(int i=0; i<enemy.length; i++) {
			enemy[i] = loadImage("enemy0"+i+".png");
		}
		for(int i=0; i<bom.length; i++) {
			bom[i] = loadImage("bom"+i+".png");
		}
		lifeIcon = loadImage("life.png");
		start = loadImage("start.png");
		pause = loadImage("pause.png");
		gameOver = loadImage("gameover.png");
		power = loadImage("recharge.png");
	}
	
	public static BufferedImage loadImage(String imageFileName) {
		try{
			BufferedImage img = ImageIO.read(Image.class.getResource("image//"+imageFileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
