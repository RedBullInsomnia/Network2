import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Thread;

/**
* Class Worker :
*   This class interacts with the user (in the client application)
*   and communicates with the server
*
*/
public class Worker extends Thread{

    Socket s;
    private int NumWorker;

    // format reponse
    HTTPReply rep;
    // Session user
    private Sessions user;

    /*  Constructor  */
    Worker(Socket s, int nw) {
        this.s = s;
        NumWorker = nw;
        rep = new HTTPReply();
        user = new Sessions();
    }

    /**   Run method  **/
    @Override
    public void run() {

        try{
            // Get socket writing and reading streams
            OutputStream out = s.getOutputStream();
            InputStream in = s.getInputStream();

            // Read HTTP request from browser
            HTTPRequest req = new HTTPRequest(s);

            // Check validity of request
            if (req.checkRequest() != "200 OK"){
                System.out.println("BAD REQUEST : " + req.checkRequest());
            }

            // New test : GET
            if ( req.getMethod().equals("GET") )
            {
                if ( req.getURL().equals("/") || req.getURL().equals("/identification.html") )
                {
                    String f = rep.getLogIn(" ", false, " ");
                    out.write(f.getBytes(), 0, (f.getBytes()).length);
                    out.flush();
                }
                else if ( req.getURL().equals("/viewPosts.html") )
                {

                }
                else if ( req.getURL().equals("/postMessage.html") )
                {
                    String f = rep.getPostMessage();
                    out.write(f.getBytes(), 0, (f.getBytes()).length);
                    out.flush();
                }
                else
                {
                    System.out.println("Page not implemented");
                    String f = rep.getNotImplemented();
                    out.write(f.getBytes(), 0, (f.getBytes()).length);
                    out.flush();
                }
            }

            // POST
            if (req.getMethod().equals("POST")) {

                if ( req.getURL().equals("/viewPosts.html") )
                {
                    // Check account :
                        if ( user.identification(req.getLog(), req.getPass()) == 2 )
                        {
                            System.out.println("Wrong password");
                            String f = rep.getLogIn("Wrong password", false, " ");
                            out.write(f.getBytes(), 0, (f.getBytes()).length);
                            out.flush();
                        }
                        else if ( user.identification(req.getLog(), req.getPass()) == 3 )
                        {
                            System.out.println("Wrong login");
                            String f = rep.getLogIn("Wrong login", false, " ");
                            out.write(f.getBytes(), 0, (f.getBytes()).length);
                            out.flush();
                        }
                        else { // ok
                            System.out.println("It's OK ");
                            // Cookie
                            Cookies cook = new Cookies();
                            String compl = cook.setCookie(req.getLog(), cook.getCookie(req.getLog()));
                            // afficher la page suivante

                            String f = rep.getViewPosts(req.getMessages());
                            //System.out.println("la reponse est : " +f);
                            out.write(f.getBytes(), 0, (f.getBytes()).length);
                            out.flush();
                        }
                }
                else if ( req.getURL().equals("/postMessage.html") )
                {

                }
            }
        }
        catch (SocketTimeoutException e1) {
            System.out.println("Socket Timeout (worker : " + NumWorker + ") " + e1.getMessage());
        }
        catch(IOException e2){
            System.err.println("Error in/out " + e2.getMessage());
            e2.printStackTrace();
        }
        catch(Exception e3){
            System.err.println("Error in worker : " + NumWorker);
            e3.printStackTrace();
        }
       // Close the connections and streams
        finally {
             try{
                s.close();
            }
            catch (IOException e) {
                System.err.println("Socket cannot close" + e.getMessage());
            }
        }
    }
}
