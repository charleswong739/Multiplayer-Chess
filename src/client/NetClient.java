import java.io.*;
import java.net.*;
import java.util.*;
class NetClient {
    
    final String LOCAL_HOST = "127.0.0.1";
    final int PORT = 5000;
    Socket mySocket;      
    BufferedReader input;     
    PrintWriter output;       
    boolean running = true;   
    String team;
    
      public void go() { 
           
    // call a method that connects to the server 
    connect("127.0.0.1",5000);
    // after connecting loop and keep appending[.append()] to the JTextArea
    
    readMessagesFromServer();
    }
      
     //Attempts to connect to the server and creates the socket and streams
  public Socket connect(String ip, int port) { 
    System.out.println("Attempting to make a connection..");
    
    try {
      mySocket = new Socket(ip,port); //attempt socket connection (local address). This will wait until a connection is made
      
      InputStreamReader stream1= new InputStreamReader(mySocket.getInputStream()); //Stream for network input
      input = new BufferedReader(stream1);     
      output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream
      
    } catch (IOException e) {  //connection error occured
      System.out.println("Connection to Server Failed");
      e.printStackTrace();
    }
    
    System.out.println("Connection made.");
    return mySocket;
  }
  public void readMessagesFromServer() { 
      boolean firstTurn = true;
  while(running) {  // loop unit a message is received
      try {
        
        if (input.ready()) { //check for an incoming messge
          String msg;          
          msg = input.readLine(); //read the message
          if(msg.equals("Server Ready")) {
              output.println("READY");
              output.flush();
          }
          else {
              if(firstTurn) {
                  if(msg.equals("First")) {
                      team="White";
                      msg = "";
                  } else {
                      team="Black";
                  }
                  firstTurn = false;
              } 
              
              //First decode string to board
              //Then update board array
              //Wait for move
              //Encode new board into string
              //Send to server
              output.println(msg+" Board"+team);
              output.flush();
              
          }
        }
        
      }catch (IOException e) { 
        System.out.println("Failed to receive msg from the server");
        e.printStackTrace();
      }
}
     try {  //after leaving the main loop we need to close all the sockets
      input.close();
      output.close();
      mySocket.close();
    }catch (Exception e) { 
      System.out.println("Failed to close socket");
    }
  
  }
}