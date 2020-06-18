package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.State;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Team;

public class ChessClient extends JFrame {
	
	ClientBoard cb;
	NetClient nb;
	
	private ChessPanel panel;

	ChessClient() {

		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Chess");
		
		nb = new NetClient();
		nb.start();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		this.setSize(480, 480);
		this.setResizable(false);

		// instantiate the panel and add to current component
		panel = new ChessPanel();
		this.add(panel);

		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}

	private class ChessPanel extends JPanel {
		
		BufferedImage connecting;
		BufferedImage searching;

		ChessPanel() {
			try {
				connecting = ImageIO.read(new File("sprites/connecting.png"));
				searching = ImageIO.read(new File("sprites/searching.png"));
			} catch (IOException e) {
				System.out.println("Connecting images not found");
				e.printStackTrace();
			}

			this.setPreferredSize(new Dimension(480, 480));
		}

		//draw stuff in here
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (!nb.status()) {
				g.drawImage(connecting, 0, 0, null);
			} else if (cb == null) {
				g.drawImage(searching, 0, 0, null);
				
				if (nb.poll()) {
					String s = nb.read();
					
					if (s.equals("WHITE"))
						cb = new ClientBoard(Team.WHITE);
					else
						cb = new ClientBoard(Team.BLACK);
				}
			} else
				cb.draw(g, 0, 0);

			repaint(); // this is needed to refresh the panel every frame
		}
	}
}
