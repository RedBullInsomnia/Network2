import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
 * Class Worker : This class interacts with the user (in the client application)
 * and communicates with the server
 *
 */
public class Worker extends Thread {

	Socket s;
	private int NumWorker;
	private HTTPReply rep;
	private Sessions user;
	private boolean cookieIdentif;

	/* Constructor */
	Worker(Socket s, int nw) {
		this.s = s;
		NumWorker = nw;
		rep = new HTTPReply();
		user = new Sessions();
	}

	/** Run method **/
	@Override
	public void run() {

		try{
			// Get socket writing and reading streams
			OutputStream out = s.getOutputStream();
			InputStream in = s.getInputStream();
		




			// OLD VERSION


			// Read HTTP request from browser
			HTTPRequest req = new HTTPRequest(s);

			// Check validity of request
			if (req.checkRequest() != "200 OK") {
				System.out.println("BAD REQUEST : " + req.checkRequest());
			}

			// Check if available cookie
	/*		while (!cookieIdentif) {
				if ( (req.getHeader()).contains("Cookie:") ) {
					cookieIdentif = true;
					break;
				}
				if (req.getURL().equals("/")) {
					// Check account :
					String account = user.checkAccount(req.getLog(), req.getPass(), req.getMessages());
					out.write(account.getBytes(), 0, (account.getBytes()).length);
					out.flush();
				}
			}
	*/

			// Identification done


			// New test : GET
			if (req.getMethod().equals("GET")) {
				if (req.getURL().equals("/")
						|| req.getURL().equals("/identification.html")) {
					String f = rep.getLogIn(" ", false, " ");
					out.write(f.getBytes(), 0, (f.getBytes()).length);
					out.flush();
				} else if (req.getURL().equals("/viewPosts.html")) {

				} else if (req.getURL().equals("/postMessage.html")) {
					String f = rep.getPostMessage();
					out.write(f.getBytes(), 0, (f.getBytes()).length);
					out.flush();
				} else {
					System.out.println("Page not implemented");
					String f = rep.getNotImplemented();
					out.write(f.getBytes(), 0, (f.getBytes()).length);
					out.flush();
				}
			}

			// POST
			if (req.getMethod().equals("POST")) {

				if (req.getURL().equals("/viewPosts.html")) {
				
					// Check account :
					Messages.addMessage("No message");
					//System.out.println("Les messages sont : " + req.getMessages());
					String account = user.checkAccount(req.getLog(), req.getPass(), req.getMessages());
					out.write(account.getBytes(), 0, (account.getBytes()).length);
					out.flush();
				
				} else if (req.getURL().equals("/postMessage.html")) {

				}
			}







		} catch (SocketTimeoutException e1) {
			System.out.println("Socket Timeout (worker : " + NumWorker + ") "
					+ e1.getMessage());
		} catch (IOException e2) {
			System.err.println("Error in/out " + e2.getMessage());
			e2.printStackTrace();
		} catch (Exception e3) {
			System.err.println("Error in worker: " + NumWorker);
			e3.printStackTrace();
		}
		// Close the connections and streams
		finally {
			try {
				s.close();
			} catch (IOException e) {
				System.err.println("Socket cannot close" + e.getMessage());
			}
		}
	}
}
