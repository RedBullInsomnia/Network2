
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

/**
* Class Server : 
* 	Received all the connections from clients
*
*/
public class WebServer {
	
	private static ServerSocket ss;
	private static final int nbPort = 8001;
	private static int maxThreads;


	/**   Main method	**/
	public static void main(String[] args) {

		// Argument on the command line 
		if (args.length != 1){
			System.err.println("ERROR : Argument on the command line");
			return;
		}
		maxThreads = args[0];

		try {
			ss = new ServerSocket(nbPort);
		}
		catch (IOException e1) {
			System.err.println("Creation of new Server Socket failed");
			e1.printStackTrace();
			System.exit(1);
		}

		System.out.println("+ SERVER CONNECTED + \n");
		
		// Manage multiple requests
		ExecutorService service = Executors.newFixedThreadPool(int maxThreads);
		int nw = 1;

		
		while (true){

			try{
				// Wait for connection
				Socket ws = ss.accept();
				ws.setSoTimeout(1000);
				service.execute(new Worker(ws, nw));
				nw++;
			}
			
			catch (IOException e2) {
				System.err.println("Failed to accept connection: ");
				e2.printStackTrace();
				System.exit(2);
			}
		}		 
		
		// Close the connections and streams
		finally{
			try{
				ss.close();
			}
			catch (IOException e) {
				System.err.println("Socket cannot close" + e.getMessage());
				System.exit(0);
			}
		}
	}
}



