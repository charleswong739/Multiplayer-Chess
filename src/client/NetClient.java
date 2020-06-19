package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

class NetClient extends Thread {

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
		while (!match && connected) {
			try {
				if (reader.ready()) {
					System.out.println("Reader ready");
					String s = reader.readLine();
					if (s.equals("SRW")) {
						message = "WHITE";
						
						writer.write("READY");
						System.out.println("White ready");
						writer.flush();
						match = true;
					} else if (s.equals("SRB")){
						System.out.println("Recieved black message");
						message = "BLACK";
						
						writer.write("READY");
						System.out.println("Black ready");
						writer.flush();
						match = true;
						read = true;
					}
					
					System.out.println(message);
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
						message = reader.readLine();
					}
					read = false;
					System.out.println("Message: " + message);
				} catch (IOException e) {
					System.out.println("Read cycle failed");
					e.printStackTrace();
				}
				System.out.println("Reading");
			}
		}
		System.out.println("End of loop");
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
	
	synchronized public boolean status() {
		return connected;
	}
	
	synchronized public void disconnect() {
		System.out.println("l");
		connected = false;
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
		System.out.println("Message read: " + s + "\nCurrent message: " + message);
		read = false;
		
		return s;
	}
	
	synchronized public void write(String s) {
		try {
			writer.write(s);
			writer.flush();
		} catch (IOException e) {
			System.out.println("Write failed");
			e.printStackTrace();
		}
	}
}