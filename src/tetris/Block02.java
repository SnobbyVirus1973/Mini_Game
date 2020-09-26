package tetris;
/*
 * 	    口
 * 	口口口
 * 	    口
 */
public class Block02 extends Block {
	Block02(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{0,-1},{-1,0},{0,1},{1,0}});
	}
}
