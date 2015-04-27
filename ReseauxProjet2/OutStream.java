import java.net.*;
import java.io.*;
import java.io.ObjectOutputStream;

public class OutStream {

	private Socket s;
	// Output stream used for the sending of objects
	private ObjectOutputStream out;



	// Sender
	public OutStream(Socket _s){
				
		try {
			  s = _s;
			  out = new ObjectOutputStream(s.getOutputStream());
		} 
		catch (IOException e) {}
	}


	// Send String
	public void sendString(String msg){
		
		try {
			out.writeObject(msg);
		} 
		catch (IOException e) { } 
	}





}