
/**
* Class Request : 
* 	Received the requests to CHECK SYNTAX
*
*/
public class Request {
	
	private static String msg; // message to check
	private static String splitString[] = null;

	// Http Code
	static String ok = "200 OK";
	static String badRequest = "400 Bad Request";
	static String notImplemented = "501 Not Implemented";
	static String httpVersionNotSupported = "505 HTTP Version Not Implemented";


	/*  Constructor  */
	Request(String msg){ this.msg = msg; }


	/* Method split :  Decompose the request and check syntax
	 *
	 *  OUTPUT : http code
	 */
	public String split() throws Exception{
		 
		// Split "msg" and put the result into "splitString"
		String tmp = msg.substring(0, msg.indexOf("\r\n"));
		splitString = tmp.split("\\s");

		// Check syntax
		if (!compareMethod(splitString[0])) { return notImplemented; }
		else if (!compareURL(splitString[1]) || !(msg.contains("Host:"))) { return badRequest; }
		else if (!compareVersion(splitString[2])) { return httpVersionNotSupported; }
		else {
			return ok;
		}
	} 


	/* Method splitRequest :  Decompose the request
	 *
	 *  OUTPUT : A String array with the request
	 */
	public String[] splitRequest() {
		 
		// Split "msg" and put the result into "splitString"
		String tmp = msg.substring(0, msg.indexOf("\r\n"));
		splitString = tmp.split("\\s");

		return splitString;
	} 

	
	/* Method compareMethode : 
	 *	 Check syntax of HTTP method
	 *
	 *	OUTPUT : True if ok, False otherwise
	 */
	public boolean compareMethod(String req){
		
		if (req.compareTo("GET") == 0 || req.compareTo("POST") == 0)
			return true;
		else
			return false;
	}
	
	
	/* Method compareURL : 
	 *	 Check syntax of URL
	 *
	 *	OUTPUT : True if ok, False otherwise
	 */
	public boolean compareURL(String req){
		
		return req.startsWith("/");
	}
	
	
	/* Method compareVersion : 
	 *	 Check syntax of HTTP version
	 *
	 *	OUTPUT : True if ok, False otherwise
	 */	
	public boolean compareVersion(String req){
		
		if(	req.compareTo("HTTP/1.1") == 0 ){
			return true;
		}
		else
			return false;
	}	

}

