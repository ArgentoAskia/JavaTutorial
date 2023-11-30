package cn.argento.askia;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * 该类将Host和IP地址封装再一块形成InetAddress,InetAddress的host输入是可以经过确实的，
 * 也就是说如果输入的是域名，他会自动解析出对应的IP
 */
public class InetAddressDemo {
    public static void main(String[] args) throws IOException {
        // 1. 使用静态方法，提供Host和IpAddress来创建一个InetAddress对象
        // 其中IpAddress使用字节表示
        byte[] addressBytes = {(byte)223, (byte)220, 0, 101};
        InetAddress address = InetAddress.getByAddress(addressBytes);
        // 或者提供Host和IP地址来进行创建，注意这种创建方式不会进行DNS解析！因此IP和主机名可能是Fake的！
        InetAddress addressWithHost = InetAddress.getByAddress("Askia",addressBytes);
        System.out.println("通过byte[]创建IP：" + address);
        System.out.println("通过byte[]和主机名来创建：" + addressWithHost);
        // 获取本机IP，注意和LoopbackAddress的区别
        InetAddress localHost = InetAddress.getLocalHost();
        InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
        System.out.println("localhost:" + localHost);
        System.out.println("LoopbackAddress:" + loopbackAddress);
        // 通过主机名、域名来创建InetAddress
        // 传递域名会解析到对应的IP地址！
        InetAddress host = InetAddress.getByName("www.baidu.com");
        InetAddress[] allByName = InetAddress.getAllByName("www.baidu.com");
        InetAddress host2 = InetAddress.getByName("www.argentoaskia.cn");
        InetAddress host3 = InetAddress.getByName(localHost.getHostName());
        System.out.println("百度域名解析出来的IP地址：" + Arrays.toString(allByName));
        System.out.println("其中一个：" + host);
        System.out.println(host2);
        System.out.println(host3);
        System.out.println();

        // 2.使用getXXX()方法获取IP和Host
        // 获取IP地址
        byte[] address1 = host2.getAddress();
        // 获取原始主机名
        String canonicalHostName = host2.getCanonicalHostName();
        // 获取域名
        String hostName = host2.getHostName();
        // 获取IP地址
        String hostAddress = host2.getHostAddress();
        System.out.println(Arrays.toString(address1));
        System.out.println(canonicalHostName);
        System.out.println(hostName);
        System.out.println(hostAddress);
        System.out.println();


        // 3.判别方法
        // 是否是localhost
        boolean loopbackAddress1 = host2.isLoopbackAddress();
        // 是否是广播地址
        boolean multicastAddress = addressWithHost.isMulticastAddress();
        // 地址是否可达？
        boolean reachable = host2.isReachable(3000);
        System.out.println(loopbackAddress1);
        System.out.println(multicastAddress);
        System.out.println(reachable);


    }
}
