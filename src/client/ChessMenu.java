package client;

/*
 * ChessMenu
 * Version 1.0
 * Author: Theo Liu
 * Date: June 12 2020
 * Description: Launches the application, displays a menu
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class ChessMenu extends JFrame {
  public static void main(String[] args){
    new ChessMenu();
  }
  
  // class variable (the frame being drawn)
  private JFrame thisFrame;
  
  //Constructor 
  ChessMenu() { 
    super("Menu");
    this.thisFrame = this; 
    
    //configure the window
    this.setSize(626,650);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    getContentPane().setBackground(Color.white);
         
    //Create a Panel for stuff
    JPanel decPanel = new DecoratedPanel();
    decPanel.setBorder(new EmptyBorder(768-240*2, 68, 68, 68));
    
    //listens for keyboard clicks
    MyKeyListener keyListener = new MyKeyListener();
    this.addKeyListener(keyListener);
    
    //JButton singlePlayerButton = new JButton("Single Player");
    JButton localMultiplayerButton = new JButton("Local Multiplayer");
    localMultiplayerButton.setBackground(Color.BLACK);
    localMultiplayerButton.setForeground(Color.WHITE);
    JButton onlineMultiplayerButton = new JButton("Online Multiplayer");
    onlineMultiplayerButton.setBackground(Color.BLACK);
    onlineMultiplayerButton.setForeground(Color.WHITE);
    onlineMultiplayerButton.addActionListener(new OnlineMultiplayerButtonListener());
    
    JPanel bottomPanel = new JPanel();
    //bottomPanel.add(singlePlayerButton);
    bottomPanel.add(localMultiplayerButton);
    bottomPanel.add(onlineMultiplayerButton);
    //add the main panel to the frame
    this.add(decPanel);
    this.add(bottomPanel,BorderLayout.SOUTH);
    
    //Start the app
    this.setVisible(true);
  }
  
  //INNER CLASS - Overide Paint Component for JPANEL
  private class DecoratedPanel extends JPanel {
    
    DecoratedPanel() {
      this.setBackground(new Color(0,0,0,0));
    }
    
    public void paintComponent(Graphics g) { 
      super.paintComponent(g);     
      g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
      g.setColor(Color.black);
      try {
        BufferedImage pic = ImageIO.read(new File( "Pictures/ChessBackground.png" ));  //draws image and title
        g.drawImage(pic,0,0,null); 
      } catch(IOException e){};
      
    } 
  }
  
  //INNER CLASS - checks for key presses
  private class MyKeyListener implements KeyListener {
    public void keyTyped(KeyEvent e) {  
    }
     
    public void keyReleased(KeyEvent e) {
    }
     
    public void keyPressed(KeyEvent e) { 
      if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
          System.out.println("YIKES ESCAPE KEY!"); //close frame & quit
          System.exit(0);
      } else {
        thisFrame.dispose();
      }    
    }
    
  }
  
  //INNER CLASS - changes background to ocean
 class OnlineMultiplayerButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event){
     //new ChessClient();
     thisFrame.dispose();
   }
 }
  
}
