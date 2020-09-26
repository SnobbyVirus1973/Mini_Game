package tetris;
/*
 * 	口口
 * 	    口口
 */
public class Block04 extends Block {
	Block04(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{0,1},{-1,-1},{-1,0}});
	}
}
