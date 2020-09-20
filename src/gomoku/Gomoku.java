package gomoku;

public class Gomoku {
	public static void main(String[] args) {
		System.out.print("请输入行数和列数：");
		java.util.Scanner scan = new java.util.Scanner(System.in);
		int width = scan.nextInt(), height = scan.nextInt();
		
		//二维数组gameData[行][列]
		//数值0代表无棋子，1代表棋子O，2代表棋子X
		int[][] gameData = new int[width][height];
		boolean isX = true;
		while(true) {
			drawPanel(gameData);
			System.out.println("当前为"+ (isX?"X":"O") +"方\n请输入想落子的位置：（格式为行列，如1A，3E）\n输入Q退出");
			int[] input = new int[2];
			while(true) {
				String inputString = scan.next();
				if(inputString.charAt(0)=='Q' || inputString.charAt(0)=='q') {
					scan.close();
					return;
				}
				input = transInput(inputString);
				if(input[0]>width || input[1]>height) {
					System.out.println("输入超出范围，请重新输入：（格式为行列，如1A，3E）");
					continue;
				}
				if(input[0]!=0 && input[1]!=0) {
					if(gameData[input[0]-1][input[1]-1] != 0) {
						System.out.println("当前位置已有棋子，请重新输入");
						continue;
					}
					break;
				}
				System.out.println("输入错误，请重新输入：（格式为行列，如1A，3E）");
			}
			if(isX) {
				gameData[input[0]-1][input[1]-1] = 2;
				isX = false;
			}else {
				gameData[input[0]-1][input[1]-1] = 1;
				isX = true;
			}
			if(countFive(gameData, input[0]-1, input[1]-1) >= 5) {
				clean();
				drawPanel(gameData);
				System.out.println("恭喜"+ (gameData[input[0]-1][input[1]-1]==2?"X":"O") +"方获胜！");
				scan.close();
				return;
			}
			clean();
		}
	}
	
	//画图形 使翻开的格子显示数字
	static void drawPanel(int[][] data) {
		//在首行标注列号
		System.out.print("    ");
		for(int x=0; x<data[0].length; x++) {
			System.out.print((char)(x+65) + "   ");
		}
		System.out.println();
		
		//画首行(制表行)
		System.out.print("  \u250f \u2501 ");
		for(int x=1; x<data[0].length; x++) {
			System.out.print("\u252f \u2501 ");
		}
		System.out.println("\u2513");
		
		//画第二行(数据行)
		System.out.print(" 1\u2503 "+chooseShow(data, 0, 0)+" ");
		for(int x=1; x<data[0].length; x++) {
			System.out.print("\u2502 "+chooseShow(data, 0, x)+" ");
		}
		System.out.println("\u2503 1");
		
		//画中间行
		for(int y=1;y<data.length;y++) {
			//制表行
			//画中间区域最左侧一个“┠─“
			System.out.print("  \u2520 \u2500 ");
			//画中心区域多个“┼─”
			for(int x=1;x<data[0].length;x++) {
				System.out.print("\u253c \u2500 ");
			}
			//画中心区域最右侧“		System.out.print┨”并换行
			System.out.println("\u2528");
			
			//标注行号
			if(y<9) {
				System.out.print(" "+(y+1));
			}else {
				System.out.print(y+1);
			}
			//数据行
			//画中间区域最左侧一个“┃ ”
			System.out.print("\u2503 "+chooseShow(data, y, 0)+" ");
			//画中心区域多个“┼ ”
			for(int x=1;x<data[0].length;x++) {
				System.out.print("\u2502 "+chooseShow(data, y, x)+" ");
			}
			//画中心区域最右侧“┃”并换行
			System.out.println("\u2503 "+ (y+1));
		}
		//画尾行(制表行)
		System.out.print("  \u2517 \u2501 ");
		for(int x=1; x<data[0].length; x++) {
			System.out.print("\u2537 \u2501 ");
		}
		System.out.println("\u251b");
		
		//在尾行标注列号
		System.out.print("    ");
		for(int x=0; x<data[0].length; x++) {
			System.out.print((char)(x+65) + "   ");
		}
		System.out.println();
	}
	
	//决定格子应该显示空白 还是O、X
	static String chooseShow(int[][] data, int x, int y) {
		String ch = " ";
		switch(data[x][y]) {
		case 1:
			ch = "O";
			break;
		case 2:
			ch = "X";
			break;
		}
		return ch;
	}
	
	//转换输入的字符串成为数组 //第一行第一列转换为[1,1]
	static int[] transInput(String input) {
		int[] addr= {0, 0};
		int index = 0;
		for(int i=index; i<input.length(); i++) {
			if(input.charAt(i)>=48 && input.charAt(i)<=57) {
				addr[0] = addr[0]*10+(input.charAt(i)-48);
			}else if(input.charAt(i)>=65 && input.charAt(i)<=90) {
				addr[1] = input.charAt(i)-64;
			}else if(input.charAt(i)>=97 && input.charAt(i)<=122) {
				addr[1] = input.charAt(i)-96;
			}
		}
		return addr;
	}
	
	//计算周围所连棋子数量(所传参数x、y为角标，即行数-1和列数-1)
	static int countFive(int[][] data, int x, int y) {
		int count = 1;
		int chessman = data[x][y]; //1代表棋子O，2代表棋子X
		int[][] around = {{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
		for(int i=0; i<4; i++) {
			count = 1;
			for(int n=1; n<=5; n++) {
				int aroundX = x + n*around[i][0], aroundy = y + n*around[i][1];
				if(aroundX<0 || aroundX>=data.length || aroundy<0 || aroundy>data[0].length) {
					break;
				}else if(data[aroundX][aroundy] != chessman) {
					break;
				}
				count++;
			}
			for(int n=1; n<=5; n++) {
				int aroundX = x + n*around[i+4][0], aroundy = y + n*around[i+4][1];
				if(aroundX<0 || aroundX>=data.length || aroundy<0 || aroundy>data[0].length) {
					break;
				}else if(data[aroundX][aroundy] != chessman) {
					break;
				}
				count++;
			}
			if(count>=5) {
				return count;
			}
		}
		return count;
	}
	
	//清控制台
	static void clean() {
		for (int i=0; i<50; i++) {
			System.out.println();
		}
	}
}
