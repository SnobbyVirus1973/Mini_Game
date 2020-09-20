package shoot_game;

import java.util.Random;

public abstract class Enemy extends FlyingObject{
	private int shootType;
	private int life;
	
	static Random random = new Random();
	
	Enemy(int width, int height, int life){
		this(width, height, random.nextInt(World.WIDTH - width + 1), life);
	}
	
	Enemy(int width, int height, int x, int life){
		this(width, height, x, 0, 3, life);
	}
	
	Enemy(int width, int height, int x, int xSpeed, int ySpeed, int life){
		super(width, height, x, -height, xSpeed, ySpeed);
		this.life = life;
		this.shootType = random.nextInt(2);
	}
	
	protected int shootIndex = 0;
	public EnemyBullet[] shoot() {
		EnemyBullet[] bullets = new EnemyBullet[1];
		bullets[0] = new EnemyBullet(this.x + this.width/2, this.y + this.height/2, shootType);
		return bullets;
	}

	public void lostLife() {
		this.life--;
		if(this.life<=0) {
			this.setDead();
		}
	}
	
	public void step() {
		this.x = this.x + 1*this.xSpeed;
		this.y = this.y + 1*this.ySpeed;
		
		if(this.x<=0 || this.x+this.width>=World.WIDTH) {
			this.xSpeed = -this.xSpeed;
		}
	}
	
	protected int levelTime;
	public abstract void levelAction(int i);
	
	public int getShootType() {
		return shootType;
	}

	public void setShootType(int shootType) {
		this.shootType = shootType%2;//偶数时为红色子弹
	}
}
