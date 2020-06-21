/**
 * NetClient
 * Version 1.0
 * @author Charles Wong
 * 2020-06-21
 * Description: conencts to server, sends and recieves messages on moves.
 */

//packae statements
package client;

//import statements
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class NetClient extends Thread {
	
	//server settings
	private final String HOST = "99.254.124.185";
	private final int PORT = 48209;
	
	private Socket socket;      
	private BufferedReader reader;     
	private BufferedWriter writer;
	
	// flags
	private boolean connected;
	private boolean match;
	private boolean read;
	
	private String message;
	
	//Constructor
	public NetClient() {
		connected = false;
		match = false;
		read = false;
	}

	/**
	 *run
	 *@param: null
	 *@return: null
	 * runs the thread
	 */
	public void run() {
		
		// Connect
		while (!connected) {
			try {
//				System.out.println("Attempting to connect...");
				socket = new Socket(HOST, PORT);
				System.out.println("Connected");
				connected = true;
			} catch (Exception e) {
//				System.out.println("Failed connection");
//				e.printStackTrace();
			}
		}
		
		//create readers/writers
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Could not create I/O streams");
			e.printStackTrace();
		}
		
		// Check for available match
		while (!match && connected) {
			try {
				if (reader.ready()) {
					String s = reader.readLine();
					if (s.equals("SRW")) {
						message = "WHITE";
						
						writer.write("READY\n");
						writer.flush();
						match = true;
					} else if (s.equals("SRB")){
						message = "BLACK";
						
						writer.write("READY\n");
						writer.flush();
						match = true;
						read = true;
					}
				}
			} catch (IOException e) {
				System.out.println("Could not join game");
				e.printStackTrace();
			}
		}

		while (match && connected) {
			while (read && connected) {
				try {
					if (reader.ready()) {
						message = reader.readLine(); //read message
						read = false;
						System.out.println("Message recieved: " + message);
					}
				} catch (IOException e) {
					System.out.println("Read cycle failed");
					e.printStackTrace();
				}
				
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					System.out.println("Sleep interrupted");
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				System.out.println("Sleep interrupted");
				e.printStackTrace();
			}
		}

		try {
			writer.close();
			reader.close();
			socket.close();
			System.out.println("Disconnected");
		} catch (IOException e) {
			System.out.println("Could not close streams");
			e.printStackTrace();
		}
	}
	
	/*
	 * status
 	 * @param: null
 	 * @return: boolean of connected or not
	 * checks connection
 	 */
	synchronized public boolean status() {
		return connected;
	}
	
	/**
 	 * disconnect
  	 * @param: null
  	 * @return: null
  	 * disconnects from the server
  	 */
	synchronized public void disconnect() {
		System.out.println("Attempting to disconnect...");
		connected = false;
	}
	
	/*
 	 * receive
  	 * @param: null
  	 * @return: null
	 * allows client to receive messages
  	 */
	synchronized public void recieve() {
		read = true;
	}
	
	/*
  	 * poll
 	 * @param: null
 	 * @return: null
	 * checks for message
 	 */
	synchronized public boolean poll() {
		if (message == null) {
			return false;
		}
		return true;
	}
	
	/*
	 * read
 	 * @param: null
	 * @return: String of message.
	 * prints out and returns the server message
 	 */
	synchronized public String read() {
		String s = message;
		message = null;
		System.out.println("Message read: " + s);
		read = false;
		
		return s;
	}
	
	/*
	 * write
  	 * @param: String of stuff
  	 * @return: null
  	 * writes message to the server
 	 */
	synchronized public void write(String s) {
		try {
			writer.write(s + "\n");
			writer.flush();
			System.out.println("Message sent: " + s);
		} catch (IOException e) {
			System.out.println("Write failed");
			e.printStackTrace();
		}
	}
} //end of NetClient Class
