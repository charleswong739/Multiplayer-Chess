package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {

	public static void main (String [] args) {
				
		try {
		ServerSocket ss = new ServerSocket(48209);
		System.out.println("Waiting for connection");
		Socket socket = ss.accept();
		System.out.println("Connection made");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		writer.write("SRB\n");
		writer.flush();
		System.out.println("SRB sent");
		String s = reader.readLine();
		
		System.out.println(s);
		
		writer.write("MOVE 1 0 2 2\n");
		writer.flush();
		System.out.println("Move sent");
		
		writer.close();
		reader.close();
		socket.close();
		ss.close();
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
