
import java.net.*;
import java.io.*;
import java.lang.Thread;

/**
* Class Worker : 
*   This class interacts with the user (in the client application)
*   and communicate with the server
*
*/
public class Worker extends Thread{
    
    Socket s;
    private int NumWorker;
    private String request = "", bufferString = "";
    private String reply = "";
    private static int sizeBuffer = 64;
    private int signIn;

    //private Sessions user;
    String log = null, pass = null;


    /*  Constructor  */
    Worker(Socket _s, int nw) {
        s=_s;
        NumWorker = nw; 
        signIn = 1;
        //user = new Sessions(_s); 
    }

    
    /**   Run method  **/
    @Override
    public void run() {

        try{
            // Get socket writing and reading streams
            OutputStream out = s.getOutputStream();
            InputStream in = s.getInputStream();
        
while(true){
            // Get HTTP request from browser
            HTTPRequest h = new HTTPRequest(s);
            // Check validity of request
            if (h.checkRequest() != "200 OK"){
                // BAD REQUEST ==>>> TO MANAGE
                System.out.println("BAD REQUEST : " + h.checkRequest());
            }


            // STEP 1 : Client authentification ==>>> on arrive ici quelque soit la requete
             HTTPReply rep = new HTTPReply();
             String f = rep.getForm();
             out.write(f.getBytes(), 0, (f.getBytes()).length);
             out.flush();
             // La reponse recue est du type : login=marine&pass=test


             // STEP 2 : Reception login
             HTTPRequest log = new HTTPRequest(s);
             String logreq = log.getRequest();
             String logheader = log.getHeader();
             System.out.println("log req : " + logreq);
             System.out.println("log header : " + logheader);


                    if (signIn == 1){
                        // VERIFIER SI VALABLE => Si oui, signIn = 0
                        //                          Sinon : boucler
                    }



                    // Check keep alive
                    if (!request.contains("Connection: keep-alive")) { 
                        System.out.println("Close worker number : " + NumWorker);
 //                       break;
                    }
                    
                    // Additional time
                    if (!s.getKeepAlive()){
                        s.setSoTimeout(5000);
                        System.out.println("Asked to be kept alive");
                        s.setKeepAlive(true);
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
