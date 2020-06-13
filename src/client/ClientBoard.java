package client;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import common.Board;
import common.Team;

public class ClientBoard extends Board{

	private BufferedImage bg;
	private Team orientation;

	public ClientBoard(Team o) {
		super();
		orientation = o;
		
		try {
			bg = ImageIO.read(new File("sprites/bg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics g, int x, int y) {
		
	}
	
}
