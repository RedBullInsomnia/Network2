
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

		if (cook.containsKey(key)){ // A cookie already exists
			return null; // return cook.get(key);
		}

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




}