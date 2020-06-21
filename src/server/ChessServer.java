/**
 * Chess Server
 * Version 1.0
 * @author Michael Du, Sherwin Chiu
 * 2020-06-21
 * Allows clients to connect and online play
 */

//package statement
package server;

//import statement
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChessServer {
    //Constants
    private final int PORT = 48209;
    private int serverCount = 0;
    private ServerSocket serverSocket;
    private int clientNum = 0;

    //main method
    public static void main(String[] args){
        ChessServer server = new ChessServer();
        server.run();
    }
    
    /**
     * run
     * @param: null
     * @return: null
     * runs Connectionthread
     */
    public void run(){
        ConnectionThread newConnection = new ConnectionThread();
        newConnection.run();
    }
    
    //----------------------INNER CLASS-----------------------------------//
    class ConnectionThread implements Runnable{
        
        public void run(){
            try {
                serverSocket = new ServerSocket(PORT);
                while(true) {
                    serverCount++;
                    System.out.println("Waiting for Connection on Server " + serverCount + "...");
                    Socket socket1 = serverSocket.accept();
                    clientNum++;
                    System.out.println("Connection" + clientNum + " is Successful");

                    Socket socket2 = serverSocket.accept();
                    clientNum++;
                    System.out.println("Connection" + clientNum + " is Successful");
                    clientNum = 0;
                    Thread playerThread = new Thread(new GameThread(socket1, socket2, serverCount));
                    playerThread.start();
                }
            } catch (IOException e) {
                System.out.println("Connection Failed");
                e.printStackTrace();
            }
        }
    } //end of ConnectionThread class
    
    //----------------------INNER CLASS-----------------------------------//
    class GameThread implements Runnable {
        
        //inner class variables
        private boolean gameStart = false;
        private Socket socketA;
        private Socket socketB;
        //writers
        private PrintWriter outputA;
        private PrintWriter outputB;
        private PrintWriter currOut;
        //readers
        private BufferedReader inputA;
        private BufferedReader inputB;
        private BufferedReader currIn;
        volatile boolean liveThread = true;
        private boolean online = true;
        private int currentServerNum;
        
        //constructor
        public GameThread (Socket socketA, Socket socketB, int num){
            this.socketA = socketA;
            this.socketB = socketB;
            this.currentServerNum = num;
        }
        
        public void run(){
            //initialization
            try {
                InputStreamReader stream = new InputStreamReader(socketA.getInputStream());
                inputA = new BufferedReader(stream);
                outputA = new PrintWriter(socketA.getOutputStream());
                stream = new InputStreamReader(socketB.getInputStream());
                inputB = new BufferedReader(stream);
                outputB = new PrintWriter(socketB.getOutputStream());
                currIn = inputA;
                currOut = outputB;
            }catch(IOException e) {
                e.printStackTrace();
            }
            
            outputA.println("SRW");
            outputB.println("SRB");
            outputA.flush();                                 //flush the output stream to make sure the message
            outputB.flush();
            
            //getting message
            String[] cmd = null;
            while (liveThread && online){
                //ready
                int ready = 0;
                while (!gameStart && online) { //finds disconnections/connections before game starts
                    try {
                        String msg = currIn.readLine();
                        System.out.println(msg);
                        cmd = msg.split(" ");
                    } catch (IOException e) {
                        System.out.println("A player has disconnected");
                        disconnect();
                    }
                    //ready command
                    if (cmd[0].equals("READY")){
                        switchStreams();
                        ready++;
                    }
                    if (ready == 2){
                        gameStart = true;
                        System.out.println("Game started");
                    }
                }
                
                currIn = inputA;
                currOut = outputB;
                
                while (gameStart && online){ //game starts
                    try {
                        String message = currIn.readLine();
                        System.out.println("Message recieved: " + message);
                        //find end results
                        if(message.equals("CHEK")){
                            System.out.println("A player has won.");
                            disconnect();
                        } else if(message.equals("STAL")) {
                            System.out.println("Stalemate reached.");
                            disconnect();
                        } else if(message.equals("RESN")){
                            System.out.println("A player has resigned.");
                            disconnect();
                        } else if (message == null){
                            System.out.println("A player has disconnected.");
                            disconnect();
                        } else{
                            currOut.println(message);
                            currOut.flush();
                            switchStreams();
                        }
                    } catch (Exception e){
                        System.out.println("Player has disconnected.");
                        disconnect();
                    }
                }
            }
        }
        
        /**
         * disconnect()
         * @param: null
         * @return: null
         * closes sockets, and disconnects.
         */
        private void disconnect(){
            try {
                currOut.println("DISC");
                currOut.flush();
                socketA.close();
                socketB.close();
                System.out.println("Server restarting session...");
                gameStart = false;
                liveThread = false;
                online = false;
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
        /**
         * switchStreams
         * @param: null
         * @return: null
         * changes input/output streams
         */
        private void switchStreams(){
            if (currIn.equals(inputA)){
                currIn = inputB;
            } else if (currIn.equals(inputB)){
                currIn = inputA;
            }
            if (currOut.equals(outputA)){
                currOut = outputB;
            } else if (currOut.equals(outputB)){
                currOut = outputA;
            }
        }
    } //end of GameThread class  
} //end of ChessServer class
