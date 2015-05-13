import java.util.ArrayList;

/**
 * Class Messages
 * 
 * @author hwk, mcz
 *
 */
public class Messages {

	public static final int initialCapacity = 20;
	public static ArrayList<String> messages = new ArrayList<String>(initialCapacity);
	public static int length = 0;

	/*
	 * Constructor
	 */
	private Messages() { }

	/*
	 * AddMessage adds a message
	 */
	public static void addMessage(String element) {

		messages.add(element);
		length++;
	}

	/* getMessages */
	public static ArrayList<String> getMessages() {
		return messages;
	}

	/* getLastIndex */
	public static int getLastMessage() {
		return length;
	}

	/* isEmpty */
	public static boolean isEmpty() {
		return messages.isEmpty();
	}


}