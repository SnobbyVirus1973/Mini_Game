package tetris;

import java.util.Arrays;

public abstract class Block {
	private int x;
	private int y;//原点的坐标，
	private int[][] blockMap;
	private int[][] locationMap;
	
	Block(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void step() {
		this.locationMap = this.location();
	}
	
	public void moveDown() {
		this.x += 1;
	}
	
	public void moveLeft() {
		this.y -= 1;
	}
	
	public void moveRight() {
		this.y += 1;
	}
	
	public boolean canMoveDown(Cell[][] cells) {
		for(int[] i : this.locationMap) {
			if(i[0]+1>=cells.length) {
				return false;
			}else if(cells[i[0]+1][i[1]].isHaveBlock()) {
				return false;
			}
		}
		return true;
	}
	
	public boolean canMoveLeft(Cell[][] cells) {
		for(int[] i : this.locationMap) {
			if(i[1]-1<0) {
				return false;
				}
			if(cells[i[0]][i[1]-1].isHaveBlock()) {
				return false;
			}
		}
		return true;	
	}
	
	public boolean canMoveRight(Cell[][] cells) {
		for(int[] i : this.locationMap) {
			if(i[1]-1>=cells[0].length) {
				return false;
			}
			if(cells[i[0]][i[1]+1].isHaveBlock()) {
				return false;
			}
		}
		return true;	
	}
	
	public int[][] location(){
		int[][] location = Arrays.copyOf(blockMap, blockMap.length);
		for(int i=0; i<location.length; i++) {
			location[i] = new int[] {blockMap[i][0]+this.x, blockMap[i][1]+this.y};
		}
		return location;
	}
	
	public void setBlockMap(int[][] map) {
		this.blockMap = map;
	}
	
	public int[][] getBlockMap() {
		return this.blockMap;
	}
	
	public int[][] getLocationMap(){
		return this.locationMap;
	}
	
	//顺时针旋转
	public boolean clockwiseRotate(Cell[][] cells) {
		//blockMap[0]是0，0原点 不需要旋转
		int[][] newBlockMap = new int[blockMap.length][2];
		newBlockMap[0] = new int[] {0,0};
		for(int i=1; i<blockMap.length; i++) {
			if(blockMap[i][0] == 0) {
				newBlockMap[i] = new int[] {blockMap[i][1], 0};
			}else if(blockMap[i][1] == 0) {
				newBlockMap[i] = new int[] {0, -blockMap[i][0]};
			}else if(blockMap[i][0]+blockMap[i][1]==0){
				newBlockMap[i] = new int[] {blockMap[i][1], blockMap[i][1]};
			}else {
				newBlockMap[i] = new int[] {blockMap[i][0], -blockMap[i][1]};
			}
			if(this.x+newBlockMap[i][0]<0||this.x+newBlockMap[i][0]>=cells.length||this.y+newBlockMap[i][1]<0||this.y+newBlockMap[i][1]>=cells[0].length) {
				return false;
			}
			if(cells[this.x+newBlockMap[i][0]][this.y+newBlockMap[i][1]].isHaveBlock()) {
				return false;
			}
		}
		blockMap = Arrays.copyOf(newBlockMap, newBlockMap.length);
		return true;
	}

	//逆时针旋转
	public boolean counterClockwiseRotate(Cell[][] cells) {
		//blockMap[0]是0，0原点 不需要旋转
		int[][] newBlockMap = new int[blockMap.length][2];
		newBlockMap[0] = new int[] {0,0};
		for(int i=1; i<blockMap.length; i++) {
			if(blockMap[i][0] == 0) {
				newBlockMap[i] = new int[] {-blockMap[i][1], 0};
			}else if(blockMap[i][1] == 0) {
				newBlockMap[i] = new int[] {0, blockMap[i][0]};
			}else if(blockMap[i][0]+blockMap[i][1]==0){
				newBlockMap[i] = new int[] {blockMap[i][0], blockMap[i][0]};
			}else {
				newBlockMap[i] = new int[] {-blockMap[i][0], blockMap[i][1]};
			}
			if(cells[this.x+newBlockMap[i][0]][this.y+newBlockMap[i][1]].isHaveBlock()) {
				return false;
			}
		}
		blockMap = Arrays.copyOf(newBlockMap, newBlockMap.length);
		return true;
	}
}
