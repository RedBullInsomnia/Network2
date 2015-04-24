
import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
* Class Worker : 
* 	This class interacts with the user (in the client application)
*	and communicate with the server
*
*/
public class Worker extends Thread{
	
	Socket s;
	private int NumWorker;
	private String request = "", bufferString = "";
	private String reply = "";
	private static int sizeBuffer = 64;

	// Manage streams
	//private InStream receiver;
	//private OutStream sender;

	// Manage account of user
	private Sessions user;


	/*  Constructor  */
	Worker(Socket _s, int nw) {
		s=_s;
		NumWorker = nw;
		//receiver = new InStream(s);
		//sender = new OutStream(s); 
		user = new Sesions(s);
	}

	
	/**   Run method  **/
	@Override
	public void run() {

		// Client authentification
		int signIn = 1; // = 1 si pas encore logé, 2 si pass not correct , 3 si log incorrect & 0 si ok
		do{
			signIn = user.authentification();
		} while (signIn);
		
		System.out.println("Welcome\n");



		// GET REQUEST
		try{
			// Get socket writing and reading streams
			OutputStream out = s.getOutputStream();
			InputStream in = s.getInputStream();
		
			// Reading input from client
			byte msg[] = new byte[sizeBuffer];

			while(true){
				// Reading
				int len = in.read(msg);
				if(len <= 0)
					break;

				bufferString += new String(msg, 0, len);
				if (bufferString.contains("\r\n\r\n")) { 
					
					// Decomposition of the request
					request = bufferString.substring(0, bufferString.indexOf("\r\n\r\n") + 4);
					// Check if the request of client finish with "\r\n\r\n" or not
					if (!bufferString.endsWith("\r\n\r\n"))
						bufferString = bufferString.substring(bufferString.indexOf("\r\n\r\n") + 4, bufferString.length());
					else
						bufferString = "";

					Request test = new Request(request);



					//////////////////////////// TO MANAGE :

					Reply rep = new Reply(request);
					reply = rep.replyMsg(test.split());

					// Sending the reply
					System.out.print("Msg from Server to Client (Worker numero:" + NumWorker + ") : \n" + reply);
					out.write(reply.getBytes(), 0, (reply.getBytes()).length);
					out.flush();


					////////////////////////////




					// Check keep alive
					if (!request.contains("Connection: keep-alive")) { 
						System.out.println("Close worker number : " + NumWorker);
						break;
					}
					
					// Additional time
					if (!s.getKeepAlive()){
						s.setSoTimeout(5000);
						System.out.println("Asked to be kept alive");
						s.setKeepAlive(true);
					}	
				}	
			}
		}
		catch (SocketTimeoutException e1) {
			System.out.println("Socket Timeout (worker : " + NumWorker + ") " + e1.getMessage());
		} 
		catch(IOException e2){
			System.err.println("Error in/out " + e2.getMessage());
			e2.printStackTrace();
		}
		catch(Exception e3){
			System.err.println("Error in worker : " + NumWorker);
			e3.printStackTrace();
		}		
		// Close the connections and streams
		finally {
			try{
				System.out.println("GoodBye ! \n");
				s.close();
			}
			catch (IOException e) {
				System.err.println("Socket cannot close" + e.getMessage());
			}
		}
	}

}
