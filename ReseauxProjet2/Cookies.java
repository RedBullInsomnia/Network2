
import java.net.*;
import java.io.*;
import java.util.*;


public class Cookies {

	private static Hashtable<String,UUID> cook = new Hashtable<String,UUID>();


	/*  Constructor  */
	// cookies(){

	//}


	/*  getCookie */
	public UUID getCookie(String key){

		if (!cook.containsKey(key)) 
			cook.put(key, randomID()); // Created a new cookie value

		return cook.get(key);
	}



	/*  randomID  */
	public UUID randomID(){
		// UUID class provides a simple means for generating unique ids
		UUID id = UUID.randomUUID();
    	//System.out.println("UUID id: " + id);
    	return id;
	}



	/*  setCookie  */
	public String setCookie(String key, UUID value){

		String header = "Set-Cookie: " + key + "=" + value + "; " + "path=/";
		return header;
	}


	// ====>>>>>>>>>>>>>>> NON USED ???
	/*  deleteCookie  */
	public String deleteCookie(){

		String header = "Set-Cookie: " + key + "=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT";
		return header;
	}




}