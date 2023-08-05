package cn.argento.askia.udp.socket;

import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPEchoServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // 1.创建DatagramSocket对象
        DatagramSocket ds = new DatagramSocket(3000);
        byte[] buf = new byte[1024];
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
            ds.receive(receivePacket);
            byte[] data = receivePacket.getData();
            String string = new String(data, 0, data.length);
            System.out.println("收到来自" + receivePacket.getAddress()+ ":" + receivePacket.getPort() + "的指令..." + string);


            System.out.println("即将发送时间...");
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String nowStr = dateTimeFormatter.format(now);
            // 2.创建一个数据包
            DatagramPacket sendData = new DatagramPacket(nowStr.getBytes(), nowStr.getBytes().length);
//        sendData.setAddress();
//        sendData.setPort();
//        sendData.setData();
//        sendData.setLength();
            sendData.setSocketAddress(receivePacket.getSocketAddress());
            ds.send(sendData);
        }
//        ds.close();
    }
}
