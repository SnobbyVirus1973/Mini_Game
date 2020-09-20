package flappy_bird;

import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class World extends JPanel{
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private BackGround backGround = new BackGround();
	private Bird bird = new Bird();
	private Pipe[] pipes = {};

	public void action(){ //����ִ��
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				backGround.step();
				repaint();
			}
		},10,10);
	}
	
	public void hitCheck() {
		for(int i=0; i<pipes.length; i++) {
			if(bird.hit(pipes[i])) {
				System.out.println("Game Over");
			}
		}
	}
	
	public void paint(Graphics g) {
		g.drawImage(Image.backGround, 0, 0, null);
		g.drawImage(Image.backGround, 0, 0, null);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		
		world.action();
		
	}
}
