package shoot_game;

import java.awt.image.BufferedImage;

public class Enemy03 extends Enemy{
	
	Enemy03() {
		super(58, 90, 4);
	}
	
	Enemy03(int x){
		super(58, 90, x, 4);
	}
	
	//实例代码块
	{
		this.setCollide(1, 30, 90, 9, 0);
		this.setCollide(2, 58, 30, 0, 0);
		this.setShootType(random.nextInt(2));
	}
	
	
	private int shootIndex = 0;
	public EnemyBullet[] shoot() {
		shootIndex++;
		if(shootIndex%150==0) {
			int x = this.x + this.width/2;
			int y = this.y + this.height/2;
			EnemyBullet[] bullets = new EnemyBullet[8];
			bullets[0] = new EnemyBullet(x, y, 4, 0, this.getShootType());
			bullets[1] = new EnemyBullet(x, y, -4, 0, this.getShootType());
			bullets[2] = new EnemyBullet(x, y, 0, 4, this.getShootType());
			bullets[3] = new EnemyBullet(x, y, 0, -4, this.getShootType());
			bullets[4] = new EnemyBullet(x, y, 3, 3, this.getShootType());
			bullets[5] = new EnemyBullet(x, y, 3, -3, this.getShootType());
			bullets[6] = new EnemyBullet(x, y, -3, 3, this.getShootType());
			bullets[7] = new EnemyBullet(x, y, -3, -3, this.getShootType());
			return bullets;
		}
		return null;
	}
	
	public void levelAction(int i) {
		this.levelTime++;
		switch(i) {
		case 1:
			if(this.levelTime <= 50) {
				this.ySpeed = 3;
			}else if(this.levelTime == 75) {
				this.ySpeed = 0;
				this.shootIndex = 149;
			}else if(this.levelTime == 90) {
				this.ySpeed = 0;
				this.shootIndex = 149;
			}else if(this.levelTime == 105) {
				this.shootIndex = 149;
			}else if(this.levelTime == 200){
				this.ySpeed = 2;
			}else if(this.levelTime == 400){
				this.ySpeed = 3;
			}
		}
	}
	
	private int bomIndex = 0;
	public BufferedImage getImage() {
		if(this.isLife()) {
			return Image.enemy[3];
		}else if(this.isDead()){
			if(bomIndex < Image.bom.length) {
				return Image.bom[bomIndex++];
			}else {
				this.setRemove();
				return null;
			}
		}else {
			return null;
		}
	}

}
