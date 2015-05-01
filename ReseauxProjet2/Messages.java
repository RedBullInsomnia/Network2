import java.net.*;
import java.io.*;
import java.util.*;

public class Messages {

	public static final int  MAXMESSAGES = 10;
	public static String[] msg;
	public static int size;
	public static int index;

	/*  Constructor  */
	Messages(){

		size = MAXMESSAGES;
		index = 0;
		msg = new String[MAXMESSAGES];
	}

	/*  addMessage  */
	public static void addMessage(String element) {

		if (index == MAXMESSAGES)
			increasedSize();

		msg[index] = new String (element);
		index++;
	}

	/*  getMessages  */
	public static String[] getMessages() {
		return msg;
	}

	/*  increasedsize  */
	public static void increasedSize(){

		size+= MAXMESSAGES;
		//int[] temp = new String[size];
		String[] temp = new String[20]; /////// !!!!!!! A SUPPRIMER

		// Copy
        for (int i=0; i<msg.length; i++)
               temp[i] = msg[i];

        msg = temp;
	}

	/*  getLastIndex  */
	public static int getLastIndex(){
		return index;
	}
}
