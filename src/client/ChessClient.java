package client;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Position;
import common.Team;

public class ChessClient extends JFrame {
	private Panel panel;

	ChessClient() {

		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Swing Template");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//		this.setSize(480, 480);
		this.setResizable(false);

		// instantiate the panel and add to current component
		panel = new Panel();
		this.add(panel);

		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}

	private class Panel extends JPanel {

		ClientBoard cb;

		Panel() {
			cb = new ClientBoard(Team.WHITE);

			this.setPreferredSize(new Dimension(480, 480));
		}

		//draw stuff in here
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			cb.draw(g, 0, 0);

			repaint(); // this is needed to refresh the panel every frame
			//			revalidate(); // this is needed to refresh any components you might have, but since
			// we have none its not needed
		}
	}
}
