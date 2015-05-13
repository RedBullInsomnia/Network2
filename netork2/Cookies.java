import java.util.*;

public class Cookies {

	private static Hashtable<String, String> cook = new Hashtable<String, String>();

	/*
	 * getCookie returns the cookie corresponding to a key, if not a new pair
	 * <key, cookie> is created
	 */
	public static String getCookie(String key) {

		if (!cook.containsKey(key))
			cook.put(key, randomID()); // Create a new cookie value

		return cook.get(key);
	}

	/*
	 * randomID
	 */
	public static String randomID() {

		// UUID class provides a simple mean for generating unique ids
		UUID id = UUID.randomUUID();
		String IDstring = id.toString();
		return IDstring;
	}

	/*
	 * setCookie
	 */
	public static String setCookie(String key, String value) {

		return "Set-Cookie: SESSID=" + value + "; path=/\r\n";
		//return "Set-Cookie: login=" + value + "; path=/\r\n";
	}

	/*
	 * deleteCookie
	 */
	public static String deleteCookie(String key) {

		String deleteCookie = "Set-Cookie: " + key
				+ "=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT\r\n";
		return deleteCookie;
	}

	public static boolean containsValue(String value) {

		if (cook.containsValue(value))
			return true;
		
		return false;
	}

	public static boolean containsKey(String key) {

		return cook.containsKey(key);
	}	
}
