package shoot_game;

import java.awt.image.BufferedImage;

public class HeroAirplane extends FlyingObject{
	private int life = 3;
	private int shapeType = 0;
	private int power = 0;
	
	HeroAirplane(){
		super(75, 75, 170, 500, 4, 4);
	}
	
	//实例代码块
	{
		this.setCollide(1, 19, 75, 28, 0);
		this.setCollide(2, 75, 32, 0, 32);
	}
	
	public void step() {
		
	}

	public HeroBullet[] shoot() {
		int xOffset = this.width/4;
		int yOffset = this.height/2;
		HeroBullet[] heroBullet = new HeroBullet[2];
		heroBullet[0] = new HeroBullet(this.x+1*xOffset, this.y+yOffset);
		heroBullet[1] = new HeroBullet(this.x+3*xOffset, this.y+yOffset);
		return heroBullet;
	}
	
	public SuperBullet shootSuperBullet() {
		if(this.power>=100) {
			this.power = 0;
			int xOffset = this.width/2;
			int yOffset = this.height/4;
			SuperBullet superBullet = new SuperBullet(this.x+xOffset, this.y+yOffset);
			return superBullet;	
		}else {
			return null;
		}
	}
	
	public void chageShape() {
		shapeType = ++shapeType%2;
	}
	
	public int getShapeType() {
		return shapeType%2;
	}
	
	public void powerRecharge(int i) {
		this.power += i;
		if(this.power>100) this.power = 100;
		//System.out.println("当前火力已充能到" + this.power);
	}
	
	public int getPower() {
		return this.power/10;
	}
	
	public BufferedImage getImage() {
		if(shapeType%2 == 0) {
			return Image.heroAirplane[0];
		}else {
			return Image.heroAirplane[1];
		}
	}
	
	public void eatBullet(EnemyBullet enemyBullet) {
		enemyBullet.setRemove();
		if(this.getShapeType() == enemyBullet.getBulletType()) {
			//System.out.println("吸收了子弹");
			this.powerRecharge(10);
		}else {
			//System.out.println("受到伤害");
			this.lostLife();
		}
	}
	
	public void moveTo(int x, int y) {//目前只能横向纵向和45度角移动//已优化
		x = x-this.width/2;
		y = y-this.height/2;
		if(Math.abs(this.x-x)>1) {
			this.x = (int)(this.xSpeed * (x-this.x) / Math.sqrt((y-this.y)*(y-this.y)+(x-this.x)*(x-this.x)) + this.x);
		}
		if(Math.abs(this.y-y)>1) {
			this.y = (int)(this.ySpeed * (y-this.y) / Math.sqrt((y-this.y)*(y-this.y)+(x-this.x)*(x-this.x)) + this.y);

		}
		/*
		if(this.x!=x) {
			if(x>this.x) {
				if(x>this.x+this.xSpeed) this.x = this.x+this.xSpeed;
				else this.x++;
			}else {
				if(x<this.x-this.xSpeed) this.x = this.x-this.xSpeed;
				else this.x--;
			}
		}
		if(this.y!=y) {
			if(y>this.y) {
				if(y>this.y+this.ySpeed) this.y = this.y+this.ySpeed;
				else this.y++;
			}else {
				if(y<this.y-this.ySpeed) this.y = this.y-this.ySpeed;
				else this.y--;
			}
		}
		*/
	}
	
	public int getLife() {
		return this.life;
	}
	
	public void addLife() {
		if(this.life<3) {
			this.life++;
		}
	}
	
	public void lostLife() {
		this.life--;
		if(this.life == 0) this.setDead();
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
