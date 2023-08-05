package cn.argento.askia.tcp.nio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 多路复用客户端
 */
public class NIOEchoClient {
    public static void main(String[] args) {
        //客户端轮询连接服务器，连接成功，结束轮询
        while (true) {
            try {
                //1.使用open方法获取客户端SocketChannel对象
                SocketChannel socketChannel = SocketChannel.open();
                System.out.println("客户端开始连接服务器...");

                //2.使用SocketChannel对象中的方法connect根据服务器的ip地址和端口号连接服务器
                boolean b = socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));
                System.out.println(b);
                System.out.println("客户端连接服务器成功,给服务器发送数据,读取服务器回写的数据...");
                //int write(ByteBuffer src) 给服务器发送数据
                ByteBuffer buffer = ByteBuffer.wrap("你好服务器".getBytes());
                System.out.println("容量:" + buffer.capacity());
                System.out.println("索引:" + buffer.position());
                System.out.println("限定:" + buffer.limit());
                socketChannel.write(buffer);

                //int read(ByteBuffer dst) 读取服务器回写的数据
                ByteBuffer buffer2 = ByteBuffer.allocate(1024);
                int len = socketChannel.read(buffer2);
                //len:读取的有效字节个数
                //System.out.println("客户端读取服务器发送的数据:" + new String(buffer2.array(), 0, len));

                buffer2.flip();//缩小limit的范围: position=0 limit=position(读取的有效字节个数)
                System.out.println("客户端读取服务器发送的数据:" + new String(buffer2.array(), 0, buffer2.limit()));

                System.out.println("客户端读写数据完毕,结束轮询...");
                break;
            } catch (IOException e) {
                System.out.println("客户端connect方法连接服务器失败,休息2秒钟,干点其他事情,在继续下一次连接服务器...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
