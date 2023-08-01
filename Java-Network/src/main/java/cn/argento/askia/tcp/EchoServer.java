package cn.argento.askia.tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

/**
 * This program implements a simple server that listens to port 8189 and echoes back all
 * client input.
 * @version 1.22 2018-03-17
 * @author Cay Horstmann
 */
public class EchoServer
{
   public static void main(String[] args) throws IOException
   {
      // establish server socket
      try (ServerSocket s = new ServerSocket(8189))
      {
         // wait for client connection
         try (Socket incoming = s.accept())
         {
            InputStream inStream = incoming.getInputStream();
            OutputStream outStream = incoming.getOutputStream();
   
            try (Scanner in = new Scanner(inStream, String.valueOf(StandardCharsets.UTF_8)))
            {
               PrintWriter out = new PrintWriter(
                  new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                  true /* autoFlush */);
      
               out.println("Hello! Enter BYE to exit.");
      
               // echo client input
               boolean done = false;
               while (!done && in.hasNextLine())
               {
                  String line = in.nextLine();
                  out.println("Echo: " + line);
                  if (line.trim().equals("BYE")) done = true;
               }
            }
         }
      }
   }
}
