import java.io.IOException;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EchoServer
 * 
 * @author Hwk
 *
 */
public class EchoServer {

	public static void main(String[] args) {
		int n = 0;

		try {
			ServerSocket ss = new ServerSocket(8001);

			while (true) {
				Socket ts = ss.accept();
				Worker w = new Worker(ts, ++n);
				w.start();
			}
		} catch (IOException io) {
			System.err.print("Error on socket: " + io.getMessage());
		}
		
	}

}
