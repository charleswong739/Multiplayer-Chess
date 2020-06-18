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
    final int PORT = 5000;
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
        boolean gameStart = false;
        Socket socketA;
        Socket socketB;
        PrintWriter outputA;
        PrintWriter outputB;
        PrintWriter currOut;
        BufferedReader inputA;
        BufferedReader inputB;
        BufferedReader currIn;
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
                while (gameStart){
                    try {
                        String message = currIn.readLine();
                        currOut.println(message);
                        currOut.flush();
                        switchStreams();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
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
