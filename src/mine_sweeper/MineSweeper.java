package mine_sweeper;

public class MineSweeper {
	public static void main(String[] args) {
		System.out.print("请输入行数和列数：");
		java.util.Scanner scan = new java.util.Scanner(System.in);
		int width = scan.nextInt(), height = scan.nextInt();
		System.out.print("请输入雷的个数：");
		int mineCount = scan.nextInt();
		Mine_Sweeper mineSweeper = new Mine_Sweeper(width, height, mineCount);
		mineSweeper.start();
		scan.close();
	}
}

class Mine_Sweeper {
	int count = 0;// 统计已经翻开多少个
	int markCount = 0;// 统计标注了多少格子
	int width = 0;// 格子行数
	int height = 0;// 格子列数
	int mineCount = 0;// 地雷总数

	Mine_Sweeper() {
		this.width = 10;
		this.height = 10;
		this.mineCount = 20;
	}

	Mine_Sweeper(int width, int height, int mineCount) {
		this.width = width;
		this.height = height;
		this.mineCount = mineCount;
	}

	void start() {
		java.util.Scanner scan = new java.util.Scanner(System.in);
		// 三维数组gameData[行][列][特殊数据]
		// 特殊数据里面存两个数
		// 第一个（0-8）表示周围有几颗雷
		// 第二个 0表示未翻开 1表示已翻开 2表示被问号标注
		int[][][] gameData = creatData();
		while (true) {
			clean();
			System.out.format("一共%2d颗雷，还剩%2d个格子未翻开，加油！", mineCount, width * height - count);
			if (markCount != 0) {
				System.out.format("您一共标注了%2d个格子\n", markCount);
			} else {
				System.out.println();
			}
			drawPanel(gameData);
			System.out.println("请输入想翻开的位置：（格式为行列，如1A，3E，如果想标注某一格请在前面加？）\n输入Q退出");
			// 数组三个数 分别表示 行 列 是否标问号（0否 1是）
			int[] addr = new int[3];
			while (true) {
				String input = scan.next();
				if (input.charAt(0) == 'Q' || input.charAt(0) == 'q') {
					System.out.println("游戏退出");
					scan.close();
					return;
				}
				addr = transInput(input);
				if (addr[0] > width || addr[1] > height) {
					System.out.print("输入超出范围，请重新输入：");
					continue;
				}
				if (addr[0] != 0 && addr[1] != 0) {
					break;
				}
				System.out.print("输入错误，请重新输入：");
			}

			// 判断是否是插旗操作（标问号）
			if (addr[2] == 1) {
				switch (gameData[addr[0] - 1][addr[1] - 1][1]) {
				case 0:
					gameData[addr[0] - 1][addr[1] - 1][1] = 2;
					markCount++;
					continue;
				case 2:
					gameData[addr[0] - 1][addr[1] - 1][1] = 0;
					markCount--;
					continue;
				}
			}

			if (gameData[addr[0] - 1][addr[1] - 1][0] == 9) {// 踩雷了 翻开所有块
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						gameData[x][y][1] = 1;
					}
				}
				clean();
				System.out.println("很遗憾\n您踩雷了！");
				drawPanel(gameData);
				System.out.println("游戏结束");
				scan.close();
				return;
			} else {
				if (gameData[addr[0] - 1][addr[1] - 1][1] == 2) {
					markCount--;
				}
				gameData = flipAround(gameData, addr[0] - 1, addr[1] - 1);
			}

