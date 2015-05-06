import java.util.Hashtable;
import java.util.ArrayList;

/**
 * Class Sessions handles accounts
 * @author hwk
 *
 */
public class Sessions {

	// Database of users
	private static Hashtable<String, String> accounts = new Hashtable<String, String>();
	// Credentials are hard-coded values in the server memory :
	static {
		accounts.put("Leduc", "mypass1");
		accounts.put("Hiard", "itagpw?");
		accounts.put("Kurose", "&Ross");
		accounts.put("ab", "cd");
	}

	/*
	 * Constructor 
	 */
	public Sessions() {

	}

	/*
	 * identification
	 * Tests a pair of login and pass
	 * Return 0 if ok
	 * 		  1 if wrong login
	 *        2 if wrong password
	 */
	public int identification(String login, String pass) {

		if (accounts.get(login) != null && accounts.get(login).equals(pass)) {
			return 0; // Correct ID
		}
		else if (accounts.get(login) == null) {
			return 1; // Wrong login
		}
		else {
			return 2; // Wrong password
		}
	}

	/*
	 * checkAccount
	 * Input : log + password + set of messages to display
	 * Return nextPage to send to Client
	 *        
	 */
	public String checkAccount(String log, String pass, ArrayList<String> messagesToDisplay){

		String nextPage;
		HTTPReply rep = new HTTPReply();

		if (identification(log, pass) == 2)  {
			System.out.println("Wrong password");
			nextPage = rep.getLogIn("Wrong password", false, " ");
		}
		else if (identification(log, pass) == 1) {
			System.out.println("Wrong login");
			nextPage = rep.getLogIn("Wrong login", false, " ");
		}
		else { // ok
			Cookies cook = new Cookies();
			String compl = cook.setCookie(log, cook.getCookie(log));	
			nextPage = rep.getViewPosts(messagesToDisplay);
		}
		return nextPage;
	}



}
