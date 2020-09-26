package tetris;
/*
 * 	口口
 * 	口
 * 	口
 */
public class Block06 extends Block {
	Block06(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{1,0},{-1,0},{-1,1}});
	}
}
