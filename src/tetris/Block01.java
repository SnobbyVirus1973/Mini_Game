package tetris;
/*
 *	口口口口
 */
public class Block01 extends Block {
	Block01(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{0,-1},{0,1},{0,2}});
	}
}
