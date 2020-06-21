package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

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
	private MovePanel movePanel;
	private InfoPanel infoPanel;

	ChessClient() {

		// set up frame (all swing component's methods are inherited, so there are a bunch of
		// other settings as well, but i've never really found a use for them
		super("Chess");
		
		nc = new NetClient();
		nc.start();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setResizable(false);

		// instantiate the panel and add to current component
		chessPanel = new ChessPanel();
		this.add(chessPanel, BorderLayout.CENTER);
		this.addWindowListener(this);
		
		movePanel = new MovePanel();
		this.add(movePanel, BorderLayout.SOUTH);
		
//		infoPanel = new InfoPanel();
//		this.add(infoPanel, BorderLayout.EAST);

		// more setup
		this.pack();
		this.requestFocusInWindow();
		this.setVisible(true);
	}

	private class ChessPanel extends JPanel implements MouseListener {
		
		BufferedImage connecting;
		BufferedImage searching;
		
		String selectedString; // weird way to write this but oh well

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
						
						if (s.equals("CHEK") || s.equals("STAL") || s.equals("RESN") || s.equals("DISC"))
							nc.disconnect();
						else if (cb.checkmate()) {
							cb.setState(GameState.CHECKMATE_LOSS);
							nc.write("CHEK");
							nc.disconnect();
						} else if (cb.stalemate()) {
							cb.setState(GameState.STALEMATE);
							nc.write("STAL");
							nc.disconnect();
						} else {
							
							if (cb.getOrientation() == Team.WHITE) {
								movePanel.addMoveToLog("BLACK:", s);
							} else {
								movePanel.addMoveToLog("WHITE:", s);
							}
							
							turn = true;
						}
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
						movePanel.addMoveToLog(cb.getOrientation().toString(), selectedString, p.toString(), cb.getBoard()[p.file][p.rank].toString());
						selectedString = null;
					} else {
						cb.selectPiece(p);
						selectedString = p.toString();
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
	
	private class MovePanel extends JPanel {
		
		private JLabel moveLabel;
		private JTextArea moveLog;
		
		private MovePanel() {
			
			moveLabel = new JLabel("Move Log");
			moveLabel.setAlignmentX(CENTER_ALIGNMENT);
			moveLabel.setFont(new Font(moveLabel.getFont().getName(), Font.PLAIN, 15));

			moveLog = new JTextArea("Welcome to Chess\n\n");
			moveLog.setBackground(Color.LIGHT_GRAY);
			moveLog.setEditable(false);
			
			Border border = BorderFactory.createLineBorder(Color.BLACK);
		    moveLog.setBorder(BorderFactory.createCompoundBorder(border,
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		    
		    JScrollPane scroll = new JScrollPane (moveLog, 
		    		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		    			
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			
			this.add(moveLabel);
			this.add(scroll);
			
			this.setPreferredSize(new Dimension(560, 120));
			this.setBackground(Color.LIGHT_GRAY);
		}
		
		public void addMoveToLog(String team, String s) {
			String letters = "ABCDEFG";
			String piece = cb.getBoard()[s.charAt(9)-48][s.charAt(11)-48].toString();
			String edited = " " + piece + " (" + letters.charAt(s.charAt(5)-48) + ", "+ (s.charAt(7)-47) + ") to (" + letters.charAt(s.charAt(9)-48) + ", " + (s.charAt(11)-47) + ")\n";
			
			moveLog.append(team + edited);
			moveLog.setCaretPosition(moveLog.getDocument().getLength());
		}
		
		public void addMoveToLog(String team, String posA, String posB, String piece) {
			moveLog.append(team + ": " + piece + " " + posA + " to " + posB + "\n");
			moveLog.setCaretPosition(moveLog.getDocument().getLength());
		}
	}
	
	private class InfoPanel extends JPanel {
		
		private JTextArea whiteInfo;
		private JTextArea blackInfo;
		
		private InfoPanel() {
			this.setPreferredSize(new Dimension(160, 560));
			this.setBackground(Color.BLUE);
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
