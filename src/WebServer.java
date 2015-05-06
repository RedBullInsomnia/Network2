import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

/**
 * Class Server : Receives all the connections from clients
 *
 */
public class WebServer {

	private static ServerSocket ss;
	private static final int nbPort = 8001;
	private static int maxThreads = 10;

	/** Main method **/
	public static void main(String[] args) {

		// Handle argument
		if (args.length < 1) {
			System.out
					.println("Warning : you should add the maximal number of concurrent threads as an argument. The default value of "
							+ maxThreads + " will be used.");
		} else {
			maxThreads = Integer.parseInt(args[0]);
		}

		try {
			ss = new ServerSocket(nbPort);
		} catch (IOException e1) {
			System.err.println("Creation of new Server Socket failed: ");
			e1.printStackTrace();
			System.exit(1);
		}

		System.out.println("Server status : Running.");

		// Manage multiple requests
		ExecutorService service = Executors.newFixedThreadPool(maxThreads);
		int workerNumber = 1;

		try {
			while (true) {
				// Wait for connection
				Socket ws = ss.accept();
				ws.setSoTimeout(1000);
				service.execute(new Worker(ws, workerNumber));
				workerNumber++;
			}
		} catch (IOException e2) {
			System.err.println("Failed to accept connection: ");
			e2.printStackTrace();
			System.exit(2);
		}

		// Close the connections and streams
		finally {
			try {
				ss.close();
			} catch (IOException e) {
				System.err.println("Socket cannot close" + e.getMessage());
				System.exit(0);
			}
		}
	}
}
