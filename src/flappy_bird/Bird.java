package flappy_bird;

public class Bird {
	private int width;
	private int height;
	private int x;
	private int y;
	private int ySpeed;//向下为正 向上为负
	private int gravity;
	private int jump;
	
	public void step() {
		this.ySpeed += this.gravity;
		this.y = this.y + this.ySpeed;
	}
	
	public void jump() {
		this.ySpeed = -this.jump;
	}
	
	public boolean hit(Pipe pipe) {
		int bx = this.x;
		int by = this.y;
		int bw = this.width;
		int bh = this.height;
		
		int px = pipe.getX();
		int py = pipe.getY();
		int pw = pipe.getWidth();
		int ph = pipe.getHeight();
		int ph1 = pipe.getPipeHeight();
		
		if(bx+bw>px && px+pw>bx && ( (by+bh>py && py+ph1>by) || (by+bh>py+ph-ph1 && py+ph>by) )) return true;
		else return false;
	}
}
