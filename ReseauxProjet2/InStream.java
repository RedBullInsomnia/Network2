import java.net.*;
import java.io.*;

public class InStream {

	private Socket s;
	// Input stream used for the reception of objects
	private ObjectInputStream in;



	// Receiver
	public InStream(Socket _s){
				
		try {
			  s = _s;
			  in = new ObjectInputStream(s.getInputStream());
		} 
		catch (IOException e) {}
	}


	// Read String
	public String readString(){
		String msg = new String();
		
		try {
			msg = (String) in.readObject();
		} 
		catch (ClassNotFoundException e) { 
			System.err.println("InStream : readString failed");
			System.exit(1);
		} 
		catch (IOException e) { 
			System.err.println("InStream : readString failed");
			System.exit(1);
		} 
		
		return msg;
	}





}