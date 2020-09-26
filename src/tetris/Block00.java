package tetris;
/*
 *	口
 *	口口
 */
public class Block00 extends Block {
	Block00(int x, int y){
		super(x, y);
		setBlockMap(new int[][] {{0,0},{-1,0},{0,1}});
	}
	
}
