
/**
* Class Reply : 
* 	This class return a response format for the client
*
*/
public class Reply {

	private static final int indexVersion = 2;
	private String req = null;
	private String splitString[] = null;

	/*  Constructor  */
	Reply(String req) {	

		this.req = req;
		//Request r = new Request(req);
		//splitString = r.splitRequest();
	}	
	Reply() {	

		this.req = "No request";
		//Request r = new Request(req);
		//splitString = r.splitRequest();
	}


	
	/* Method replyMsg : 
	 *	 Return a response format
	 *
	 *	INPUT : http code
	 */
	public String replyMsg(String code){

		String r1 = "<HTML> \r\n"
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"> \r\n"
				+ "<HEAD> \r\n"
				+ "<TITLE>EchoServer Results</TITLE>\r\n"
				+ "</HEAD>\r\n";
		
		String r2 = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n"
				+ "<H1 ALIGN=\"CENTER\">EchoServer Results</H1> \r\n"
				+ "Here is the request line and request headers"
				+ " sent by your browser : \r\n"
				+ "<PRE> \r\n";

		String r2bis = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n"
				+ "<H1 ALIGN=\"CENTER\">EchoServer Results</H1> \r\n"
				+ "Sorry your request is wrong : \r\n"
				+ "<PRE> \r\n";
		
		String r3 = "</PRE> \r\n"
				+ "</BODY> \r\n"
				+ "</HTML> \r\n";

		String rep = r1 + r2 + req + r3;

		String r0 = splitString[indexVersion] + " " + code + "\r\n"
				+ "Server : EchoServer \r\n"
				+ "Content-Type: text/html \r\n"
				+ "Content-Length: " + rep.getBytes().length + "\r\n\r\n";


		// Check the validity and send the adapted response 
		if (code == "200 OK"){
			return (r0 + rep);
		}
		else{
			return (r1 + r2bis + r0 + r3);
		}
	}


		public String auth(String msg){

		String r1 = "<HTML> \r\n"
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"> \r\n"
				+ "<HEAD> \r\n"
				+ "<TITLE>Authentification</TITLE>\r\n"
				+ "</HEAD>\r\n";
		
		String r2 = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n"
				+ "<H1 ALIGN=\"CENTER\">Authentification</H1> \r\n"
				+ "Please, provide your login and password : \r\n"
				+ "<PRE> \r\n";
		
		String r3 = "</PRE> \r\n"
				+ "</BODY> \r\n"
				+ "</HTML> \r\n";

		String rep = r1 + r2 + msg + r3;

		String r0 = "HTTP/1.1" + " 200 OK " + "\r\n"
				+ "Server : EchoServer \r\n"
				+ "Content-Type: text/html \r\n"
				+ "Content-Length: " + rep.getBytes().length + "\r\n\r\n";

			return (r0 + rep);

		}



	public String formul(){

		String form = "<!DOCTYPE html> \r\n"
					+ "<html> \r\n"
					+ "<body> \r\n"
					+ "<form action=\"identification.html\" method=\"POST\">\r\n"
					+ "Login:<br> \r\n"
					+ "<input type=\"text\" name=\"login\" value=\"\"> \r\n"  
					+ "<br> \r\n"
					+ "Password:<br> \r\n"
					+ "<input type=\"text\" name=\"pass\" value=\"\">\r\n" 
					+ "<br><br>\r\n"
					+ "<input type=\"submit\" value=\"Submit\">\r\n"
					+ "</form> \r\n"
					+ "<p>If you click \"Submit\", the form-data will be sent to a page called \"identification.html\".</p>\r\n"
					+ "<p>The first name will not be submitted, because the input element does not have a name attribute.</p>\r\n"
					+ "</body>\r\n"
					+ "</html>\r\n";

		return form;
	}




}
