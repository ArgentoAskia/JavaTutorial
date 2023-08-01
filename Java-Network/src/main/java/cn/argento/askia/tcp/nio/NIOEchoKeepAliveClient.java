package cn.argento.askia.tcp.nio;


import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.UUID;

/**
 * 如果需要保持连接过程（聊天室等等），请使用available()方法来处理读！否则可能会卡死
 */
public class NIOEchoKeepAliveClient {
    // 客户端中设置configureBlocking会影响read的方式
    // 服务器中设置configureBlocking会影响accept的方式

    public static void main(String[] args) throws Exception {
        SocketChannel open = SocketChannel.open();
        System.out.println(open);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9999);
        System.out.println("客户端开始连接服务器...");
        open.configureBlocking(false);
        boolean connect = open.connect(inetSocketAddress);
        boolean b = open.finishConnect();
        System.out.println(connect);
        System.out.println(b);
        if (b) {
            System.out.println("客户端连接服务器成功,给服务器发送数据,读取服务器回写的数据...");
            //int write(ByteBuffer src) 给服务器发送数据
            ByteBuffer buffer = ByteBuffer.wrap("你好服务器".getBytes());
            System.out.println("容量:" + buffer.capacity());
            System.out.println("索引:" + buffer.position());
            System.out.println("限定:" + buffer.limit());
            open.write(buffer);
            int len = 0;
            // NIO使用了类似全双工的方式，两根管道，一根是服务器写-客户端读，另外一根是服务器读，客户端写，客户端不写数据，服务器端读将会卡住，知道有数据时就会触发！
            // NIO情况下read()方法可能会什么也读不到，这时候可以稍微阻塞下线程
            // 没有到文件尾，实际上处于打开的两个channel不可能会到达文件尾
            while (true) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                // 客户端的read()方法在非阻塞模式下读不到数据会返回0，阻塞模式下会阻塞
                if ((len = open.read(byteBuffer)) > 0){
                    byte[] array = byteBuffer.array();
                    String s = new String(array, 0, len);
                    System.out.println("服务器说了：" + s);
                }
                // 不加这行可能不会打印出"服务器说了：已跟你建立连接"这句话
                Thread.sleep(3000);

                // 使用Scanner类会导致收不到上一次的信息
                // 模拟给服务端发信息
//                String s = UUID.randomUUID().toString();
//                System.out.println("给服务器写信息：" + s);
//                ByteBuffer wrap = ByteBuffer.wrap(s.getBytes());
//                open.write(wrap);
            }
        } else {
            System.out.println("服务器可能未开启或者存在较大网络波动！");
        }

        open.close();
    }
}
