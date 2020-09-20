package shoot_game;

import java.awt.image.BufferedImage;

public class HeroBullet extends Bullet {
	
	HeroBullet(int x, int y) {
		super(8, 20, x, y, 0, -7);
	}

	//实例代码块
	{
		this.setCollide(1, 8, 20, 0, 0);
		this.setCollide(2, 8, 20, 0, 0);
	}
	
	public BufferedImage getImage() {
		if(this.isLife()) {
			return Image.heroBullet;
		}else {
			return null;
		}
	}
}
