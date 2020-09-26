package tetris;
/*
 *	    口
 *	口口口
 */
public class Block08 extends Block {
	Block08(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{0,-1},{0,1},{-1,0}});
	}
}
