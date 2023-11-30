package cn.argento.askia.tcp.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 如果需要保持连接过程（聊天室等等），请使用available()方法来处理读！否则可能会卡死
 * 参考：https://blog.csdn.net/weixin_44398687/article/details/110007855
 */
public class NIOEchoKeepAliveServer {
    private final int SERVER_PORT = 9999;
    private ServerSocketChannel serverSocketChannel;
    private SocketAddress socketAddress;
    private int waitCount = 0;

    public NIOEchoKeepAliveServer() throws IOException {
        // 可以链式调用
        serverSocketChannel = ServerSocketChannel.open();
        socketAddress = new InetSocketAddress(SERVER_PORT);
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(socketAddress);
        //  //SelectableChannel configureBlocking(boolean block) 设置服务器的阻塞模式 true:阻塞(不写默认) false:非阻塞
        System.out.println(serverSocketChannel);
    }

    private void welcome(SocketChannel accept) throws IOException {
        SocketAddress localAddress = accept.getLocalAddress();
        SocketAddress remoteAddress = accept.getRemoteAddress();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = accept.read(buffer);
        String string = new String(buffer.array(), 0, read);
        System.out.println("欢迎用户：" + remoteAddress + "访问：" + localAddress);
        System.out.println("服务器本地地址：" + localAddress);
        System.out.println("客户端地址：" + remoteAddress);
        System.out.println("客户端发送的内容：" + string);
        // 发送连接成功报文
        accept.write(ByteBuffer.wrap("已跟你建立连接".getBytes()));
//        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
//        read = accept.read(byteBuffer);
        System.out.println("已成功连接一个客户端");
    }
    private void waiting() throws InterruptedException {
        System.out.println("暂时没有客户进来，服务端暂停2秒钟");
        Thread.sleep(2000);
    }
    private boolean readLoop(SocketChannel accept) throws IOException, InterruptedException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int read = accept.read(byteBuffer);
        // 对同一个buffer
        // read()方法比较有意思，阻塞IO、如果read()方法返回的实际字节数 < ByteBuffer的容量，则下一次read()将会阻塞
        // 如果等于或者大于，则再次调用read()将会返回0
        // 只有更换新的buffer，再次调用read()才能读取剩余的字节！
        // 这个判断有可能是读到最后一次分片
        if (read > 0){
            byteArrayOutputStream.write(byteBuffer.array(), 0 , read);
            // 清空buffer，否则buffer没有满的话会获取上一次在buffer内的内容

            // 还有数据剩余
            if (read >= byteBuffer.capacity()){
                read = accept.read(byteBuffer);
                System.out.println("再读一次，结果为" + read + "不阻塞,代表可能还有数据！");
            }
        }
        System.out.println(accept.getRemoteAddress() +"说：" + byteArrayOutputStream.toString());
        byteArrayOutputStream.close();
        return true;
    }
    private void writeLoop(SocketChannel accept) throws IOException {
        LocalDateTime now = LocalDateTime.now();
        String re = "在" + now +"收到亲爱的用户" + accept.getRemoteAddress() + "的信息";
        ByteBuffer wrap = ByteBuffer.wrap(re.getBytes());
        accept.write(wrap);
    }


    // 标准NIO流程
    public void startConnecting() throws IOException, InterruptedException {
        System.out.println("开启服务监听循环...");
        AtomicBoolean cycle = new AtomicBoolean(true);
        while(true){
            if (cycle.get()) System.out.println("正在监听，等待连接... x" + waitCount++);
            // accept不会阻塞！
            SocketChannel accept = serverSocketChannel.accept();
            if (accept != null){
//                accept.configureBlocking(false);
                new Thread(()->{
                    try {
                        cycle.set(false);
                        welcome(accept);
                        while(readLoop(accept)){
                            // dosome other works
                            writeLoop(accept);
                        }
                        // exit指令
                        accept.close();
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
            else if (cycle.get()){
                waiting();
                System.out.println();
            }
        }
    }




    public static void main(String[] args) throws IOException, InterruptedException {
        NIOEchoKeepAliveServer NIOEchoKeepAliveServer = new NIOEchoKeepAliveServer();
        NIOEchoKeepAliveServer.startConnecting();
    }
}