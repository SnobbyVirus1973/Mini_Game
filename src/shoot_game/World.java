package shoot_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 窗口类 继承JPanle，用于画游戏窗口。
 *
 * @author SnobbyVirus1973
 */
public class World extends JPanel {
    // 游戏窗口的宽和高
    public static final int WIDTH = 528;
    public static final int HEIGHT = 850;
    private static final long serialVersionUID = 1L;
    // 判定游戏当前状态
    private static final int START = 0;
    private static final int RUNNING = 1;
    private static final int PAUSE = 2;
    private static final int GAME_OVER = 3;
    private static int gameState = START;
    private int moveX;
    private int moveY;

    // 实例化游戏物体
    private Sky sky = new Sky();
    private BackGround backGround = new BackGround();
    private HeroAirplane heroAirplane = new HeroAirplane();
    private Enemy[] enemies = {};
    private HeroBullet[] heroBullets = {};
    private SuperBullet[] superBullets = {};
    private EnemyBullet[] enemyBullets = {};
    private Items[] items = {};

    // 记分
    private int score = 0;
    private double distance = 0.0;
    private int enemyEnterIndex = 0;
    private int heroShootIndex = 0;
    private int levelTime = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        World world = new World();
        frame.add(world);

        // 设置按X关闭程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 设置窗口的宽和高
        frame.setSize(World.WIDTH, World.HEIGHT);
        // 设置窗口居中
        frame.setLocationRelativeTo(null);
        // 设置窗口标题
        frame.setTitle("飞机大战");
        // 设置窗口不能改变大小
        frame.setResizable(false);
        // 获取焦点（获取键盘输入用）
        frame.setFocusable(true);
        // 设置窗口可见
        frame.setVisible(true);
        // 获取焦点（获取键盘输入用）
        world.requestFocus();
        // 执行主方法
        world.action();
    }

    /**
     * 游戏加载的时候调用的主方法 主要功能是获取键盘输入和鼠标输入 并设置定时器，每10毫秒调取一次
     */
    public void action() {
        // 创建键盘监听器
        KeyAdapter k = new KeyAdapter() {
            // 获取按键对应的值
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    gameState = PAUSE;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (gameState == GAME_OVER) {
                        restart();
                    } else if (gameState != RUNNING) {
                        gameState = RUNNING;
                    } else {
                        heroShootSuperBullet();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    heroAirplane.chageShape();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    moveY = -1;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveY = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    moveX = -1;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveX = 1;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    moveY = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    moveX = 0;
                }
            }
        };
        this.addKeyListener(k);
