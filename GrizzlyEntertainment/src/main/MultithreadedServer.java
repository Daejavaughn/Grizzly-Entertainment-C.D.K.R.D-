package main;

import java.io.EOFException;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class MultithreadedServer {

	private ServerSocket serverSocket;
	private Socket connectionSocket;
	
	private static final Logger Logger=LogManager.getLogger(MultithreadedServer.class);
	
	public MultithreadedServer(){
		
		this.createConnection();
		this.waitForRequests();
	}
	
	private void createConnection()
	{
		try {
			//create new instance of the ServerSocket listening on port 8888
			serverSocket = new ServerSocket(8888, 1);
			Logger.info("Server Connection Creation Attempted");
		}
		catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}

	private void waitForRequests()
	{
		try {
			while (true) {
				connectionSocket = serverSocket.accept();
				while (connectionSocket.isConnected()) {//when the connection is made to the server
					ClientHandler c = new ClientHandler(connectionSocket);
					Thread newThread = new Thread(c);//new thread is created
					newThread.start();//creates a client on the new thread
					System.out.println("A client has connected");
					connectionSocket = serverSocket.accept();
					Logger.info("Client-Server Connection Established");
				}
	}
		}catch (EOFException ex) {
			System.out.println("Client has terminated connections with the server");
			ex.printStackTrace();
			Logger.error("EOF Excepton " + ex.getMessage());
		} catch (IOException ex) {
			ex.printStackTrace();
			Logger.error("IOExcepton " + ex.getMessage());
		}
	}
}
