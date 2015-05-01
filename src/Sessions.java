import java.util.Hashtable;

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
	}

	/*
	 * Constructor 
	 */
	public Sessions() {

	}

	/*
	 * Identification
	 * Tests a pair of login and pass
	 * Return 0 if ok
	 * 		  1 if wrong login
	 *        2 if wrong password
	 */
	public int Identification(String login, String pass) {

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
}
