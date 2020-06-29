package test;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.ClientBoard;
import common.Team;

public class Test extends JFrame {

	private Panel panel;
	private InfoPanel pane;
	
	Test() {
		
		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Swing Template");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		this.setResizable(false);
		
		// instantiate the panel and add to current component
		panel = new Panel();
		this.add(panel);
		
		pane = new InfoPanel();
		this.add(pane);
		
		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}
	
	public static void main (String [] args) {
		new Test();
	}
	
	private class Panel extends JPanel {
		
		ClientBoard cb;
		
		Panel() {
			cb = new ClientBoard(Team.WHITE);
			
			/*------------------------------------------------*/
			/*
			cb.selectPiece(new Position(Position.D, 1));
			cb.makeMove(new Position(Position.D, 3));
			cb.selectPiece(new Position(Position.D, 0));
			cb.makeMove(new Position(Position.D, 2));
			cb.selectPiece(new Position(Position.G, 0));
			cb.makeMove(new Position(Position.F, 2));
			cb.selectPiece(new Position(Position.D, 2));
			
			/*------------------------------------------------*/
			
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
	
	private class InfoPanel extends JPanel {
		InfoPanel() {
			this.setPreferredSize(new Dimension(480, 100));
		}
	}
}