/*
        // 创建鼠标监听器
        MouseAdapter l = new MouseAdapter() {
            // 鼠标点击事件
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == 1) {
                    if (gameState == GAME_OVER) {
                        restart();
                    } else if (gameState != RUNNING) {
                        gameState = RUNNING;
                    } else {
                        heroShootSuperBullet();
                    }
                }
                if (e.getButton() == 3) {
                    heroAirplane.chageShape();
                }
            }

            // 鼠标移动事件
            public void mouseMoved(MouseEvent e) {
                moveX = e.getX();
                moveY = e.getY();
            }

            // 鼠标移出窗口事件
            public void mouseExited(MouseEvent e) {
                if (gameState == RUNNING) {
                    gameState = PAUSE;
                }
            }
        };
        this.addMouseListener(l);
        this.addMouseMotionListener(l);
*/
        // 创建定时器
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if (gameState == RUNNING)
                    step(true);
                repaint();
            }
        }, 10, 10);
    }

    /**
     * 重新开始游戏时执行的方法 将游戏实例初始化
     */
    public void restart() {
        this.sky = new Sky();
        this.backGround = new BackGround();
        this.heroAirplane = new HeroAirplane();
        this.enemies = new Enemy[0];
        this.heroBullets = new HeroBullet[0];
        this.enemyBullets = new EnemyBullet[0];
        this.items = new Items[0];
        this.score = 0;
        this.distance = 0.0;
        this.doubleScoreTime = 0;
        this.levelTime = 0;
        gameState = START;
    }

    /**
     * 敌人随机出现的实现方法
     */
    public void enemyRandomEnter() {
        enemyEnterIndex++;
        if (enemyEnterIndex % (70 - (int) (distance / 10)) == 0) {
            Enemy newEnemy;
            double randomEnemy = Math.random();
            if (randomEnemy < 0.1) {
                newEnemy = new Enemy03();
            } else if (randomEnemy < 0.4) {
                newEnemy = new Enemy02();
            } else if (randomEnemy < 0.5) {
                newEnemy = new Enemy01();
            } else {
                newEnemy = new Enemy00();
            }
            enemies = Arrays.copyOf(enemies, enemies.length + 1);
            enemies[enemies.length - 1] = newEnemy;
        }
    }

    /**
     * 检查是否发生碰撞的方法
     */
    private void hitCheck() {
        for (Enemy i : enemies) {
            if (i.isAlive() && i.isHit(heroAirplane)) {
                i.setDead();
                heroAirplane.lostLife();
                if (!heroAirplane.isAlive()) {
                    gameState = GAME_OVER;
                }
            }
        }
    }

    /**
     * 英雄机每隔一段时间就发射子弹的方法
     */
    private void heroShoot() {
        heroShootIndex++;
        if (heroShootIndex % 10 == 0) {
            HeroBullet[] newHeroBullets = heroAirplane.shoot();
            heroBullets = Arrays.copyOf(heroBullets, heroBullets.length + newHeroBullets.length);
            System.arraycopy(newHeroBullets, 0, heroBullets, heroBullets.length - newHeroBullets.length,
                    newHeroBullets.length);
        }
    }

    /**
     * 英雄机发射超级子弹的方法
     */
    private void heroShootSuperBullet() {
        SuperBullet newSuperBullet = heroAirplane.shootSuperBullet();
        if (newSuperBullet != null) {
            superBullets = Arrays.copyOf(superBullets, superBullets.length + 1);
            superBullets[superBullets.length - 1] = newSuperBullet;
        }
    }

    /**
     * 敌人发射子弹的方法
     */
    private void enemyShoot() {
        for (Enemy i : enemies) {
            if (i.isAlive() && i.getY() + i.height < World.HEIGHT) {
                EnemyBullet[] newEnemyBullets = i.shoot();
                if (newEnemyBullets != null) {
                    enemyBullets = Arrays.copyOf(enemyBullets, enemyBullets.length + newEnemyBullets.length);
                    System.arraycopy(newEnemyBullets, 0, enemyBullets, enemyBullets.length - newEnemyBullets.length,
                            newEnemyBullets.length);
                }
            }
        }
    }

    private void heroGetItem() {
        for (Items i : items) {
            if (i.isAlive() && i.isHit(heroAirplane)) {
                i.setRemove();
                switch (i.getAwardType()) {
                    case EnemyAwardItem.MISSILE:
                        heroAirplane.powerRecharge(100);
                        break;
                    case EnemyAwardItem.SCORE:
                        this.score += 50;
                        break;
                    case EnemyAwardItem.DOUBLE_SCORE:
                        this.doubleScoreTime = 1000;
                        break;
                    case EnemyAwardItem.SHOOT_LEVEL_UP:
                        heroAirplane.shootLevelUp();
                        break;
                    case EnemyAwardItem.LIFE:
                        heroAirplane.addLife();
                        break;
                    default:
                }
            }
        }
    }

    /**
     * 检测英雄机是否和敌人发射的子弹相撞的方法
     */
    private void heroGetBullet() {
        for (EnemyBullet i : enemyBullets) {
            if (i.isAlive() && heroAirplane.isHit(i)) {
                heroAirplane.eatBullet(i);
                if (!heroAirplane.isAlive()) {
                    gameState = GAME_OVER;
                }
            }
        }
    }

    /**
     * 检测敌人是否和英雄机发射的子弹相撞的方法
     */
    private void enemyGetBullet() {
        for (Enemy i : enemies) {
            if (i.isAlive()) {
                for (HeroBullet j : heroBullets) {
                    if (j.isAlive() && i.isHit(j)) {
                        i.lostLife();
                        j.setRemove();
                        if (i.isDead()) {
                            enemyGoDead(i);
                        }
                    }
                }
            }
        }
    }

    /**
     * 检测敌人是否和英雄机发射的超级子弹相撞的方法
     */
    private void enemyGetSuperBullet() {
        for (Enemy i : enemies) {
            if (i.isAlive()) {
                for (SuperBullet j : superBullets) {
                    if (j.isAlive() && i.isHit(j)) {
                        enemyGoDead(i);
                    }
                }
            }
        }
    }

    private int doubleScoreTime = 0;

    private void enemyGoDead(Enemy enemy) {
        enemy.setDead();
        if (doubleScoreTime > 0) {
            this.score += enemy.getScore() * 2;
            //System.out.println("获取了双倍得分");
        } else {
            this.score += enemy.getScore();
        }
        if (enemy instanceof EnemyAwardItem) {
            if (Math.round(Math.random()) == 1) {
                Items newItem = ((EnemyAwardItem) enemy).creatItem();
                items = Arrays.copyOf(items, items.length + 1);
                items[items.length - 1] = newItem;
            }
        }
    }

    /**
     * 检测敌人和子弹是否超出窗口 如果超出了就使其引用为空 等待垃圾回收器回收
     */
    private void outOfBoundsCheck() {
        enemies = (Enemy[]) removeObject(enemies);
        //System.out.println("当前敌人数"+enemies.length);
        enemyBullets = (EnemyBullet[]) removeObject(enemyBullets);
        //System.out.println("当前敌人子弹数"+enemyBullets.length);
        heroBullets = (HeroBullet[]) removeObject(heroBullets);
        //System.out.println("当前子弹数"+heroBullets.length);
        superBullets = (SuperBullet[]) removeObject(superBullets);
        //System.out.println("当前超级子弹数"+superBullets.length);
        items = (Items[]) removeObject(items);
        //System.out.println("当前道具数"+items.length);
    }

    private FlyingObject[] removeObject(FlyingObject[] obj) {
        for (int i = 0; i < obj.length; i++) {
            if (obj[i].isOutOfBounds() || !obj[i].isAlive()) {
                obj[i] = obj[obj.length - 1];
                obj = Arrays.copyOf(obj, obj.length - 1);
                i--;
            }
        }
        return obj;
    }

    /**
     * 此方法在间隔固定时间后会按照预设出现敌人 不是随机出现
     */
    private void level() {
        levelTime++;
        Enemy[] newEnemies = null;
        if (levelTime % (400 - (int) distance) == 0) {
            switch ((int) (Math.random() * 10)) {
                case 0:
                case 1:
                case 2:
                    newEnemies = new Enemy[5];
                    newEnemies[0] = new Enemy00(10);
                    newEnemies[1] = new Enemy00(100);
                    newEnemies[2] = new Enemy00(190);
                    newEnemies[3] = new Enemy00(280);
                    newEnemies[4] = new Enemy00(350);
                    newEnemies[1].y = -newEnemies[1].height * 2;
                    newEnemies[3].y = -newEnemies[3].height * 2;
                    break;
                case 3:
                case 4:
                    newEnemies = new Enemy[5];
                    newEnemies[0] = new Enemy00(10);
                    newEnemies[1] = new Enemy02(100);
                    newEnemies[2] = new Enemy00(190);
                    newEnemies[3] = new Enemy02(280);
                    newEnemies[4] = new Enemy00(350);
                    newEnemies[1].y = -newEnemies[1].height * 2;
                    newEnemies[3].y = -newEnemies[3].height * 2;
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
                    newEnemies[1].setShootType(newEnemies[0].getShootType() + 1);
                    break;
                case 9:
                    newEnemies = new Enemy[3];
                    newEnemies[0] = new Enemy03(10);
                    newEnemies[1] = new Enemy03(190);
                    newEnemies[2] = new Enemy03(350);
                    newEnemies[1].setShootType(newEnemies[0].getShootType() + 1);
                    newEnemies[2].setShootType(newEnemies[0].getShootType());
                    break;
            }
        } else if (levelTime % 1200 == 0) {
            newEnemies = new Enemy[2];
            newEnemies[0] = new Enemy01(100);
            newEnemies[1] = new Enemy01(280);
        }
        if (newEnemies != null) {
            enemies = Arrays.copyOf(enemies, enemies.length + newEnemies.length);
            System.arraycopy(newEnemies, 0, enemies, enemies.length - newEnemies.length, newEnemies.length);
        }
        for (Enemy i : enemies) {
            i.levelAction(1);
        }
    }

    /**
     * 此方法调用每个实例化物体的移动方法 和当前类定义的其他方法
     */
    private void step(boolean isRandom) {
        if (doubleScoreTime > 0) {
            doubleScoreTime--;
        }
        distance += 0.02;
        sky.step();
        backGround.step();
//        heroAirplane.moveTo(moveX, moveY);
        heroAirplane.move(moveX, moveY);
        for (HeroBullet i : heroBullets) {
            i.step();
        }
        for (SuperBullet i : superBullets) {
            i.step();
        }
        for (EnemyBullet i : enemyBullets) {
            i.step();
        }
        for (Items i : items) {
            i.step();
        }
        hitCheck();
        heroShoot();
        outOfBoundsCheck();
        heroGetItem();
        heroGetBullet();
        enemyGetBullet();
        enemyGetSuperBullet();
        enemyShoot();
        for (Enemy i : enemies) {
            i.step();
        }
        if (isRandom) {
            enemyRandomEnter();
        } else {
            level();
        }
    }

    /**
     * 此方法画窗口里的图形
     */
    public void paint(Graphics g) {
        g.drawImage(backGround.getImage(), backGround.x, backGround.getY1(), null);
        g.drawImage(backGround.getImage(), backGround.x, backGround.getY2(), null);
        g.drawImage(backGround.getImage(), backGround.x, backGround.getY3(), null);
        g.drawImage(sky.getImage(0), sky.x, sky.getY1(), null);
        g.drawImage(sky.getImage(1), sky.x, sky.getY2(), null);
        g.drawImage(sky.getImage(2), sky.x, sky.getY3(), null);

        for (Enemy i : enemies) {
            g.drawImage(i.getImage(), i.x, i.y, null);
        }
        for (HeroBullet i : heroBullets) {
            g.drawImage(i.getImage(), i.x, i.y, null);
        }
        g.drawImage(heroAirplane.getImage(), heroAirplane.getX(), heroAirplane.getY(), null);

        for (EnemyBullet i : enemyBullets) {
            g.drawImage(i.getImage(), i.x, i.y, null);
        }
        for (SuperBullet i : superBullets) {
            g.drawImage(i.getImage(), i.x, i.y, null);
        }
        for (Items i : items) {
            g.drawImage(i.getImage(), i.x, i.y, 78, 50, null);
        }
        
        for (int i = 1; i <= heroAirplane.getPower(); i++) {
            g.drawImage(Images.power, WIDTH - 28 - 14 * i, HEIGHT - 95, null);
        }
        for (int i = 0; i < heroAirplane.getLife(); i++) {
            g.drawImage(Images.lifeIcon, 20 + (30 + 15) * i, HEIGHT - 90, null);
        }
        g.drawImage(Images.hud, 0, 0, null);
        g.drawString("SCORE: " + this.score, 10, 25);
        g.drawString("DISTANCE: " + (int) this.distance + " kM", 200, 25);

        if (gameState == START) {
            g.drawImage(Images.start, 0, 0, null);
        } else if (gameState == PAUSE) {
            g.drawImage(Images.pause, 0, 0, null);
        } else if (gameState == GAME_OVER) {
            g.drawImage(Images.gameOver, 0, 0, null);
        }
    }
}
