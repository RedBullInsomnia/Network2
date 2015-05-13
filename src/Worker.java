import java.io.*;
import java.net.*;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

/**
 * 
 * @author hwk, mcz
 *
 */
public class Worker extends Thread {

	private static int numAlive = 0;
	private Socket s;
	private int num;
	private int echos;
	private int bufferSize = 512;
	private int chunkSize = 100;
	private String isLog;
	private boolean sendCookie;
	
	private String answerCode;
	private String answerBody;
	
	// Server and content info
	private static String server = "Host: ChatServer\r\n";
	private static String contentType = "Content-Type: text/html, charset=UTF-8\r\n";
	private static String chunked = "Transfer-Encoding: chunked\r\n";
	private static String encoding = "Content-Encoding: gzip\r\n";

	Worker(Socket _s, int _n) {
		s = _s;
		num = _n;
		numAlive++;
		echos = 0;
		answerCode = "HTTP/1.1 400 Bad Request\r\n";
		answerBody = "";
		isLog = "";
		sendCookie = false;
	}

	@Override
	public void run() {
		String request = "", buffer = "", answer = "";
		byte msg[] = new byte[bufferSize];
		int len = 0;
		boolean timeout = false;

		// wait for request
		System.out.println("Worker " + num + " executed");
		try {
			s.setSoTimeout(1000);
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();

			while (true) {
				// Read incoming bytes
				len = in.read(msg);
				if (len <= 0)
					break;

				// Add incoming to buffer
				buffer += new String(msg, 0, len);

				// Bound length
				if (buffer.length() >= 8096)
					break;

				//System.out.println("Buffer :\r\n" + buffer + "\r\n");
				// If we received the entire request, we can parse it
				if (buffer.contains("\r\n\r\n")) {
					request = buffer.substring(0,
							buffer.indexOf("\r\n\r\n") + 4);

					// Reset buffer for future messages
					if (!buffer.endsWith("\r\n\r\n")) {
						buffer = buffer
								.substring(buffer.indexOf("\r\n\r\n") + 4,
										buffer.length());
					} else {
						buffer = "";
					}

					// Parse request
					
					int bodyLength = parseHeader(request);
					if (bodyLength > 0)
					{
						while (buffer.length() < bodyLength)
						{
							len = in.read(msg);
							buffer += new String(msg, 0, len);
						}
						String body = buffer.substring(0, bodyLength);
						if (buffer.length() > bodyLength)
							buffer = buffer.substring(bodyLength, buffer.length());
						else
							buffer = "";
						//boolean logged = 
						parseBody(body);
						//if (logged)
						//	answerBody = HTTPRequest.getPostsPage(1, 10);
						//else
						//	answerBody = HTTPRequest.getLoginPage(1);
					}
					// Send back answer
					
					answer = answerCode;// + getStatusLine();// + answerBody;
					byte answerBytes[] = answerBody.getBytes();
					if (request.contains("Accept-Encoding: gzip") || request.contains("Accept-Encoding: deflate, gzip"))
					{
						answerBytes = compress(answerBytes);
						answer += getStatusLine(true);
					}
					else
					{
						answer += getStatusLine(false);
					}
					byte codeBytes[] = answer.getBytes();
					int length = answerBytes.length;
					int i = 0;
					out.write(codeBytes);
					out.flush();
					for (; i < length/chunkSize; i++)
					{
						out.write(Integer.toHexString(chunkSize).getBytes("UTF-8"));
						out.write("\r\n".getBytes());
						out.write(answerBytes, i*chunkSize, chunkSize);
						out.write("\r\n".getBytes());
						out.flush();
					}
					int modulo = length % chunkSize;
					if (modulo > 0)
					{
						out.write(Integer.toHexString(modulo).getBytes("UTF-8"));
						out.write("\r\n".getBytes());
						out.write(answerBytes, i*chunkSize, modulo);
						out.write("\r\n".getBytes());
						out.flush();
					}
					out.write("0\r\n\r\n".getBytes());
					out.flush();
					
					//System.out.println(answer);
					//System.out.println(answerBody);
					ackAnswer();

					// Can we close the connection or not ?
					if (request.contains("Connection: keep-alive")) {
						if (!s.getKeepAlive()) {
							s.setKeepAlive(true);
							s.setSoTimeout(5000);
						}
					} else {
						break;
					}
				}
			}
			s.close(); // acknowledge end of connection

		} catch (SocketTimeoutException to) {
			timeout = true;
			try {
				s.close();
			} catch (IOException io) {
				System.err.println("Error on socket: " + io.getMessage());
			}
		} catch (IOException io) {
			System.err.println("Error on socket: " + io.getMessage());
		} catch (Exception e) {
			System.err.println("Houston we have a problem: " + e.getMessage());
			e.printStackTrace();
		}

		ackClose(timeout);
	}
	
	public String getStatusLine(boolean encode) {
		String status = server + contentType + chunked;
		if (true == encode)
			status += encoding;
		
		if (true == sendCookie)
		{
			status += Cookies.setCookie(isLog, Cookies.getCookie(isLog));
			sendCookie = false;
		}
		System.out.println(status);
		status += "\r\n";
		return status;
	}

	/*
	 * Print acknowledgement to terminal
	 */
	public void ackAnswer() {
		echos++;
		System.out
				.println("Worker " + num + ": treated " + echos + " requests");
	}

	/*
	 * Print that worker finished its work and update numAlive
	 */
	public void ackClose(boolean timeout) {
		if (timeout)
			System.out.println("Worker " + num + " closed : timeout");
		else
			System.out.println("Worker " + num + " closed");
		numAlive--;
		System.out.println(numAlive + " workers still alive");
	}
	
