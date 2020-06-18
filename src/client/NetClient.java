package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class NetClient extends Thread {

	final String HOST = "70.27.133.217";
	final int PORT = 5000;
	
	Socket socket;      
	BufferedReader reader;     
	BufferedWriter writer;
	
	// flags
	boolean connected;
	boolean match;
	boolean read;
	
	String message;

	public NetClient() {
		connected = false;
		match = false;
		read = false;
	}

	public void run() {
		
		// Connect
		try {
			System.out.println("Attempting to connect...");
			socket = new Socket(HOST, PORT);
			System.out.println("Connected");
			connected = true;
		} catch (Exception e) {
			System.out.println("Failed connection");
			e.printStackTrace();
		}
		
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Could not create I/O streams");
			e.printStackTrace();
		}
		
		// Check for available match
		while (!match) {
			try {
				if (reader.ready()) {
					if (reader.readLine().equals("SRW")) {
						message = "WHITE";
					} else {
						message = "BLACK";
					}
				}
				
				writer.write("READY");
				match = true;
			} catch (IOException e) {
				System.out.println("Could not join game");
				e.printStackTrace();
			}
		}
		
//		while (match) {
//			while (read) {
//				
//				read = false;
//			}
//		}
		
		try {
			writer.close();
			reader.close();
			socket.close();
		} catch (IOException e) {
			System.out.println("Could not close streams");
			e.printStackTrace();
		}
	}
	
	synchronized public boolean status() {
		return connected;
	}
	
	synchronized public void disconnect() {
		match = false;
	}
	
	synchronized public void recieve() {
		read = true;
	}
	
	synchronized public boolean poll() {
		if (message == null)
			return false;
		return true;
	}
	
	synchronized public String read() {
		String s = message;
		message = null;
		return s;
	}

	/*
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

	}*/
}