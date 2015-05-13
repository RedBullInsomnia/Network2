import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * HTTPRequest class
 * 
 * @author hwk, mcz
 *
 */
public class HTTPRequest {

	public static String getLoginPage(int status) {
		String page = getBodyTop("Identification",
				"Please provide your login and password :")
				+ getLoginForm(status) + getBodyDown();
		return page;
	}

	public static String getPostsPage(int pageNum, int maxPosts) {
		
		String page = getBodyTop("View posts", "The " + maxPosts + " last posts are :")
				+ getViewPosts(pageNum, maxPosts) 
			+ getBodyDown();
		
		return page;
	}
	

	public static String getViewPosts(int pageNum, int maxPosts) {

		ArrayList<String> arrayList = Messages.getMessages();
		
		//String msg = "<form action=\"viewPosts.html\">\r\n";
		String msg = "";
		int i = arrayList.size() - pageNum*maxPosts;
		if (i < 0)
			i = 0;
		for (int j = 0; j < maxPosts && (i+j) < arrayList.size(); ++j)
			msg += "<p>" + arrayList.get(i + j) + "<p>\r\n";

		msg += "<a href=\"/postMessage.html\">New message</a>\r\n";

		return msg;
	}

	public static String getBodyTop(String title, String message) {
		String r1 = "<HTML>\r\n"
				+ "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\r\n"
				+ "<HEAD>\r\n" + "<TITLE>" + title + "</TITLE>\r\n"
				+ "</HEAD>\r\n";

		String r2 = "<BODY BGCOLOR=\"#FDF5E6\"> \r\n" + "<H1 ALIGN=\"CENTER\">"
				+ title + "</H1> \r\n" + message + "\r\n" + "<PRE>\r\n";

		return r1 + r2;
	}

	public static String getBodyDown() {
		return "</PRE>\r\n" + "</BODY>\r\n" + "</HTML>\r\n";
	}

	public static String getLoginForm() {
		return getLoginForm(0);
	}

	public static String getLoginForm(int status) {
		String form = "<form action=\"loginAction\" method=\"POST\">\r\n"
				+ "Login:<br> \r\n"
				+ "<input type=\"text\" name=\"login\" value=\"\"> \r\n"
				+ "<br> \r\n" + "Password:<br> \r\n"
				+ "<input type=\"password\" name=\"pass\" value=\"\">\r\n"
				+ "<br><br>\r\n"
				+ "<input type=\"submit\" value=\"Submit\">\r\n"
				+ "</form> \r\n";

		if (1 == status)
			form += "<p> Wrong login </p>\r\n";
		else if (2 == status)
			form += "<p> Wrong passwrod </p>\r\n";

		return form;
	}
	
	public static String getPostsMessage()
	{
		String msg = "<form action=\"viewPosts.html\" id=\"usrform\" method=\"POST\">\r\n"
				+ "<br> \r\n"
				+ "<textarea rows=\"4\" cols=\"50\" name=\"comment\" form=\"usrform\">\r\n"
				+ "Enter text here...</textarea>\r\n"
				+ "<br> \r\n"
				+ "<input type=\"submit\"> \r\n"
				+ "</form>\r\n";

		return msg;
	}

	public static String errorPage() {
		return getBodyTop("An error occurred", "") + getBodyDown();
	}
}