			// 当翻开的个数与雷的个数之和 等于 格子的总数时 游戏获胜 翻开所有块
			if (count + mineCount == width * height) {
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++) {
						gameData[x][y][1] = 1;
					}
				}
				clean();
				System.out.println("恭喜！！ \n您获胜了！！！！");
				drawPanel(gameData);
				System.out.println("游戏结束");
				scan.close();
				return;
			}
		}
	}

	// 画图形 使翻开的格子显示数字
	void drawPanel(int[][][] data) {
		// 在首行标注列号
		System.out.print("    ");
		for (int x = 0; x < data[0].length; x++) {
			System.out.print((char) (x + 65) + "   ");
		}
		System.out.println();

		// 画首行(制表行)
		System.out.print("  \u250f \u2501 ");
		for (int x = 1; x < data[0].length; x++) {
			System.out.print("\u252f \u2501 ");
		}
		System.out.println("\u2513");

		// 画第二行(数据行)
		System.out.print(" 1\u2503 " + chooseShow(data, 0, 0) + " ");
		for (int x = 1; x < data[0].length; x++) {
			System.out.print("\u2502 " + chooseShow(data, 0, x) + " ");
		}
		System.out.println("\u2503 1");

		// 画中间行
		for (int y = 1; y < data.length; y++) {
			// 制表行
			// 画中间区域最左侧一个“┠─“
			System.out.print("  \u2520 \u2500 ");
			// 画中心区域多个“┼─”
			for (int x = 1; x < data[0].length; x++) {
				System.out.print("\u253c \u2500 ");
			}
			// 画中心区域最右侧“ System.out.print┨”并换行
			System.out.println("\u2528");

			// 标注行号
			if (y < 9) {
				System.out.print(" " + (y + 1));
			} else {
				System.out.print(y + 1);
			}
			// 数据行
			// 画中间区域最左侧一个“┃ ”
			System.out.print("\u2503 " + chooseShow(data, y, 0) + " ");
			// 画中心区域多个“┼ ”
			for (int x = 1; x < data[0].length; x++) {
				System.out.print("\u2502 " + chooseShow(data, y, x) + " ");
			}
			// 画中心区域最右侧“┃”并换行
			System.out.println("\u2503 " + (y + 1));
		}
		// 画尾行(制表行)
		System.out.print("  \u2517 \u2501 ");
		for (int x = 1; x < data[0].length; x++) {
			System.out.print("\u2537 \u2501 ");
		}
		System.out.println("\u251b");

		// 在尾行标注列号
		System.out.print("    ");
		for (int x = 0; x < data[0].length; x++) {
			System.out.print((char) (x + 65) + "   ");
		}
		System.out.println();
	}

	// 建立数据 随机生成雷
	int[][][] creatData() {
		int[][][] array = new int[width][height][2];
		int i = 0;
		java.util.Random random = new java.util.Random();
		// 随机位置 放雷（9代表是雷）
		if (mineCount <= width * height / 2) {// 如果雷的数量小于等于格子总数的一半
			while (i < mineCount) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				if (array[x][y][0] != 9) {
					array[x][y][0] = 9;
					i++;
				}
			}
		} else {// 如果雷的数量过多，可以先把所有格子都放上雷，再随机几个格子取消雷
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					array[x][y][0] = 9;
				}
			}
			while (i < width * height - mineCount) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				if (array[x][y][0] == 9) {
					array[x][y][0] = 0;
					i++;
				}
			}
		}
		// 对于每个位置 如果不是雷的话 计算周围格子一共有几个雷
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (array[x][y][0] == 9) {
					continue;
				}
				int count = 0;
				// 周围格子就是[-1][-1] [-1][0] [-1][1] [0][-1] [0][0] [0][1] [1][-1] [1][0] [1][1]
				int[] around = { -1, 0, 1 };
				for (int a = 0; a < around.length; a++) {
					for (int b = 0; b < around.length; b++) {
						// 防止超边界
						if (x + around[a] < 0 || y + around[b] < 0 || x + around[a] >= width
								|| y + around[b] >= height) {
							continue;
						}
						if (array[x + around[a]][y + around[b]][0] == 9) {
							count++;
						}
					}
				}
				array[x][y][0] = count;
			}
		}
		return array;
	}

	// 清控制台
	void clean() {
		for (int i = 0; i < 50; i++) {
			System.out.println();
		}
	}

	// 转换输入的字符串成为数组
	int[] transInput(String input) {
		int[] addr = { 0, 0, 0 };
		int index = 0;
		if (input.charAt(0) == '?') {
			addr[2] = 1;
			index = 1;
		}
		for (int i = index; i < input.length(); i++) {
			if (input.charAt(i) >= 48 && input.charAt(i) <= 57) {
				addr[0] = addr[0] * 10 + (input.charAt(i) - 48);
			} else if (input.charAt(i) >= 65 && input.charAt(i) <= 90) {
				addr[1] = input.charAt(i) - 64;
			} else if (input.charAt(i) >= 97 && input.charAt(i) <= 122) {
				addr[1] = input.charAt(i) - 96;
			}
		}
		return addr;
	}

	// 翻开格子
	int[][][] flipAround(int[][][] data, int x, int y) {
		int[] around = { -1, 0, 1 };
		data[x][y][1] = 1;
		count++;
		// 如果是0 翻开周围的格子
		if (data[x][y][0] == 0) {
			for (int a = 0; a < 3; a++) {
				for (int b = 0; b < 3; b++) {
					if (x + around[a] < 0 || y + around[b] < 0 || x + around[a] >= data.length
							|| y + around[b] >= data[0].length) {
						continue;
					} else if (data[x + around[a]][y + around[b]][1] == 0) {
						data = flipAround(data, x + around[a], y + around[b]);
					}
				}
			}
		}
		return data;
	}

	// 决定格子应该显示空白 数字 还是问号
	String chooseShow(int[][][] data, int x, int y) {
		String ch = " ";
		switch (data[x][y][1]) {
		case 1:
			if (data[x][y][0] == 9) {
				ch = "*";
			} else {
				ch = "" + data[x][y][0];
			}
			break;
		case 2:
			ch = "?";
			break;
		}
		return ch;
	}
}
