package com.company;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TCPClient {
    public static void main(String[] args) throws IOException {

        // Variables for setting up connection and communication
        Socket Socket = null; // socket to connect with ServerRouter
        PrintWriter out = null; // for writing to ServerRouter
        BufferedReader in = null; // for reading form ServerRouter
        InetAddress addr = InetAddress.getLocalHost();
        String host = addr.getHostAddress(); // Client machine's IP
        String routerName = "192.168.1.86"; // ServerRouter host name
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
        String file = "file.mp4";
        //Reader reader = new FileReader(file);
        //BufferedReader fromFile =  new BufferedReader(reader); // reader for the string file
        String fromServer; // messages received from ServerRouter
        String fromUser; // messages sent to ServerRouter
        String address ="192.168.1.186"; // destination IP (Server)
        FileWriter logger = new FileWriter("ClientLog.txt") ;
        File sizer = new File(file);
        double fileSize = sizer.length()/1024;
        logger.write("File Size in KB: "+(int)fileSize);
        long t0, t1, t;
        String medium = "";
        int i = file.lastIndexOf('.');

        // Communication process (initial sends/receives
        out.println(address);// initial send (IP of the destination Server)
        fromServer = in.readLine();//initial receive from router (verification of connection)
        System.out.println("ServerRouter: " + fromServer);
        out.println(host); // Client sends the IP of its machine as initial send
        t0 = System.currentTimeMillis();

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[4096];
        int bytesRead;
        String tempHolder = "";
        while((bytesRead = bis.read(buffer))!=-1){
            out.println(tempHolder = new String(buffer, 0, bytesRead));
            out.flush();
        }

        // closing connections
        out.close();
        in.close();
        Socket.close();
    }
}
