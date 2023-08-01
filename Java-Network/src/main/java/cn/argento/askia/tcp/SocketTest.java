package cn.argento.askia.tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

/**
 * This program makes a socket connection to the atomic clock in Boulder, Colorado, and prints 
 * the time that the server sends.
 *
 * Thank you for Mr.Horstmann And Core Java!
 * @version 1.22 2018-03-17
 * @author Cay Horstmann
 * @modifier Askia
 */
public class SocketTest
{
   public static void main(String[] args) throws IOException
   {
      try (Socket s = new Socket("time-a.nist.gov", 13);
           Scanner in = new Scanner(s.getInputStream(), String.valueOf(StandardCharsets.UTF_8)))
      {
         while (in.hasNextLine())
         {
            String line = in.nextLine();
            System.out.println(line);
         }
      }
   }
}
