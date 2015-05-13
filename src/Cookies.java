import java.util.*;

public class Cookies {

	private static Hashtable<String, UUID> cook = new Hashtable<String, UUID>();

	/*
	 * getCookie returns the cookie corresponding to a key, if not a new pair
	 * <key, cookie> is created
	 */
	public static UUID getCookie(String key) {

		if (!cook.containsKey(key))
			cook.put(key, randomID()); // Create a new cookie value

		return cook.get(key);
	}

	/*
	 * randomID
	 */
	public static UUID randomID() {

		// UUID class provides a simple mean for generating unique ids
		UUID id = UUID.randomUUID();
		// System.out.println("UUID id: " + id);
		return id;
	}

	/*
	 * setCookie
	 */
	public static String setCookie(String key, UUID value) {

		return "Set-Cookie: " + key + "=" + value + "; path=/\r\n";
	}

	/*
	 * deleteCookie
	 */
	public static String deleteCookie(String key) {

		String deleteCookie = "Set-Cookie: " + key
				+ "=deleted; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT\r\n";
		return deleteCookie;
	}

	public static boolean containsValue(UUID value) {
		
		if (cook.containsValue(value))
			return true;
		
		return false;
	}
}
