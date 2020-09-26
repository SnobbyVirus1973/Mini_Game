package tetris;
/*
 * 	    口口
 *	口口
 */
public class Block03 extends Block {
	Block03(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{0,-1},{-1,0},{-1,1}});
	}
}
