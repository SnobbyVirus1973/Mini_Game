package shoot_game;

import java.awt.image.BufferedImage;

public class Enemy01 extends Enemy{
	
	Enemy01(){
		super(58, 50, 1);
		this.xSpeed = (this.x+this.width/2)>World.WIDTH/2? -1 : 1;

	}
	
	Enemy01(int x){
		super(58, 50, x, 1);
		this.xSpeed = (this.x+this.width/2)>World.WIDTH/2? -1 : 1;
	}
	
	//实例代码块
	{
		this.setCollide(1, 24, 50, 17, 0);
		this.setCollide(2, 58, 24, 0, 0);
	}
	
	public EnemyBullet[] shoot() {
		shootIndex++;
		if(shootIndex%100==0) {
			return super.shoot();
		}else {
			return null;
		}
	}
	
	public void levelAction(int i) {
		this.levelTime++;
		switch(i) {
		case 1:
			if(this.levelTime <= 50) {
				this.ySpeed = 3;
			}else if(this.levelTime == 75) {
				this.shootIndex = 99;
			}else if(this.levelTime == 90) {
				this.shootIndex = 99;
			}else if(this.levelTime == 105) {
				this.shootIndex = 99;
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
			return Image.enemy[1];
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
