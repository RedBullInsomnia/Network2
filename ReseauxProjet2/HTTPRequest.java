
import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.regex.Pattern;


/**
* Class HTTPRequest : 
* 	Received the requests of client
*
*/
public class HTTPRequest {
	
	private String req; // message to check
	private String header; // message without the first line
	private String splitString[] = null;
	// Http Code
	static String ok = "200 OK";
	static String badRequest = "400 Bad Request";
	static String notImplemented = "501 Not Implemented";
	static String httpVersionNotSupported = "505 HTTP Version Not Implemented";
    


	/*  Constructor  */
	HTTPRequest(Socket s) throws IOException { 
		getRequest(s);
	}



	/*  getRequest  */
	public void getRequest(Socket s) throws IOException {

		// Read request and put into buffer
        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader buffer = new BufferedReader(in);

        // request = HTTP request + header : So we decompose
        String bufferString = "";
        boolean requestLine = true;

        while ( (bufferString = buffer.readLine()) != null && !bufferString.equals("") ){
	        	// First line (HTTP request)
	        	if (requestLine){
	        		splitRequest(bufferString);
	        		req = bufferString;
	        		System.out.println("CECI EST UN TEST : " + req);
	        		requestLine = false;
	        	} else{
	        		splitMessage(bufferString);
	        	}
        }

        System.out.println("J'ai fini"); 
	}




	/*  Split HTTP request  */
	public void splitRequest(String msg){

		// Computation of regex
		Pattern p = Pattern.compile(" ");
		// Split into subStrings
		splitString = p.split(msg);
	}



	/*  Split message  */
	public void splitMessage(String msg){

		header = msg;//String tmp = msg.substring(0, msg.indexOf("\r\n"));
		//System.out.println("TEST : " + req);
		//splitString = tmp.split("\\s");
	}



	/*  Check validity of request  */
	public String checkRequest(){
		
		if ( ! (getMethod().equals("GET") || getMethod().equals("POST")) ) { return notImplemented; }
		else if (! (getURL().startsWith("/") || getHeader().contains("Host")) ) { return badRequest; }
		else if (!getVersion().equals("HTTP/1.1")) { return httpVersionNotSupported; }
		else {
			return ok;
		}
	} 
	


	/*  get methods  */

	public String getRequest(){
		return req; 
	}

	public String getHeader(){
		return header;
	}

	public String getMethod(){
		// GET or POST
		return splitString[0]; 
	}

	public String getURL(){
		return splitString[1];
	}

	public String getVersion(){
		return splitString[2];
	}


} // end class


	