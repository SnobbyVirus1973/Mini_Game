package shoot_game;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
	private final static int LIFE = 0;
	private final static int DEAD = 1;
	private final static int REMOVE = 2;
	private int state = LIFE;
	
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected int xSpeed;
	protected int ySpeed;
	
	//设置碰撞体，一共两个，width和height代表碰撞体的宽和高，Offset代表碰撞体左上顶点距离对象左上原点的偏移量
	protected int collide1Width;
	protected int collide1Height;
	protected int collide1xOffset;
	protected int collide1yOffset;
	protected int collide2Width;
	protected int collide2Height;
	protected int collide2xOffset;
	protected int collide2yOffset;
	
	FlyingObject(int width, int height, int x, int y, int xSpeed, int ySpeed){
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
	}
	
	public abstract void step();
	
	public abstract BufferedImage getImage();
	
	public void setCollide(int num, int collide_width, int collide_height, int collide_xOffect, int collide_yOffect) {
		switch(num) {
		case 1:
			this.collide1Width = collide_width;
			this.collide1Height = collide_height;
			this.collide1xOffset = collide_xOffect;
			this.collide1yOffset = collide_yOffect;
			break;
		case 2:
			this.collide2Width = collide_width;
			this.collide2Height = collide_height;
			this.collide2xOffset = collide_xOffect;
			this.collide2yOffset = collide_yOffect;
			break;
		default:
			System.out.println("输入数字错误");
		}
	}
	
	public boolean hit(FlyingObject other) {
		int x11 = this.x + this.collide1xOffset;
		int y11 = this.y + this.collide1yOffset;
		int w11 = this.collide1Width;
		int h11 = this.collide1Height;
		int x12 = this.x + this.collide2xOffset;
		int y12 = this.y + this.collide2yOffset;
		int w12 = this.collide2Width;
		int h12 = this.collide2Height;
		
		int x21 = other.x + other.collide1xOffset;
		int y21 = other.y + other.collide1yOffset;
		int w21 = other.collide1Width;
		int h21 = other.collide1Height;		
		int x22 = other.x + other.collide2xOffset;
		int y22 = other.y + other.collide2yOffset;
		int w22 = other.collide2Width;
		int h22 = other.collide2Height;
		
		//11-21
		//12-21
		//11-22
		//12-22
		if((x11+w11>=x21 && x11<=x21+w21 && y11+h11>=y21 && y11<=y21+h21) ||
			(x12+w12>=x21 && x12<=x21+w21 && y12+h12>=y21 && y12<=y21+h21) ||
			(x12+w12>=x22 && x12<=x22+w22 && y12+h12>=y22 && y12<=y22+h22) ||
			(x11+w11>=x22 && x11<=x22+w22 && y11+h11>=y22 && y11<=y22+h22)) return true;
		else return false;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public int getXSpeed() {
		return xSpeed;
	}
	
	public void setYSpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public int getYSpeed() {
		return ySpeed;
	}

	public boolean isLife() {
		return state == LIFE;
	}
	
	public boolean isDead() {
		return state == DEAD;
	}
	
	public boolean isRemove() {
		return state == REMOVE;
	}
	
	public void setDead() {
		state = DEAD;
	}
	
	public void setRemove() {
		state = REMOVE;
	}
}
