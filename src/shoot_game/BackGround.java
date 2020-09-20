package shoot_game;

import java.awt.image.BufferedImage;

public class BackGround extends FlyingObject{
	protected int y2;
	protected int y3;
	
	BackGround() {
		super(420, 700, 0, 0, 0, 1);
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
	
	public void step() {
		this.y = resetLocation(y) + 1*this.ySpeed;
		this.y2 = resetLocation(y2) + 1*this.ySpeed;
		this.y3 = resetLocation(y3) + 1*this.ySpeed;
	}
	
	public int resetLocation(int y) {
		return y>=World.HEIGHT ? World.HEIGHT-this.height*3 : y;
	}
	
	public BufferedImage getImage() {
		return Image.backGround;
	}
}
