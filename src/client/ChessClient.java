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
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import common.GameState;
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
	
	private ChessPanel chessPanel;
	private InfoPanel infoPanel;

	ChessClient() {

		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Chess");
		
		nc = new NetClient();
		nc.start();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setResizable(false);

		// instantiate the panel and add to current component
		chessPanel = new ChessPanel();
		this.add(chessPanel);
		this.addWindowListener(this);
		
//		infoPanel = new InfoPanel();
//		this.add(infoPanel);

		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}

	private class ChessPanel extends JPanel implements MouseListener {
		
		BufferedImage connecting;
		BufferedImage searching;

		private ChessPanel() {
			try {
				connecting = ImageIO.read(new File("sprites/lettered_connecting.png"));
				searching = ImageIO.read(new File("sprites/lettered_searching.png"));
			} catch (IOException e) {
				System.out.println("Connecting images not found");
				e.printStackTrace();
			}

			this.setPreferredSize(new Dimension(560, 560));
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
			}

			if (cb != null) {
				cb.draw(g, 0, 0);
				
				if (!turn && cb.getState() == GameState.ACTIVE) {
					if (nc.poll()) {
						String s = nc.read();
						
						cb.opponentMove(s);
						
						if (s.equals("CHEK") || s.equals("STAL") || s.equals("RESN"))
							nc.disconnect();
						else if (cb.checkmate()) {
							cb.setState(GameState.CHECKMATE_LOSS);
							nc.write("CHEK");
							nc.disconnect();
						} else if (cb.stalemate()) {
							cb.setState(GameState.STALEMATE);
							nc.write("STAL");
							nc.disconnect();
						} else
							turn = true;
					}
				}
			}

			repaint(); // this is needed to refresh the panel every frame
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getX() < 520 && e.getX() > 40 && e.getY() < 520 && e.getX() > 40) {
				if (turn) {
					Position p;
					if (cb.getOrientation() == Team.WHITE) {
						p = new Position((e.getX()-40)/60, 7 - (e.getY()-40)/60);
					} else {
						p = new Position(7 - (e.getX()-40)/60, (e.getY()-40)/60);
					}

					if (cb.makeMove(p, nc)) {
						turn = false;
					} else {
						cb.selectPiece(p);
					}
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
	
	private class InfoPanel extends JPanel{
		
		private InfoPanel() {
			this.setPreferredSize(new Dimension(560, 240));
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
