package flappy_bird;

public class Pipe {
	private int width;
	private int height;
	private int space_height;//管道中间空白区域的高度
	private int x;
	private int y;
	private int xSpeed;
	
	Pipe(int lastY, int offset){
		this.setY(lastY + (int)((Math.random()>=0.5 ? 1: -1)*(Math.random()*offset+1)));
	}
	
	public void step() {
		this.x = this.x - this.xSpeed;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
	
	public int getPipeHeight() {
		return (this.height-this.space_height)/2;
	}
		
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setY(int y) {
		while(y+this.height/2>World.HEIGHT) {
			y--;
		}
		while(y+this.height/2<0) {
			y++;
		}
		this.y = y;
	}
}
