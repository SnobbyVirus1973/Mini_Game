package shoot_game;

import java.awt.image.BufferedImage;

public class Sky extends BackGround{
	
	Sky(){
		super();
		this.ySpeed = 2;
	}
	
	public BufferedImage getImage(int i) {
		return Image.sky[i];
	}
}
