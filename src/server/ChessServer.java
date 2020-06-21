package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author Michael Du
 *
 */
public class ChessServer {


    //Constants
    final int PORT = 48209;
    final int MAXPLAYER = 10;

    ServerSocket serverSocket;

    int clientNum = 0;

    public static void main(String[] args){
        ChessServer server = new ChessServer();
        server.start();
    }

    public void start(){
        System.out.println("Waiting for Connection...");

        try {
            serverSocket = new ServerSocket(PORT);
            while (true){
                Socket socket1 = serverSocket.accept();
                clientNum++;
                System.out.println("Connection" + clientNum + " is Successful");

                Socket socket2 = serverSocket.accept();
                clientNum++;
                System.out.println("Connection" + clientNum + " is Successful");

                Thread playerThread = new Thread(new GameThread(socket1, socket2));
                playerThread.run();
            }
        } catch (IOException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }
    }
    class GameThread extends Thread {
        private boolean gameStart = false;
        private Socket socketA;
        private Socket socketB;
        private PrintWriter outputA;
        private PrintWriter outputB;
        private PrintWriter currOut;
        private BufferedReader inputA;
        private BufferedReader inputB;
        private BufferedReader currIn;
        volatile boolean liveThread = true;
        public GameThread (Socket socketA, Socket socketB){
            this.socketA = socketA;
            this.socketB = socketB;
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
            while (liveThread){
                //ready
                int ready = 0;
                while (!gameStart) {
                    try {
                        String msg = currIn.readLine();
                        System.out.println(msg);
                        cmd = msg.split(" ");
                    } catch (IOException e) {
                        e.printStackTrace();
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
                while (gameStart){
                    try {
                        String message = currIn.readLine();
                        System.out.println("Message recieved: " + message);
                        if(message != null) {
                            currOut.println(message);
                            currOut.flush();
                            switchStreams();
                        } else if(message.equals("CHEK")){
                            System.out.println("A player has won.");
                            currOut.println("You have won! GG.");
                            currOut.flush();
                            disconnect();
                        } else if(message.equals("STAL")) {
                            System.out.println("Stalemate reached.");
                            currOut.println("Stalemate. No winners.");
                            currOut.flush();
                            disconnect();
                        } else if(message.equals("RESN")){
                            System.out.println("A player has resigned.");
                            currOut.println("Opponent Resigned.");
                            currOut.flush();
                            disconnect();
                        }
                        else{
                            System.out.println("A player has disconnected.");
                            gameStart = false;
                            currOut.println("The opponent has disconnected.");
                            currOut.flush();
                            disconnect();
                        }
                    } catch (Exception e){
                        System.out.println("Player has disconnected.");
                        disconnect();
                    }
                }
            }
        }
        private void disconnect(){
            try {
                currOut.println("The opponent has disconnected.");
                currOut.flush();
                socketA.close();
                socketB.close();
                serverSocket.close();
                System.out.println("Server restarting...");
                ChessServer server = new ChessServer();
                server.start();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
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
    }
}
