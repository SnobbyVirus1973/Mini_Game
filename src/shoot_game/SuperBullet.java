package shoot_game;

import java.awt.image.BufferedImage;

public class SuperBullet extends Bullet {

	SuperBullet(int x, int y) {
		super(48, 120, x, y, 0, -3);
	}
	
	//实例代码块
	{
		this.setCollide(1, 48, 100, 0, 0);
		this.setCollide(2, 48, 100, 0, 0);
	}

	private int index = 0;
	public BufferedImage getImage() {
		index++;
		return Image.superBullet[index%2];
	}

}
