package flappy_bird;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
	public static BufferedImage backGround;
	public static BufferedImage bird;
	public static BufferedImage pipe;

	static {
		backGround = loadImage("background.png");
		bird = loadImage("bird.png");
		pipe = loadImage("pipe.png");
	}

	public static BufferedImage loadImage(String imageFileName) {
		try {
			BufferedImage img = ImageIO.read(Image.class.getResource(imageFileName));
			return img;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
