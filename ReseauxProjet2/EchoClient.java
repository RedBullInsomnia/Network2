
import java.net.*;
import java.io.*;

/**
* Class Client : 
* 	Program that will connect to the server
*
*/
public class EchoClient {
	
	private static final int nbPort = 8246;
	private static Socket s;
	private static int sizeBuffer = 800;
	

	/**   Main method  **/
	public static void main(String[] args) throws IOException {
		
		try{
			s = new Socket("localhost", nbPort);
		}
		catch (IOException e1) {
			System.err.println("Creation of new Socket failed");
			e1.printStackTrace();
			System.exit(1);
		}

		System.out.println("+ CLIENT CONNECTED + \n");

		// Manage in and out streams
		OutputStream out = s.getOutputStream ();
		InputStream in = s.getInputStream ();
			
		try{
			// Send a message to server
			String st = "GET / HTTP/1.1 \r\n" + "Host: localhost:" + nbPort + "\r\n\r\n";
			out.write(st.getBytes()); 
								
			// Response from server
			byte msg[ ] = new byte[sizeBuffer];
			while (true){
				if(in.read(msg) <= 0) break;
				String response = new String(msg);
				System.out.print(response);
			}
		}
		catch (IOException e2){
			System.err.println("Connexion with server failed");
			e2.printStackTrace();
			System.exit(2);
		}
		// Close the connection and streams
		finally{
			try{
				s.close();
			}
			catch (IOException e) {
				System.err.println("Socket cannot close" + e.getMessage());
				System.exit(0);
			}
		}
			
	}
}
			
	