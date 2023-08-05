package cn.argento.askia.udp.channel;


import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UDPEchoServerChannel {
    public static void main(String[] args) throws Exception{
        // 1.创建DatagramSocket对象
        DatagramChannel serverChannel = DatagramChannel.open();
        serverChannel.configureBlocking(false);
        InetSocketAddress serverAddress = new InetSocketAddress(5823);
        serverChannel.bind(serverAddress);
        int count = 0;
        while(true){
            // 接收数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            // 因为配置了非阻塞型IO、所以receive()方法只有准备好了才返回数据，否则byteBuffer立即返回null
            // 这种方式可以让代码不阻塞在receive()方法中，可以趁着现在做其他活
            InetSocketAddress receiveClient;
            do {
                // 做其他事情
                receiveClient = (InetSocketAddress) serverChannel.receive(byteBuffer);
                count++;
            }while (receiveClient == null);
            System.out.println("经过了" + count + "次循环后，接收到了来自：" + receiveClient +"的消息");
            byte[] array = byteBuffer.array();
            String receiveContext = new String(array, 0, array.length);
            System.out.println("服务端收到来自" + receiveClient.getAddress()+ ":" + receiveClient.getPort() + "的指令：" + receiveContext);

            System.out.println("即将回发时间+指令...");

            // 重新发送数据
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowStr = dateTimeFormatter.format(now);
            ByteBuffer byteBuffer1 = ByteBuffer.wrap(("服务端在时间" + nowStr + "收到客户端指令：" + receiveContext).getBytes());
            int remaining = byteBuffer1.remaining();
            System.out.println(remaining);
            int send = serverChannel.send(byteBuffer1, receiveClient);
            System.out.println(send);
        }
//        ds.close();
    }
}
