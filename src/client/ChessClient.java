package client;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Position;
import common.Team;

/**
 * 
 * @author Charles Wong
 *
 */
public class ChessClient extends JFrame implements WindowListener {
	
	ClientBoard cb;
	NetClient nc;
	boolean turn;
	
	private ChessPanel panel;

	ChessClient() {

		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Chess");
		
		nc = new NetClient();
		nc.start();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//		this.setSize(480, 480);
		this.setResizable(false);

		// instantiate the panel and add to current component
		panel = new ChessPanel();
		this.add(panel);
		this.addWindowListener(this);

		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}

	private class ChessPanel extends JPanel implements MouseListener {
		
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
			this.addMouseListener(this);
		}

		//draw stuff in here
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (!nc.status()) {
				g.drawImage(connecting, 0, 0, null);
			} else if (cb == null) {
				g.drawImage(searching, 0, 0, null);
				if (nc.poll()) {
					String s = nc.read();
					if (s.equals("WHITE")) {
						cb = new ClientBoard(Team.WHITE);
						turn = true;
					} else {
						cb = new ClientBoard(Team.BLACK);
						nc.recieve();
						turn = false;
					}
				}
			} else
				cb.draw(g, 0, 0);
			
			if (!turn) {
				if (nc.poll()) {
					String s = nc.read();
					
					
					cb.opponentMove(s);
						
					turn = true;
				}
			}

			repaint(); // this is needed to refresh the panel every frame
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (turn) {
				Position p;
				if (cb.getOrientation() == Team.WHITE) {
					p = new Position(e.getX()/60, 7 - e.getY()/60);
				} else {
					p = new Position(7 - e.getX()/60, e.getY()/60);
				}
				
				if (cb.makeMove(p, nc)) {
					turn = false;
				} else {
					cb.selectPiece(p);
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
//			System.out.println("Press");
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		nc.disconnect();
		this.dispose();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
