package cn.argento.askia.udp.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class UDPEchoClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                try {
                    client();
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
    private static void client() throws IOException, InterruptedException{
        // 1.客户端创建一个Socket对象，不指定绑定的端口的话，代码会自动寻找可用端口
        DatagramSocket ds = new DatagramSocket();
        String s = "please give me a time";
        // 2.创建一个数据包，包括发送的内容，发送的地址，端口
        DatagramPacket dp = new DatagramPacket(s.getBytes(),s.getBytes().length);
        dp.setAddress(InetAddress.getLocalHost());
        dp.setPort(3000);
        System.out.println("1.给服务器发送一个请求时间指令");
        // 3.Socket发送
        ds.send(dp);
        System.out.println("2.发送成功...");
        Thread.sleep(100);
        System.out.println("3.接收服务器返回的时间...");
        // 4.创建一个数据包，用于接口服务器返回时间
        byte[] buf = new byte[1024];
        DatagramPacket dp2 = new DatagramPacket(buf, buf.length);
        ds.receive(dp2);
        System.out.println("接收到的报文：" + Arrays.toString(dp2.getData()) + "长度：" + dp2.getLength());
        System.out.println("接收到的报文内容：" + new String(dp2.getData(),0, dp2.getLength()));
        ds.close();
    }
}
