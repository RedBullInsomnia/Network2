import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.regex.Pattern;


/**
* Class HTTPRequest :
* 	Receives the requests of client
*
*/
public class HTTPRequest {

	private String header; // message without the first line
	private String splitString[] = null;
	private final static int sizeBuffer = 164;
	private String log;
	private String pass;
	private Messages postMessages;
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
		postMessages = new Messages();
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
					if (!bufferString.endsWith("\r\n\r\n")){
						//System.out.println("TEST3 : " + bufferString);
						bufferString = bufferString.substring(bufferString.indexOf("\r\n\r\n") + 4);
					}
					else{
						bufferString = "";
						//System.out.println("TEST2 : " + bufferString);
					}

					// request = HTTP request + header : So we decompose
					splitRequest(request);
					splitHeader(request);
					isPostRequest = getMethod().equals("POST");

					System.out.println("bufferString : " + bufferString);
					System.out.println("header : " +request);

					if(isPostRequest) {
						bodyRequest(bufferString);
						break;
					}

					if (bufferString.equals("")) {
		        		if (!isPostRequest)
		        			break;
		        		else if (isPostRequest)
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
			}
		}
    System.out.println("J'ai fini");
	}

	/*  Split HTTP request  */
	public void splitRequest(String msg){

		// Take the first line of the request :
		// Computation of regex
		Pattern p = Pattern.compile("\r\n");
		String linesRequest[] = null;
		linesRequest = p.split(msg);
		// Sparse first line :
		Pattern p2 = Pattern.compile(" ");
		splitString = p2.split(linesRequest[0]);

	}

	/*  Split message  */
	public void splitHeader(String msg){

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

	public void bodyRequest(String body){

		Pattern pat = Pattern.compile("=");
		String test[] = null;
		test = pat.split(body);

		// Login and password
		if (test[0].equals("login"))
		{
			// Computation of regex
			Pattern p = Pattern.compile("&");
			String extract1[] = null;
			extract1 = p.split(body); // extract1[0] = "login=XXX" - extract1[1] = "pass=XXX"
			// login
			Pattern p2 = Pattern.compile("=");
			String extract2[] = null;
			extract2 = p2.split(extract1[0]);
			log = extract2[1];
			// password
			Pattern p3 = Pattern.compile("=");
			String extract3[] = null;
			extract3 = p3.split(extract1[1]);
			pass = extract3[1];
		}

		// New message
		else if (test[0].equals("comment"))
		{
			Pattern p4 = Pattern.compile("\\+");
			String extract4[] = null;
			extract4 = p4.split(test[1]);

			// new message
			String newMessage = new String();
			for(int i = 0; i<extract4.length ; ++i)
			{
				newMessage+= extract4[i];
			}
			// envoyer le message à la classe message
			postMessages.addMessage(newMessage);
		}

		else
			System.out.println("It's a problem !");
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
	public String[] getMessages(){
		return postMessages.getMessages();
	}

}
