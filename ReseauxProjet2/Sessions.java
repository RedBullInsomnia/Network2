
//import javax.servlet.http.Cookie;
import javax.servlet.*;
import java.util.Hashtable;



public class Sessions extends HttpServlet {

	Socket s;
	// Manage streams
	private InStream receiver;
	private OutStream sender;

	// Database of users
	private static Hashtable<String,String> accounts = new Hashtable<String,String>();
	// Credentials are hard-coded values in the server memory :
	accounts.put("Leduc","mypass1");
	accounts.put("Hiard","itagpw?");
	accounts.put("Kurose","&Ross");



	/* Constructor */
	public Sessions(Socket _s) {
		
		try {  s = _s;	} 
		catch (IOException e) {}

		receiver = new InStream(s);
		sender = new OutStream(s);
	
	}


	/* authentification */
	public int authentification() {

			String log = null, pass = null;

			try{
				 sender.sendString("Provide your login and password");
				 // Login
				 sender.sendString("Login :");
				 log = receiver.readString();

				 // Password
				 sender.sendString("Password :");
				 pass = receiver.readString();
			}
			catch(){
				// To manage
			}

			// Check validity
			int auth = validity(log, pass);
			if(auth) {
				sender.sendString("Authentification failed");
			}
			return auth;
	}


	/* validity */
	public int validity(String log, String pass){

		// ID ok
		if (accounts.get(log) == pass){
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


	/* cookie */
/*	public void getCookie (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
	// Recupere la session et en cree une si elle n'existe pas
	HttpSession session = request.getSession(true);    
	...
	// Ecrit la reponse 
	out = response.getWriter();   
	...  
	} 
*/





} 