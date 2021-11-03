package main;

public class ServerDriver {

	public static void main(String[] args) {
		
		//new Server(); //This server does not allow multiple clients; serving as backup code. pls ignore
		new MultithreadedServer(); //Allows multiple clients
		
	}

}
