import java.util.Hashtable;
import java.net.*;
import java.io.*;
import java.lang.*;

public class Sessions {

	// Database of users
	private static Hashtable<String,String> accounts = new Hashtable<String,String>();
	// Credentials are hard-coded values in the server memory :
	static {
		accounts.put("Leduc","mypass1");
		accounts.put("Hiard","itagpw?");
		accounts.put("Kurose","&Ross");
	}

	/* Constructor */
	public Sessions() {

	}

	/* identification */
	public int identification(String log, String pass) {

		// ID ok
		if (accounts.get(log) != null && accounts.get(log).equals(pass)){
			//Cookie ID = new Cookie(log, pas); // Comment associer le meme cookie pour le meme navigateur ?
			//getCookie(?, ?);

			return 0;
		}
		// Wrong login
		else if(accounts.get(log) == null){
			return 3;
		}
		// Wrong password
		else {
			return 2;
		}
	}
}
