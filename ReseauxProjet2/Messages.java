
import java.net.*;
import java.io.*;
import java.util.*;


public class Messages {

	public static final int  MAXMESSAGES = 10;
	public static String[] msg;
	public static int size;
	public static int index;

	/*  Constructor  */
	public messages(){

		size = MAXMESSAGES;
		index = 0;
		msg = new String[size];
	}


	/*  addMessage  */
	public void addMessage(String element) {

		if (index == MAXMESSAGES)
			incresedSize();

		msg[index] = element;
		index++;
	}


	/*  increasedsize  */
	public void increasedSize(){

		size+= MAXMESSAGES;
		int[] temp = new String[size];

		// Copy
        for (int =0;i<msg.length;i++)
               temp[i] = msg[i];

        msg = temp;
	}


	/*  getLastIndex  */
	public int getLastIndex(){
		return index;
	}

}



