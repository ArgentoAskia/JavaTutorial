package cn.argento.askia;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class InetSocketAddressDemo {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(localHost, 2000);
        InetSocketAddress inetSocketAddress2 = new InetSocketAddress(loopbackAddress, 3000);
        
        InetAddress address = inetSocketAddress.getAddress();
        String hostName = inetSocketAddress.getHostName();
        String hostString = inetSocketAddress.getHostString();
        int port = inetSocketAddress.getPort();
        System.out.println(address);
        System.out.println(hostName);
        System.out.println(hostString);
        System.out.println(port);
        System.out.println();

        InetAddress address2 = inetSocketAddress2.getAddress();
        String hostName2 = inetSocketAddress2.getHostName();
        String hostString2 = inetSocketAddress2.getHostString();
        int port2 = inetSocketAddress2.getPort();
        System.out.println(address2);
        System.out.println(hostName2);
        System.out.println(hostString2);
        System.out.println(port2);
        System.out.println();
        
        boolean unresolved = inetSocketAddress.isUnresolved();
        boolean unresolved2 = inetSocketAddress2.isUnresolved();
        System.out.println(unresolved);
        System.out.println(unresolved2);
        
    }
}
