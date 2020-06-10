package common;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Charles Wong
 *
 */

public abstract class Board {

	private BufferedImage bg;
	
	Board() {
		try {
			bg = ImageIO.read(new File("sprites/bg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		
	}
}
