package tetris;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.imageio.ImageIO;

import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class World extends JPanel {
	private static final long serialVersionUID = 1L;

	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;
	private static int gameState = START;

	private static final int WIDTH = 428;
	private static final int HEIGHT = 740;

	private Cell[][] cells = new Cell[21][17];
	private Block block = null;
	private Block nextBlock = null;

	{
		restart();
	}

	private void action() {
		KeyAdapter k = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// System.out.println(e.getKeyCode());
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (gameState == GAME_OVER) {
						gameState = START;
					} else if (gameState == START) {
						gameState = RUNNING;
						restart();
					} else if (gameState == PAUSE) {
						gameState = RUNNING;
					} else {
						gameState = PAUSE;
					}
				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					if (gameState == RUNNING)
						gameState = PAUSE;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
					if (block != null)
						moveDown();
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
					if (block != null)
						moveLeft();
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
					if (block != null)
						moveRight();
				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W
						|| e.getKeyCode() == KeyEvent.VK_E) {
					if (block != null)
						block.clockwiseRotate(cells);
				} else if (e.getKeyCode() == KeyEvent.VK_Q) {
					if (block != null)
						block.counterClockwiseRotate(cells);
				}
			}
		};
		this.addKeyListener(k);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				if (gameState == RUNNING)
					step();
				repaint();
			}
		}, 10, 10);
	}

	public void restart() {
		stepTime = 0;
		cells = new Cell[21][17];
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < 17; j++) {
				cells[i][j] = new Cell();
			}
		}
		for (Cell i : cells[cells.length - 1]) {
			i.setHaveBlock(true);
		}
		for (Cell[] i : cells) {
			i[0].setHaveBlock(true);
			i[16].setHaveBlock(true);
		}
		newBlock();
	}

	public void newBlock() {
		if (nextBlock == null) {
			int randomX = (int) (Math.random() * 12 + 2);
			switch ((int) (Math.random() * 9)) {
			case 0:
				nextBlock = new Block00(0, randomX);
				break;
			case 1:
				nextBlock = new Block01(0, randomX);
				break;
			case 2:
				nextBlock = new Block02(0, randomX);
				break;
			case 3:
				nextBlock = new Block03(0, randomX);
				break;
			case 4:
				nextBlock = new Block04(0, randomX);
				break;
			case 5:
				nextBlock = new Block05(0, randomX);
				break;
			case 6:
				nextBlock = new Block06(0, randomX);
				break;
			case 7:
				nextBlock = new Block07(0, randomX);
				break;
			case 8:
				nextBlock = new Block08(0, randomX);
				break;
			}
			// System.out.println(nextBlock);
		}
		if (block == null) {
			block = nextBlock;
			// System.out.println(block);
			nextBlock = null;
		}
	}

	private int stepTime;

	public void step() {
		newBlock();
		if (block != null) {
			block.step();
			this.stepTime++;
			if (stepTime % 50 == 0) {
				moveDown();
			}
		} else {
			stepTime = 0;
		}
		fullCheck();
		gameOverCheck();
	}

	private void gameOverCheck() {
		for (int j = 1; j < cells[0].length - 1; j++) {
			if (cells[0][j].isHaveBlock()) {
				gameState = GAME_OVER;
				System.out.println("Game Over!");
			}
		}
	}

	private void fullCheck() {
		// 不算最后一行（第26行，索引为25）
		a: for (int i = cells.length - 2; i >= 0; i--) {
			// 不算每行第一个和最后一个
			for (int j = 1; j < cells[i].length - 1; j++) {
				if (!cells[i][j].isHaveBlock())
					continue a;
			}
			removeLine(i);
		}
	}

	public void moveDown() {
		if (block.canMoveDown(cells)) {
			block.moveDown();
		} else {
			int[][] blockLocaltion = block.getLocationMap();
			for (int[] i : blockLocaltion) {
				if (i[0] < 0 || i[0] >= 21 || i[1] < 0 || i[1] >= 17)
					continue;
				cells[i[0]][i[1]].setHaveBlock(true);
			}
			block = null;
		}
	}

	private void removeLine(int line) {
		int count = 0;
		for (int i = 1; i < cells[line].length - 1; i++) {
			cells[line][i].setHaveBlock(false);
		}
		if (line - 1 < 0)
			return;
		for (int i = 1; i < cells[line - 1].length - 1; i++) {
			if (cells[line - 1][i].isHaveBlock()) {
				cells[line][i].setHaveBlock(true);
				count++;
			}
		}
		if (count == 0) {
			return;
		} else {
			removeLine(line - 1);
		}
	}

	public void moveLeft() {
		if (block.canMoveLeft(cells)) {
			block.moveLeft();
		}
	}

	public void moveRight() {
		if (block.canMoveRight(cells)) {
			block.moveRight();
		}
	}

	public void paint(Graphics g) {
		g.drawImage(readImage("background.png"), 0, 0, null);
		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells[i].length; j++) {
				if (cells[i][j].isHaveBlock()) {
					g.drawImage(readImage("block0.png"), j * 25, 175 + i * 25, null);
				}
			}
		}
		if (gameState != START) {
			if (block != null) {
				int[][] map = block.getLocationMap();
				for (int[] i : map) {
					g.drawImage(readImage("block1.png"), i[1] * 25, 175 + i[0] * 25, null);
				}
			}
			if (nextBlock != null) {
				for (int[] i : nextBlock.getBlockMap()) {
					g.drawImage(readImage("block1.png"), 325 + i[1] * 25, 75 + i[0] * 25, null);
				}
			}
		}

		switch (gameState) {
		case START:
			g.drawImage(readImage("start.png"), 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(readImage("pause.png"), 0, 0, null);
			break;
		case GAME_OVER:
			g.drawImage(readImage("gameover.png"), 0, 0, null);
			break;
		}
	}

	public static BufferedImage readImage(String fileName) {
		try {
			BufferedImage img = ImageIO.read(World.class.getResource(fileName));
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("俄罗斯方块");
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setVisible(true);
		world.requestFocus();

		world.action();
	}
}
