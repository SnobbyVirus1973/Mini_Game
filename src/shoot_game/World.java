package shoot_game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class World extends JPanel {
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	private static int gameState = START;
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 420;
	public static final int HEIGHT = 800;
	private int mouseX;
	private int mouseY;
	private Sky sky = new Sky();
	private BackGround backGround = new BackGround();
	private HeroAirplane heroAirplane = new HeroAirplane();
	private Enemy[] enemies = {};
	private HeroBullet[] heroBullets = {};
	private SuperBullet[] superBullets = {};
	private EnemyBullet[] enemyBullets = {};
	private int score = 0;
	
	private void action() {
		MouseAdapter l = new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1) {
					if(gameState == GAME_OVER) {
						restart();
					}else if(gameState != RUNNING) {
						gameState = RUNNING;
					}else {
						heroShootSuperBullet();
					}
				}
				if(e.getButton()==3) {
					heroAirplane.chageShape();
				}
			}
			public void mouseMoved(MouseEvent e){
				mouseX = e.getX();
				mouseY = e.getY();
			}
			public void mouseExited(MouseEvent e){
				if(gameState==RUNNING){
					gameState = PAUSE;
				}
			}
		};
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if(gameState == RUNNING) step(false);
				repaint();
			}
		}, 10, 10);
	}
	
	private void restart() {
		this.sky = new Sky();
		this.backGround = new BackGround();
		this.heroAirplane = new HeroAirplane();
		this.enemies = new Enemy[0];
		this.heroBullets = new HeroBullet[0];
		this.enemyBullets = new EnemyBullet[0];
		gameState = RUNNING;
		this.score = 0;
	}
	
	private int enemyEnterIndex = 0;
	private void enemyRandomEnter() {
		enemyEnterIndex++;
		if(enemyEnterIndex%50==0) {
			Enemy newEnemy;
			double randomEnemy = Math.random();
			if(randomEnemy<0.1) {
				newEnemy = new Enemy03();
			}else if(randomEnemy<0.4) {
				newEnemy = new Enemy02();
			}else if(randomEnemy<0.5){
				newEnemy = new Enemy01();
			}else {
				newEnemy = new Enemy00();
			}
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1] = newEnemy;
		}
	}
	
	private void hitCheck() {
		for(Enemy i : enemies) {
			if(i.isLife() && i.hit(heroAirplane)) {
				//System.out.println("撞上了");
				i.setDead();
				heroAirplane.lostLife();
				if(!heroAirplane.isLife()) {
					//System.out.println("Game Over");
					gameState = GAME_OVER;
				}
			}
		}	
	}
	
	private int heroShootIndex = 0;
	private void heroShoot() {
		heroShootIndex++;
		if(heroShootIndex%60==0) {
			HeroBullet[] newHeroBullets = heroAirplane.shoot();
			heroBullets = Arrays.copyOf(heroBullets, heroBullets.length+newHeroBullets.length);
			System.arraycopy(newHeroBullets, 0, heroBullets, heroBullets.length-newHeroBullets.length, newHeroBullets.length);
		}
	}
	
	private void heroShootSuperBullet() {
		SuperBullet newSuperBullet = heroAirplane.shootSuperBullet();
		if(newSuperBullet != null) {
			superBullets = Arrays.copyOf(superBullets, superBullets.length+1);
			superBullets[superBullets.length-1] = newSuperBullet;
			//System.out.println("发射超极武器");
		}
	}
	
	private void enemyShoot() {
		for(Enemy i : enemies) {
			if(i.isLife()) {
				EnemyBullet[] newEnemyBullets = i.shoot();
				if(newEnemyBullets != null) {
					enemyBullets = Arrays.copyOf(enemyBullets, enemyBullets.length+newEnemyBullets.length);
					System.arraycopy(newEnemyBullets, 0, enemyBullets, enemyBullets.length-newEnemyBullets.length, newEnemyBullets.length);
				}
			}
		}
	}

	private void heroGetBullet() {
		for(EnemyBullet i : enemyBullets) {
			if(i.isLife() && heroAirplane.hit(i)) {
				heroAirplane.eatBullet(i);
				if(!heroAirplane.isLife()) {
					//System.out.println("Game Over");
					gameState = GAME_OVER;
				}
			}
		}
	}
	
	private void enemyGetBullet() {
		for(Enemy i : enemies) {
			if(i.isLife()) {
				for(HeroBullet j : heroBullets) {
					if(j.isLife() && i.hit(j)) {
						i.lostLife();
						j.setRemove();
						if(i.isDead()) {
							if(i instanceof Enemy00) {
								this.score += 2;
								//System.out.println("分数加2，当前分数为" + this.score);
							}else if(i instanceof Enemy01) {
								heroAirplane.powerRecharge(10);
							}else if(i instanceof Enemy02) {
								this.score += 5;
								//System.out.println("分数加5，当前分数为" + this.score);
							}else if(i instanceof Enemy03) {
								this.score += 10;
								//System.out.println("分数加10，当前分数为" + this.score);
							}
						}
					}
				}
			}
		}
	}
	
	private void enemyGetSuperBullet() {
		for(Enemy i : enemies) {
			if(i.isLife()) {
				for(SuperBullet j : superBullets) {
					if(j.isLife() && i.hit(j)) {
						i.setDead();
						if(i instanceof Enemy00) {
							this.score += 2;
							//System.out.println("分数加2，当前分数为" + this.score);
						}else if(i instanceof Enemy01) {
							heroAirplane.powerRecharge(10);
						}else if(i instanceof Enemy02) {
							this.score += 5;
							//System.out.println("分数加5，当前分数为" + this.score);
						}else if(i instanceof Enemy03) {
							this.score += 10;
							//System.out.println("分数加10，当前分数为" + this.score);
						}
					}
				}
			}
		}
	}
	
	private void outOfBoundsCheck() {
		Enemy[] enemyLive = new Enemy[enemies.length];
		int enemyLiveIndex = 0;
		for(Enemy i : enemies) {
			if(i.isLife() && (i.x >= 0 && i.x + i.width <= World.WIDTH && i.y <= World.HEIGHT)) {
				enemyLive[enemyLiveIndex++] = i;
			}
		}
		enemies = Arrays.copyOf(enemyLive, enemyLiveIndex);
		//System.out.println("当前敌人数"+enemies.length);
		EnemyBullet[] enemyBulletsLive = new EnemyBullet[enemyBullets.length];
		int enemyBulletsIndex = 0;
		for(EnemyBullet i : enemyBullets) {
			if(i.isLife() && (i.x >= 0 && i.x + i.width <= World.WIDTH && i.y >= 0 && i.y <= World.HEIGHT)) {
				enemyBulletsLive[enemyBulletsIndex++] = i;
			}
		}
		enemyBullets = Arrays.copyOf(enemyBulletsLive, enemyBulletsIndex);
		//System.out.println("当前敌人子弹数"+enemyBullets.length);
		HeroBullet[] heroBulletsLive = new HeroBullet[heroBullets.length];
		int heroBulletsIndex = 0;
		for(HeroBullet i : heroBullets) {
			if(i.isLife() && (i.x >= 0 && i.x + i.width <= World.WIDTH && i.y >= 0)) {
				heroBulletsLive[heroBulletsIndex++] = i;
			}
		}
		heroBullets = Arrays.copyOf(heroBulletsLive, heroBulletsIndex);
		//System.out.println("当前子弹数"+heroBullets.length);
		SuperBullet[] superBulletsLive = new SuperBullet[superBullets.length];
		int superBulletsIndex = 0;
		for(SuperBullet i : superBullets) {
			if(i.isLife() && (i.x >= 0 && i.x + i.width <= World.WIDTH && i.y >= 0)) {
				superBulletsLive[superBulletsIndex++] = i;
			}
		}
		superBullets = Arrays.copyOf(superBulletsLive, superBulletsIndex);
		//System.out.println("当前超级子弹数"+superBullets.length);
	}
	
	private int levelTime;
	private void level() {
		levelTime++;
		Enemy[] newEnemies = null;
		if(levelTime%500 == 0) {
			switch ((int)(Math.random()*10)) {
			case 0:
			case 1:
			case 2:
				newEnemies = new Enemy[5];
				newEnemies[0] = new Enemy00(10);
				newEnemies[1] = new Enemy00(100);
				newEnemies[2] = new Enemy00(190);
				newEnemies[3] = new Enemy00(280);
				newEnemies[4] = new Enemy00(350);
				newEnemies[1].y = -newEnemies[1].height*2;
				newEnemies[3].y = -newEnemies[3].height*2;
				break;
			case 3:
			case 4:
				newEnemies = new Enemy[5];
				newEnemies[0] = new Enemy00(10);
				newEnemies[1] = new Enemy02(100);
				newEnemies[2] = new Enemy00(190);
				newEnemies[3] = new Enemy02(280);
				newEnemies[4] = new Enemy00(350);
				newEnemies[1].y = -newEnemies[1].height*2;
				newEnemies[3].y = -newEnemies[3].height*2;
				break;
			case 5:
			case 6:
				newEnemies = new Enemy[4];
				newEnemies[0] = new Enemy02(30);
				newEnemies[1] = new Enemy02(130);
				newEnemies[2] = new Enemy02(230);
				newEnemies[3] = new Enemy02(330);
				break;
			case 7:
				newEnemies = new Enemy[3];
				newEnemies[0] = new Enemy02(10);
				newEnemies[1] = new Enemy03(190);
				newEnemies[2] = new Enemy02(350);
				break;
			case 8:
				newEnemies = new Enemy[2];
				newEnemies[0] = new Enemy03(100);
				newEnemies[1] = new Enemy03(280);
				newEnemies[1].setShootType(newEnemies[0].getShootType()+1);
				break;
			case 9:
				newEnemies = new Enemy[3];
				newEnemies[0] = new Enemy03(10);
				newEnemies[1] = new Enemy03(190);
				newEnemies[2] = new Enemy03(350);
				newEnemies[1].setShootType(newEnemies[0].getShootType()+1);
				newEnemies[2].setShootType(newEnemies[0].getShootType());
				break;
			}
		}else if(levelTime%1200 == 0) {
			newEnemies = new Enemy[2];
			newEnemies[0] = new Enemy01(100);
			newEnemies[1] = new Enemy01(280);
		}
		if(newEnemies != null) {
			enemies = Arrays.copyOf(enemies, enemies.length+newEnemies.length);
			System.arraycopy(newEnemies, 0, enemies, enemies.length-newEnemies.length, newEnemies.length);
		}
		for(Enemy i : enemies) {
			i.levelAction(1);
		}
	}
	
	private void step(boolean isRandom) {
		sky.step();
		backGround.step();
		heroAirplane.moveTo(mouseX, mouseY);
		for(HeroBullet i : heroBullets) {
			i.step();
		}
		for(SuperBullet i : superBullets) {
			i.step();
		}
		for(EnemyBullet i : enemyBullets) {
			i.step();
		}
		hitCheck();
		heroShoot();
		outOfBoundsCheck();
		heroGetBullet();
		enemyGetBullet();
		enemyGetSuperBullet();
		enemyShoot();
		for(Enemy i : enemies) {
			i.step();
		}
		if(isRandom) {
			enemyRandomEnter();
		}else {
			level();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(backGround.getImage(), backGround.x, backGround.getY1(), null);
		g.drawImage(backGround.getImage(), backGround.x, backGround.getY2(), null);
		g.drawImage(backGround.getImage(), backGround.x, backGround.getY3(), null);
		g.drawImage(sky.getImage(0), sky.x, sky.getY1(), null);
		g.drawImage(sky.getImage(1), sky.x, sky.getY2(), null);
		g.drawImage(sky.getImage(2), sky.x, sky.getY3(), null);
		
		for(Enemy i : enemies) {
			g.drawImage(i.getImage(), i.x, i.y, null);
		}
		g.drawImage(heroAirplane.getImage(), heroAirplane.getX(), heroAirplane.getY(), null);
		for(HeroBullet i : heroBullets) {
			g.drawImage(i.getImage(), i.x, i.y, null);
		}
		for(EnemyBullet i : enemyBullets) {
			g.drawImage(i.getImage(), i.x, i.y, null);
		}
		for(SuperBullet i : superBullets) {
			g.drawImage(i.getImage(), i.x, i.y, null);
		}
		
		for(int i=1; i<=heroAirplane.getPower(); i++) {
			g.drawImage(Image.power, WIDTH-14-14*i, HEIGHT-95, null);
		}
		
		g.drawImage(Image.hud, 0, 60, null);

		for(int i=0; i<heroAirplane.getLife(); i++) {
			g.drawImage(Image.lifeIcon, 20+(30+15)*i, HEIGHT-90, null);
		}

		g.drawString("SCORE: "+this.score,10,25);
		
		if (gameState == START) {
			g.drawImage(Image.start, 0, 0, null);
		} else if (gameState == PAUSE) {
			g.drawImage(Image.pause, 0, 0, null);
		} else if (gameState == GAME_OVER) {
			g.drawImage(Image.gameOver, 0, 0, null);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(World.WIDTH, World.HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		world.action();
	}
}
