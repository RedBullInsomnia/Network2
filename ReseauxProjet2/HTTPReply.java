
import java.net.*;
import java.io.*;

/**
* Class HTTPReply : 
* 	This class return a response format for the client
*
*/
public class HTTPReply {


	/*  Constructor  */
	HTTPReply() {	
	}	


	/*  HTML header DOWN */
	public String htmlHeader(){
		
		String r0 = "HTTP/1.1" + " 200 OK " + "\r\n"
				+ "Server : Web Server \r\n"
				+ "Content-Type: text/html \r\n\r\n";
				//+ "Content-Length: " + rep.getBytes().length + "\r\n\r\n";
		return r0;
	}
	/*  HTML header TOP */
	public String htmlHeaderTop(String title){

		String r1 = "<HTML> \r\n"
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"> \r\n"
				+ "<HEAD> \r\n"
				+ "<TITLE>"+ title +"</TITLE>\r\n"
				+ "</HEAD>\r\n";
		return r1;
	}
	/*  HTML header DOWN */
	public String htmlHeaderDown(){

		String r3 = "</PRE> \r\n"
				+ "</BODY> \r\n"
				+ "</HTML> \r\n";
		return r3;
	}
	/*  HTML header BODY */
	public String htmlHeaderBody(String title, String sentence){

		String r2 = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n"
				+ "<H1 ALIGN=\"CENTER\">" + title + "</H1> \r\n"
				+ sentence + "\r\n"
				+ "<PRE> \r\n";
		return r2;		

	}
		
		



	/*  Submit form  */
	public String getForm(){

		String f = htmlHeader()
					+ htmlHeaderTop("Identification")
					+ htmlHeaderBody("Identification", "Please, provide your login and password :")
					+ form()
					+ htmlHeaderDown();
		return f;
	}

	public String form(){

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




} // end class



























	/*  Redirect order to viewPosts.html  */
/*	public String redirectViewPosts(){

		String rep = "HTTP/1.1 303 See Other \r\n"
					+ "Location : http://localhost:8001/viewPosts.html \r\n";
		return rep;
	}
*/







	/*  Display 10 posts  */
/*	public String viewPosts(){

		String msg = headerTOP();

		msg += LESPOSTES + headerDOWN();


	}

*/







/*

		String r0 = splitString[indexVersion] + " " + code + "\r\n"
				+ "Server : EchoServer \r\n"
				+ "Content-Type: text/html \r\n"
				+ "Content-Length: " + rep.getBytes().length + "\r\n\r\n";
*/


