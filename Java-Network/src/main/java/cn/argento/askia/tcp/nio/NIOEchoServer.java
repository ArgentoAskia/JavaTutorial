package cn.argento.askia.tcp.nio;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * NIO编程的一大爽处就是accept()可以不阻塞！
 * 并且使用channel来进行交流而不是基本的IO
 * 使用Buffer类来交流数据
 *
 *
 *
 *
 */
public class NIOEchoServer {
    /**
     * 核心是下面这两个类：
     * ServerSocketChannel
     * SocketChannel
     *
     * static ServerSocketChannel open() 		            // 打开服务器插槽通道
     * ServerSocketChannel bind(SocketAddress local) 		// 给服务器绑定指定的端口号
     * SocketChannel accept() 		                        // 监听客户端的请求
     * SelectableChannel configureBlocking(boolean block) 	// 设置服务器的阻塞模式 true：阻塞(不写默认) false：非阻塞
     *
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        int i = 0;
//1.使用open方法获取ServerSocketChannel对象
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2.使用ServerSocketChannel对象中的方法bind给服务器绑定指定的端口号
        serverSocketChannel.bind(new InetSocketAddress(8888));

        //SelectableChannel configureBlocking(boolean block) 设置服务器的阻塞模式 true:阻塞(不写默认) false:非阻塞
        serverSocketChannel.configureBlocking(false);

        //轮询监听客户端的请求 ==> 死循环一直执行,监听客户端
        while (true){
            //3.使用ServerSocketChannel对象中的方法accept监听客户端的请求
            System.out.println("服务器等待客户端的连接..." + i++);
            SocketChannel socketChannel = serverSocketChannel.accept();//accpet:非阻塞 不会等待客户端请求

            //对客户端SocketChannel对象进行一个非空判断,没有客户端连接服务器,accpet方法返回null
            if(socketChannel != null){
                System.out.println("有客户端连接服务器,服务器读取客户端发送的数据,给客户端回写数据...");

                //int read(ByteBuffer dst) 读取客户端发送的数据
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int len = socketChannel.read(buffer);
                String msg = new String(buffer.array(), 0, len);
                System.out.println("服务器读取客户端发送的数据:" + msg);

                //int write(ByteBuffer src) 服务器给客户端发送数据
                socketChannel.write(ByteBuffer.wrap("收到,谢谢".getBytes()));

                System.out.println("服务器读写数据完成,结束轮询...");
                break;//结束轮询
            }else{
                System.out.println("没有客户端连接服务器,休息2秒钟,干点其他事情,在继续下一次轮询监听客户端连接...");
                System.out.println();
                Thread.sleep(2000);
            }
        }

        //释放资源
        serverSocketChannel.close();
    }
}
