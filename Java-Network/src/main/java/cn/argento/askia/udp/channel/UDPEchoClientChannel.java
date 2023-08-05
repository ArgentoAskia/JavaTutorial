package cn.argento.askia.udp.channel;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.Scanner;

public class UDPEchoClientChannel {
    public static void main(String[] args) throws Exception{
        System.out.println("请输入内容，发送给服务器，服务器会讲内容返回给你并携带服务器接收到的时间！");
        DatagramChannel channel = DatagramChannel.open();
//        channel.configureBlocking(false);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String inputStr = scanner.nextLine();
            if (inputStr.equals("exit")){
                System.out.println("bye！bye！");
                break;
            }
            // 发送消息
            byte[] bytes = inputStr.getBytes();
            InetSocketAddress serverAddress = new InetSocketAddress(InetAddress.getLocalHost(),5823);
            ByteBuffer byteBufferSend = ByteBuffer.wrap(bytes);
            int send = channel.send(byteBufferSend, serverAddress);
            System.out.println("客户端发送：" + inputStr + ", 已发送：" + send + "个字节！");

            // 接收消息
            ByteBuffer byteBufferReceive = ByteBuffer.allocate(1024);
            // 直接接收就ok，因为发送给服务器后，服务其可以调用receive()方法获取目标IP和端口！
            SocketAddress receive = channel.receive(byteBufferReceive);
            System.out.println("服务器地址是：" + receive);
            byte[] array = byteBufferReceive.array();
            String string = new String(array, 0, array.length);
            System.out.println("服务器说：" + string);
            System.out.println("==============================");
        }
        channel.close();
    }
}
