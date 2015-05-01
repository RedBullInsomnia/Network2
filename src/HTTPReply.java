import java.util.ArrayList;
import java.util.Iterator;

/**
 * Class HTTPReply : This class returns a response format for the client
 *
 */
public class HTTPReply {

	/* Constructor */
	HTTPReply() {
	}

	/* HTML : Status line */
	public String statusLine(boolean extra, String compl) {

		String r0 = "HTTP/1.1" + " 200 OK " + "\r\n"
				+ "Server : Web Server \r\n" + "Content-Type: text/html \r\n";
		// + "Content-Length: " + rep.getBytes().length + "\r\n\r\n";
		if (extra)
			r0 += compl;

		r0 += "\r\n\r\n";

		return r0;
	}

	/* HTML : Body TOP */
	public String bodyTop(String title, String sentence) {

		String r1 = "<HTML> \r\n"
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\"> \r\n"
				+ "<HEAD> \r\n" + "<TITLE>" + title + "</TITLE>\r\n"
				+ "</HEAD>\r\n";

		String r2 = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n" + "<H1 ALIGN=\"CENTER\">"
				+ title + "</H1> \r\n" + sentence + "\r\n" + "<PRE> \r\n";

		return (r1 + r2);
	}

	/* HTML : Body DOWN */
	public String bodyDown() {

		String r3 = "</PRE> \r\n" + "</BODY> \r\n" + "</HTML> \r\n";
		return r3;
	}

	/* Identification : Submit form */
	public String getLogIn(String test, boolean extra, String compl) {

		String f = statusLine(extra, compl)
				+ bodyTop("Identification",
						"Please, provide your login and password :")
				+ logIn(test) + bodyDown();
		return f;
	}

	/* logIn */
	public String logIn(String test) {

		String form = "<!DOCTYPE html> \r\n" + "<html> \r\n" + "<body> \r\n"
				+ "<form action=\"viewPosts.html\" method=\"POST\">\r\n"
				+ "Login:<br> \r\n"
				+ "<input type=\"text\" name=\"login\" value=\"\"> \r\n"
				+ "<br> \r\n" + "Password:<br> \r\n"
				+ "<input type=\"password\" name=\"pass\" value=\"\">\r\n"
				+ "<br><br>\r\n"
				+ "<input type=\"submit\" value=\"Submit\">\r\n"
				+ "</form> \r\n"
				// +
				// "<p>If you click \"Submit\", the form-data will be sent to a page called \"identification.html\".</p>\r\n"
				+ "<p>" + test + "</p>\r\n" + "</body>\r\n" + "</html>\r\n";

		return form;
	}

	/* viewPosts */
	public String getViewPosts(ArrayList<String> arrayList) {

		String posts = statusLine(false, " ")
				+ bodyTop("ViewPosts", "The ten last posts are  :")
				+ viewPosts(arrayList) + bodyDown();
		return posts;
	}

	/* Display messages */
	public String viewPosts(ArrayList<String> arrayList) {

		String msg = "<!DOCTYPE html> \r\n" + "<html> \r\n" + "<body> \r\n"
				+ "<form action=\"viewPosts.html\"> \r\n" + "<p> ";
		Iterator<String> it = arrayList.iterator();
		while(it.hasNext())
		{
			msg += it + "<p> \r\n";
		}

		String msgNext = "<a href=\"http://localhost:8001/postMessage.html\">New message</a>\r\n"
				+ "</body> \r\n" + "</html> \r\n";

		return (msg + msgNext);
	}

	/* postMessage */
	public String getPostMessage() {

		String st = statusLine(false, " ")
				+ bodyTop("PostMessage", "Enter a new message :")
				+ postMessage() + bodyDown();
		return st;
	}

	/* body postMessage */
	public String postMessage() {

		String msg = "<!DOCTYPE html> \r\n"
				+ "<html> \r\n"
				+ "<body> \r\n"
				+ "<form action=\"viewPosts.html\" id=\"usrform\" method=\"POST\">\r\n"
				+ "<input type=\"submit\"> \r\n"
				+ "</form> \r\n"
				+ "<br> \r\n"
				+ "<textarea rows=\"4\" cols=\"50\" name=\"comment\" form=\"usrform\">\r\n"
				+ "Enter text here...</textarea>\r\n" + "</body> \r\n"
				+ "</html> \r\n";

		return msg;
	}

	/* Page not implemented */
	public String getNotImplemented() {

		String f = statusLine(false, "")
				+ bodyTop("Sorry", "This HTML page is not implemented")
				+ bodyDown();
		return f;
	}
}