	public int parseHeader(String s)
	{
		String splits[], splitsContent[];
		int bodyLength = 0;

		if (s == null || s.length() <= 0)
			answerCode = "HTTP/1.1 400 Bad Request\r\n";

		String str = s.substring(0, s.indexOf("\r\n"));
		splits = str.split("\\s");
		if (splits.length != 3 || !s.contains("Host: "))
			answerCode = "HTTP/1.1 400 Bad Request\r\n";

		if (splits[0].equals("PUT") || splits[0].equals("OPTIONS")
				|| splits[0].equals("DELETE") || splits[0].equals("TRACE")
				|| splits[0].equals("CONNECT") || splits[0].equals("HEAD"))
			answerCode = "HTTP/1.1 501 Not Implemented\r\n";

		if (!(splits[0].equals("GET") || splits[0].equals("POST"))
				|| !splits[1].startsWith("/") || !splits[2].equals("HTTP/1.1"))
			answerCode = "HTTP/1.1 400 Bad Request\r\n";
		
		if (splits[0].equals("POST"))
		{
			if (s.contains("Content-Length: "))
			{
				str = s.substring(s.indexOf("Content-Length: "));// + 16, s.indexOf("\r\n"));
				splitsContent = str.split("\r\n", 1);
				str = splitsContent[0].substring(16, splitsContent[0].indexOf("\r\n"));
				bodyLength =  Integer.parseInt(str);
			}
			else
			{
				answerCode = "HTTP/1.1 400 Bad Request\r\n";
				answerBody = "";
			}
		}
		
		/* Get Pages */
		if (splits[0].equals("GET"))
		{
			if(splits[1].equals("/") || splits[1].equals("/identification.html"))
			{
				answerCode = "HTTP/1.1 200 OK\r\n";
				answerBody = HTTPRequest.getLoginPage(0);
			}
			else if (splits[1].startsWith("/viewPosts.html"))
			{
				int page = 1;
				int maxPosts = 10;
				try {
					URL url = new URL("http://localhost:8001" + splits[1]);
					String query = url.getQuery();
					answerBody = HTTPRequest.getPostsPage(page, maxPosts);
				} catch (MalformedURLException e) {
					answerBody = HTTPRequest.errorPage();
					e.printStackTrace();
				}
				answerCode = "HTTP/1.1 200 OK\r\n";
			}
			else if (splits[1].equals("/postMessage.html"))
				answerBody = HTTPRequest.getPostsMessage();
			else
			{
				answerBody = "";
				answerCode = "HTTP/1.1 404 Page Not Found\r\n";
			}
			return 0;
		}
		else if (splits[0].equals("POST"))
		{
			if (splits[1].startsWith("/loginAction"))
			{
				answerBody = "";
				answerCode = "HTTP/1.1 303 See Other\r\n"
						+ "Location: http://localhost:8001/viewPosts.html\r\n";
				return bodyLength;
			}
			
		}

		answerCode = "HTTP/1.1 200 OK\r\n";
		
		// Check is there is a password
		boolean cookieOK = false;
		if (s.contains("Cookie: "))
		{
			String lineCookie = s.substring(s.indexOf("Cookie:"), s.lastIndexOf("\r\n"));
			
			int cookieStart = lineCookie.indexOf("login=");
			if (cookieStart != -1)
			{
				int valueEnd = lineCookie.indexOf(";");
				if (valueEnd == -1)
					valueEnd = lineCookie.length();
				String substr = lineCookie.substring(cookieStart, valueEnd);
				cookieOK = Cookies.containsValue(UUID.fromString(substr));
				sendCookie = true;
			}
		}
		if (false == cookieOK)
		{
			answerBody = "";
			answerCode = "HTTP/1.1 303 See Other\r\n" + "Location: http://localhost:8001/identification.html\r\n";
			return 0;
		}
		
		if (splits[1].equals("/viewPosts.html"))
		{
			answerBody = HTTPRequest.getPostsPage(1, 10);
		}
		
		
		
		return bodyLength;
	}
	
	public boolean parseBody(String body) {

		try {
			body = URLDecoder.decode(body, "UTF-8");
			Pattern pat = Pattern.compile("=");
			String login = null, pass = null;
			String test[] = null;
			test = pat.split(body);
	
			// Login and password
			if (test[0].equals("login")) {
				// Computation of regex
				Pattern p = Pattern.compile("&");
				String extract1[] = null;
				extract1 = p.split(body); // extract1[0] = "login=XXX" - extract1[1]
											// = "pass=XXX"
				// login
				Pattern p2 = Pattern.compile("=");
				String extract2[] = null;
				if (extract1 != null)
				{
					extract2 = p2.split(extract1[0]);
					if (extract2.length > 1)
						login = extract2[1];
				}
	
				// password
				Pattern p3 = Pattern.compile("=");
				String extract3[] = null;
				if (extract1.length > 1)
				{
					extract3 = p3.split(extract1[1]);
					if (extract3.length > 1)
						pass = extract3[1];
				}
				
				Sessions user = new Sessions();
				if (user.Identification(login, pass) == 0)
				{
					System.out.println("Identifaction :");
					isLog = login;
					Cookies.getCookie(login);
					return true;
				}
				else
				{
					return false;
				}
			}
			else if (test[0].equals("comment"))
			{
				if (test.length > 1)
					Messages.addMessage(test[1]);
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

	 	return true;
	}

	 public byte[] compress(byte[] content){
	        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	        try{
	            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	            gzipOutputStream.write(content);
	            gzipOutputStream.close();
	        } catch(IOException e){
	            throw new RuntimeException(e);
	        }
	        return byteArrayOutputStream.toByteArray();
	    }

}
