
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
	
	private String header; // message without the first line
	private String splitString[] = null;
	private final static int sizeBuffer = 64;
	private String log;
	private String pass;
	// Http Code
	static String ok = "200 OK";
	static String badRequest = "400 Bad Request";
	static String notImplemented = "501 Not Implemented";
	static String httpVersionNotSupported = "505 HTTP Version Not Implemented";
    


	/*  Constructor  */
	HTTPRequest(Socket s) throws IOException { 
		log = new String();
		pass = new String();
		getRequest(s);
	}



	/*  getRequest  */
	public void getRequest(Socket s) throws IOException {
		boolean isPostRequest = false;
		int blankLinesCount = 0;
		String request;
		String bufferString = "";

		// Read request and put into buffer
        InputStream in = s.getInputStream();
		byte buffer[] = new byte[sizeBuffer];

		while (true) {

			int len = in.read(buffer);
				if(len <= 0)
					break;
			bufferString += new String(buffer, 0, len);

			if (bufferString.contains("\r\n\r\n")) { 
					
					// Decomposition of the request
					request = bufferString.substring(0, bufferString.indexOf("\r\n\r\n") + 4);
					// Check if the request of client finish with "\r\n\r\n" or not
					if (!bufferString.endsWith("\r\n\r\n"))
						bufferString = bufferString.substring(bufferString.indexOf("\r\n\r\n") + 4, bufferString.length());
					else
						bufferString = "";
					
					// request = HTTP request + header : So we decompose
					splitRequest(request);
					splitHeader(request);
					isPostRequest = getMethod().equals("POST");

					System.out.println("bufferString : " + bufferString);
					System.out.println("header : " +request);

					if(isPostRequest)
						bodyRequest(bufferString);


					if (bufferString.equals("")) {
		        		if (!isPostRequest)
		        			break;
		        		else if (blankLinesCount > 0)
		        			break;
		        		else {
		        			blankLinesCount += 1;
		        			System.out.println("BLANK LINE : ");
		        			continue;
		        		}
		        	}
		     
	
					// Check keep alive
					if (!request.contains("Connection: keep-alive")) { 
						System.out.println("Close worker number");
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
	public void splitHeader(String msg){

		header = msg;//String tmp = msg.substring(0, msg.indexOf("\r\n"));
		//System.out.println("TEST : " + req);
		//splitString = tmp.split("\\s");
	}



	/*  Check validity of request  */
	public String checkRequest(){
		
		System.out.println("TEST : " + getMethod());

		if ( ! (getMethod().equals("GET") || getMethod().equals("POST")) ) { return notImplemented; }
		else if (! (getURL().startsWith("/") || getHeader().contains("Host")) ) { return badRequest; }
		else if (!getVersion().equals("HTTP/1.1")) { return httpVersionNotSupported; }
		else {
			return ok;
		}
	} 



	public void bodyRequest(String body){

		log = body.substring(body.indexOf("=")+1, body.indexOf("&"));
		pass = body.substring(body.lastIndexOf("=")+1, body.length());
		//System.out.println("log : " +log);
		//System.out.println("pass : " +pass);

	}
	


	/*  get methods  */

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

	public String getLog(){
		return log;
	}

	public String getPass(){
		return pass;
	}



} // end class


	