import java.awt.*;
import java.io.*;
import java.net.*;


class TCPServer {
   public static void main(String[] args) throws IOException {


       // Variables for setting up connection and communication
       Socket Socket = null; // socket to connect with ServerRouter
       PrintWriter out = null; // for writing to ServerRouter
       BufferedReader in = null; // for reading form ServerRouter
       InetAddress addr = InetAddress.getLocalHost();
       String host = addr.getHostAddress(); // Server machine's IP
       String routerName = "34.69.245.2"; // ServerRouter host name
       int SockNum = 5555; // port number


       // Tries to connect to the ServerRouter
       try {
           Socket = new Socket(routerName, SockNum);
           out = new PrintWriter(Socket.getOutputStream(), true);
           in = new BufferedReader(new InputStreamReader(Socket.getInputStream()));
       }
       catch (UnknownHostException e) {
           System.err.println("Don't know about router: " + routerName);
           System.exit(1);
       }
       catch (IOException e) {
           System.err.println("Couldn't get I/O for the connection to: " + routerName);
           System.exit(1);
       }


       // Variables for message passing
       String fromServer; // messages sent to ServerRouter
       String fromClient; // messages received from ServerRouter
       String address ="172.17.0.1"; // destination IP (Client)


       // Communication process (initial sends/receives)
       out.println(address);// initial send (IP of the destination Client)
       fromClient = in.readLine();// initial receive from router (verification of connection)
       System.out.println("ServerRouter: " + fromClient);
       FileWriter logger = new FileWriter("Log.txt");


       byte[] catcher = new byte[4096];
       File tempfile = File.createTempFile("temp", "mp4");
       FileOutputStream fileOutputStream = new FileOutputStream(tempfile);
       tempfile.deleteOnExit();
       while((catcher= in.readLine().getBytes())!=null){
           fileOutputStream.write(catcher);
       }
       Desktop.getDesktop().open(tempfile);


       // closing connections
       out.close();
       in.close();
       Socket.close();
   }
}
