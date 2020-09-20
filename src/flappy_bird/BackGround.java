package flappy_bird;

public class BackGround {
	private int width;
	private int height;
	private int x1;
	private int x2;
	private int y;
	private int xSpeed;
	
	public void step() {
		this.x1 = this.resetLocation(this.x1) - this.xSpeed;
		this.x2 = this.resetLocation(this.x2) - this.xSpeed;
	}
	
	public int resetLocation(int x) {
		return x < -this.width ? this.width : x;
	}
	
	public int getX1() {
		return this.x1;
	}
	
	public int getX2() {
		return this.x2;
	}
	
	public int getY() {
		return this.y;
	}
}
