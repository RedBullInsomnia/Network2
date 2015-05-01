import java.util.ArrayList;

/**
 * Class Messages
 * 
 * @author hwk
 *
 */
public class Messages {

	public static final int initialCapacity = 20;
	public static ArrayList<String> messages;
	public static int length;

	/*
	 * Constructor
	 */
	Messages() {

		length = 0;
		messages = new ArrayList<String>(initialCapacity);
	}

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
}
