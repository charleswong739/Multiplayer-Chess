/*
 * ChessMenu
 * Version 1.0
 * Author: Theo Liu
 * Date: June 12 2020
 * Description: Launches the application, displays a menu
 */

//package statements
package client;

//import statements
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

class ChessMenu extends JFrame implements KeyListener, ActionListener {
	
	//Main Method
	public static void main(String[] args) {
		new ChessMenu();
	}

	// class variable (the frame being drawn)
	private JFrame thisFrame;
	private JButton onlineMultiplayerButton;

	// Constructor
	ChessMenu() {
		super("Menu");
		this.thisFrame = this;

		// configure the window
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);		

		// listens for keyboard clicks
		this.setFocusable(true);
		this.requestFocusInWindow();
		this.addKeyListener(this);

		// Create a Panel for stuff
		JPanel decPanel = new DecoratedPanel();
		decPanel.setBorder(new EmptyBorder(768 - 240 * 2, 68, 68, 68));
		
		onlineMultiplayerButton = new JButton("Online Multiplayer");
		onlineMultiplayerButton.setBackground(Color.BLACK);
		onlineMultiplayerButton.setOpaque(true);
		onlineMultiplayerButton.setBorderPainted(false);
		onlineMultiplayerButton.setForeground(Color.WHITE);
		onlineMultiplayerButton.addActionListener(this);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.add(onlineMultiplayerButton);
		
		// add the main panel to the frame
		this.add(decPanel);
		this.add(bottomPanel, BorderLayout.SOUTH);

		// Start the app
		this.pack();
		this.setLocationRelativeTo(null); // start the frame in the center of the screen
		this.setVisible(true);
	}

	// INNER CLASS - Overide Paint Component for JPANEL
	private class DecoratedPanel extends JPanel {
		
		BufferedImage pic;
		
		//Constructor
		DecoratedPanel() {			
			try {
				pic = ImageIO.read(new File("sprites/ChessBackground.png"));
			} catch (IOException e) {
				System.out.println("Main menu pic not loaded");
				e.printStackTrace();
			}
			
			this.setPreferredSize(new Dimension(626, 626));
		}
	
		/* paintComponent
  		 * @param: Graphics g
  		 * @return: null
  		 * draws the background image
  		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(pic, 0, 0, null);

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // If ESC is pressed
			System.out.println("YIKES ESCAPE KEY!"); // close frame & quit
			System.exit(0);
		} else {
			thisFrame.dispose();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == onlineMultiplayerButton) {
			new ChessClient();  //starts chess client
		}
	}

} //end of ChessMenu Class
